package com.mgpartslab.context.application;

public final class ContextInputException extends RuntimeException {

    private final String code;
    private final String field;

    public ContextInputException(String code, String field, String message) {
        super(message);
        this.code = code;
        this.field = field;
    }

    public String code() {
        return code;
    }

    public String field() {
        return field;
    }
}
