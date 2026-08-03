package com.mgpartslab.context.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.mgpartslab.context.domain.DisplayCurrency;
import com.mgpartslab.context.domain.Language;
import com.mgpartslab.context.domain.Market;
import com.mgpartslab.context.domain.ShopperContext;
import org.junit.jupiter.api.Test;

class FxQuoteServiceTest {

    private final FxQuoteService service = new FxQuoteService();

    @Test
    void convertsAtOneDocumentedMinorUnitBoundary() {
        ShopperContext context = new ShopperContext(
                Market.EGYPT, Language.ARABIC, DisplayCurrency.GBP);

        DemonstrationQuote quote = service.quote(10_000, context);

        assertThat(quote.baseAmount()).isEqualTo(new MoneyAmount(10_000, DisplayCurrency.EGP));
        assertThat(quote.fxSnapshot().rate()).isEqualTo("0.015873");
        assertThat(quote.displayAmount()).isEqualTo(new MoneyAmount(159, DisplayCurrency.GBP));
        assertThat(quote.roundingMethod()).isEqualTo("HALF_UP_AT_DISPLAY_MINOR_UNIT");
        assertThat(quote.demonstrationData()).isTrue();
    }

    @Test
    void displayCurrencyNeverChangesTheMarketBaseCurrency() {
        ShopperContext context = new ShopperContext(
                Market.UNITED_KINGDOM, Language.ARABIC, DisplayCurrency.EGP);

        DemonstrationQuote quote = service.quote(10_000, context);

        assertThat(quote.baseAmount().currency()).isEqualTo(DisplayCurrency.GBP);
        assertThat(quote.displayAmount()).isEqualTo(new MoneyAmount(630_000, DisplayCurrency.EGP));
    }

    @Test
    void rejectsNegativeOrUnboundedInput() {
        assertThatThrownBy(() -> service.quote(-1, ShopperContext.defaults()))
                .isInstanceOf(ContextInputException.class)
                .extracting(exception -> ((ContextInputException) exception).code())
                .isEqualTo("QUOTE_BASE_MINOR_INVALID");

        assertThatThrownBy(() -> service.quote(
                FxQuoteService.MAX_BASE_MINOR + 1, ShopperContext.defaults()))
                .isInstanceOf(ContextInputException.class);
    }
}
