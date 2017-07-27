package com.thelearningproject.applogin.usuario.dominio;

/**
 * Created by nicolas on 27/07/2017.
 */

public enum Status {
    ATIVADO(0), DESATIVADO(1);

    private int valor;

    private Status(int i){
        this.valor = i;
    }
    public int getValor(){
        return this.valor;
    }

}
