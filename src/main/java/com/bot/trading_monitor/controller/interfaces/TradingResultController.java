package com.bot.trading_monitor.controller.interfaces;

import com.bot.trading_monitor.dto.TradingPositionsResponseDto;
import com.bot.trading_monitor.dto.prediction.TradingPredictionResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/trading-results")
public interface TradingResultController {

    @GetMapping("/{userId}/positions")
    ResponseEntity<TradingPositionsResponseDto> getTradingPositions(
            @PathVariable Long userId);

    @GetMapping("/{userId}/predictions")
    ResponseEntity<TradingPredictionResponseDto> getPredictions(
            @PathVariable Long userId);
}
