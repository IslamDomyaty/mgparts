package com.mgpartslab.context.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import com.mgpartslab.context.domain.DisplayCurrency;
import com.mgpartslab.context.domain.Language;
import com.mgpartslab.context.domain.Market;
import com.mgpartslab.context.domain.ShopperContext;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

class MarketContextServiceTest {

    private static final Clock FIXED_CLOCK =
            Clock.fixed(Instant.parse("2026-08-03T12:00:00Z"), ZoneOffset.UTC);

    private final List<Object> events = new ArrayList<>();
    private final ApplicationEventPublisher publisher = events::add;
    private final MarketContextService service = new MarketContextService(FIXED_CLOCK, publisher);

    @Test
    void changingMarketAppliesTheNewMarketDefaultsAndPublishesSafeContextEvent() {
        ShopperContext current = new ShopperContext(
                Market.EGYPT, Language.ARABIC, DisplayCurrency.USD);

        ShopperContext updated = service.update(
                current, ContextUpdate.market(Market.UNITED_KINGDOM));

        assertThat(updated).isEqualTo(new ShopperContext(
                Market.UNITED_KINGDOM, Language.ENGLISH, DisplayCurrency.GBP));

        assertThat(events).singleElement().isInstanceOf(ContextChangedEvent.class);
        ContextChangedEvent event = (ContextChangedEvent) events.getFirst();
        assertThat(event.timestamp()).isEqualTo(FIXED_CLOCK.instant());
        assertThat(event.fixtureVersion()).isEqualTo("context-demo-v1");
        assertThat(event.changedFields()).containsExactlyInAnyOrder(
                ContextField.MARKET,
                ContextField.LANGUAGE,
                ContextField.DISPLAY_CURRENCY);
        assertThat(event.activeContext()).isEqualTo(updated);
    }

    @Test
    void changingOnlyDisplayCurrencyPreservesMarketAndLanguage() {
        ShopperContext current = new ShopperContext(
                Market.EGYPT, Language.ENGLISH, DisplayCurrency.EGP);

        ShopperContext updated = service.update(
                current, ContextUpdate.currency(DisplayCurrency.EUR));

        assertThat(updated.market()).isEqualTo(Market.EGYPT);
        assertThat(updated.language()).isEqualTo(Language.ENGLISH);
        assertThat(updated.displayCurrency()).isEqualTo(DisplayCurrency.EUR);
    }

    @Test
    void selectingTheAlreadyActiveMarketIsIdempotentAndPreservesExplicitChoices() {
        ShopperContext current = new ShopperContext(
                Market.EGYPT, Language.ENGLISH, DisplayCurrency.USD);

        ShopperContext updated = service.update(current, ContextUpdate.market(Market.EGYPT));

        assertThat(updated).isEqualTo(current);
        assertThat(events).isEmpty();
    }

    @Test
    void rejectsAnEmptyUpdate() {
        assertThatThrownBy(() -> service.update(
                ShopperContext.defaults(), new ContextUpdate(null, null, null)))
                .isInstanceOf(ContextInputException.class)
                .extracting(exception -> ((ContextInputException) exception).code())
                .isEqualTo("CONTEXT_UPDATE_EMPTY");
    }
}
