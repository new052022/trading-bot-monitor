package com.bot.trading_monitor.client;

import com.bot.trading_monitor.dto.TradingPositionDtoResponseDto;
import com.bot.trading_monitor.dto.TradingSessionResponseDto;
import com.bot.trading_monitor.dto.prediction.TradingPredictionResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "quant", url = "${quant.url}")
public interface QuantClient {

    @GetMapping("/user-trades/{userId}")
    List<TradingPositionDtoResponseDto> getTradingPositions(@PathVariable Long userId);

    @GetMapping("/user-trades/{userId}/predictions")
    List<TradingPredictionResponseDto> getPredictionsOnly(@PathVariable Long userId);

    @GetMapping("/strategy-session/{userId}")
    TradingSessionResponseDto getTradingSession(@PathVariable Long userId);

}

