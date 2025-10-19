package com.bot.trading_monitor.service.interfaces;

import com.bot.trading_monitor.dto.SessionResponseDto;

public interface TradingSessionService {

    SessionResponseDto getSessionStatus(Long userId);

}
