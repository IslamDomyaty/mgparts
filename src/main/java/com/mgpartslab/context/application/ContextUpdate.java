package com.mgpartslab.context.application;

import com.mgpartslab.context.domain.DisplayCurrency;
import com.mgpartslab.context.domain.Language;
import com.mgpartslab.context.domain.Market;

public record ContextUpdate(
        Market market,
        Language language,
        DisplayCurrency displayCurrency) {

    public boolean isEmpty() {
        return market == null && language == null && displayCurrency == null;
    }

    public static ContextUpdate market(Market market) {
        return new ContextUpdate(market, null, null);
    }

    public static ContextUpdate language(Language language) {
        return new ContextUpdate(null, language, null);
    }

    public static ContextUpdate currency(DisplayCurrency currency) {
        return new ContextUpdate(null, null, currency);
    }
}
