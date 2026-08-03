package com.mgpartslab.context.domain;

import java.util.Objects;

public enum Market {
    EGYPT("EG", Language.ARABIC, DisplayCurrency.EGP),
    UNITED_KINGDOM("GB", Language.ENGLISH, DisplayCurrency.GBP);

    private final String code;
    private final Language defaultLanguage;
    private final DisplayCurrency baseCurrency;

    Market(String code, Language defaultLanguage, DisplayCurrency baseCurrency) {
        this.code = code;
        this.defaultLanguage = defaultLanguage;
        this.baseCurrency = baseCurrency;
    }

    public String code() {
        return code;
    }

    public Language defaultLanguage() {
        return defaultLanguage;
    }

    public DisplayCurrency baseCurrency() {
        return baseCurrency;
    }

    public DisplayCurrency defaultDisplayCurrency() {
        return baseCurrency;
    }

    public static Market fromCode(String code) {
        for (Market market : values()) {
            if (Objects.equals(market.code, code)) {
                return market;
            }
        }
        throw new IllegalArgumentException("Unsupported market code");
    }
}
