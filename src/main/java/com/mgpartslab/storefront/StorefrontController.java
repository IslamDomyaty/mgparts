package com.mgpartslab.storefront;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import com.mgpartslab.context.application.DemonstrationQuote;
import com.mgpartslab.context.application.MoneyAmount;
import com.mgpartslab.context.domain.DisplayCurrency;
import com.mgpartslab.context.domain.Language;
import com.mgpartslab.context.domain.Market;
import com.mgpartslab.context.domain.ShopperContext;
import com.mgpartslab.context.infrastructure.ContextCoordinator;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StorefrontController {

    private static final long PREVIEW_BASE_MINOR = 10_000L;

    private final ContextCoordinator coordinator;
    private final MessageSource messages;

    public StorefrontController(ContextCoordinator coordinator, MessageSource messages) {
        this.coordinator = coordinator;
        this.messages = messages;
    }

    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        ShopperContext context = coordinator.current(session);
        DemonstrationQuote quote = coordinator.quote(session, PREVIEW_BASE_MINOR);
        Locale locale = LocaleContextHolder.getLocale();

        model.addAttribute("context", context);
        model.addAttribute("marketOptions", marketOptions(locale));
        model.addAttribute("languageOptions", languageOptions(locale));
        model.addAttribute("currencyOptions", currencyOptions());
        model.addAttribute("quote", QuoteView.from(quote));
        model.addAttribute("returnTo", "/");
        return "storefront/home";
    }

    private List<SelectorOption> marketOptions(Locale locale) {
        return Arrays.stream(Market.values())
                .map(market -> new SelectorOption(
                        market.code(),
                        messages.getMessage("market." + market.code(), null, locale)))
                .toList();
    }

    private List<SelectorOption> languageOptions(Locale locale) {
        return Arrays.stream(Language.values())
                .map(language -> new SelectorOption(
                        language.code(),
                        messages.getMessage("language." + language.code(), null, locale)))
                .toList();
    }

    private static List<SelectorOption> currencyOptions() {
        return Arrays.stream(DisplayCurrency.values())
                .map(currency -> new SelectorOption(currency.code(), currency.code()))
                .toList();
    }

    public record SelectorOption(String code, String label) {
    }

    public record QuoteView(
            String baseAmount,
            String displayAmount,
            String snapshotId,
            String rate,
            String roundingMethod) {

        static QuoteView from(DemonstrationQuote quote) {
            return new QuoteView(
                    format(quote.baseAmount()),
                    format(quote.displayAmount()),
                    quote.fxSnapshot().id(),
                    quote.fxSnapshot().rate(),
                    quote.roundingMethod());
        }

        private static String format(MoneyAmount amount) {
            BigDecimal major = BigDecimal.valueOf(
                    amount.minor(), amount.currency().minorUnitDigits());
            return amount.currency().code() + " " + major.toPlainString();
        }
    }
}
