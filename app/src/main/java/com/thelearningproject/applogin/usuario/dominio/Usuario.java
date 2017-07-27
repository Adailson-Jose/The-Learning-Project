package com.thelearningproject.applogin.usuario.dominio;

/**
 * Created by Ebony Marques on 17/07/2017.
 */

public class Usuario {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private Boolean desativado = false;

    public Usuario() {
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public Boolean getDesativado() {
        return desativado;
    }

    public void setDesativado(Boolean desativado) {
        this.desativado = desativado;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return nome.toString();
    }
}
