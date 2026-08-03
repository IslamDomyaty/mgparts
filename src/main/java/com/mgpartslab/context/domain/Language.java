package com.mgpartslab.context.domain;

import java.util.Locale;
import java.util.Objects;

public enum Language {
    ARABIC("ar", "rtl", Locale.forLanguageTag("ar")),
    ENGLISH("en", "ltr", Locale.ENGLISH);

    private final String code;
    private final String direction;
    private final Locale locale;

    Language(String code, String direction, Locale locale) {
        this.code = code;
        this.direction = direction;
        this.locale = locale;
    }

    public String code() {
        return code;
    }

    public String direction() {
        return direction;
    }

    public Locale locale() {
        return locale;
    }

    public static Language fromCode(String code) {
        for (Language language : values()) {
            if (Objects.equals(language.code, code)) {
                return language;
            }
        }
        throw new IllegalArgumentException("Unsupported locale code");
    }
}
