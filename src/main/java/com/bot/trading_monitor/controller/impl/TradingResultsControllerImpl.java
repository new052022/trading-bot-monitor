package com.bot.trading_monitor.controller.impl;

import com.bot.trading_monitor.controller.interfaces.TradingResultController;
import com.bot.trading_monitor.dto.TradingPositionsResponseDto;
import com.bot.trading_monitor.dto.prediction.TradingPredictionResponseDto;
import com.bot.trading_monitor.security.SecurityUtils;
import com.bot.trading_monitor.service.interfaces.TradingPositionsService;
import com.bot.trading_monitor.service.interfaces.TradingPredictionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TradingResultsControllerImpl implements TradingResultController {

    private final TradingPositionsService tradingPositionsService;
    private final TradingPredictionService tradingPredictionService;

    @Override
    public ResponseEntity<TradingPositionsResponseDto> getTradingPositions(Long userId) {
        SecurityUtils.validateUserAccess(userId);
        TradingPositionsResponseDto response = tradingPositionsService.geTradingPositions(userId);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<TradingPredictionResponseDto> getPredictions(Long userId) {
        SecurityUtils.validateUserAccess(userId);
        TradingPredictionResponseDto response = tradingPredictionService.getLatestPrediction(userId);
        return ResponseEntity.ok(response);
    }
}
