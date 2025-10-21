package com.bot.trading_monitor.service.interfaces;

import com.bot.trading_monitor.dto.prediction.TradingPredictionResponseDto;

public interface TradingPredictionService {

    TradingPredictionResponseDto getLatestPrediction(Long userId);
}

