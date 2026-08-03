package com.mgpartslab.context.web;

import com.mgpartslab.context.application.ContextCodeParser;
import com.mgpartslab.context.application.ContextUpdate;

public record ContextUpdateRequest(String market, String locale, String currency) {

    public ContextUpdate toCommand() {
        return new ContextUpdate(
                market == null ? null : ContextCodeParser.market(market),
                locale == null ? null : ContextCodeParser.language(locale),
                currency == null ? null : ContextCodeParser.currency(currency));
    }
}
