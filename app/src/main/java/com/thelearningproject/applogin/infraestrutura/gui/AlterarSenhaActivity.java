package com.thelearningproject.applogin.infraestrutura.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.thelearningproject.applogin.R;
import com.thelearningproject.applogin.infraestrutura.utils.Auxiliar;
import com.thelearningproject.applogin.infraestrutura.utils.ControladorSessao;
import com.thelearningproject.applogin.pessoa.dominio.Pessoa;
import com.thelearningproject.applogin.pessoa.negocio.PessoaServices;
import com.thelearningproject.applogin.usuario.negocio.UsuarioServices;

public class AlterarSenhaActivity extends AppCompatActivity {
    private EditText alterarSenha;

    private ControladorSessao sessao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_senha);

        Button btAlterar;
        Button btCancelar;

        sessao = ControladorSessao.getInstancia(this.getApplicationContext());

        alterarSenha = (EditText) findViewById(R.id.senhaID);
        btAlterar = (Button) findViewById(R.id.alterarSenha);
        btCancelar = (Button) findViewById(R.id.Cancelar);

        btAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alterar(v);
                Auxiliar.esconderTeclado(AlterarSenhaActivity.this);

            }
        });
        btCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AlterarSenhaActivity.this,ConfiguracaoActivity.class));
                finish();
            }
        });

    }

    private void executarAlterar(Pessoa pessoa){
        UsuarioServices negocioUsuario = UsuarioServices.getInstancia(getBaseContext());
        PessoaServices negocioPessoa = PessoaServices.getInstancia(getBaseContext());

        negocioUsuario.alterarSenhaUsuario(pessoa.getUsuario());
        negocioPessoa.alterarPessoa(pessoa);

        sessao.setUsuario(pessoa.getUsuario());
        sessao.setPessoa(pessoa);

        Auxiliar.criarToast(this, "Dados atualizados com sucesso");

        Intent entidade = new Intent(AlterarSenhaActivity.this, ConfiguracaoActivity.class);
        entidade.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(entidade);
    }

    public void alterar(View view){
        String senha = alterarSenha.getText().toString();

        Pessoa pessoa = sessao.getPessoa();

        pessoa.setUsuario(sessao.getUsuario());
        pessoa.getUsuario().setSenha(senha);
        pessoa.getUsuario().setStatus(com.thelearningproject.applogin.infraestrutura.utils.Status.ATIVADO);

        if(validaAlterar(pessoa)){
            executarAlterar(pessoa);
        }
    }

    private Boolean validaAlterar(Pessoa pessoa) {
        Boolean validacao = true;
        if (pessoa.getUsuario().getSenha() == null || pessoa.getUsuario().getSenha().trim().length() == 0) {
            alterarSenha.setError("Senha inválida");
            validacao = false;
        }
        return validacao;
    }
}
