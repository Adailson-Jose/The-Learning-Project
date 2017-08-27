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
import com.thelearningproject.infraestrutura.utils.UsuarioException;
import com.thelearningproject.pessoa.dominio.Pessoa;
import com.thelearningproject.usuario.dominio.Usuario;
import com.thelearningproject.usuario.negocio.UsuarioServices;

/**
 * The type Alterar email activity.
 */
public class AlterarEmailActivity extends AppCompatActivity {
    private EditText alterarEmail;
    private Auxiliar auxiliar = new Auxiliar();
    private ControladorSessao sessao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_email);
        setTitle(R.string.alterarEmail);

        sessao = ControladorSessao.getInstancia(this.getApplicationContext());
        alterarEmail = (EditText) findViewById(R.id.emailID);
        alterarEmail.setText(sessao.getPessoa().getUsuario().getEmail());
        alterarEmail.setSelection(alterarEmail.getText().length());
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
        Auxiliar.esconderTeclado(AlterarEmailActivity.this);
        return super.onOptionsItemSelected(item);
    }

    private void executarAlterar(Usuario usuario) throws UsuarioException {
        UsuarioServices negocioUsuario = UsuarioServices.getInstancia(getBaseContext());

        negocioUsuario.alterarEmailUsuario(usuario);
        Pessoa pessoa = sessao.getPessoa();
        pessoa.setUsuario(usuario);
        sessao.setPessoa(pessoa);

        Auxiliar.criarToast(this, "E-mail atualizado com sucesso");

        Intent entidade = new Intent(AlterarEmailActivity.this, ConfiguracaoActivity.class);
        entidade.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(entidade);
        finish();
    }

    /**
     * Alterar.
     */
    public void alterar() {
        String email = alterarEmail.getText().toString();
        Usuario usuario = sessao.getPessoa().getUsuario();
        usuario.setEmail(email);

        try {
            if (validaAlterar(usuario)) {
                executarAlterar(usuario);
            }

        } catch (UsuarioException e) {
            alterarEmail.setError("E-mail já cadastrado");
        }
    }

    private Boolean validaAlterar(Usuario usuario) {
        Boolean validacao = true;
        if (!auxiliar.aplicaPattern(usuario.getEmail().toUpperCase())) {
            alterarEmail.setError("E-mail inválido");
            validacao = false;
        }
        return validacao;
    }
}
