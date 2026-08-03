package com.mgpartslab.context.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.mgpartslab.context.domain.DisplayCurrency;
import com.mgpartslab.context.domain.Language;
import com.mgpartslab.context.domain.Market;
import org.junit.jupiter.api.Test;

class ContextCodeParserTest {

    @Test
    void acceptsOnlyTheDocumentedStableCodes() {
        assertThat(ContextCodeParser.market("EG")).isEqualTo(Market.EGYPT);
        assertThat(ContextCodeParser.market("GB")).isEqualTo(Market.UNITED_KINGDOM);
        assertThat(ContextCodeParser.language("ar")).isEqualTo(Language.ARABIC);
        assertThat(ContextCodeParser.language("en")).isEqualTo(Language.ENGLISH);
        assertThat(ContextCodeParser.currency("EUR")).isEqualTo(DisplayCurrency.EUR);
    }

    @Test
    void rejectsFreeTextAndUnsupportedIsoLikeCodes() {
        assertThatThrownBy(() -> ContextCodeParser.market("United Kingdom"))
                .isInstanceOf(ContextInputException.class)
                .extracting(exception -> ((ContextInputException) exception).code())
                .isEqualTo("CONTEXT_MARKET_UNSUPPORTED");
        assertThatThrownBy(() -> ContextCodeParser.market("UK"))
                .isInstanceOf(ContextInputException.class);
        assertThatThrownBy(() -> ContextCodeParser.language("fr"))
                .isInstanceOf(ContextInputException.class);
        assertThatThrownBy(() -> ContextCodeParser.currency("AED"))
                .isInstanceOf(ContextInputException.class);
    }
}
