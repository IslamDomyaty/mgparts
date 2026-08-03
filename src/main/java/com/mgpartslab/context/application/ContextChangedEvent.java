package com.mgpartslab.context.application;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import com.mgpartslab.context.domain.ShopperContext;

public record ContextChangedEvent(
        UUID eventId,
        int schemaVersion,
        Instant timestamp,
        String correlationId,
        String causationId,
        String fixtureVersion,
        ShopperContext previousContext,
        ShopperContext activeContext,
        Set<ContextField> changedFields) {
}
