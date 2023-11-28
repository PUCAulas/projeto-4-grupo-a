package main.java.exceptions;

import java.io.Serial;

public class DevolucaoDoEmprestimoException extends Exception{

    @Serial
    private static final long serialVersionUID = 1L;


    public DevolucaoDoEmprestimoException(String message) {
        super(message);
    }
}
