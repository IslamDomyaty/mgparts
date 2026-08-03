package com.mgpartslab.context.application;

import java.time.Clock;
import java.util.EnumSet;
import java.util.Set;
import java.util.UUID;

import com.mgpartslab.context.domain.DisplayCurrency;
import com.mgpartslab.context.domain.Language;
import com.mgpartslab.context.domain.Market;
import com.mgpartslab.context.domain.ShopperContext;
import com.mgpartslab.shared.observability.CorrelationIds;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class MarketContextService {

    public static final String FIXTURE_VERSION = "context-demo-v1";

    private final Clock clock;
    private final ApplicationEventPublisher eventPublisher;

    public MarketContextService(Clock clock, ApplicationEventPublisher eventPublisher) {
        this.clock = clock;
        this.eventPublisher = eventPublisher;
    }

    public ShopperContext update(ShopperContext current, ContextUpdate update) {
        if (update == null || update.isEmpty()) {
            throw new ContextInputException(
                    "CONTEXT_UPDATE_EMPTY",
                    "context",
                    "At least one context field is required");
        }

        ShopperContext next = current;
        EnumSet<ContextField> changedFields = EnumSet.noneOf(ContextField.class);

        if (update.market() != null && update.market() != current.market()) {
            Market market = update.market();
            next = new ShopperContext(
                    market,
                    market.defaultLanguage(),
                    market.defaultDisplayCurrency());
        }
        if (update.language() != null) {
            next = new ShopperContext(next.market(), update.language(), next.displayCurrency());
        }
        if (update.displayCurrency() != null) {
            next = new ShopperContext(next.market(), next.language(), update.displayCurrency());
        }

        collectChanges(current, next, changedFields);
        if (!changedFields.isEmpty()) {
            String correlationId = CorrelationIds.currentOrCreate();
            eventPublisher.publishEvent(new ContextChangedEvent(
                    UUID.randomUUID(),
                    1,
                    clock.instant(),
                    correlationId,
                    correlationId,
                    FIXTURE_VERSION,
                    current,
                    next,
                    Set.copyOf(changedFields)));
        }
        return next;
    }

    private static void collectChanges(
            ShopperContext current,
            ShopperContext next,
            EnumSet<ContextField> changedFields) {
        if (current.market() != next.market()) {
            changedFields.add(ContextField.MARKET);
        }
        if (current.language() != next.language()) {
            changedFields.add(ContextField.LANGUAGE);
        }
        if (current.displayCurrency() != next.displayCurrency()) {
            changedFields.add(ContextField.DISPLAY_CURRENCY);
        }
    }
}
