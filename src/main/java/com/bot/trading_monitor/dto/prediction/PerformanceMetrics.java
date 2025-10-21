package com.bot.trading_monitor.dto.prediction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PerformanceMetrics {
    private Double projectedWinRate;
    private Double projectedSharpeRatio;
    private Double projectedProfitFactor;
    private BigDecimal projectedAverageDailyPnl;
}

