package com.bot.trading_monitor.service.impl;

import com.bot.trading_monitor.client.QuantClient;
import com.bot.trading_monitor.dto.TradingPositionDtoResponseDto;
import com.bot.trading_monitor.dto.TradingPositionsResponseDto;
import com.bot.trading_monitor.mapper.TradingPositionsMapper;
import com.bot.trading_monitor.service.interfaces.TradingPositionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TradingPositionsServiceImpl implements TradingPositionsService {

    private final TradingPositionsMapper tradingPositionsMapper;

    private final QuantClient quantClient;

    @Override
    public TradingPositionsResponseDto geTradingPositions(Long userId) {
        List<TradingPositionDtoResponseDto> tradingPositions = quantClient.getTradingPositions(userId);
        return tradingPositionsMapper.toResponseDto(tradingPositions);
    }
}
