package com.thelearningproject.applogin.usuario.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.thelearningproject.applogin.usuario.dominio.Usuario;
import com.thelearningproject.applogin.R;
import com.thelearningproject.applogin.infra.UsuarioException;
import com.thelearningproject.applogin.usuario.negocio.SessionController;
import com.thelearningproject.applogin.usuario.negocio.UsuarioServices;

import java.util.regex.Pattern;

public class CadastroActivity extends Activity {

    private SessionController session;
    private Button btCadastro;
    private EditText entradaNome;
    private EditText entradaEmail;
    private EditText entradaSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        session = new SessionController(getApplicationContext());

        btCadastro = (Button) findViewById(R.id.botaoCadastroID);
        entradaNome = (EditText) findViewById(R.id.nomeEntradaID);
        entradaEmail = (EditText) findViewById(R.id.emailEntradaID);
        entradaSenha = (EditText) findViewById(R.id.senhaEntradaID);

        btCadastro.setOnClickListener( new View.OnClickListener(){

            @Override
            public void onClick(View v){
                cadastrar(v);
             }

        });

    }
    private void cadastrar(View view){
        UsuarioServices negocio = UsuarioServices.getsInstance(getBaseContext());
        String nome = entradaNome.getText().toString();
        String email = entradaEmail.getText().toString();
        String senha = entradaSenha.getText().toString();
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setNome(nome);
        usuario.setSenha(senha);
        try {
            if(validaCadastro(usuario)){
                negocio.inserirUsuario(usuario);

                Intent intent = new Intent(CadastroActivity.this, MainActivity.class);
                session.iniciaSessao(usuario.getNome());

                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);
                finish();
            }

        }catch(UsuarioException e){
            Toast.makeText(CadastroActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            entradaEmail.setError("E-mail já cadastrado");
        }
    }
    private Boolean validaCadastro(Usuario usuario){
        Boolean validacao=true;
        StringBuilder erro = new StringBuilder();
        if (usuario.getNome() == null || usuario.getNome().trim().length() == 0) {
            erro.append("Nome inválido\n");
            entradaNome.setError("Nome inválido");
            validacao = false;
        }
        if (usuario.getEmail() == null || usuario.getEmail().trim().length() == 0 || !Pattern.matches("^[A-Z0-9._%-]+@[A-Z0-9.-]+.[A-Z]{2,4}$",usuario.getEmail().toUpperCase())) {
            erro.append("E-mail inválido\n");
            entradaEmail.setError("E-mail inválido");
            validacao = false;
        }
        if (usuario.getSenha() == null || usuario.getSenha().trim().length() == 0) {
            erro.append("Senha inválida\n");
            entradaSenha.setError("Senha inválida");
            validacao = false;
        }
        String resultado = (erro.toString().trim());
        if (resultado!= "") {
            Toast.makeText(CadastroActivity.this, resultado, Toast.LENGTH_LONG).show();
        }
        return validacao;

    }
}
