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
 * The type Alterar telefone activity.
 */
public class AlterarTelefoneActivity extends AppCompatActivity {
    private EditText alterarTelefone;
    private ControladorSessao sessao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_telefone);

        sessao = ControladorSessao.getInstancia(this.getApplicationContext());
        alterarTelefone = (EditText) findViewById(R.id.telefoneID);
        alterarTelefone.setText(sessao.getPessoa().getTelefone());
        alterarTelefone.setSelection(alterarTelefone.getText().length());
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

        Auxiliar.criarToast(this, "Telefone atualizado com sucesso");

        Intent entidade = new Intent(AlterarTelefoneActivity.this, ConfiguracaoActivity.class);
        entidade.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(entidade);
        finish();
    }

    /**
     * Alterar.
     */
    public void alterar() {
        String telefone = alterarTelefone.getText().toString();
        Pessoa pessoa = sessao.getPessoa();
        pessoa.setTelefone(telefone);

        if (validaAlterar(pessoa)) {
            executarAlterar(pessoa);
        }
    }

    private Boolean validaAlterar(Pessoa pessoa) {
        Boolean validacao = true;
        if (!Auxiliar.telefonePattern(pessoa.getTelefone())) {
            alterarTelefone.setError("Telefone inválido");
            validacao = false;
        }
        return validacao;
    }
}
