package com.bot.trading_monitor.controller.interfaces;

import com.bot.trading_monitor.dto.SessionResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/trading-session")
public interface TradingSessionController {

    @GetMapping("/{userId}/status")
    ResponseEntity<SessionResponseDto> getSessionStatus(@PathVariable Long userId);
}

