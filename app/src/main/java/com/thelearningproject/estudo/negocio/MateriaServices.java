package com.thelearningproject.estudo.negocio;

import android.content.Context;

import com.thelearningproject.estudo.dominio.Materia;
import com.thelearningproject.estudo.persistencia.MateriaDAO;

import java.util.ArrayList;

/**
 * The type Materia services.
 */
public class MateriaServices {
    private static MateriaServices instancia;
    private MateriaDAO persistencia;

    /**
     * Instantiates a new Materia services.
     *
     * @param context the context
     */
    public MateriaServices(Context context) {
        this.persistencia = MateriaDAO.getInstancia(context);
    }

    /**
     * Gets instancia.
     *
     * @param context the context
     * @return the instancia
     */
    public static MateriaServices getInstancia(Context context) {
        if (instancia == null) {
            instancia = new MateriaServices(context);
        }
        return instancia;
    }

    private Materia inserirMateria(Materia materia) {
        persistencia.inserir(materia);
        return persistencia.consultaNome(materia.getNome());
    }

    /**
     * Cadastra materia materia.
     *
     * @param materia the materia
     * @return the materia
     */
    public Materia cadastraMateria(Materia materia) {
        Materia novamateria = persistencia.consultaNome(materia.getNome());
        if (novamateria == null) {
            novamateria = inserirMateria(materia);
        }
        return novamateria;
    }

    /**
     * Consultar materia.
     *
     * @param id the id
     * @return the materia
     */
    public Materia consultar(int id) {
        return persistencia.consultar(id);
    }

    /**
     * Consultar nome materia.
     *
     * @param nome the nome
     * @return the materia
     */
    public Materia consultarNome(String nome) {
        return persistencia.consultaNome(nome);
    }

    /**
     * Retorna lista array list.
     *
     * @param nome the nome
     * @return the array list
     */
    public ArrayList<String> retornaLista(String nome) {
        return persistencia.retornaMateriasNome(nome);
    }


}
