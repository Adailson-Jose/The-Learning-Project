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

    public void setId(int novoId) {
        this.id = novoId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String novoNome) {
        this.nome = novoNome;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario novoUsuario) {
        this.usuario = novoUsuario;
    }

}
