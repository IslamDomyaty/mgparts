package com.mgpartslab.localization;

import java.util.Locale;

import com.mgpartslab.context.domain.ShopperContext;
import com.mgpartslab.context.infrastructure.SessionShopperContextRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.LocaleResolver;

public final class ContextLocaleResolver implements LocaleResolver {

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Object stored = session.getAttribute(SessionShopperContextRepository.ATTRIBUTE_NAME);
            if (stored instanceof ShopperContext context) {
                return context.language().locale();
            }
        }
        return ShopperContext.defaults().language().locale();
    }

    @Override
    public void setLocale(
            HttpServletRequest request,
            HttpServletResponse response,
            Locale locale) {
        throw new UnsupportedOperationException(
                "Locale changes must use the market-context application service");
    }
}
