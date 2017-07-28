package com.thelearningproject.applogin.infra.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.thelearningproject.applogin.R;
import com.thelearningproject.applogin.infra.utils.Auxiliar;
import com.thelearningproject.applogin.infra.utils.SessionController;
import com.thelearningproject.applogin.infra.utils.UsuarioException;
import com.thelearningproject.applogin.usuario.dominio.Usuario;
import com.thelearningproject.applogin.usuario.negocio.UsuarioServices;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Heitor on 25/07/2017.
 */

public class ConfiguracaoActivity extends AppCompatActivity{

    private EditText alterarNome;
    private EditText alterarEmail;
    private EditText alterarSenha;
    private Button btAlterar;
    private Button btDesativar;
    private Button btLogout;

    private SessionController session;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);
        setTitle("Configurações");

        session = SessionController.getInstance(this.getApplicationContext());

        alterarNome = (EditText) findViewById(R.id.nomeID);
        alterarEmail = (EditText) findViewById(R.id.emailID);
        alterarSenha = (EditText) findViewById(R.id.senhaID);
        btAlterar = (Button) findViewById(R.id.alterarID);
        btDesativar = (Button) findViewById(R.id.deletarID);
        btLogout = (Button) findViewById(R.id.LogoutID);

        btAlterar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                alterar(v);
                Auxiliar.esconderTeclado(ConfiguracaoActivity.this);
            }

        });
        btDesativar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                desativar(v);
            }
        });

        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.encerraSessao();
                Toast.makeText(ConfiguracaoActivity.this, "Logout efetuado com sucesso", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        alterarNome.setText(session.getUsuario().getNome());
        alterarEmail.setText(session.getUsuario().getEmail());
    }

    private void alterar(View view){
        UsuarioServices negocio = UsuarioServices.getInstancia(getBaseContext());
        String nome = alterarNome.getText().toString();
        String email = alterarEmail.getText().toString();
        String senha = alterarSenha.getText().toString();

        Usuario usuario = session.getUsuario();
//        int id = usuario.getId();
//        usuario.setId(id);
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);

        try {
            if(validaAlterar(usuario)){
                negocio.alterarUsuario(usuario);

                Intent intent = new Intent(ConfiguracaoActivity.this, MainActivity.class);
                session.setUsuario(usuario);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                Toast.makeText(this.getApplicationContext(), "Dados atualizados com sucesso", Toast.LENGTH_LONG).show();
                startActivity(intent);
                finish();

            }

        } catch (UsuarioException e){
            alterarEmail.setError("E-mail já cadastrado");
        }
    }

    private void desativar(View view){
        UsuarioServices negocio = UsuarioServices.getInstancia(getBaseContext());
        negocio.deletarUsuario(session.getUsuario());
        Toast.makeText(ConfiguracaoActivity.this, "Usuário desativado com sucesso", Toast.LENGTH_LONG).show();
        finish();
        session.encerraSessao();
    }

    private Boolean validaAlterar(Usuario usuario){
        Boolean validacao=true;
        StringBuilder erro = new StringBuilder();
        if (usuario.getNome() == null || usuario.getNome().trim().length() == 0) {
            alterarNome.setError("Nome inválido");
            validacao = false;
        }
        if (usuario.getEmail() == null || usuario.getEmail().trim().length() == 0 || !aplicandoPattern(usuario.getEmail().toUpperCase())) {
            alterarEmail.setError("E-mail inválido");
            validacao = false;
        }
        if (usuario.getSenha() == null || usuario.getSenha().trim().length() == 0) {
            alterarSenha.setError("Senha inválida");
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
