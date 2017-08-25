package com.thelearningproject.combinacao.negocio;

/**
 * Created by Heitor on 25/08/2017.
 */

public enum StatusCombinacao {
    ATIVADO(0),PENDENTE(1),SOLICITADO(2);

    private int valor;

    StatusCombinacao(int i) {
        this.valor = i;
    }

    public int getValor() {
        return this.valor;
    }
}
