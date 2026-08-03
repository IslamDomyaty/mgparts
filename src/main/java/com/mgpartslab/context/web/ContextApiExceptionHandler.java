package com.mgpartslab.context.web;

import java.util.List;

import com.mgpartslab.context.application.ContextInputException;
import com.mgpartslab.shared.observability.CorrelationIds;
import com.mgpartslab.shared.web.ApiError;
import com.mgpartslab.shared.web.ApiFieldError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice(assignableTypes = ContextApiController.class)
public class ContextApiExceptionHandler {

    @ExceptionHandler(ContextInputException.class)
    ResponseEntity<ApiError> handleContextInput(
            ContextInputException exception,
            HttpServletRequest request) {
        ApiError error = new ApiError(
                exception.code(),
                exception.getMessage(),
                correlationId(request),
                List.of(new ApiFieldError(exception.field(), exception.code())));
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class})
    ResponseEntity<ApiError> handleMalformedRequest(Exception exception, HttpServletRequest request) {
        ApiError error = new ApiError(
                "REQUEST_MALFORMED",
                "The request could not be parsed",
                correlationId(request),
                List.of());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    private static String correlationId(HttpServletRequest request) {
        Object value = request.getAttribute(CorrelationIds.REQUEST_ATTRIBUTE);
        return value instanceof String correlationId
                ? correlationId
                : CorrelationIds.currentOrCreate();
    }
}
