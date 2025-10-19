package com.bot.trading_monitor.mapper;

import com.bot.trading_monitor.dto.TradingPositionDtoResponseDto;
import com.bot.trading_monitor.dto.TradingPositionsResponseDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Component
public class TradingPositionsMapper {

    public TradingPositionsResponseDto toResponseDto(List<TradingPositionDtoResponseDto> tradingPositions) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime thirtyDaysAgo = now.minusDays(30);
        LocalDateTime sevenDaysAgo = now.minusDays(7);

        // Calculate total statistics
        long totalTrades = tradingPositions.size();
        double totalRealizedPnL = calculateRealizedPnL(tradingPositions);

        // Calculate 30 days statistics
        List<TradingPositionDtoResponseDto> positions30Days = filterPositionsByDate(tradingPositions, thirtyDaysAgo);
        long tradesNumber30Days = positions30Days.size();
        double realizedPnL30Days = calculateRealizedPnL(positions30Days);

        // Calculate 7 days statistics
        List<TradingPositionDtoResponseDto> positions7Days = filterPositionsByDate(tradingPositions, sevenDaysAgo);
        long tradesNumber7Days = positions7Days.size();
        double realizedPnL7Days = calculateRealizedPnL(positions7Days);

        return TradingPositionsResponseDto.builder()
                .tradesNumber(totalTrades)
                .realizedPnL(totalRealizedPnL)
                .tradesNumber30Days(tradesNumber30Days)
                .realizedPnL30Days(realizedPnL30Days)
                .tradesNumber7Days(tradesNumber7Days)
                .realizedPnL7Days(realizedPnL7Days)
                .build();
    }

    private List<TradingPositionDtoResponseDto> filterPositionsByDate(
            List<TradingPositionDtoResponseDto> positions,
            LocalDateTime fromDate) {
        return positions.stream()
                .filter(position -> position.getTime() != null && position.getTime().isAfter(fromDate))
                .toList();
    }

    private double calculateRealizedPnL(List<TradingPositionDtoResponseDto> positions) {
        return positions.stream()
                .mapToDouble(position -> {
                    BigDecimal realizedPnl = position.getRealizedPnl();
                    BigDecimal commission = position.getCommission();

                    if (realizedPnl == null) {
                        return 0.0;
                    }

                    double pnlValue = realizedPnl.doubleValue();
                    double commissionValue = commission != null ? commission.doubleValue() : 0.0;

                    return pnlValue - commissionValue;
                })
                .sum();
    }

}
