package com.bot.trading_monitor.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TradingSessionResponseDto {

    private String sessionStatus;

    private Boolean isPause;

    private Double totalBalance;

    private Boolean isQuantActive;

    private Boolean isMarketActive;

    private Boolean isStrategyActive;

    private Boolean isOrderActive;

    private Boolean isStreamingActive;

    private Boolean isUserActive;

}
