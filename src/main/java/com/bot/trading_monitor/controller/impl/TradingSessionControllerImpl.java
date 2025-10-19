package com.bot.trading_monitor.controller.impl;

import com.bot.trading_monitor.controller.interfaces.TradingSessionController;
import com.bot.trading_monitor.dto.SessionResponseDto;
import com.bot.trading_monitor.security.SecurityUtils;
import com.bot.trading_monitor.service.interfaces.TradingSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TradingSessionControllerImpl implements TradingSessionController {

    private final TradingSessionService tradingSessionService;

    @Override
    public ResponseEntity<SessionResponseDto> getSessionStatus(Long userId) {
        SecurityUtils.validateUserAccess(userId);
        SessionResponseDto response = tradingSessionService.getSessionStatus(userId);
        return ResponseEntity.ok(response);
    }
}

