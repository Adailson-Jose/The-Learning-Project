package com.thelearningproject.combinacao.negocio;

import android.content.Context;

import com.thelearningproject.combinacao.dominio.Combinacao;
import com.thelearningproject.combinacao.persistencia.CombinacaoDAO;
import com.thelearningproject.perfil.dominio.Perfil;

import java.util.ArrayList;

/**
 * The type Combinacao services.
 */
public final class CombinacaoServices {
    private static CombinacaoServices instancia;
    private CombinacaoDAO persistencia;

    private CombinacaoServices(Context context) {
        this.persistencia = CombinacaoDAO.getInstancia(context);
    }

    /**
     * Gets instancia.
     *
     * @param contexto the contexto
     * @return the instancia
     */
    public static CombinacaoServices getInstancia(Context contexto) {
        if (instancia == null) {
            instancia = new CombinacaoServices(contexto);
        }

        return instancia;
    }

    /**
     * Retorna combinacoes array list.
     *
     * @param perfil the perfil
     * @return the array list
     */
    public  ArrayList<Combinacao> retornaCombinacoes(Perfil perfil){
        ArrayList<Combinacao> combinacoes = new ArrayList<>();
        combinacoes.addAll(persistencia.retornaCombinacoes(perfil.getId()));
        return combinacoes;
    }

    /**
     * Retorna combinacoes ativas array list.
     *
     * @param perfil the perfil
     * @return the array list
     */
    public ArrayList<Combinacao> retornaCombinacoesAtivas(Perfil perfil) {
        ArrayList<Combinacao> combinacoes = new ArrayList<>();
        combinacoes.addAll(persistencia.retornaCombinacoesTipo(perfil.getId(), StatusCombinacao.ATIVADO.getValor()));
        return combinacoes;
    }

    /**
     * Retorna combinacoes pendentes array list.
     *
     * @param perfil the perfil
     * @return the array list
     */
    public ArrayList<Combinacao> retornaCombinacoesPendentes(Perfil perfil) {
        ArrayList<Combinacao> combinacoes = new ArrayList<>();
        combinacoes.addAll(persistencia.retornaCombinacoesTipo(perfil.getId(), StatusCombinacao.PENDENTE.getValor()));
        return combinacoes;
    }

    /**
     * Retorna combinacoes solicitadas array list.
     *
     * @param perfil the perfil
     * @return the array list
     */
    public ArrayList<Combinacao> retornaCombinacoesSolicitadas(Perfil perfil) {
        ArrayList<Combinacao> combinacoes = new ArrayList<>();
        combinacoes.addAll(persistencia.retornaCombinacoesTipo(perfil.getId(), StatusCombinacao.SOLICITADO.getValor()));
        return combinacoes;
    }

    /**
     * Requerer combinacao.
     *
     * @param perfil1 the perfil 1
     * @param perfil2 the perfil 2
     */
    public void requererCombinacao(Perfil perfil1, Perfil perfil2) {
        Combinacao combinacao = new Combinacao();
        Combinacao combinacao1 = new Combinacao();
        combinacao.setPerfil1(perfil1.getId());
        combinacao.setPerfil2(perfil2.getId());
        combinacao.setStatus(StatusCombinacao.PENDENTE.getValor());
        combinacao1.setPerfil1(perfil2.getId());
        combinacao1.setPerfil2(perfil1.getId());
        combinacao1.setStatus(StatusCombinacao.SOLICITADO.getValor());
        perfil1.addCombinacoes(combinacao);
        perfil2.addCombinacoes(combinacao1);
        persistencia.inserir(combinacao);
        persistencia.inserir(combinacao1);
    }

    /**
     * Atualiza combinacao.
     *
     * @param combinacao the combinacao
     * @param tipo       the tipo
     * @param perfil     the perfil
     */
    public void atualizaCombinacao(Combinacao combinacao, int tipo, Perfil perfil) {
        ArrayList<Combinacao> combinacaos = perfil.getCombinacoes();
        combinacaos.get(combinacaos.indexOf(combinacao)).setStatus(tipo);
        perfil.setCombinacoes(combinacaos);
        persistencia.atualizaStatus(combinacao);
    }

    /**
     * Remover combinacao.
     *
     * @param perfil     the perfil
     * @param combinacao the combinacao
     */
    public void removerCombinacao(Perfil perfil, Combinacao combinacao) {
        ArrayList<Combinacao> combinacaos = perfil.getCombinacoes();
        combinacaos.remove(combinacao);
        perfil.setCombinacoes(combinacaos);
        persistencia.removeCombinacao(combinacao);
    }

}
