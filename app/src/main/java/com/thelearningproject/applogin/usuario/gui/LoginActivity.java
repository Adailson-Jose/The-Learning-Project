package com.thelearningproject.applogin.usuario.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.thelearningproject.applogin.R;
import com.thelearningproject.applogin.infra.gui.MainActivity;
import com.thelearningproject.applogin.infra.utils.SessionController;
import com.thelearningproject.applogin.infra.utils.UsuarioException;
import com.thelearningproject.applogin.perfil.negocio.PerfilServices;
import com.thelearningproject.applogin.usuario.dominio.Usuario;
import com.thelearningproject.applogin.usuario.negocio.UsuarioServices;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ebony Marques on 17/07/2017.
 */

public class LoginActivity extends Activity {

    private SessionController session;

    private EditText tLogin;
    private EditText tSenha;
    private Button btLogin;
    private Button btCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        session = SessionController.getInstance(this.getApplicationContext());

        tLogin = (EditText) findViewById(R.id.tLogin);
        tSenha = (EditText) findViewById(R.id.tSenha);
        btLogin = (Button) findViewById(R.id.btLogin);
        btCadastro = (Button) findViewById(R.id.cadastroID);

        btLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logar(v);
            }
        });

        btCadastro.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
                startActivity(new Intent(LoginActivity.this, CadastroActivity.class));
            }
        });

    }

    private void logar(View view){
        Usuario usuario = new Usuario();
        String email = tLogin.getText().toString();
        String senha = tSenha.getText().toString();

        usuario.setEmail(email);
        usuario.setSenha(senha);

        try{
            if(validaLogin(usuario)){
                UsuarioServices negocio = UsuarioServices.getInstancia(getBaseContext());
                PerfilServices perfilServices = PerfilServices.getInstancia(this.getApplicationContext());
                Usuario user = negocio.login(usuario);

                if (user != null) {
                    session.encerraSessao();
                    session.defineSessao(user.getNome(), user.getEmail());
                    session.iniciaSessao();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    finish();
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Usuário ou senha incorretos", Toast.LENGTH_LONG).show();

                }
            }
        }catch (UsuarioException e){
            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private boolean validaLogin(Usuario usuario){
        Boolean validacao = true;
        StringBuilder erro = new StringBuilder();
        if (usuario.getEmail() == null || usuario.getEmail().trim().length() == 0 || !aplicandoPattern(usuario.getEmail().toUpperCase())) {
            tLogin.setError("Email inválido");
            validacao = false;

        }
        if (usuario.getSenha() == null || usuario.getSenha().trim().length() == 0) {
            tSenha.setError("Senha inválida");
            validacao = false;
        }
        String resultado = (erro.toString().trim());
        if (resultado!= "") {
            Toast.makeText(LoginActivity.this, resultado, Toast.LENGTH_LONG).show();
        }
        return validacao;

    }
    private Boolean aplicandoPattern (String email){
        Pattern pattern = Pattern.compile("^[A-Z0-9._%-]+@[A-Z0-9.-]+.[A-Z]{2,4}$");
        Matcher m = pattern.matcher(email);
        Boolean resultado = m.matches();

        return  resultado;

    }
}
