package com.bot.trading_monitor.repository;

import com.bot.trading_monitor.entity.TradingPrediction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TradingPredictionRepository extends JpaRepository<TradingPrediction, Long> {

    Optional<TradingPrediction> findTopByUserIdOrderByFetchedAtDesc(Long userId);

    void deleteByUserId(Long userId);
}
