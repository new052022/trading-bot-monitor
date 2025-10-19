package com.bot.trading_monitor.service.interfaces;

import com.bot.trading_monitor.dto.AuthRequestDto;
import com.bot.trading_monitor.dto.AuthResponseDto;

public interface AuthService {

    AuthResponseDto authenticate(AuthRequestDto request);

    AuthResponseDto register(AuthRequestDto request);
}

