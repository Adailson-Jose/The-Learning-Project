package com.thelearningproject.applogin.infraestrutura.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.thelearningproject.applogin.R;
import com.thelearningproject.applogin.infraestrutura.utils.ControladorSessao;
import com.thelearningproject.applogin.perfil.dominio.Perfil;
import com.thelearningproject.applogin.perfil.negocio.PerfilServices;
import com.thelearningproject.applogin.pessoa.dominio.Pessoa;
import com.thelearningproject.applogin.pessoa.negocio.PessoaServiços;
import com.thelearningproject.applogin.usuario.dominio.Usuario;
import com.thelearningproject.applogin.usuario.negocio.UsuarioServices;

public class MainActivity extends AppCompatActivity {
    private ControladorSessao sessao;
    private TextView apresentacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessao = ControladorSessao.getInstancia(this.getApplicationContext());
        apresentacao = (TextView) findViewById(R.id.apresentacaoID);
        Button botaoconfig = (Button) findViewById(R.id.configID);
        Button botaoBusca = (Button) findViewById(R.id.botaoBuscaID);
        Button botaoInsereMateria = (Button) findViewById(R.id.BotaoCadastraMateriaID);
        Button botaoabrir = (Button) findViewById(R.id.btnChamar);

        if(sessao.verificaConexao()) {
            resumir();
        }

        if(sessao.verificaLogin()){
            finish();

        } else {
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

        botaoabrir.setOnClickListener(new View.OnClickListener(){
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
        UsuarioServices negociousuario = UsuarioServices.getInstancia(getBaseContext());
        PessoaServiços negociopessoa = PessoaServiços.getInstancia(getBaseContext());

        Usuario usuario = negociousuario.consulta(sessao.retornaIdUsuario());
        Pessoa pessoa = negociopessoa.consulta(sessao.retornaIdPessoa());
        pessoa.setUsuario(usuario);

        sessao.iniciaSessao();
        sessao.setUsuario(usuario);
        sessao.setPessoa(pessoa);
    }

    private void exibir() {
        PerfilServices negocioperfil = PerfilServices.getInstancia(getBaseContext());

        Perfil perfil = negocioperfil.retornaPerfil(sessao.getPessoa().getId());
        perfil.setPessoa(sessao.getPessoa());
        sessao.setPerfil(perfil);

        String mensagem = "Oi, " + sessao.getPessoa().getNome() + ".";

        apresentacao.setText(mensagem);


    }
}
