package com.bot.trading_monitor.service.interfaces;

import com.bot.trading_monitor.dto.TradingPositionsResponseDto;

public interface TradingPositionsService {

   TradingPositionsResponseDto geTradingPositions(Long userId);

}
