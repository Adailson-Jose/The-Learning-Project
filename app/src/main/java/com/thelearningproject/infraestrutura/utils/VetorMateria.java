package com.thelearningproject.infraestrutura.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VetorMateria {

    private int materiaID;
    private List<Integer> perfisID;
    private Integer[] arrayCombinacao;


    public VetorMateria() {
        perfisID = new ArrayList<>();
    }

    public int getMateria() {
        return materiaID;
    }

    public void setMateria(int novo) {
        this.materiaID = novo;
    }

    public Integer[] getArrayCombinacao() {
        return arrayCombinacao;
    }

    public void setArrayCombinacao(Integer[] novo) {
        this.arrayCombinacao = novo;

    }


    public List<Integer> getPerfisID() {
        return perfisID;
    }

    public void setPerfisID(String perfisID) {
        List<String> listaTemp = Arrays.asList(perfisID.split(","));
        List<Integer> listaRetorno = new ArrayList<>();
        for (String s : listaTemp) {
            listaRetorno.add(Integer.valueOf(s));
        }
        this.perfisID = listaRetorno;
    }

    @Override
    public boolean equals(Object o) {
        boolean r = false;
        if (o instanceof VetorMateria) {
            VetorMateria that = (VetorMateria) o;
            r = (this.getMateria() == that.getMateria());
        }
        return r;

    }

    public int hashCode() {
        return (Integer.toString(this.getMateria()).hashCode());
    }


}
