package com.mgpartslab.localization;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.web.servlet.LocaleResolver;

@Configuration(proxyBeanMethods = false)
public class LocalizationConfiguration {

    @Bean(name = AbstractApplicationContext.MESSAGE_SOURCE_BEAN_NAME)
    static MessageSource messageSource() {
        return new StrictMessageSource();
    }

    @Bean
    LocaleResolver localeResolver() {
        return new ContextLocaleResolver();
    }
}
