package com.thelearningproject.pessoa.dominio;

import com.thelearningproject.usuario.dominio.Usuario;

/**
 * The type Pessoa.
 */
public class Pessoa {
    private int id;
    private String nome;
    private Usuario usuario;
    private String telefone;


    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param novoId the novo id
     */
    public void setId(int novoId) {
        this.id = novoId;
    }

    /**
     * Gets nome.
     *
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Sets nome.
     *
     * @param novoNome the novo nome
     */
    public void setNome(String novoNome) {
        this.nome = novoNome;
    }

    /**
     * Gets usuario.
     *
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Gets telefone.
     *
     * @return the telefone
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     * Sets telefone.
     *
     * @param novoTelefone the novo telefone
     */
    public void setTelefone(String novoTelefone) {
        this.telefone = novoTelefone;
    }

    /**
     * Sets usuario.
     *
     * @param novoUsuario the novo usuario
     */
    public void setUsuario(Usuario novoUsuario) {
        this.usuario = novoUsuario;
    }

}
