package com.bot.trading_monitor.exception;

public class UnauthorizedAccessException extends RuntimeException {

    public UnauthorizedAccessException(String message) {
        super(message);
    }

    public UnauthorizedAccessException(Long currentUserId, Long requestedUserId) {
        super(String.format("User with ID %d is not authorized to access resources for user ID %d",
                currentUserId, requestedUserId));
    }
}

