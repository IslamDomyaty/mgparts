package com.mgpartslab.localization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TranslationDiagnosticLogger {

    private static final Logger LOGGER = LoggerFactory.getLogger(TranslationDiagnosticLogger.class);

    @EventListener
    public void onMissingTranslation(TranslationMissingEvent event) {
        LOGGER.atError()
                .addKeyValue("eventName", "translation.missing")
                .addKeyValue("eventId", event.eventId())
                .addKeyValue("schemaVersion", event.schemaVersion())
                .addKeyValue("correlationId", event.correlationId())
                .addKeyValue("locale", event.locale())
                .addKeyValue("messageCode", event.messageCode())
                .log("Required translation is missing");
    }
}
