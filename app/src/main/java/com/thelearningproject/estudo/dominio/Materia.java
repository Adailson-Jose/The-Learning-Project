package com.thelearningproject.estudo.dominio;

/**
 * Criado por Ebony Marques on 26/07/2017.
 */

public class Materia {
    private int id;
    private String nome;

    public int getId() {
        return id;
    }

    public void setId(int novoId) {
        this.id = novoId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String novoNome) {
        this.nome = novoNome;
    }

    public boolean equals(Object o) {
        boolean r = false;
        if (o instanceof Materia) {
            Materia that = (Materia) o;
            r = (this.getNome().equals(that.getNome()));
        }
        return r;
    }

    public int hashCode() {
        return (Integer.toString(this.getId()).hashCode());
    }

    @Override
    public String toString() {
        return this.getNome();
    }
}
