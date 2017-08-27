package com.thelearningproject.infraestrutura.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.thelearningproject.R;
import com.thelearningproject.infraestrutura.utils.ControladorSessao;
import com.thelearningproject.perfil.dominio.Perfil;
import com.thelearningproject.perfil.gui.HabilidadeActivity;
import com.thelearningproject.perfil.negocio.PerfilServices;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type Termos activity.
 */
public class TermosActivity extends AppCompatActivity {
    private ControladorSessao sessao;
    private Pattern pTheLearningProject = Pattern.compile(String.valueOf(R.string.copyright));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termos);

        sessao = ControladorSessao.getInstancia(this.getApplicationContext());
        TextView linkLearning = (TextView) findViewById(R.id.theLearnProjectId);

        Linkify.TransformFilter tf = new Linkify.TransformFilter() {
            @Override
            public String transformUrl(Matcher match, String url) {
                return ("");
            }
        };

        Linkify.MatchFilter mf = new Linkify.MatchFilter() {
            @Override
            public boolean acceptMatch(CharSequence s, int start, int end) {
                return (true);
            }
        };

        Linkify.addLinks(linkLearning, pTheLearningProject, "http://learnbsiproject.000webhostapp.com/", mf, tf);

        Button botaocontinuar = (Button) findViewById(R.id.botaoContinuar1);
        botaocontinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                continuar();
            }
        });
    }

    private void continuar() {
        criarPerfil();

        Intent entidade = new Intent(TermosActivity.this, HabilidadeActivity.class);
        entidade.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        entidade.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(entidade);
        finish();
    }

    private void criarPerfil() {
        PerfilServices negocioPerfil = PerfilServices.getInstancia(getApplicationContext());
        Perfil perfil = new Perfil();

        perfil.setPessoa(sessao.getPessoa());
        negocioPerfil.inserirPerfil(perfil);
        sessao.setPerfil(negocioPerfil.retornaPerfil(sessao.getPessoa().getId()));
    }
}
