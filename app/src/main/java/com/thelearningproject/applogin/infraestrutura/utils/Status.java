package com.thelearningproject.applogin.infraestrutura.utils;

/**
 * Created by nicolas on 27/07/2017.
 */

public enum Status {
    ATIVADO(0), DESATIVADO(1);

    private int valor;

    Status(int i){
        this.valor = i;
    }
    public int getValor(){
        return this.valor;
    }

}
