package com.mgpartslab.shared.web;

import java.util.List;

public record ApiError(
        String code,
        String message,
        String correlationId,
        List<ApiFieldError> fieldErrors) {
}
