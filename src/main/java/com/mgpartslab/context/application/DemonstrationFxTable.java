package com.mgpartslab.context.application;

import java.math.BigDecimal;
import java.util.Map;

import com.mgpartslab.context.domain.DisplayCurrency;

final class DemonstrationFxTable {

    static final String VERSION = "fx-demo-v1";

    private static final Map<CurrencyPair, BigDecimal> RATES = Map.of(
            new CurrencyPair(DisplayCurrency.EGP, DisplayCurrency.EGP), new BigDecimal("1.000000"),
            new CurrencyPair(DisplayCurrency.EGP, DisplayCurrency.GBP), new BigDecimal("0.015873"),
            new CurrencyPair(DisplayCurrency.EGP, DisplayCurrency.USD), new BigDecimal("0.020000"),
            new CurrencyPair(DisplayCurrency.EGP, DisplayCurrency.EUR), new BigDecimal("0.018500"),
            new CurrencyPair(DisplayCurrency.GBP, DisplayCurrency.EGP), new BigDecimal("63.000000"),
            new CurrencyPair(DisplayCurrency.GBP, DisplayCurrency.GBP), new BigDecimal("1.000000"),
            new CurrencyPair(DisplayCurrency.GBP, DisplayCurrency.USD), new BigDecimal("1.260000"),
            new CurrencyPair(DisplayCurrency.GBP, DisplayCurrency.EUR), new BigDecimal("1.165000"));

    private DemonstrationFxTable() {
    }

    static BigDecimal rate(DisplayCurrency base, DisplayCurrency display) {
        BigDecimal rate = RATES.get(new CurrencyPair(base, display));
        if (rate == null) {
            throw new IllegalStateException("Missing demonstration FX pair");
        }
        return rate;
    }

    private record CurrencyPair(DisplayCurrency base, DisplayCurrency display) {
    }
}
