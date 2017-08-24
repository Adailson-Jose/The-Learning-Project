package com.thelearningproject.applogin.perfil.gui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.thelearningproject.applogin.R;
import com.thelearningproject.applogin.infraestrutura.utils.ControladorSessao;
import com.thelearningproject.applogin.perfil.dominio.Perfil;
import com.thelearningproject.applogin.perfil.negocio.PerfilServices;

public class PerfilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ControladorSessao sessao = ControladorSessao.getInstancia(this);
        Perfil perfilAtual = sessao.getPerfilSelecionado();
        setContentView(R.layout.activity_perfil);
        setTitle(perfilAtual.getPessoa().getNome());
        PerfilServices perfilServices = PerfilServices.getInstancia(PerfilActivity.this);

        TextView tvDescricaoPerfil = (TextView) findViewById(R.id.descricaoUsuarioID);
        tvDescricaoPerfil.setText(perfilAtual.getDescricao());

        TextView tvStrHabilidades = (TextView) findViewById(R.id.stringHabilidades);
        tvStrHabilidades.setText(perfilServices.retornaStringListaHabilidades(perfilAtual.getId()));

        TextView tvStrNecessidades = (TextView) findViewById(R.id.stringNecessidade);
        tvStrNecessidades.setText(perfilServices.retornaStringListaNecessidades(perfilAtual.getId()));

        String[] listaOpcoes = {getString(R.string.iniciarChat)};

        ListView lvOpcoes = (ListView) findViewById(R.id.listaPerfilOpcoes);

        ArrayAdapter<String> adapterOpcoesOutras = new ArrayAdapter<>(
                PerfilActivity.this,
                android.R.layout.simple_list_item_1,
                listaOpcoes);

        lvOpcoes.setAdapter(adapterOpcoesOutras);
    }
}