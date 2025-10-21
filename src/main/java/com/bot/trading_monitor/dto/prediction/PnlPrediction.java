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
public class PnlPrediction {
    private BigDecimal predicted;
    private ConfidenceInterval confidence50;
    private ConfidenceInterval confidence80;
    private ConfidenceInterval confidence95;
}

