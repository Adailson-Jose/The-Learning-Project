package com.thelearningproject.infraestrutura.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The type Vetor materia.
 */
public class VetorMateria {

    private int materiaID;
    private List<Integer> perfisID;
    private Integer[] arrayCombinacao;


    /**
     * Instantiates a new Vetor materia.
     */
    public VetorMateria() {
        perfisID = new ArrayList<>();
    }

    /**
     * Gets materia.
     *
     * @return the materia
     */
    public int getMateria() {
        return materiaID;
    }

    /**
     * Sets materia.
     *
     * @param novo the novo
     */
    public void setMateria(int novo) {
        this.materiaID = novo;
    }

    /**
     * Get array combinacao integer [ ].
     *
     * @return the integer [ ]
     */
    public Integer[] getArrayCombinacao() {
        return arrayCombinacao;
    }

    /**
     * Sets array combinacao.
     *
     * @param novo the novo
     */
    public void setArrayCombinacao(Integer[] novo) {
        this.arrayCombinacao = novo;

    }


    /**
     * Gets perfis id.
     *
     * @return the perfis id
     */
    public List<Integer> getPerfisID() {
        return perfisID;
    }

    /**
     * Sets perfis id.
     *
     * @param perfisID the perfis id
     */
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
