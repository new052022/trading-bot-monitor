package com.bot.trading_monitor.security;

import com.bot.trading_monitor.exception.UnauthorizedAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return (Long) authentication.getPrincipal();
    }

    public static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }

    public static void validateUserAccess(Long requestedUserId) {
        Long currentUserId = getCurrentUserId();

        if (currentUserId == null) {
            throw new UnauthorizedAccessException("User is not authenticated");
        }

        if (!currentUserId.equals(requestedUserId)) {
            throw new UnauthorizedAccessException(currentUserId, requestedUserId);
        }
    }
}
