package com.thelearningproject.infraestrutura.utils;

/**
 * Criado por Nícolas on 27/07/2017.
 */

public enum Status {
    ATIVADO(0), DESATIVADO(1), PENDENTE(2);

    private int valor;

    Status(int i) {
        this.valor = i;
    }

    public int getValor() {
        return this.valor;
    }

}
