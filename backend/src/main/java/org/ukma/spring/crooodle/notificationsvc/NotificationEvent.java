package org.ukma.spring.crooodle.notificationsvc;

import lombok.Builder;

import java.util.UUID;

@Builder
public record NotificationEvent(
        UUID userId,
        String userName,
        String userEmail,
        NotificationType type
) { }
