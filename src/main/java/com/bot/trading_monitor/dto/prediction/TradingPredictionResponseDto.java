package com.bot.trading_monitor.dto.prediction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TradingPredictionResponseDto {

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime generatedAt;

    private HistoricalDataRange historicalDataRange;
    private List<DailyPrediction> predictions;
    private RiskMetrics riskMetrics;
    private PerformanceMetrics performanceMetrics;
    private ModelMetadata modelMetadata;
}

