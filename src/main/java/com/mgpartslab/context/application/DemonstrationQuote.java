package com.mgpartslab.context.application;

public record DemonstrationQuote(
        String quoteVersion,
        MoneyAmount baseAmount,
        FxSnapshot fxSnapshot,
        MoneyAmount displayAmount,
        String roundingMethod,
        boolean demonstrationData) {
}
