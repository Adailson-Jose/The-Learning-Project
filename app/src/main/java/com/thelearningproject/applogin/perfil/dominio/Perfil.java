package com.thelearningproject.applogin.perfil.dominio;

import com.thelearningproject.applogin.estudo.dominio.Materia;
import java.util.ArrayList;

/**
 * Created by Ebony Marques on 26/07/2017.
 */

public class Perfil {
    private int id;
    private int usuario;
    private String descricao;
    private ArrayList<Materia> habilidades = new ArrayList<Materia>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
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

    public Materia getHabilidadePrincipal() {
        return habilidades.get(0);
    }
}
