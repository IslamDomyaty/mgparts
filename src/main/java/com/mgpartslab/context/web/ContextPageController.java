package com.mgpartslab.context.web;

import com.mgpartslab.context.application.ContextCodeParser;
import com.mgpartslab.context.application.ContextUpdate;
import com.mgpartslab.context.infrastructure.ContextCoordinator;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContextPageController {

    private final ContextCoordinator coordinator;

    public ContextPageController(ContextCoordinator coordinator) {
        this.coordinator = coordinator;
    }

    @PostMapping("/context/market")
    public String changeMarket(
            @RequestParam String market,
            @RequestParam(defaultValue = "/") String returnTo,
            HttpSession session) {
        coordinator.update(session, ContextUpdate.market(ContextCodeParser.market(market)));
        return "redirect:" + RedirectTarget.safeLocalPath(returnTo);
    }

    @PostMapping("/context/language")
    public String changeLanguage(
            @RequestParam String locale,
            @RequestParam(defaultValue = "/") String returnTo,
            HttpSession session) {
        coordinator.update(session, ContextUpdate.language(ContextCodeParser.language(locale)));
        return "redirect:" + RedirectTarget.safeLocalPath(returnTo);
    }

    @PostMapping("/context/currency")
    public String changeCurrency(
            @RequestParam String currency,
            @RequestParam(defaultValue = "/") String returnTo,
            HttpSession session) {
        coordinator.update(session, ContextUpdate.currency(ContextCodeParser.currency(currency)));
        return "redirect:" + RedirectTarget.safeLocalPath(returnTo);
    }
}
