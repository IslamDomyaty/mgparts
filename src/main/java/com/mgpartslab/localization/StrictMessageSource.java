package com.mgpartslab.localization;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.time.Clock;
import java.time.Instant;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.mgpartslab.shared.observability.CorrelationIds;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.core.io.ClassPathResource;

public final class StrictMessageSource extends AbstractMessageSource
        implements ApplicationEventPublisherAware {

    private static final Set<String> SUPPORTED_LANGUAGES = Set.of("ar", "en");

    private final Map<String, Properties> bundles;
    private final Clock clock;
    private final Set<String> emittedDiagnostics = ConcurrentHashMap.newKeySet();
    private ApplicationEventPublisher eventPublisher;

    public StrictMessageSource() {
        this(Clock.systemUTC());
    }

    StrictMessageSource(Clock clock) {
        setUseCodeAsDefaultMessage(false);
        this.clock = clock;
        this.bundles = Map.of(
                "ar", load("i18n/messages_ar.properties"),
                "en", load("i18n/messages_en.properties"));
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        String message = lookup(code, locale);
        return message == null ? null : createMessageFormat(message, locale);
    }

    @Override
    protected String resolveCodeWithoutArguments(String code, Locale locale) {
        return lookup(code, locale);
    }

    private String lookup(String code, Locale locale) {
        String language = supportedLanguage(locale);
        String message = bundles.get(language).getProperty(code);
        if (message == null) {
            emitDiagnosticOnce(language, code);
        }
        return message;
    }

    private void emitDiagnosticOnce(String language, String code) {
        String diagnosticKey = language + ':' + code;
        if (eventPublisher != null && emittedDiagnostics.add(diagnosticKey)) {
            eventPublisher.publishEvent(new TranslationMissingEvent(
                    UUID.randomUUID(),
                    1,
                    clock.instant(),
                    CorrelationIds.currentOrCreate(),
                    language,
                    code));
        }
    }

    private static String supportedLanguage(Locale locale) {
        String language = locale == null ? "en" : locale.getLanguage();
        return SUPPORTED_LANGUAGES.contains(language) ? language : "en";
    }

    private static Properties load(String path) {
        Properties properties = new Properties();
        ClassPathResource resource = new ClassPathResource(path);
        try (InputStreamReader reader = new InputStreamReader(
                resource.getInputStream(), StandardCharsets.UTF_8)) {
            properties.load(reader);
            return properties;
        } catch (IOException exception) {
            throw new IllegalStateException("Cannot load required translation bundle: " + path, exception);
        }
    }
}
