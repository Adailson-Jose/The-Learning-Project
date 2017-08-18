package com.thelearningproject.applogin.perfil.gui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.thelearningproject.applogin.R;
import com.thelearningproject.applogin.perfil.dominio.Perfil;

public class PerfilActivity extends AppCompatActivity {
    private Perfil perfilAtual;
    private TextView tvNomePerfil;
    private TextView tvDescricaoPerfil;
    private Perfil perfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        perfil = (Perfil) getIntent().getSerializableExtra("Perfil");
        setTitle(perfil.getPessoa().getNome());

        tvNomePerfil = (TextView) findViewById(R.id.nomeUsuarioID);
        tvDescricaoPerfil = (TextView) findViewById(R.id.descricaoUsuarioID);

        tvNomePerfil.setText(perfilAtual.getPessoa().getNome());
        tvDescricaoPerfil.setText(perfilAtual.getDescricao());

/*        PerfilServices perfilServices;
        String[] listaHabilidades = {perfilServices.retornaStringListaHabilidades(perfilAtual.getId())};
        String[] listaNecessidades = {perfilServices.retornaStringListaNecessidades(perfilAtual.getId())};
        String[] listaOpcoes = {getString(R.string.iniciarChat)};*/
    }
}
