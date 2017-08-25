package com.thelearningproject.perfil.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.thelearningproject.R;
import com.thelearningproject.combinacao.dominio.Combinacao;
import com.thelearningproject.combinacao.dominio.IAceitarCombinacao;
import com.thelearningproject.combinacao.dominio.IRecusarCombinacao;
import com.thelearningproject.combinacao.negocio.CombinacaoServices;
import com.thelearningproject.combinacao.negocio.StatusCombinacao;
import com.thelearningproject.infraestrutura.utils.Auxiliar;
import com.thelearningproject.infraestrutura.utils.ControladorSessao;
import com.thelearningproject.infraestrutura.utils.PerfilAdapter;
import com.thelearningproject.perfil.dominio.Perfil;
import com.thelearningproject.perfil.negocio.PerfilServices;

import java.util.ArrayList;

public class NotificacoesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, IAceitarCombinacao, IRecusarCombinacao {
    private ListView listaInteracoes;
    private ControladorSessao sessao;
    private PerfilServices perfilServices;
    private CombinacaoServices combinacaoServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacoes);
        setTitle(R.string.notificacoes);

        sessao = ControladorSessao.getInstancia(this);
        combinacaoServices = CombinacaoServices.getInstancia(this);
        perfilServices = PerfilServices.getInstancia(this);

        listaInteracoes = (ListView) findViewById(R.id.listViewNotifyID);
        listaInteracoes.setOnItemClickListener(this);
        listar();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Perfil p = (Perfil) parent.getAdapter().getItem(position);
        sessao.setPerfilSelecionado(p);
        Intent intent = new Intent(this, PerfilActivity.class);
        startActivity(intent);

        listar();
    }

    private void listar() {
        ArrayList<Perfil> perfils = new ArrayList<>();
        for (Combinacao c : sessao.getPerfil().getCombinacoes()) {
            if (c.getStatus() == StatusCombinacao.PENDENTE.getValor()){
                if (c.getPerfil1() == sessao.getPerfil().getId()) {
                    perfils.add(perfilServices.consultaPendentes(c.getPerfil2()));
                } else {
                    perfils.add(perfilServices.consultaPendentes(c.getPerfil1()));
                }
            }
        }

        ArrayAdapter adaptador = new PerfilAdapter(this, new ArrayList<>(perfils), NotificacoesActivity.this, null, null, this, this);

        adaptador.notifyDataSetChanged();
        listaInteracoes.setAdapter(adaptador);
    }
    @Override
    public void aceitarCombinacao(int i) {
        for (Combinacao c : sessao.getPerfil().getCombinacoes()){
            if (c.getPerfil2() == i){
                combinacaoServices.atualizaCombinacao(c, StatusCombinacao.ATIVADO.getValor(), sessao.getPerfil());
                sessao.getPerfil().getCombinacoes().remove(c);
            }
        }
        Perfil perfil2 = perfilServices.consultar(i);
        for (Combinacao c : perfil2.getCombinacoes()) {
            if (c.getPerfil2() == sessao.getPerfil().getId()) {
                combinacaoServices.atualizaCombinacao(c, StatusCombinacao.ATIVADO.getValor(), perfil2);
                sessao.getPerfil().getCombinacoes().remove(c);
            }
        }

        listar();
        Auxiliar.criarToast(this, "Você aceitou o match");
    }
    @Override
    public void recusarCombinacao(int i) {
        Combinacao com = new Combinacao();
        com.setPerfil1(sessao.getPerfil().getId());
        com.setPerfil2(i);
        combinacaoServices.removerCombinacao(sessao.getPerfil(),com);
        listar();
        Auxiliar.criarToast(this, "Você recusou o match");
    }
}
