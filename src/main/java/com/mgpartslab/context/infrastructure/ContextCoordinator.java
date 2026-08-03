package com.mgpartslab.context.infrastructure;

import com.mgpartslab.context.application.ContextUpdate;
import com.mgpartslab.context.application.DemonstrationQuote;
import com.mgpartslab.context.application.FxQuoteService;
import com.mgpartslab.context.application.MarketContextService;
import com.mgpartslab.context.domain.ShopperContext;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

@Component
public class ContextCoordinator {

    private final SessionShopperContextRepository repository;
    private final MarketContextService contextService;
    private final FxQuoteService quoteService;

    public ContextCoordinator(
            SessionShopperContextRepository repository,
            MarketContextService contextService,
            FxQuoteService quoteService) {
        this.repository = repository;
        this.contextService = contextService;
        this.quoteService = quoteService;
    }

    public ShopperContext current(HttpSession session) {
        return repository.current(session);
    }

    public ShopperContext update(HttpSession session, ContextUpdate update) {
        ShopperContext updated = contextService.update(repository.current(session), update);
        repository.save(session, updated);
        return updated;
    }

    public DemonstrationQuote quote(HttpSession session, long baseMinor) {
        return quoteService.quote(baseMinor, repository.current(session));
    }
}
