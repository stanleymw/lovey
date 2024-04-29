package com.sleepamos.game.util;

/**
 * A non-fatal exception that the game can recover from.
 * These errors should be caught, and fatal errors can remain uncaught if needed.
 */
public class NonFatalException extends RuntimeException {
    public NonFatalException(String msg) {
        super(msg);
    }

    public NonFatalException() {
        this("");
    }
}
