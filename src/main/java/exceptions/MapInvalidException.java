package main.java.exceptions;

import java.io.Serializable;

/**
 * Defined exception is thrown by this class
 */
public class MapInvalidException extends Exception implements Serializable {

    /**
     * Custom Exception message shown by this class.
     *
     * @param p_message is used to show invalid message
     */
    public MapInvalidException(String p_message) {
        super(p_message);
    }
}
