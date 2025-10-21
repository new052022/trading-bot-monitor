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

        // Merge positions with the same orderId
        List<TradingPositionDtoResponseDto> mergedPositions = mergePositionsByOrderId(tradingPositions);

        // Calculate total statistics
        long totalTrades = countUniqueTrades(mergedPositions);
        double totalRealizedPnL = calculateRealizedPnL(mergedPositions);

        // Calculate 30 days statistics
        List<TradingPositionDtoResponseDto> positions30Days = filterPositionsByDate(mergedPositions, thirtyDaysAgo);
        long tradesNumber30Days = countUniqueTrades(positions30Days);
        double realizedPnL30Days = calculateRealizedPnL(positions30Days);

        // Calculate 7 days statistics
        List<TradingPositionDtoResponseDto> positions7Days = filterPositionsByDate(mergedPositions, sevenDaysAgo);
        long tradesNumber7Days = countUniqueTrades(positions7Days);
        double realizedPnL7Days = calculateRealizedPnL(positions7Days);

        return TradingPositionsResponseDto.builder()
                .tradesNumber(totalTrades)
                .realizedPnL(totalRealizedPnL)
                .tradesNumber30Days(tradesNumber30Days)
                .realizedPnL30Days(realizedPnL30Days)
                .tradesNumber7Days(tradesNumber7Days)
                .realizedPnL7Days(realizedPnL7Days)
                .tradingPositions(mergedPositions)
                .build();
    }

    private long countUniqueTrades(List<TradingPositionDtoResponseDto> positions) {
        return positions.stream()
                .filter(position -> position.getRealizedPnl() != null && position.getRealizedPnl().compareTo(BigDecimal.ZERO) != 0)
                .map(TradingPositionDtoResponseDto::getOrderId)
                .distinct()
                .count();
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

    private List<TradingPositionDtoResponseDto> mergePositionsByOrderId(List<TradingPositionDtoResponseDto> positions) {
        return positions.stream()
                .collect(java.util.stream.Collectors.groupingBy(TradingPositionDtoResponseDto::getOrderId))
                .values()
                .stream()
                .map(this::mergePositions)
                .toList();
    }

    private TradingPositionDtoResponseDto mergePositions(List<TradingPositionDtoResponseDto> positions) {
        if (positions.isEmpty()) {
            return null;
        }

        // Take first position as base
        TradingPositionDtoResponseDto first = positions.get(0);

        // Sum realizedPnl and commission for all positions with same orderId
        BigDecimal totalRealizedPnl = positions.stream()
                .map(TradingPositionDtoResponseDto::getRealizedPnl)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalCommission = positions.stream()
                .map(TradingPositionDtoResponseDto::getCommission)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return TradingPositionDtoResponseDto.builder()
                .symbol(first.getSymbol())
                .time(first.getTime())
                .exchange(first.getExchange())
                .side(first.getSide())
                .orderId(first.getOrderId())
                .realizedPnl(totalRealizedPnl)
                .commission(totalCommission)
                .build();
    }

}
