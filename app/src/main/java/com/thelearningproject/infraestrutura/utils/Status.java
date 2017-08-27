package com.thelearningproject.infraestrutura.utils;

/**
 * The enum Status.
 */
public enum Status {
    /**
     * Ativado status.
     */
    ATIVADO(0),
    /**
     * Desativado status.
     */
    DESATIVADO(1),
    /**
     * Pendente status.
     */
    PENDENTE(2);

    private int valor;

    Status(int i) {
        this.valor = i;
    }

    /**
     * Gets valor.
     *
     * @return the valor
     */
    public int getValor() {
        return this.valor;
    }

}
