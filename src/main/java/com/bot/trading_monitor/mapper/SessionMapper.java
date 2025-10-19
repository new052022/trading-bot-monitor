package com.bot.trading_monitor.mapper;

import com.bot.trading_monitor.dto.SessionResponseDto;
import com.bot.trading_monitor.dto.TradingSessionResponseDto;
import org.springframework.stereotype.Component;

@Component
public class SessionMapper {

    public SessionResponseDto sessionResponseDto(TradingSessionResponseDto response){
        return SessionResponseDto.builder()
                .sessionStatus(response.getSessionStatus())
                .totalBalance(response.getTotalBalance())
                .isQuantActive(response.getIsQuantActive())
                .isMarketActive(response.getIsMarketActive())
                .isStrategyActive(response.getIsStrategyActive())
                .isOrderActive(response.getIsOrderActive())
                .isStreamingActive(response.getIsStreamingActive())
                .isUserActive(response.getIsUserActive())
                .build();
    }

}
