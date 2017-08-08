package com.thelearningproject.applogin.pessoa.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.thelearningproject.applogin.R;
import com.thelearningproject.applogin.infraestrutura.utils.Auxiliar;
import com.thelearningproject.applogin.infraestrutura.utils.ControladorSessao;
import com.thelearningproject.applogin.pessoa.dominio.Pessoa;
import com.thelearningproject.applogin.usuario.dominio.Usuario;
import com.thelearningproject.applogin.usuario.negocio.UsuarioServices;

public class AlterarSenhaActivity extends AppCompatActivity {
    private EditText alterarSenha;
    private ControladorSessao sessao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_senha);

        sessao = ControladorSessao.getInstancia(this.getApplicationContext());
        alterarSenha = (EditText) findViewById(R.id.senhaID);
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
            Auxiliar.esconderTeclado(this);
        }
        return super.onOptionsItemSelected(item);
    }

    private void executarAlterar(Usuario usuario){
        UsuarioServices negocioUsuario = UsuarioServices.getInstancia(getBaseContext());
        negocioUsuario.alterarSenhaUsuario(usuario);

        Pessoa pessoa = sessao.getPessoa();
        pessoa.setUsuario(usuario);
        sessao.setPessoa(pessoa);

        Auxiliar.criarToast(this, "Senha atualizada com sucesso");

        Intent entidade = new Intent(AlterarSenhaActivity.this, ConfiguracaoActivity.class);
        entidade.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(entidade);
        finish();
    }

    public void alterar(){
        String senha = alterarSenha.getText().toString();
        Usuario usuario = sessao.getPessoa().getUsuario();
        usuario.setSenha(senha);

        if(validaAlterar(usuario)){
            executarAlterar(usuario);
        }
    }

    private Boolean validaAlterar(Usuario usuario) {
        Boolean validacao = true;
        if (usuario.getSenha() == null || usuario.getSenha().trim().length() == 0) {
            alterarSenha.setError("Senha inválida");
            validacao = false;
        }
        return validacao;
    }
}
