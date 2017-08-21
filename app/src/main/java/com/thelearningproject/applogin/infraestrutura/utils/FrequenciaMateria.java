package com.thelearningproject.applogin.infraestrutura.utils;

/**
 * Criado por Pichau em 17/08/2017.
 */

public class FrequenciaMateria {

    private int materiaID;
    private Integer frequencia;

    public FrequenciaMateria() {
        this.frequencia = 0;
    }

    public int getMateria() {
        return materiaID;
    }

    public void setMateria(int novo) {
        this.materiaID = novo;
    }

    public Integer getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(Integer novo) {
        this.frequencia += novo;

    }

    @Override
    public boolean equals(Object o) {
        boolean r = false;
        if (o instanceof FrequenciaMateria) {
            FrequenciaMateria that = (FrequenciaMateria) o;
            r = (this.getMateria() == that.getMateria());
            if (r) {
                that.setFrequencia(this.getFrequencia());
            }
        }
        return r;

    }

    @Override
    public String toString() {
        return "FrequenciaMateria{" +
                "materiaID=" + materiaID +
                ", frequencia=" + frequencia +
                '}';
    }

    public int hashCode() {
        return (Integer.toString(this.getMateria()).hashCode());
    }
}
