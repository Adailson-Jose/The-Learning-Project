package com.thelearningproject.pessoa.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.thelearningproject.R;
import com.thelearningproject.infraestrutura.utils.Auxiliar;
import com.thelearningproject.infraestrutura.utils.ControladorSessao;
import com.thelearningproject.pessoa.dominio.Pessoa;
import com.thelearningproject.pessoa.negocio.PessoaServices;

/**
 * The type Alterar nome activity.
 */
public class AlterarNomeActivity extends AppCompatActivity {
    private EditText alterarNome;
    private ControladorSessao sessao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_nome);

        sessao = ControladorSessao.getInstancia(this.getApplicationContext());
        alterarNome = (EditText) findViewById(R.id.nomeID);
        alterarNome.setText(sessao.getPessoa().getNome());
        alterarNome.setSelection(alterarNome.getText().length());
        Auxiliar.abrirTeclado(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.salvar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.salvarBtn) {
            alterar();
        }
        Auxiliar.esconderTeclado(this);
        return super.onOptionsItemSelected(item);
    }

    private void executarAlterar(Pessoa pessoa) {
        PessoaServices negocioPessoa = PessoaServices.getInstancia(getBaseContext());

        negocioPessoa.alterarPessoa(pessoa);

        sessao.setPessoa(pessoa);

        Auxiliar.criarToast(this, "Nome atualizado com sucesso");

        Intent entidade = new Intent(AlterarNomeActivity.this, ConfiguracaoActivity.class);
        entidade.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(entidade);
        finish();
    }

    /**
     * Alterar.
     */
    public void alterar() {
        String nome = alterarNome.getText().toString();
        Pessoa pessoa = sessao.getPessoa();
        pessoa.setNome(nome);

        if (validaAlterar(pessoa)) {
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
