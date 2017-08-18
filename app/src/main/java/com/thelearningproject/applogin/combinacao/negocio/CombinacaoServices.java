package com.thelearningproject.applogin.combinacao.negocio;

import android.content.Context;

import com.thelearningproject.applogin.combinacao.dominio.Combinacao;
import com.thelearningproject.applogin.combinacao.persistencia.CombinacaoDAO;
import com.thelearningproject.applogin.perfil.dominio.Perfil;
import com.thelearningproject.applogin.perfil.negocio.PerfilServices;

import java.util.ArrayList;

/**
 * Created by Pichau on 17/08/2017.
 */

public class CombinacaoServices {
    private static CombinacaoServices instancia;
    private CombinacaoDAO persistencia;
    private PerfilServices perfilServices;

    private CombinacaoServices(Context context) {
        this.persistencia = CombinacaoDAO.getInstancia(context);
        this.perfilServices = PerfilServices.getInstancia(context);
    }

    public static CombinacaoServices getInstancia(Context contexto) {
        if (instancia == null) {
            instancia = new CombinacaoServices(contexto);
        }

        return instancia;
    }

    public void iniciarChat() {

    }

    public ArrayList<Perfil> retornaCombinacoesAtivas(Perfil perfil) {
        ArrayList<Combinacao> combinacoes = new ArrayList<>();
        ArrayList<Perfil> perfis = new ArrayList<>();
        combinacoes.addAll(persistencia.retornaCombinacoes(perfil.getId(), 1));
        for (Combinacao c : combinacoes) {
            perfis.add(perfilServices.consulta(c.getPerfil2()));
        }
        return perfis;
    }

    public ArrayList<Perfil> retornaCombinacoesPendentes(Perfil perfil) {
        ArrayList<Combinacao> combinacoes = new ArrayList<>();
        ArrayList<Perfil> perfis = new ArrayList<>();
        combinacoes.addAll(persistencia.retornaCombinacoes(perfil.getId(), 0));
        for (Combinacao c : combinacoes) {
            perfis.add(perfilServices.consulta(c.getPerfil2()));
        }
        return perfis;
    }

    public void inserirCombinacao(Perfil perfil1, Perfil perfil2) {
        Combinacao combinacao1 = new Combinacao();
        Combinacao combinacao2 = new Combinacao();
        combinacao1.setPerfil1(perfil1.getId());
        combinacao1.setPerfil2(perfil2.getId());
        combinacao1.setStatus(0);
        combinacao2.setPerfil1(perfil2.getId());
        combinacao2.setPerfil2(perfil1.getId());
        combinacao2.setStatus(0);
        persistencia.inserir(combinacao1);
        persistencia.inserir(combinacao2);
    }

    public void atualizaCombinacao(Combinacao combinacao, int tipo) {
        combinacao.setStatus(tipo);
        persistencia.atualizaStatus(combinacao);
    }

    public void removerCombinacao(Combinacao combinacao) {
        persistencia.removeCombinacao(combinacao);
    }

}
