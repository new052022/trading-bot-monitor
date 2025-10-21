package com.bot.trading_monitor.service.impl;

import com.bot.trading_monitor.client.QuantClient;
import com.bot.trading_monitor.dto.prediction.TradingPredictionResponseDto;
import com.bot.trading_monitor.entity.TradingPrediction;
import com.bot.trading_monitor.repository.TradingPredictionRepository;
import com.bot.trading_monitor.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TradingPredictionSchedulerService {

    private final QuantClient quantClient;
    private final TradingPredictionRepository tradingPredictionRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedRate = 600000) // 10 minutes in milliseconds
    public void fetchAndSavePredictions() {
        log.info("Starting scheduled prediction fetch");

        List<Long> userIds = userRepository.findAll().stream()
                .map(user -> user.getExternalId())
                .filter(externalId -> externalId != null)
                .toList();

        for (Long userId : userIds) {
            try {
                fetchAndSavePredictionForUser(userId);
            } catch (Exception e) {
                log.error("Failed to fetch predictions for user {}: {}", userId, e.getMessage());
            }
        }

        log.info("Completed scheduled prediction fetch");
    }

    private void fetchAndSavePredictionForUser(Long userId) {
        try {
            List<TradingPredictionResponseDto> predictions = quantClient.getPredictionsOnly(userId);

            if (predictions == null || predictions.isEmpty()) {
                log.warn("No predictions received for user {}", userId);
                return;
            }

            // Take the first prediction (most recent)
            TradingPredictionResponseDto prediction = predictions.get(0);

            String predictionJson = objectMapper.writeValueAsString(prediction);

            TradingPrediction entity = TradingPrediction.builder()
                    .userId(userId)
                    .predictionData(predictionJson)
                    .generatedAt(prediction.getGeneratedAt() != null ? prediction.getGeneratedAt() : LocalDateTime.now())
                    .fetchedAt(LocalDateTime.now())
                    .build();

            tradingPredictionRepository.save(entity);
            log.info("Successfully saved prediction for user {}", userId);

        } catch (JsonProcessingException e) {
            log.error("Failed to serialize prediction for user {}: {}", userId, e.getMessage());
        } catch (Exception e) {
            log.error("Error fetching prediction for user {}: {}", userId, e.getMessage());
        }
    }
}

