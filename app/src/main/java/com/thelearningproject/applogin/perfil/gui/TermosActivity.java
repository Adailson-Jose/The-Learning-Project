package com.thelearningproject.applogin.perfil.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.thelearningproject.applogin.R;

public class TermosActivity extends AppCompatActivity {
    private Button botaocontinuar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termos);

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
    }
}
