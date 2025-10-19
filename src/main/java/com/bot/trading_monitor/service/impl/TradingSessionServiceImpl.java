package com.bot.trading_monitor.service.impl;

import com.bot.trading_monitor.client.QuantClient;
import com.bot.trading_monitor.dto.SessionResponseDto;
import com.bot.trading_monitor.dto.TradingSessionResponseDto;
import com.bot.trading_monitor.mapper.SessionMapper;
import com.bot.trading_monitor.security.SecurityUtils;
import com.bot.trading_monitor.service.interfaces.TradingSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TradingSessionServiceImpl implements TradingSessionService {

    private final QuantClient quantClient;

    private final SessionMapper sessionMapper;

    @Override
    public SessionResponseDto getSessionStatus(Long userId) {
        SecurityUtils.validateUserAccess(userId);
        TradingSessionResponseDto tradingSession = quantClient.getTradingSession(userId);
        return sessionMapper.sessionResponseDto(tradingSession);
    }

}
