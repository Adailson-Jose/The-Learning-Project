package com.thelearningproject.applogin.usuario.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.thelearningproject.applogin.usuario.dominio.Usuario;
import com.thelearningproject.applogin.R;
import com.thelearningproject.applogin.infra.UsuarioException;
import com.thelearningproject.applogin.usuario.negocio.SessionController;
import com.thelearningproject.applogin.usuario.negocio.UsuarioServices;

public class CadastroActivity extends Activity {

    private SessionController session;
    private Button btCadastro;
    private EditText entradaNome;
    private EditText entradaEmail;
    private EditText entradaSenha;
    private TextView aviso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        session = new SessionController(getApplicationContext());

        btCadastro = (Button) findViewById(R.id.botaoCadastroID);
        entradaNome = (EditText) findViewById(R.id.nomeEntradaID);
        entradaEmail = (EditText) findViewById(R.id.emailEntradaID);
        entradaSenha = (EditText) findViewById(R.id.senhaEntradaID);
        aviso = (TextView) findViewById(R.id.avisoID);

        btCadastro.setOnClickListener( new View.OnClickListener(){

            @Override
            public void onClick(View v){
                UsuarioServices negocio = UsuarioServices.getsInstance(getBaseContext());
                String nome = entradaNome.getText().toString();
                String email = entradaEmail.getText().toString();
                String senha = entradaSenha.getText().toString();
                Usuario usuario = new Usuario();
                usuario.setEmail(email);
                usuario.setNome(nome);
                usuario.setSenha(senha);
                try {
                    if (negocio.inserirUsuario(usuario) != null) {
                        Intent intent = new Intent(CadastroActivity.this, MainActivity.class);
                        session.iniciaSessao(usuario.getNome());
                        startActivity(intent);
                        finish();

                    } else {
                        aviso.setText(" Email já cadastrado ");
                    }
                }catch(UsuarioException e){
                    aviso.setText(e.getMessage());
                }
             }


        });

    }
}
