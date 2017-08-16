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
import com.thelearningproject.applogin.perfil.dominio.Perfil;
import com.thelearningproject.applogin.perfil.negocio.PerfilServices;

public class AlterarDescricaoActivity extends AppCompatActivity {
    private EditText alterarDescricao;
    private ControladorSessao sessao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_descricao);

        sessao = ControladorSessao.getInstancia(this.getApplicationContext());
        alterarDescricao = (EditText) findViewById(R.id.descricaoID);
        alterarDescricao.setText(sessao.getPerfil().getDescricao());
        alterarDescricao.setSelection(alterarDescricao.getText().length());
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

    private void executarAlterar(Perfil perfil) {
        PerfilServices negocioPerfil = PerfilServices.getInstancia(getApplicationContext());
        negocioPerfil.alterarPerfil(perfil);
        sessao.setPerfil(perfil);

        Auxiliar.criarToast(this, "Descrição atualizada com sucesso.");

        Intent entidade = new Intent(AlterarDescricaoActivity.this, ConfiguracaoActivity.class);
        entidade.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(entidade);
        finish();
    }

    public void alterar() {
        String descricao = alterarDescricao.getText().toString();
        Perfil perfil = sessao.getPerfil();

        perfil.setDescricao(descricao);

        if (validaAlterar(perfil)) {
            executarAlterar(perfil);
        }
    }

    private Boolean validaAlterar(Perfil perfil) {
        Boolean validacao = true;
        if (perfil.getDescricao() == null || perfil.getDescricao().trim().length() == 0) {
            alterarDescricao.setError("Descrição inválida");
            validacao = false;
        }
        return validacao;
    }
}
