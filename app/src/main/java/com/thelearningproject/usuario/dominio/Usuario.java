package com.thelearningproject.usuario.dominio;

import com.thelearningproject.infraestrutura.utils.Status;

/**
 * The type Usuario.
 */
public class Usuario {
    private int id;
    private String email;
    private String senha;
    private Status status;

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param novoEmail the novo email
     */
    public void setEmail(String novoEmail) {
        this.email = novoEmail;
    }

    /**
     * Gets senha.
     *
     * @return the senha
     */
    public String getSenha() {
        return senha;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param novoStatus the novo status
     */
    public void setStatus(Status novoStatus) {
        this.status = novoStatus;
    }

    /**
     * Sets senha.
     *
     * @param novaSenha the nova senha
     */
    public void setSenha(String novaSenha) {
        this.senha = novaSenha;
    }

    /**
     * Sets id.
     *
     * @param novoId the novo id
     */
    public void setId(int novoId) {
        this.id = novoId;
    }

}
