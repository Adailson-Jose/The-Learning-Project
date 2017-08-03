package com.thelearningproject.applogin.estudo.dominio;

/**
 * Criado por Ebony Marques on 26/07/2017.
 */

public class Materia {
    private int id;
    private String nome;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o){
        boolean r = false;
        if(o instanceof Materia){
            Materia that = (Materia) o;
            r = (this.getId() == that.getId());
        }
        return r;
    }

    @Override
    public int hashCode(){
        return (""+this.getId()).hashCode();
    }

}
