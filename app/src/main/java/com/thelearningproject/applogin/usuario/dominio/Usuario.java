package com.thelearningproject.applogin.usuario.dominio;

import com.thelearningproject.applogin.infraestrutura.utils.Status;

/**
 * Created by Ebony Marques on 17/07/2017.
 */

public class Usuario {
    private int id;
    private String email;
    private String senha;
    private Status status;

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

    public String getSenha() {
        return senha;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setId(int id) {
        this.id = id;
    }

}
