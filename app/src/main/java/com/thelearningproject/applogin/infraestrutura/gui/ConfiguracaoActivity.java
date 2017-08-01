package com.thelearningproject.applogin.infraestrutura.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.thelearningproject.applogin.R;
import com.thelearningproject.applogin.estudo.dominio.Status;
import com.thelearningproject.applogin.infraestrutura.utils.Auxiliar;
import com.thelearningproject.applogin.infraestrutura.utils.ControladorSessao;
import com.thelearningproject.applogin.infraestrutura.utils.UsuarioException;
import com.thelearningproject.applogin.pessoa.dominio.Pessoa;
import com.thelearningproject.applogin.pessoa.negocio.PessoaServices;
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

    private ControladorSessao session;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);
        setTitle("Configurações");

        session = ControladorSessao.getInstancia(this.getApplicationContext());

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

        alterarNome.setText(session.getPessoa().getNome());
        alterarEmail.setText(session.getUsuario().getEmail());
    }

    private void alterar(View view){
        UsuarioServices negocioUsuario = UsuarioServices.getInstancia(getBaseContext());
        PessoaServices negocioPessoa = PessoaServices.getInstancia(getBaseContext());

        String nome = alterarNome.getText().toString();
        String email = alterarEmail.getText().toString();
        String senha = alterarSenha.getText().toString();

        Usuario usuario = session.getPessoa().getUsuario();
        Pessoa pessoa = session.getPessoa();

        pessoa.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuario.setStatus(com.thelearningproject.applogin.infraestrutura.utils.Status.ATIVADO);
        pessoa.setUsuario(usuario);

        try {
            if(validaAlterar(pessoa)){
                negocioUsuario.alterarUsuario(pessoa.getUsuario());
                negocioPessoa.alterarPessoa(pessoa);

                Intent intent = new Intent(ConfiguracaoActivity.this, MainActivity.class);
                session.setUsuario(usuario);
                session.setPessoa(pessoa);


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

    private Boolean validaAlterar(Pessoa pessoa){
        Boolean validacao=true;
        StringBuilder erro = new StringBuilder();
        if (pessoa.getNome() == null || pessoa.getNome().trim().length() == 0) {
            alterarNome.setError("Nome inválido");
            validacao = false;
        }
        if (pessoa.getUsuario().getEmail() == null || pessoa.getUsuario().getEmail().trim().length() == 0 || !aplicandoPattern(pessoa.getUsuario().getEmail().toUpperCase())) {
            alterarEmail.setError("E-mail inválido");
            validacao = false;
        }
        if (pessoa.getUsuario().getSenha() == null || pessoa.getUsuario().getSenha().trim().length() == 0) {
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
