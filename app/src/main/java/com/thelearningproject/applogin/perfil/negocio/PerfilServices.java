package com.thelearningproject.applogin.perfil.negocio;

import android.content.Context;

import com.thelearningproject.applogin.perfil.dominio.Perfil;
import com.thelearningproject.applogin.perfil.persistencia.Banco;
import com.thelearningproject.applogin.perfil.persistencia.PerfilDAO;

/**
 * Created by Ebony Marques on 26/07/2017.
 */

public class PerfilServices {
    private static PerfilServices instancia;
    private static PerfilDAO persistencia;
    private static Banco banco;

    public PerfilServices(Context contexto) {
        this.banco = Banco.getInstancia(contexto);
        this.persistencia = PerfilDAO.getInstance(contexto);
    }

    public static PerfilServices getInstancia(Context contexto) {
        if (instancia == null) {
            instancia = new PerfilServices(contexto);
        }

        return instancia;
    }

    public void inserirPerfil(Perfil perfil) {
        persistencia.inserir(perfil);
    }

    public Perfil retornaPerfil(String id) {
        Perfil perfil = persistencia.retornaPerfil(id);

        return perfil;
    }
}
