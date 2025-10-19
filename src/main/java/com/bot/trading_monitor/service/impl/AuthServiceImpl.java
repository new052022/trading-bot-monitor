package com.bot.trading_monitor.service.impl;

import com.bot.trading_monitor.dto.AuthRequestDto;
import com.bot.trading_monitor.dto.AuthResponseDto;
import com.bot.trading_monitor.entity.User;
import com.bot.trading_monitor.repository.UserRepository;
import com.bot.trading_monitor.security.JwtTokenProvider;
import com.bot.trading_monitor.service.interfaces.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public AuthResponseDto authenticate(AuthRequestDto request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found with username: " + request.getUsername()));

        if (!passwordEncoder.matches(request.getCredentials(), user.getCredentials())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtTokenProvider.generateToken(user.getId(), user.getUsername());

        return AuthResponseDto.builder()
                .token(token)
                .tokenType("Bearer")
                .expiresIn(jwtTokenProvider.getExpirationMs())
                .userId(user.getId())
                .username(user.getUsername())
                .build();
    }

    @Override
    @Transactional
    public AuthResponseDto register(AuthRequestDto request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username is already taken");
        }

        User user = User.builder()
                .username(request.getUsername())
                .credentials(passwordEncoder.encode(request.getCredentials()))
                .build();

        user = userRepository.save(user);

        String token = jwtTokenProvider.generateToken(user.getId(), user.getUsername());

        return AuthResponseDto.builder()
                .token(token)
                .tokenType("Bearer")
                .expiresIn(jwtTokenProvider.getExpirationMs())
                .userId(user.getId())
                .username(user.getUsername())
                .build();
    }
}

