package com.thelearningproject.infraestrutura.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.thelearningproject.R;
import com.thelearningproject.infraestrutura.utils.Auxiliar;
import com.thelearningproject.infraestrutura.utils.ControladorSessao;
import com.thelearningproject.infraestrutura.utils.UsuarioException;
import com.thelearningproject.pessoa.dominio.Pessoa;
import com.thelearningproject.pessoa.gui.CriarContaActivity;
import com.thelearningproject.pessoa.gui.RecuperarContaActivity;
import com.thelearningproject.pessoa.negocio.PessoaServices;
import com.thelearningproject.usuario.dominio.Usuario;
import com.thelearningproject.usuario.negocio.UsuarioServices;

/**
 * Criado por Ebony Marques em 17/07/2017.
 */

public class LoginActivity extends Activity {
    private ControladorSessao sessao;
    private EditText entradaLogin;
    private EditText entradaSenha;
    private Switch switchConectado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessao = ControladorSessao.getInstancia(this.getApplicationContext());
        entradaLogin = (EditText) findViewById(R.id.tLogin);
        entradaSenha = (EditText) findViewById(R.id.tSenha);
        Button botaoLogin = (Button) findViewById(R.id.btLogin);
        Button botaoCadastro = (Button) findViewById(R.id.cadastroID);
        Button botaoEsqueci = (Button) findViewById(R.id.btEsqueci);
        switchConectado = (Switch) findViewById(R.id.manterSwitch);

        botaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processoLogin();
            }
        });

        botaoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, CriarContaActivity.class));
            }
        });

        botaoEsqueci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RecuperarContaActivity.class));
            }
        });
    }

    private boolean validaCampos(Usuario usuario) {
        boolean validacao = true;

        if (usuario.getEmail() == null || usuario.getEmail().trim().length() == 0) {
            entradaLogin.setError("Email inválido");
            validacao = false;
        }
        if (usuario.getSenha() == null || usuario.getSenha().trim().length() == 0) {
            entradaSenha.setError("Senha inválida");
            validacao = false;
        }

        return validacao;
    }

    private void processoLogin() {
        String email = entradaLogin.getText().toString();
        String senha = entradaSenha.getText().toString();

        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setSenha(senha);

        try {
            if (validaCampos(usuario)) {
                executarLogin(usuario);
            }

        } catch (UsuarioException e) {
            Auxiliar.criarToast(this, e.getMessage());
        }
    }

    private void executarLogin(Usuario usuario) throws UsuarioException {
        UsuarioServices negocioUsuario = UsuarioServices.getInstancia(getBaseContext());
        PessoaServices negocioPessoa = PessoaServices.getInstancia(getBaseContext());

        Usuario logado = negocioUsuario.logar(usuario);

        if (logado != null) {
            Pessoa pessoaLogada = negocioPessoa.retornaPessoa(logado.getId());
            sessao.encerraSessao();

            pessoaLogada.setUsuario(logado);
            sessao.setPessoa(pessoaLogada);
            sessao.iniciaSessao();

            if (switchConectado.isChecked()) {
                sessao.salvarSessao();
            }

            Intent entidade = new Intent(LoginActivity.this, MainActivity.class);
            entidade.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(entidade);

        } else {
            Auxiliar.criarToast(this, "Usuário ou senha incorretos");

        }
    }

}
