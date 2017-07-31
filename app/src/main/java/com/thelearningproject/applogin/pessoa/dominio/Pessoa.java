package com.thelearningproject.applogin.pessoa.dominio;

import com.thelearningproject.applogin.usuario.dominio.Usuario;

/**
 * Created by nicolas on 30/07/2017.
 */

public class Pessoa {
    private int id;
    private String nome;
    private Usuario usuario;
    private int usuarioID;

    public Pessoa() {
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(int usuarioID) {
        this.usuarioID = usuarioID;
    }

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
    public String toString() {
        return nome.toString();
    }
}
