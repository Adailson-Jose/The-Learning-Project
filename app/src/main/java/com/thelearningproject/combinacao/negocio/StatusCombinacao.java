package com.thelearningproject.combinacao.negocio;

/**
 * The enum Status combinacao.
 */
public enum StatusCombinacao {
    /**
     * Ativado status combinacao.
     */
    ATIVADO(0), /**
     * Pendente status combinacao.
     */
    PENDENTE(1), /**
     * Solicitado status combinacao.
     */
    SOLICITADO(2);

    private int valor;

    StatusCombinacao(int i) {
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
