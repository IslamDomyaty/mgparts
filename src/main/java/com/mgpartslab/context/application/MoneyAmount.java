package com.mgpartslab.context.application;

import com.mgpartslab.context.domain.DisplayCurrency;

public record MoneyAmount(long minor, DisplayCurrency currency) {

    public MoneyAmount {
        if (minor < 0) {
            throw new IllegalArgumentException("Money cannot be negative");
        }
    }
}
