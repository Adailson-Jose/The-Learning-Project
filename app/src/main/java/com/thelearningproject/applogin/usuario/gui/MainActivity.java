package com.thelearningproject.applogin.usuario.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.thelearningproject.applogin.R;
import com.thelearningproject.applogin.usuario.negocio.SessionController;

public class MainActivity extends AppCompatActivity {

    private SessionController session;
    Button btNovoCadastro;
    TextView apresentacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new SessionController(getApplicationContext());

        apresentacao = (TextView) findViewById(R.id.apresentacaoID);
        btNovoCadastro = (Button) findViewById(R.id.LogoutID);

        if(session.verificaLogin()) {
            finish();
        }

        String usuario_logado = session.getNome();

        apresentacao.setText("Bem-vindo(a), " + usuario_logado +".");

        btNovoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                session.encerraSessao();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
    }
}
