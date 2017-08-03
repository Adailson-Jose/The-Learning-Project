package com.thelearningproject.applogin.pessoa.dominio;

import com.thelearningproject.applogin.usuario.dominio.Usuario;

/**
 * Criado por Nícolas on 30/07/2017.
 */

public class Pessoa {
    private int id;
    private String nome;
    private Usuario usuario;

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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
