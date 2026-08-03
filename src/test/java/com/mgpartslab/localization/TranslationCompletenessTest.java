package com.mgpartslab.localization;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.NoSuchMessageException;

class TranslationCompletenessTest {

    @Test
    void englishAndArabicContainTheSameNonEmptyKeys() throws IOException {
        Properties english = load("/i18n/messages_en.properties");
        Properties arabic = load("/i18n/messages_ar.properties");

        assertThat(arabic.stringPropertyNames())
                .containsExactlyInAnyOrderElementsOf(english.stringPropertyNames());
        assertThat(english.values()).allSatisfy(value -> assertThat(value.toString()).isNotBlank());
        assertThat(arabic.values()).allSatisfy(value -> assertThat(value.toString()).isNotBlank());
    }

    @Test
    void missingKeyDoesNotLeakAsVisibleTextAndEmitsOneDiagnostic() {
        List<Object> events = new ArrayList<>();
        ApplicationEventPublisher publisher = events::add;
        StrictMessageSource source = new StrictMessageSource(Clock.fixed(
                Instant.parse("2026-08-03T12:00:00Z"), ZoneOffset.UTC));
        source.setApplicationEventPublisher(publisher);

        assertThatThrownBy(() -> source.getMessage("missing.shared.key", null, Locale.ENGLISH))
                .isInstanceOf(NoSuchMessageException.class);

        assertThat(events).singleElement().isInstanceOf(TranslationMissingEvent.class);
        TranslationMissingEvent event = (TranslationMissingEvent) events.getFirst();
        assertThat(event.messageCode()).isEqualTo("missing.shared.key");
        assertThat(event.locale()).isEqualTo("en");
        assertThat(event.timestamp()).isEqualTo(Instant.parse("2026-08-03T12:00:00Z"));
    }

    private static Properties load(String path) throws IOException {
        Properties properties = new Properties();
        try (InputStreamReader reader = new InputStreamReader(
                TranslationCompletenessTest.class.getResourceAsStream(path),
                StandardCharsets.UTF_8)) {
            properties.load(reader);
        }
        return properties;
    }
}
