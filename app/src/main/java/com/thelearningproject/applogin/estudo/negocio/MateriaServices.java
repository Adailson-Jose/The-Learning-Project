package com.thelearningproject.applogin.estudo.negocio;

import android.content.Context;

import com.thelearningproject.applogin.estudo.dominio.Materia;
import com.thelearningproject.applogin.estudo.persistencia.MateriaDAO;

/**
 * Created by Pichau on 26/07/2017.
 */

public class MateriaServices {
    private static MateriaServices instancia;
    private MateriaDAO persistencia;

    public MateriaServices(Context context){
        this.persistencia = MateriaDAO.getInstance(context);
    }

    public static MateriaServices getInstancia(Context context){
        if(instancia == null){
            instancia = new MateriaServices(context);
        }
        return instancia;
    }

    private Materia inserirMateria(Materia materia){
        persistencia.inserir(materia);
        return persistencia.consultaNome(materia.getNome());
    }

    public Materia cadastraMateria(Materia materia){
        Materia novamateria = persistencia.consultaNome(materia.getNome());
        if (novamateria== null){
            novamateria = inserirMateria(materia);
        }
        return novamateria;
    }

    public Materia consultar(int id){ return persistencia.consultar(id); }

    public Materia consultarNome(String nome){
        return persistencia.consultaNome(nome);
    }

    public boolean verificaExistencia(String nome){
        Boolean flag = false;
        if(persistencia.consultaNome(nome) != null){
            flag = true;
        }
        return flag;
    }


}
