package main.java.exceptions;

import java.io.Serial;

public class EmprestimoLimiteException extends Exception{

    @Serial
    private static final long serialVersionUID = 1L;

    public EmprestimoLimiteException(String message) {
        super(message);
    }
}
