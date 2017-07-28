package com.thelearningproject.applogin.usuario.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.thelearningproject.applogin.R;
import com.thelearningproject.applogin.infra.utils.SessionController;
import com.thelearningproject.applogin.infra.utils.UsuarioException;
import com.thelearningproject.applogin.perfil.gui.TermosActivity;
import com.thelearningproject.applogin.usuario.dominio.Status;
import com.thelearningproject.applogin.usuario.dominio.Usuario;
import com.thelearningproject.applogin.usuario.negocio.UsuarioServices;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CadastroActivity extends Activity {

    private SessionController session;
    private Button btCadastro;
    private EditText entradaNome;
    private EditText entradaEmail;
    private EditText entradaSenha;
    private Pattern pattern;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        session = SessionController.getInstance(this.getApplicationContext());

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
        UsuarioServices negocio = UsuarioServices.getInstancia(getBaseContext());
        String nome = entradaNome.getText().toString();
        String email = entradaEmail.getText().toString();
        String senha = entradaSenha.getText().toString();
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setNome(nome);
        usuario.setSenha(senha);
        usuario.setStatus(Status.ATIVADO);

        try {
            if(validaCadastro(usuario)){
                negocio.inserirUsuario(usuario);

                Intent entidade = new Intent(CadastroActivity.this, TermosActivity.class);
                session.setUsuario(negocio.retornaUsuario(usuario.getEmail()));
                session.iniciaSessao();

                entidade.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(entidade);
                finish();
            }

        }catch(UsuarioException e){
            entradaEmail.setError("Email já cadastrado");
        }
    }
    private Boolean validaCadastro(Usuario usuario){
        Boolean validacao=true;
        StringBuilder erro = new StringBuilder();
        if (usuario.getNome() == null || usuario.getNome().trim().length() == 0) {
            entradaNome.setError("Nome inválido");
            validacao = false;
        }
        if (usuario.getEmail() == null || usuario.getEmail().trim().length() == 0 || !aplicandoPattern(usuario.getEmail().toUpperCase())) {
            entradaEmail.setError("Email inválido");
            validacao = false;
        }
        if (usuario.getSenha() == null || usuario.getSenha().trim().length() == 0) {
            entradaSenha.setError("Senha inválida");
            validacao = false;
        }
        String resultado = (erro.toString().trim());
        if (resultado!= "") {
            Toast.makeText(CadastroActivity.this, resultado, Toast.LENGTH_LONG).show();
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
