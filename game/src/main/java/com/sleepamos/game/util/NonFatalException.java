package com.sleepamos.game.util;

import java.io.Serial;

/**
 * A non-fatal exception that the game can recover from.
 * These errors should be caught, and fatal errors can remain uncaught if needed.
 */
public class NonFatalException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -7942043739634770748L;

    public NonFatalException(String msg) {
        super(msg);
    }

    public NonFatalException(Throwable cause) {
        super(cause);
    }

    public NonFatalException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
