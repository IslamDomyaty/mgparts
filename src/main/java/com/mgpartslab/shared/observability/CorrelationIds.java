package com.mgpartslab.shared.observability;

import java.util.UUID;
import java.util.regex.Pattern;

import org.slf4j.MDC;

public final class CorrelationIds {

    public static final String HEADER_NAME = "X-Correlation-ID";
    public static final String REQUEST_ATTRIBUTE = CorrelationIds.class.getName() + ".value";
    public static final String MDC_KEY = "correlationId";

    private static final Pattern SAFE_VALUE = Pattern.compile("[A-Za-z0-9._-]{8,128}");

    private CorrelationIds() {
    }

    public static String acceptOrCreate(String candidate) {
        if (candidate != null && SAFE_VALUE.matcher(candidate).matches()) {
            return candidate;
        }
        return UUID.randomUUID().toString();
    }

    public static String currentOrCreate() {
        String current = MDC.get(MDC_KEY);
        return current == null ? UUID.randomUUID().toString() : current;
    }
}
