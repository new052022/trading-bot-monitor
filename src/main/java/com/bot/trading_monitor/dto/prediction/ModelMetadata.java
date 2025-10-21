package com.bot.trading_monitor.dto.prediction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModelMetadata {
    private Map<String, Double> ensembleWeights;
    private Double validationRMSE;
    private Double validationMAE;
    private Double confidenceScore;
}

