package main.java.exceptions;

import java.io.Serial;

public class ItemIndisponivelException extends Exception{

    @Serial
    private static final long serialVersionUID = 1L;

    public ItemIndisponivelException(String message) {
        super(message);
    }


}
