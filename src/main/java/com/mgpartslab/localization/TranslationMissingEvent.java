package com.mgpartslab.localization;

import java.time.Instant;
import java.util.UUID;

public record TranslationMissingEvent(
        UUID eventId,
        int schemaVersion,
        Instant timestamp,
        String correlationId,
        String locale,
        String messageCode) {
}
