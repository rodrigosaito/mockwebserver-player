package com.rodrigosaito.mockwebserver.player;

public class TapeNotFoundException extends RuntimeException {
    public TapeNotFoundException() {
    }

    public TapeNotFoundException(final String message) {
        super(message);
    }

    public TapeNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public TapeNotFoundException(final Throwable cause) {
        super(cause);
    }

    public TapeNotFoundException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
