package com.sleepamos.game.exceptions;

import java.io.Serial;

/**
 * A non-fatal exception that the game can recover from.
 * These errors are caught in the game's main loop, but should be handled as early as possible with proper error handling and displaying instead of being allowed to propagate.
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
