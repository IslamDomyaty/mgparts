package com.mgpartslab.context.application;

import com.mgpartslab.context.domain.DisplayCurrency;
import com.mgpartslab.context.domain.Language;
import com.mgpartslab.context.domain.Market;

public final class ContextCodeParser {

    private ContextCodeParser() {
    }

    public static Market market(String code) {
        try {
            return Market.fromCode(code);
        } catch (IllegalArgumentException exception) {
            throw new ContextInputException(
                    "CONTEXT_MARKET_UNSUPPORTED",
                    "market",
                    "Market must be one of: EG, GB");
        }
    }

    public static Language language(String code) {
        try {
            return Language.fromCode(code);
        } catch (IllegalArgumentException exception) {
            throw new ContextInputException(
                    "CONTEXT_LOCALE_UNSUPPORTED",
                    "locale",
                    "Locale must be one of: ar, en");
        }
    }

    public static DisplayCurrency currency(String code) {
        try {
            return DisplayCurrency.fromCode(code);
        } catch (IllegalArgumentException exception) {
            throw new ContextInputException(
                    "CONTEXT_CURRENCY_UNSUPPORTED",
                    "currency",
                    "Currency must be one of: EGP, GBP, USD, EUR");
        }
    }
}
