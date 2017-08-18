package com.thelearningproject.applogin.combinacao.negocio;

import android.content.Context;

import com.thelearningproject.applogin.combinacao.dominio.Combinacao;
import com.thelearningproject.applogin.combinacao.persistencia.CombinacaoDAO;
import com.thelearningproject.applogin.perfil.dominio.Perfil;

import java.util.ArrayList;

/**
 * Created by Pichau on 17/08/2017.
 */

public class CombinacaoServices {
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

    public void iniciarChat() {

    }

    public ArrayList<Combinacao> retornaCombinacoesAtivas(Perfil perfil) {
        ArrayList<Combinacao> combinacoes = new ArrayList<>();
        combinacoes.addAll(persistencia.retornaCombinacoes(perfil.getId(), 1));
        return combinacoes;
    }

    public ArrayList<Combinacao> retornaCombinacoesPendentes(Perfil perfil) {
        ArrayList<Combinacao> combinacoes = new ArrayList<>();
        combinacoes.addAll(persistencia.retornaCombinacoes(perfil.getId(), 0));
        return combinacoes;
    }

    public void inserirCombinacao(Perfil perfil1, Perfil perfil2) {
        Combinacao combinacao = new Combinacao();
        combinacao.setPerfil1(perfil1.getId());
        combinacao.setPerfil2(perfil2.getId());
        combinacao.setStatus(0);
        perfil1.addCombinacoes(combinacao);
        persistencia.inserir(combinacao);
    }

    public void atualizaCombinacao(Combinacao combinacao, int tipo) {
        combinacao.setStatus(tipo);
        persistencia.atualizaStatus(combinacao);
    }

    public void removerCombinacao(Perfil perfil, Combinacao combinacao) {
        ArrayList<Combinacao> combinacaos = perfil.getCombinacoes();
        combinacaos.remove(combinacao);
        perfil.setCombinacoes(combinacaos);
        persistencia.removeCombinacao(combinacao);
    }

}
