package com.thelearningproject.applogin.infraestrutura.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.thelearningproject.applogin.R;
import com.thelearningproject.applogin.infraestrutura.utils.Auxiliar;
import com.thelearningproject.applogin.infraestrutura.utils.ControladorSessao;
import com.thelearningproject.applogin.infraestrutura.utils.UsuarioException;
import com.thelearningproject.applogin.pessoa.dominio.Pessoa;
import com.thelearningproject.applogin.pessoa.negocio.PessoaServices;
import com.thelearningproject.applogin.usuario.dominio.Usuario;
import com.thelearningproject.applogin.usuario.negocio.UsuarioServices;

/**
 * Criado por Heitor em 25/07/2017.
 */

public class ConfiguracaoActivity extends AppCompatActivity{
    private ControladorSessao session;
    private Auxiliar auxiliar = new Auxiliar();
    private EditText alterarNome;
    private EditText alterarEmail;
    private EditText alterarSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);
        setTitle("Configurações");

        session = ControladorSessao.getInstancia(this.getApplicationContext());

        alterarNome = (EditText) findViewById(R.id.nomeID);
        alterarEmail = (EditText) findViewById(R.id.emailID);
        alterarSenha = (EditText) findViewById(R.id.senhaID);

        Button botaoAlterar = (Button) findViewById(R.id.alterarID);
        Button botaoDesativar = (Button) findViewById(R.id.deletarID);
        Button botaoLogout = (Button) findViewById(R.id.LogoutID);

        botaoAlterar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                processoAlterar();
                Auxiliar.esconderTeclado(ConfiguracaoActivity.this);

                Intent entidade = new Intent(ConfiguracaoActivity.this, MainActivity.class);
                entidade.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(entidade);
                finish();
            }

        });
        botaoDesativar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                desativar(v);
            }
        });

        botaoLogout.setOnClickListener(new View.OnClickListener() {
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

    private void executarAlterar(Pessoa pessoa) throws UsuarioException {
        UsuarioServices negocioUsuario = UsuarioServices.getInstancia(getBaseContext());
        PessoaServices negocioPessoa = PessoaServices.getInstancia(getBaseContext());

        negocioUsuario.alterarUsuario(pessoa.getUsuario());
        negocioPessoa.alterarPessoa(pessoa);

        session.setUsuario(pessoa.getUsuario());
        session.setPessoa(pessoa);

        Toast.makeText(this.getApplicationContext(), "Dados atualizados com sucesso", Toast.LENGTH_LONG).show();
    }

    private void processoAlterar(){
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
                executarAlterar(pessoa);
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
        Boolean validacao = true;
        StringBuilder erro = new StringBuilder();

        if (pessoa.getNome() == null || pessoa.getNome().trim().length() == 0) {
            alterarNome.setError("Nome inválido");
            validacao = false;
        }
        if (pessoa.getUsuario().getEmail() == null || pessoa.getUsuario().getEmail().trim().length() == 0 || !auxiliar.aplicaPattern(pessoa.getUsuario().getEmail().toUpperCase())) {
            alterarEmail.setError("E-mail inválido");
            validacao = false;
        }
        if (pessoa.getUsuario().getSenha() == null || pessoa.getUsuario().getSenha().trim().length() == 0) {
            alterarSenha.setError("Senha inválida");
            validacao = false;
        }

        String resultado = (erro.toString().trim());

        if (!resultado.equals("")) {
            Toast.makeText(ConfiguracaoActivity.this, resultado, Toast.LENGTH_LONG).show();
        }
        return validacao;
    }

}
