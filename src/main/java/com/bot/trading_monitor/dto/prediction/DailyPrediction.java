package com.bot.trading_monitor.dto.prediction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DailyPrediction {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private PnlPrediction cumulativePnl;
    private PnlPrediction dailyPnl;
}

