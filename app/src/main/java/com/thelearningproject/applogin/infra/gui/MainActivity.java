package com.thelearningproject.applogin.infra.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.thelearningproject.applogin.R;
import com.thelearningproject.applogin.infra.utils.SessionController;
import com.thelearningproject.applogin.perfil.dominio.Perfil;
import com.thelearningproject.applogin.perfil.negocio.PerfilServices;
import com.thelearningproject.applogin.usuario.dominio.Usuario;
import com.thelearningproject.applogin.usuario.negocio.UsuarioServices;

public class MainActivity extends AppCompatActivity {
    private SessionController sessao;
    private UsuarioServices negociousuario;
    private PerfilServices negocioperfil;

    private Button botaoconfig;
    private Button botaoBusca;
    private Button botaoInsereMateria;
    private TextView apresentacao;

    private Button btnAbrir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessao = SessionController.getInstance(this.getApplicationContext());

        apresentacao = (TextView) findViewById(R.id.apresentacaoID);
        botaoconfig = (Button) findViewById(R.id.configID);
        botaoBusca = (Button) findViewById(R.id.botaoBuscaID);
        botaoInsereMateria = (Button) findViewById(R.id.BotaoCadastraMateriaID);

        btnAbrir = (Button) findViewById(R.id.btnChamar);

        if(sessao.verificarConectado()) {
            resumir();
        }
        if(sessao.verificaLogin()){
            finish();
        }else {
            exibir();
        }

        botaoBusca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BuscaActivity.class));
            }
            });

        botaoconfig.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, ConfiguracaoActivity.class));
            }
        });

        btnAbrir.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
            }
        });

        botaoInsereMateria.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, CadastroMateriaActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void resumir(){
        negociousuario = UsuarioServices.getInstancia(getBaseContext());
        Usuario usuario = negociousuario.consulta(sessao.retornaID());
        sessao.iniciaSessao();
        sessao.setUsuario(usuario);
    }

    private void exibir() {
        negocioperfil = PerfilServices.getInstancia(getBaseContext());

        Perfil perfil = negocioperfil.retornaPerfil(sessao.getUsuario().getId());
        perfil.setUsuario(sessao.getUsuario());
        sessao.setPerfil(perfil);

        String mensagem = "Oi, " + sessao.getUsuario().getNome() + ".";

        apresentacao.setText(mensagem);


    }
}
