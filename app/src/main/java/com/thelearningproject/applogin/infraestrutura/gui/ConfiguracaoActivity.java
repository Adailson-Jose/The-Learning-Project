package com.thelearningproject.applogin.infraestrutura.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.thelearningproject.applogin.R;
import com.thelearningproject.applogin.infraestrutura.utils.ControladorSessao;
import com.thelearningproject.applogin.usuario.negocio.UsuarioServices;

/**
 * Criado por Heitor em 25/07/2017.
 */

public class ConfiguracaoActivity extends AppCompatActivity{
    private ControladorSessao session;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);
        setTitle("Configurações");

        session = ControladorSessao.getInstancia(this.getApplicationContext());

        Button botaoAlterarNome = (Button) findViewById(R.id.alterarNomeID);
        Button botaoAlterarEmail = (Button) findViewById(R.id.alterarEmailID);
        Button botaoAlterarSenha = (Button) findViewById(R.id.alterarSenhaID);
        Button botaoDesativar = (Button) findViewById(R.id.deletarID);
        Button botaoLogout = (Button) findViewById(R.id.LogoutID);

        botaoAlterarNome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent entidade = new Intent(ConfiguracaoActivity.this, AlterarNomeActivity.class);
                entidade.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(entidade);
            }

        });
        botaoAlterarEmail.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent entidade = new Intent(ConfiguracaoActivity.this, AlterarEmailActivity.class);
                entidade.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(entidade);
            }

        });
        botaoAlterarSenha.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent entidade = new Intent(ConfiguracaoActivity.this, AlterarSenhaActivity.class);
                entidade.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(entidade);
            }

        });
        botaoDesativar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                desativar();
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
    }

    private void desativar(){
        UsuarioServices negocio = UsuarioServices.getInstancia(getBaseContext());
        negocio.deletarUsuario(session.getUsuario());
        Toast.makeText(ConfiguracaoActivity.this, "Usuário desativado com sucesso", Toast.LENGTH_LONG).show();
        finish();
        session.encerraSessao();
    }


}
