package main.java.exceptions;

import java.io.Serial;

public class UsuarioAutenticadoException extends Exception{


    @Serial
    private static final long serialVersionUID = 1L;


    public UsuarioAutenticadoException(String message) {
        super(message);
    }
}
