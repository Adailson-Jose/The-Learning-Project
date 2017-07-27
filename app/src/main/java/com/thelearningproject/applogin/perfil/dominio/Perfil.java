package com.thelearningproject.applogin.perfil.dominio;

import com.thelearningproject.applogin.estudo.dominio.Materia;
import com.thelearningproject.applogin.usuario.dominio.Usuario;

import java.util.ArrayList;

/**
 * Created by Ebony Marques on 26/07/2017.
 */

public class Perfil {
    private int id;
    private Usuario usuario;
    private int usuarioID;
    private String descricao;
    private ArrayList<Materia> habilidades = new ArrayList<Materia>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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


    @Override
    public String toString(){
        return "ID do Perfil: " + this.getId();
    }
}
