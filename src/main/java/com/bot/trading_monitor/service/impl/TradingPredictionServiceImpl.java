package com.bot.trading_monitor.service.impl;

import com.bot.trading_monitor.dto.prediction.TradingPredictionResponseDto;
import com.bot.trading_monitor.entity.TradingPrediction;
import com.bot.trading_monitor.exception.ResourceNotFoundException;
import com.bot.trading_monitor.repository.TradingPredictionRepository;
import com.bot.trading_monitor.service.interfaces.TradingPredictionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TradingPredictionServiceImpl implements TradingPredictionService {

    private final TradingPredictionRepository tradingPredictionRepository;
    private final ObjectMapper objectMapper;

    @Override
    public TradingPredictionResponseDto getLatestPrediction(Long userId) {
        TradingPrediction prediction = tradingPredictionRepository
                .findTopByUserIdOrderByFetchedAtDesc(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No predictions found for user " + userId));

        try {
            return objectMapper.readValue(
                    prediction.getPredictionData(),
                    TradingPredictionResponseDto.class);
        } catch (JsonProcessingException e) {
            log.error("Failed to deserialize prediction data for user {}: {}", userId, e.getMessage());
            throw new RuntimeException("Failed to parse prediction data", e);
        }
    }
}

