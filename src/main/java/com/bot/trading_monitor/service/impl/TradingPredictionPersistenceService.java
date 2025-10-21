package com.bot.trading_monitor.service.impl;

import com.bot.trading_monitor.dto.prediction.TradingPredictionResponseDto;
import com.bot.trading_monitor.entity.TradingPrediction;
import com.bot.trading_monitor.repository.TradingPredictionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class TradingPredictionPersistenceService {

    private final TradingPredictionRepository tradingPredictionRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public void saveOrUpdatePrediction(Long userId, TradingPredictionResponseDto prediction) throws JsonProcessingException {
        String predictionJson = objectMapper.writeValueAsString(prediction);

        // Delete old predictions for this user before saving new one
        tradingPredictionRepository.deleteByUserId(userId);
        log.debug("Deleted old predictions for user {}", userId);

        TradingPrediction entity = TradingPrediction.builder()
                .userId(userId)
                .predictionData(predictionJson)
                .generatedAt(prediction.getGeneratedAt() != null ? prediction.getGeneratedAt() : LocalDateTime.now())
                .fetchedAt(LocalDateTime.now())
                .build();

        tradingPredictionRepository.save(entity);
        log.info("Successfully saved prediction for user {}", userId);
    }
}

