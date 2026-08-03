package com.mgpartslab.context.web;

import java.net.URI;
import java.net.URISyntaxException;

final class RedirectTarget {

    private RedirectTarget() {
    }

    static String safeLocalPath(String candidate) {
        if (candidate == null || candidate.isBlank() || candidate.contains("\r") || candidate.contains("\n")) {
            return "/";
        }
        try {
            URI uri = new URI(candidate);
            if (uri.isAbsolute()
                    || uri.getHost() != null
                    || uri.getRawAuthority() != null
                    || !candidate.startsWith("/")
                    || candidate.startsWith("//")) {
                return "/";
            }
            return candidate;
        } catch (URISyntaxException exception) {
            return "/";
        }
    }
}
