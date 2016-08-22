package com.thesis.crud;

/**
 * Created by Alex on 18-Aug-16.
 */
public class PersistException extends Exception{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Class constructor specifying String message to create.
     *
     * @param message
     *            Message to display.
     */
    public PersistException(final String message) {
        super(message);
    }

    /**
     * Class constructor specifying String message to create. Used when
     * rethrowing exceptions.
     *
     * @param message message to display.
     * @param cause caught exception
     */
    public PersistException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
