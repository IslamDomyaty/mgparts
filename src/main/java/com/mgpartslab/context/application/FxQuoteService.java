package com.mgpartslab.context.application;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.mgpartslab.context.domain.DisplayCurrency;
import com.mgpartslab.context.domain.ShopperContext;
import org.springframework.stereotype.Service;

@Service
public class FxQuoteService {

    public static final String FIXTURE_VERSION = DemonstrationFxTable.VERSION;
    public static final String ROUNDING_METHOD = "HALF_UP_AT_DISPLAY_MINOR_UNIT";
    public static final long MAX_BASE_MINOR = 1_000_000_000L;

    public DemonstrationQuote quote(long baseMinor, ShopperContext context) {
        if (baseMinor < 0 || baseMinor > MAX_BASE_MINOR) {
            throw new ContextInputException(
                    "QUOTE_BASE_MINOR_INVALID",
                    "baseMinor",
                    "baseMinor must be between 0 and " + MAX_BASE_MINOR);
        }

        DisplayCurrency baseCurrency = context.market().baseCurrency();
        DisplayCurrency displayCurrency = context.displayCurrency();
        BigDecimal rate = DemonstrationFxTable.rate(baseCurrency, displayCurrency);

        BigDecimal baseMajor = BigDecimal.valueOf(baseMinor, baseCurrency.minorUnitDigits());
        long displayMinor = baseMajor
                .multiply(rate)
                .movePointRight(displayCurrency.minorUnitDigits())
                .setScale(0, RoundingMode.HALF_UP)
                .longValueExact();

        return new DemonstrationQuote(
                "quote-" + FIXTURE_VERSION,
                new MoneyAmount(baseMinor, baseCurrency),
                new FxSnapshot(
                        FIXTURE_VERSION + "-" + baseCurrency.code() + "-" + displayCurrency.code(),
                        rate.toPlainString()),
                new MoneyAmount(displayMinor, displayCurrency),
                ROUNDING_METHOD,
                true);
    }
}
