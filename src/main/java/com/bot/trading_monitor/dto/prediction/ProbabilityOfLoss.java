package com.bot.trading_monitor.dto.prediction;

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
public class ProbabilityOfLoss {
    private Double anyLoss;
    private Double loss10Percent;
    private Double loss20Percent;
    private Double loss30Percent;
}

