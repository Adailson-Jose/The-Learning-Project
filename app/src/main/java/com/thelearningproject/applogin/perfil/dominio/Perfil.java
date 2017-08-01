package com.thelearningproject.applogin.perfil.dominio;

import com.thelearningproject.applogin.estudo.dominio.Materia;
import com.thelearningproject.applogin.pessoa.dominio.Pessoa;
import com.thelearningproject.applogin.usuario.dominio.Usuario;

import java.util.ArrayList;

/**
 * Created by Ebony Marques on 26/07/2017.
 */

public class Perfil {
    private int id;
    private Pessoa pessoa;
    private String descricao;
    private ArrayList<Materia> habilidades = new ArrayList<Materia>();
    private ArrayList<Materia> necessidades = new ArrayList<Materia>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public ArrayList<Materia> getHabilidades() {
        return habilidades;
    }

    public void addHabilidade(Materia nova) {
        habilidades.add(nova);
    }

    public ArrayList<Materia> getNecessidades() { return necessidades; }

    public void addNecessidade(Materia nova) { necessidades.add(nova); }
}
