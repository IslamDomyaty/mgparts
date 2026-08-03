package com.mgpartslab.context.infrastructure;

import com.mgpartslab.context.domain.ShopperContext;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Repository;

@Repository
public class SessionShopperContextRepository {

    public static final String ATTRIBUTE_NAME =
            SessionShopperContextRepository.class.getName() + ".context";

    public ShopperContext current(HttpSession session) {
        Object stored = session.getAttribute(ATTRIBUTE_NAME);
        if (stored instanceof ShopperContext context) {
            return context;
        }
        ShopperContext defaults = ShopperContext.defaults();
        session.setAttribute(ATTRIBUTE_NAME, defaults);
        return defaults;
    }

    public void save(HttpSession session, ShopperContext context) {
        session.setAttribute(ATTRIBUTE_NAME, context);
    }
}
