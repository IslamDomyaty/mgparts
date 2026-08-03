package com.mgpartslab.context.web;

import java.util.Locale;

import com.mgpartslab.context.application.ContextInputException;
import com.mgpartslab.context.infrastructure.ContextCoordinator;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(assignableTypes = ContextPageController.class)
public class ContextPageExceptionHandler {

    private final ContextCoordinator coordinator;
    private final MessageSource messages;

    public ContextPageExceptionHandler(ContextCoordinator coordinator, MessageSource messages) {
        this.coordinator = coordinator;
        this.messages = messages;
    }

    @ExceptionHandler(ContextInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handleContextInput(
            ContextInputException exception,
            HttpSession session,
            Model model) {
        Locale locale = LocaleContextHolder.getLocale();
        model.addAttribute("context", coordinator.current(session));
        model.addAttribute("errorCode", exception.code());
        model.addAttribute("errorMessage", messages.getMessage(
                "error." + exception.code(), null, locale));
        return "storefront/context-error";
    }
}
