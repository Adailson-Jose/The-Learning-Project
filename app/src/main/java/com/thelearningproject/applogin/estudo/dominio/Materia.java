package com.thelearningproject.applogin.estudo.dominio;

import com.thelearningproject.applogin.infra.Status;

/**
 * Created by Ebony Marques on 26/07/2017.
 */

public class Materia {
    private int id;
    private int valor;
    private String nome;
    private String descricao;
    private Status status = Status.ATIVA;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
