package com.bot.trading_monitor.service.impl;

import com.bot.trading_monitor.client.QuantClient;
import com.bot.trading_monitor.dto.prediction.TradingPredictionResponseDto;
import com.bot.trading_monitor.entity.TradingPrediction;
import com.bot.trading_monitor.entity.User;
import com.bot.trading_monitor.repository.TradingPredictionRepository;
import com.bot.trading_monitor.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class TradingPredictionSchedulerService {

    private final QuantClient quantClient;
    private final TradingPredictionRepository tradingPredictionRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedRate = 300000) // 5 minutes in milliseconds
    public void fetchAndSavePredictions() {
        log.info("Starting scheduled prediction fetch");

        List<Long> userIds = userRepository.findAll().stream()
                .map(User::getExternalId)
                .filter(Objects::nonNull)
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
            TradingPredictionResponseDto prediction = quantClient.getPredictionsOnly(userId);

            if (prediction == null) {
                log.warn("No predictions received for user {}", userId);
                return;
            }

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
