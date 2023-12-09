package main.java.exceptions;

import java.io.Serial;

public class SugestaoException extends Exception{
    @Serial
    private static final long serialVersionUID = 1L;


    public SugestaoException(String message) {
        super(message);
    }
}
