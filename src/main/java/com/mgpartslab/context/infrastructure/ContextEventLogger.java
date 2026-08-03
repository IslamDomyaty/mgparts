package com.mgpartslab.context.infrastructure;

import com.mgpartslab.context.application.ContextChangedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ContextEventLogger {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContextEventLogger.class);

    @EventListener
    public void onContextChanged(ContextChangedEvent event) {
        LOGGER.atInfo()
                .addKeyValue("eventName", "context.changed")
                .addKeyValue("eventId", event.eventId())
                .addKeyValue("schemaVersion", event.schemaVersion())
                .addKeyValue("correlationId", event.correlationId())
                .addKeyValue("causationId", event.causationId())
                .addKeyValue("market", event.activeContext().market().code())
                .addKeyValue("locale", event.activeContext().language().code())
                .addKeyValue("currency", event.activeContext().displayCurrency().code())
                .addKeyValue("fixtureVersion", event.fixtureVersion())
                .addKeyValue("changedFields", event.changedFields())
                .log("Shopper context changed");
    }
}
