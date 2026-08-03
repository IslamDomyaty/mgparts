package com.mgpartslab.context.domain;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public record ShopperContext(
        Market market,
        Language language,
        DisplayCurrency displayCurrency) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public ShopperContext {
        Objects.requireNonNull(market, "market");
        Objects.requireNonNull(language, "language");
        Objects.requireNonNull(displayCurrency, "displayCurrency");
    }

    public static ShopperContext defaults() {
        Market market = Market.EGYPT;
        return new ShopperContext(
                market,
                market.defaultLanguage(),
                market.defaultDisplayCurrency());
    }
}
