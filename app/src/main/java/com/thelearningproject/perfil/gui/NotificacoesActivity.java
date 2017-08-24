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
import com.thelearningproject.infraestrutura.utils.ControladorSessao;
import com.thelearningproject.infraestrutura.utils.PerfilAdapter;
import com.thelearningproject.perfil.dominio.Perfil;
import com.thelearningproject.perfil.negocio.PerfilServices;

import java.util.ArrayList;

public class NotificacoesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView listaInteracoes;
    private ControladorSessao sessao;
    private PerfilServices perfilServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacoes);
        setTitle(R.string.notificacoes);

        sessao = ControladorSessao.getInstancia(this);
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
            if (c.getPerfil1() == sessao.getPerfil().getId()) {
                perfils.add(perfilServices.consulta(c.getPerfil2()));
            } else {
                perfils.add(perfilServices.consulta(c.getPerfil1()));
            }
        }

        ArrayAdapter adaptador = new PerfilAdapter(this, new ArrayList<>(perfils), NotificacoesActivity.this, null, null);

        adaptador.notifyDataSetChanged();
        listaInteracoes.setAdapter(adaptador);
    }
}
