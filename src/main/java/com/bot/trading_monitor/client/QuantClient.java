package com.bot.trading_monitor.client;

import com.bot.trading_monitor.dto.TradingPositionDtoRequestDto;
import com.bot.trading_monitor.dto.TradingPositionDtoResponseDto;
import com.bot.trading_monitor.dto.TradingSesionRequestDto;
import com.bot.trading_monitor.dto.TradingSesionResponseDto;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

@FeignClient(name = "quant", url = "${quant.url}")
public interface QuantClient {

    List<TradingPositionDtoResponseDto> getTradingPositions(TradingPositionDtoRequestDto request);

    TradingSesionResponseDto getTradingSession(TradingSesionRequestDto request);

}

