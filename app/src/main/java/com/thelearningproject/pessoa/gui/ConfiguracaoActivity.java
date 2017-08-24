package com.thelearningproject.pessoa.gui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.thelearningproject.R;
import com.thelearningproject.infraestrutura.utils.Auxiliar;
import com.thelearningproject.infraestrutura.utils.ControladorSessao;
import com.thelearningproject.usuario.negocio.UsuarioServices;

/**
 * Criado por Heitor em 25/07/2017.
 */

public class ConfiguracaoActivity extends AppCompatActivity implements DialogInterface.OnClickListener, AdapterView.OnItemClickListener {
    private ControladorSessao session;
    private AlertDialog alertExclusao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);
        setTitle("Configurações");

        session = ControladorSessao.getInstancia(this.getApplicationContext());
        alertExclusao = Auxiliar.criarDialogConfirmacao(this, "Excluir conta", "Realmente deseja excluir sua conta?");

        String[] listaOpPerfil = {getApplicationContext().getString(R.string.alterarNome), getApplicationContext().getString(R.string.alterarDescricao),
                getApplicationContext().getString(R.string.alterarTelefone)};
        String[] listaOpConta = {getApplicationContext().getString(R.string.alterarEmail), getApplicationContext().getString(R.string.alterarSenha)};
        String[] listaOpOutras = {getApplicationContext().getString(R.string.excluir)};

        ListView listView1 = (ListView) findViewById(R.id.listaConfigPerfil);
        ListView listView2 = (ListView) findViewById(R.id.listaConfigConta);
        ListView listView3 = (ListView) findViewById(R.id.listaConfigOpcoes);

        ArrayAdapter<String> adapterOpcoesPerfil = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                listaOpPerfil);

        ArrayAdapter<String> adapterOpcoesConta = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                listaOpConta);

        ArrayAdapter<String> adapterOpcoesOutras = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                listaOpOutras);

        listView1.setAdapter(adapterOpcoesPerfil);
        listView2.setAdapter(adapterOpcoesConta);
        listView3.setAdapter(adapterOpcoesOutras);

        listView1.setOnItemClickListener(this);
        listView2.setOnItemClickListener(this);
        listView3.setOnItemClickListener(this);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                desativar();
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                alertExclusao.dismiss();
                break;
        }
    }

    private void desativar() {
        UsuarioServices negocio = UsuarioServices.getInstancia(getBaseContext());
        negocio.deletarUsuario(session.getPessoa().getUsuario());
        Auxiliar.criarToast(ConfiguracaoActivity.this, "Usuário deletado com sucesso");
        finish();
        session.encerraSessao();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String opcao = String.valueOf(parent.getAdapter().getItem(position));
        switch (opcao) {
            case "Alterar nome":
                Intent entidade1 = new Intent(ConfiguracaoActivity.this, AlterarNomeActivity.class);
                entidade1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(entidade1);
                break;
            case "Alterar descrição":
                Intent entidade2 = new Intent(ConfiguracaoActivity.this, AlterarDescricaoActivity.class);
                entidade2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(entidade2);
                break;
            case "Alterar telefone":
                Intent entidade3 = new Intent(ConfiguracaoActivity.this, AlterarTelefoneActivity.class);
                entidade3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(entidade3);
                break;
            case "Alterar e-mail":
                Intent entidade4 = new Intent(ConfiguracaoActivity.this, AlterarEmailActivity.class);
                entidade4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(entidade4);
                break;
            case "Alterar senha":
                Intent entidade5 = new Intent(ConfiguracaoActivity.this, AlterarSenhaActivity.class);
                entidade5.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(entidade5);
                break;
            case "Excluir conta":
                alertExclusao.show();
                break;
        }
    }
}
