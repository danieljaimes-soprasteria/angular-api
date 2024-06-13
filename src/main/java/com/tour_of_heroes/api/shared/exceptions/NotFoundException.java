package com.tour_of_heroes.api.shared.exceptions;

public class NotFoundException extends Exception {
    public final static String MESSAGE_STRING = "Not found";
    private static final long serialVersionUID = 1L;

    public NotFoundException() {
        this(MESSAGE_STRING);
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(Throwable cause) {
        this(MESSAGE_STRING, cause);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(String message, Throwable cause, boolean enableSuppression,
                             boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
