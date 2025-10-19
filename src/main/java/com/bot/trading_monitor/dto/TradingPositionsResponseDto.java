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
public class TradingPositionsResponseDto {

    private Long tradesNumber;

    private Double realizedPnL;

    private Double realizedPnL30Days;

    private Long tradesNumber30Days;

    private Double realizedPnL7Days;

    private Long tradesNumber7Days;

}
