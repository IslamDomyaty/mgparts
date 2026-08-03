package com.mgpartslab.context.web;

import java.util.Arrays;
import java.util.List;

import com.mgpartslab.context.application.DemonstrationQuote;
import com.mgpartslab.context.application.FxQuoteService;
import com.mgpartslab.context.application.MarketContextService;
import com.mgpartslab.context.domain.DisplayCurrency;
import com.mgpartslab.context.domain.Language;
import com.mgpartslab.context.domain.Market;
import com.mgpartslab.context.domain.ShopperContext;

public record ContextResponse(
        ActiveContext active,
        List<MarketOption> markets,
        List<LocaleOption> locales,
        List<String> displayCurrencies,
        ContextConfiguration configuration,
        DemonstrationQuote demonstrationQuote) {

    public static ContextResponse from(ShopperContext context, DemonstrationQuote quote) {
        return new ContextResponse(
                new ActiveContext(
                        context.market().code(),
                        context.language().code(),
                        context.language().direction(),
                        context.market().baseCurrency().code(),
                        context.displayCurrency().code()),
                Arrays.stream(Market.values())
                        .map(market -> new MarketOption(
                                market.code(),
                                market.defaultLanguage().code(),
                                market.baseCurrency().code(),
                                market.defaultDisplayCurrency().code()))
                        .toList(),
                Arrays.stream(Language.values())
                        .map(language -> new LocaleOption(language.code(), language.direction()))
                        .toList(),
                Arrays.stream(DisplayCurrency.values()).map(DisplayCurrency::code).toList(),
                new ContextConfiguration(
                        MarketContextService.FIXTURE_VERSION,
                        FxQuoteService.FIXTURE_VERSION,
                        FxQuoteService.ROUNDING_METHOD,
                        true),
                quote);
    }

    public record ActiveContext(
            String market,
            String locale,
            String direction,
            String baseCurrency,
            String displayCurrency) {
    }

    public record MarketOption(
            String code,
            String defaultLocale,
            String baseCurrency,
            String defaultDisplayCurrency) {
    }

    public record LocaleOption(String code, String direction) {
    }

    public record ContextConfiguration(
            String contextFixtureVersion,
            String fxFixtureVersion,
            String roundingMethod,
            boolean demonstrationData) {
    }
}
