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
public class RiskMetrics {
    private BigDecimal expectedMaxDrawdown;
    private Double expectedMaxDrawdownPercent;
    private ProbabilityOfLoss probabilityOfLoss;
    private BigDecimal valueAtRisk95;
    private BigDecimal expectedShortfall;
    private Integer expectedRecoveryDays;
}

