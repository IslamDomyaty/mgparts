package com.mgpartslab.context.web;

import com.mgpartslab.context.application.DemonstrationQuote;
import com.mgpartslab.context.domain.ShopperContext;
import com.mgpartslab.context.infrastructure.ContextCoordinator;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/context", produces = MediaType.APPLICATION_JSON_VALUE)
public class ContextApiController {

    private static final long PREVIEW_BASE_MINOR = 10_000L;

    private final ContextCoordinator coordinator;

    public ContextApiController(ContextCoordinator coordinator) {
        this.coordinator = coordinator;
    }

    @GetMapping
    public ContextResponse getContext(HttpSession session) {
        ShopperContext context = coordinator.current(session);
        DemonstrationQuote quote = coordinator.quote(session, PREVIEW_BASE_MINOR);
        return ContextResponse.from(context, quote);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ContextResponse updateContext(
            @RequestBody ContextUpdateRequest request,
            HttpSession session) {
        ShopperContext context = coordinator.update(session, request.toCommand());
        DemonstrationQuote quote = coordinator.quote(session, PREVIEW_BASE_MINOR);
        return ContextResponse.from(context, quote);
    }

    @GetMapping("/quote")
    public DemonstrationQuote getDemonstrationQuote(
            @RequestParam(defaultValue = "10000") long baseMinor,
            HttpSession session) {
        return coordinator.quote(session, baseMinor);
    }
}
