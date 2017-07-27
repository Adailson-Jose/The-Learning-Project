package com.thelearningproject.applogin.infra.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.thelearningproject.applogin.R;
import com.thelearningproject.applogin.infra.SessionController;
import com.thelearningproject.applogin.infra.UsuarioException;
import com.thelearningproject.applogin.infra.gui.MainActivity;
import com.thelearningproject.applogin.usuario.dominio.Usuario;
import com.thelearningproject.applogin.usuario.negocio.UsuarioServices;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Heitor on 25/07/2017.
 */

public class ConfiguracaoActivity extends Activity{

    private EditText alterarnome;
    private EditText alteraremail;
    private EditText alterarsenha;
    private Button btAlterar;
    private Button btDesativar;
    private Button btVoltar;

    private SessionController session;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);

        session = SessionController.getInstance(this.getApplicationContext());

        alterarnome = (EditText) findViewById(R.id.nomeID);
        alteraremail = (EditText) findViewById(R.id.emailID);
        alterarsenha = (EditText) findViewById(R.id.senhaID);
        btAlterar = (Button) findViewById(R.id.alterarID);
        btDesativar = (Button) findViewById(R.id.deletarID);
        btVoltar = (Button) findViewById(R.id.voltarID);

        btAlterar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                alterar(v);
            }

        });
        btDesativar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                desativar(v);
            }
        });
        btVoltar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(ConfiguracaoActivity.this,MainActivity.class));
            }
        });
    }

    private void alterar(View view){
        UsuarioServices negocio = UsuarioServices.getInstancia(getBaseContext());
        String nome = alterarnome.getText().toString();
        String email = alteraremail.getText().toString();
        String senha = alterarsenha.getText().toString();

        Usuario usuario = negocio.retornaUsuario(session.getEmail());
        int id = usuario.getId();
        usuario.setId(id);
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);

        try {
            if(validaAlterar(usuario)){
                negocio.alterarUsuario(usuario);

                Intent intent = new Intent(ConfiguracaoActivity.this, MainActivity.class);
                session.defineSessao(nome,email);

                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);
                finish();

            }

        } catch (UsuarioException e){
            alteraremail.setError("Email ja cadastrado");
        }
    }

    private void desativar(View view){
        UsuarioServices negocio = UsuarioServices.getInstancia(getBaseContext());
        negocio.deletarUsuario(session.getEmail());
        finish();
        session.encerraSessao();
    }

    private Boolean validaAlterar(Usuario usuario){
        Boolean validacao=true;
        StringBuilder erro = new StringBuilder();
        if (usuario.getNome() == null || usuario.getNome().trim().length() == 0) {
            alterarnome.setError("Nome inválido");
            validacao = false;
        }
        if (usuario.getEmail() == null || usuario.getEmail().trim().length() == 0 || !aplicandoPattern(usuario.getEmail().toUpperCase())) {
            alteraremail.setError("Email inválido");
            validacao = false;
        }
        if (usuario.getSenha() == null || usuario.getSenha().trim().length() == 0) {
            alterarsenha.setError("Senha inválida");
            validacao = false;
        }
        String resultado = (erro.toString().trim());
        if (resultado!= "") {
            Toast.makeText(ConfiguracaoActivity.this, resultado, Toast.LENGTH_LONG).show();
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
