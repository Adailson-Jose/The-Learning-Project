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

public class AlterarNomeActivity extends AppCompatActivity {
    private EditText alterarNome;

    private ControladorSessao sessao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_nome);

        Button btAlterar;
        Button btCancelar;

        sessao = ControladorSessao.getInstancia(this.getApplicationContext());

        alterarNome = (EditText) findViewById(R.id.nomeID);
        btAlterar = (Button) findViewById(R.id.alterarNome);
        btCancelar = (Button) findViewById(R.id.Cancelar);

        btAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alterar(v);
                Auxiliar.esconderTeclado(AlterarNomeActivity.this);

            }
        });

        btCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AlterarNomeActivity.this,ConfiguracaoActivity.class));
            }
        });

        alterarNome.setText(sessao.getPessoa().getNome());
    }

    private void executarAlterar(Pessoa pessoa){
        PessoaServices negocioPessoa = PessoaServices.getInstancia(getBaseContext());

        negocioPessoa.alterarPessoa(pessoa);

        sessao.setPessoa(pessoa);

        Auxiliar.criarToast(this, "Dados atualizados com sucesso");

        Intent entidade = new Intent(AlterarNomeActivity.this, ConfiguracaoActivity.class);
        entidade.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(entidade);
    }

    public void alterar(View view){
        String nome = alterarNome.getText().toString();

        Pessoa pessoa = sessao.getPessoa();

        pessoa.setNome(nome);
        pessoa.setUsuario(sessao.getUsuario());
        pessoa.getUsuario().setStatus(com.thelearningproject.applogin.infraestrutura.utils.Status.ATIVADO);

        if(validaAlterar(pessoa)){
            executarAlterar(pessoa);
        }
    }

    private Boolean validaAlterar(Pessoa pessoa) {
        Boolean validacao = true;
        if (pessoa.getNome() == null || pessoa.getNome().trim().length() == 0) {
            alterarNome.setError("Nome inválido");
            validacao = false;
        }
        return validacao;
    }
}
