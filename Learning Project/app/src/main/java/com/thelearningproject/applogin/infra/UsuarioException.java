package com.thelearningproject.applogin.infra;

/**
 * Created by Pichau on 20/07/2017.
 */

public class UsuarioException extends Exception {
    public UsuarioException(String erro){
        super(erro);
    }

    public UsuarioException(String erro, Throwable causa){
        super(erro,causa);
    }

}
