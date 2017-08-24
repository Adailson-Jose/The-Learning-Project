package com.thelearningproject.infraestrutura.utils;

import java.util.ArrayList;

public class FrequenciaMateria {

    private int materiaID;
    private ArrayList perfisID;
    private Integer frequencia;


    public FrequenciaMateria() {
        this.frequencia = 0;
        perfisID = new ArrayList();
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


    public ArrayList getPerfisID() {
        return perfisID;
    }

    public void setPerfisID(ArrayList perfisID) {
        this.perfisID = perfisID;
    }

    public void addPerfil(int perfilid) {
        this.perfisID.add(perfilid);
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
