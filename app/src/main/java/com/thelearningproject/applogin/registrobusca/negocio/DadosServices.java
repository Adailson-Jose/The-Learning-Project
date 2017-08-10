package com.thelearningproject.applogin.registrobusca.negocio;

import android.content.Context;

import com.thelearningproject.applogin.estudo.dominio.Materia;
import com.thelearningproject.applogin.estudo.negocio.MateriaServices;
import com.thelearningproject.applogin.perfil.dominio.Perfil;
import com.thelearningproject.applogin.perfil.negocio.PerfilServices;
import com.thelearningproject.applogin.registrobusca.persistencia.DadosBuscaDAO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Criado por Pichau em 02/08/2017.
 */

public final class DadosServices {
    private static DadosServices instancia;
    private DadosBuscaDAO dadosDAO;
    private MateriaServices materiaServices;
    private PerfilServices perfilServices;

    public static DadosServices getInstancia(Context contexto) {
        if (instancia == null) {
            instancia = new DadosServices(contexto);
        }
        return instancia;
    }

    private DadosServices(Context context) {
        this.dadosDAO = DadosBuscaDAO.getInstancia(context);
        this.materiaServices = MateriaServices.getInstancia(context);
        this.perfilServices = PerfilServices.getInstancia(context);
    }

    public void cadastraBusca(Perfil perfil, String entrada) {
        if (!dadosDAO.verificaExistencia(perfil.getId(), entrada)) {
            dadosDAO.insereBusca(perfil.getId(), entrada);
        }
    }

    public ArrayList<Perfil> recomendaMateria(Perfil perfil, String entrada) {
        ArrayList<String> buscas = dadosDAO.retornaFrequencia(perfil.getId(), entrada);
        ArrayList<Materia> materias = new ArrayList<>();
        ArrayList<Perfil> listaperfil = new ArrayList<>();
        Set<Perfil> setperfil = new HashSet<>();
        for (String s : buscas) {
            Materia materia = materiaServices.consultarNome(s);
            if (materia != null) {
                materias.add(materia);
            }
        }
        if (!materias.isEmpty()) {
            for (Materia m : materias) {
                ArrayList<Perfil> tempListPerfil = perfilServices.listarPerfil(m);
                if (!tempListPerfil.isEmpty()) {
                    setperfil.addAll(tempListPerfil);
                }
            }
            listaperfil.addAll(setperfil);
        }

        return listaperfil;
    }
}
