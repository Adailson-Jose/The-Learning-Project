package com.thelearningproject.applogin.infra.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.thelearningproject.applogin.R;
import com.thelearningproject.applogin.perfil.gui.HabilidadeActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TermosActivity extends AppCompatActivity {
    private Button botaocontinuar;
    private TextView linkLearning;
    private Pattern pTheLearningProject = Pattern.compile("The Learning © Project 2017");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termos);

        linkLearning = (TextView) findViewById(R.id.theLearnProjectId);

        Linkify.TransformFilter tf = new Linkify.TransformFilter(){
            @Override
            public String transformUrl(Matcher match, String url) {
                return ("");
            }
        };

        Linkify.MatchFilter mf = new Linkify.MatchFilter(){
            @Override
            public boolean acceptMatch(CharSequence s, int start, int end) {
                return(true);
            }
        };

        Linkify.addLinks(linkLearning, pTheLearningProject, "http://learnbsiproject.000webhostapp.com/",mf,tf);

        botaocontinuar = (Button) findViewById(R.id.botaoContinuar1);
        botaocontinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                continuar(view);
            }
        });
    }

    private void continuar(View view) {
        Intent entidade = new Intent(TermosActivity.this, HabilidadeActivity.class);
        entidade.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        entidade.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(entidade);
        finish();
    }
}
