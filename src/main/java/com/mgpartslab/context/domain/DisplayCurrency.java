package com.mgpartslab.context.domain;

import java.util.Objects;

public enum DisplayCurrency {
    EGP("EGP", 2),
    GBP("GBP", 2),
    USD("USD", 2),
    EUR("EUR", 2);

    private final String code;
    private final int minorUnitDigits;

    DisplayCurrency(String code, int minorUnitDigits) {
        this.code = code;
        this.minorUnitDigits = minorUnitDigits;
    }

    public String code() {
        return code;
    }

    public int minorUnitDigits() {
        return minorUnitDigits;
    }

    public static DisplayCurrency fromCode(String code) {
        for (DisplayCurrency currency : values()) {
            if (Objects.equals(currency.code, code)) {
                return currency;
            }
        }
        throw new IllegalArgumentException("Unsupported currency code");
    }
}
