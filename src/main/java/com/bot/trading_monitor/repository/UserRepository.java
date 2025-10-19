package com.bot.trading_monitor.repository;

import com.bot.trading_monitor.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByExternalId(Long externalId);

    boolean existsByUsername(String username);
}

