package com.thelearningproject.combinacao.negocio;

import android.content.Context;

import com.thelearningproject.combinacao.dominio.Combinacao;
import com.thelearningproject.combinacao.persistencia.CombinacaoDAO;
import com.thelearningproject.perfil.dominio.Perfil;

import java.util.ArrayList;

/**
 * Criado por Pichau em 17/08/2017.
 */

public final class CombinacaoServices {
    private static CombinacaoServices instancia;
    private CombinacaoDAO persistencia;

    private CombinacaoServices(Context context) {
        this.persistencia = CombinacaoDAO.getInstancia(context);
    }

    public static CombinacaoServices getInstancia(Context contexto) {
        if (instancia == null) {
            instancia = new CombinacaoServices(contexto);
        }

        return instancia;
    }

    public  ArrayList<Combinacao> retornaCombinacoes(Perfil perfil){
        ArrayList<Combinacao> combinacoes = new ArrayList<>();
        combinacoes.addAll(persistencia.retornaCombinacoesTipo(perfil.getId(), StatusCombinacao.ATIVADO.getValor()));
        return combinacoes;
    }

    public ArrayList<Combinacao> retornaCombinacoesAtivas(Perfil perfil) {
        ArrayList<Combinacao> combinacoes = new ArrayList<>();
        combinacoes.addAll(persistencia.retornaCombinacoesTipo(perfil.getId(), StatusCombinacao.ATIVADO.getValor()));
        return combinacoes;
    }

    public ArrayList<Combinacao> retornaCombinacoesPendentes(Perfil perfil) {
        ArrayList<Combinacao> combinacoes = new ArrayList<>();
        combinacoes.addAll(persistencia.retornaCombinacoesTipo(perfil.getId(), StatusCombinacao.PENDENTE.getValor()));
        return combinacoes;
    }

    public ArrayList<Combinacao> retornaCombinacoesSolicitadas(Perfil perfil) {
        ArrayList<Combinacao> combinacoes = new ArrayList<>();
        combinacoes.addAll(persistencia.retornaCombinacoesTipo(perfil.getId(), StatusCombinacao.SOLICITADO.getValor()));
        return combinacoes;
    }

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

    public void atualizaCombinacao(Combinacao combinacao, int tipo, Perfil perfil) {
        ArrayList<Combinacao> combinacaos = perfil.getCombinacoes();
        combinacaos.get(combinacaos.indexOf(combinacao)).setStatus(tipo);
        perfil.setCombinacoes(combinacaos);
        persistencia.atualizaStatus(combinacao);
    }

    public void removerCombinacao(Perfil perfil, Combinacao combinacao) {
        ArrayList<Combinacao> combinacaos = perfil.getCombinacoes();
        combinacaos.remove(combinacao);
        perfil.setCombinacoes(combinacaos);
        persistencia.removeCombinacao(combinacao);
    }

}
