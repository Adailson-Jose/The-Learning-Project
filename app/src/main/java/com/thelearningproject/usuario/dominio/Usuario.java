package com.thelearningproject.usuario.dominio;

import com.thelearningproject.infraestrutura.utils.Status;

/**
 * Criado por Ebony Marques on 17/07/2017.
 */

public class Usuario {
    private int id;
    private String email;
    private String senha;
    private Status status;

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String novoEmail) {
        this.email = novoEmail;
    }

    public String getSenha() {
        return senha;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status novoStatus) {
        this.status = novoStatus;
    }

    public void setSenha(String novaSenha) {
        this.senha = novaSenha;
    }

    public void setId(int novoId) {
        this.id = novoId;
    }

}
