package main.java.exceptions;

import java.io.Serial;

public class ItemNaoEmprestavelException extends Exception{

    @Serial
    private static final long serialVersionUID = 1L;

    public ItemNaoEmprestavelException(String message) {
        super(message);
    }
}
