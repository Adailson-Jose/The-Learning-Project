package com.thelearningproject.applogin.usuario.gui;

import com.thelearningproject.applogin.usuario.dominio.Usuario;
import com.thelearningproject.applogin.R;
import com.thelearningproject.applogin.infra.UsuarioException;
import com.thelearningproject.applogin.usuario.negocio.SessionController;
import com.thelearningproject.applogin.usuario.negocio.UsuarioServices;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Ebony Marques on 17/07/2017.
 */

public class LoginActivity extends Activity {

    private SessionController session;

    private EditText tLogin;
    private EditText tSenha;
    private Button btLogin;
    private Button btCadastro;
    private TextView alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new SessionController(getApplicationContext());

        tLogin = (EditText) findViewById(R.id.tLogin);
        tSenha = (EditText) findViewById(R.id.tSenha);
        btLogin = (Button) findViewById(R.id.btLogin);
        btCadastro = (Button) findViewById(R.id.cadastroID);
        alerta = (TextView) findViewById(R.id.AvisoID);

        btLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String email = tLogin.getText().toString();
                String senha = tSenha.getText().toString();

                try{
                    UsuarioServices negocio = UsuarioServices.getsInstance(getBaseContext());
                    Usuario user = negocio.validaLogin(email, senha);

                    if (user != null) {

                        session.iniciaSessao(user.getNome());

                        Intent principal = new Intent(LoginActivity.this, MainActivity.class);
                        principal.addFlags(principal.FLAG_ACTIVITY_CLEAR_TOP);
                        principal.setFlags(principal.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(principal);
                        finish();
                    } else {
                        alerta.setText("Usuario ou senha incorreto");
                    }
                }catch (UsuarioException e){
                    alerta.setText(e.getMessage());
                }
            }
        });

        btCadastro.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
                startActivity(new Intent(LoginActivity.this, CadastroActivity.class));
            }
        });

    }

}
