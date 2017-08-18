package com.thelearningproject.applogin.perfil.gui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.thelearningproject.applogin.R;
import com.thelearningproject.applogin.perfil.dominio.Perfil;
import com.thelearningproject.applogin.perfil.negocio.PerfilServices;

public class PerfilActivity extends AppCompatActivity {
    private Perfil perfilAtual;

    //    public PerfilActivity() {
//        this.perfilAtual = (Perfil) getIntent().getSerializableExtra("Perfil");
//    }
    public PerfilActivity(Perfil p) {
        this.perfilAtual = p;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        setTitle(perfilAtual.getPessoa().getNome());
        PerfilServices perfilServices = PerfilServices.getInstancia(PerfilActivity.this);

        TextView tvNomePerfil = (TextView) findViewById(R.id.nomeUsuarioID);
        TextView tvDescricaoPerfil = (TextView) findViewById(R.id.descricaoUsuarioID);

        tvNomePerfil.setText(perfilAtual.getPessoa().getNome());
        tvDescricaoPerfil.setText(perfilAtual.getDescricao());

        String[] listaHabilidades = {perfilServices.retornaStringListaHabilidades(perfilAtual.getId())};
        String[] listaNecessidades = {perfilServices.retornaStringListaNecessidades(perfilAtual.getId())};
        String[] listaOpcoes = {getString(R.string.iniciarChat)};

        ListView lvHabilidades = (ListView) findViewById(R.id.listaPerfilHabilidades);
        ListView lvNecessidades = (ListView) findViewById(R.id.listaPerfilNecessidades);
        ListView lvOpcoes = (ListView) findViewById(R.id.listaPerfilOpcoes);

        ArrayAdapter<String> adapterOpcoesHabilidades = new ArrayAdapter<>(
                PerfilActivity.this,
                android.R.layout.simple_list_item_1,
                listaHabilidades);
        ArrayAdapter<String> adapterOpcoesNecessidades = new ArrayAdapter<>(
                PerfilActivity.this,
                android.R.layout.simple_list_item_1,
                listaNecessidades);
        ArrayAdapter<String> adapterOpcoesOutras = new ArrayAdapter<>(
                PerfilActivity.this,
                android.R.layout.simple_list_item_1,
                listaOpcoes);

        lvHabilidades.setAdapter(adapterOpcoesHabilidades);
        lvNecessidades.setAdapter(adapterOpcoesNecessidades);
        lvOpcoes.setAdapter(adapterOpcoesOutras);

        lvOpcoes.setAlpha((float) 0.75);
    }
}