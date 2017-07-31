package com.thelearningproject.applogin.perfil.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.thelearningproject.applogin.R;
import com.thelearningproject.applogin.estudo.dominio.Materia;
import com.thelearningproject.applogin.estudo.negocio.MateriaServices;
import com.thelearningproject.applogin.infraestrutura.gui.MainActivity;
import com.thelearningproject.applogin.infraestrutura.utils.Auxiliar;
import com.thelearningproject.applogin.infraestrutura.utils.ControladorSessao;
import com.thelearningproject.applogin.infraestrutura.utils.UsuarioException;
import com.thelearningproject.applogin.perfil.dominio.Perfil;
import com.thelearningproject.applogin.perfil.negocio.PerfilServices;
import com.thelearningproject.applogin.pessoa.dominio.Pessoa;
import com.thelearningproject.applogin.pessoa.negocio.PessoaServiços;
import com.thelearningproject.applogin.usuario.dominio.Usuario;
import com.thelearningproject.applogin.usuario.negocio.UsuarioServices;

public class HabilidadeActivity extends AppCompatActivity {
    private ControladorSessao sessao;
    private EditText entradahabilidade;
    private EditText entradadescricao;
    private Button botaocriar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habilidade);

        sessao = ControladorSessao.getInstancia(this.getApplicationContext());

        entradahabilidade = (EditText) findViewById(R.id.habilidadeentrada);
        entradadescricao = (EditText) findViewById(R.id.descricaoentrada);

        botaocriar = (Button) findViewById(R.id.botaoContinuar2);
        botaocriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                criarPerfil(view);
                Auxiliar.esconderTeclado(HabilidadeActivity.this);
            }
        });
    }

    private void criarPerfil(View view) {
        PerfilServices negocioperfil = PerfilServices.getInstancia(getBaseContext());
        UsuarioServices negocioUsuario = UsuarioServices.getInstancia(getBaseContext());
        PessoaServiços negocioPessoa = PessoaServiços.getInstancia(getBaseContext());
        MateriaServices materiaServices = MateriaServices.getInstancia(getBaseContext());

        String habilidade = entradahabilidade.getText().toString();

        Usuario usuario = negocioUsuario.retornaUsuario(sessao.getUsuario().getEmail());
        Pessoa pessoa = negocioPessoa.retornaPessoa(sessao.getUsuario().getId());


        try {
            Materia materia = new Materia();
            materia.setNome(habilidade);

            if(validaHabilidade(materia)) {
                materia = materiaServices.cadastraMateria(materia);

                Perfil perfil = new Perfil();
                perfil.setPessoaID(pessoa.getId());
                perfil.addHabilidade(materia);

                negocioperfil.inserirPerfil(perfil);
                perfil = negocioperfil.retornaPerfil(pessoa.getId());
                perfil.setPessoa(pessoa);
                negocioperfil.insereHabilidade(perfil, materia);

                Toast.makeText(this, "Cadastro efetuado com sucesso.", Toast.LENGTH_LONG).show();


                sessao.setPerfil(perfil);
                sessao.iniciaSessao();

                Intent entidade = new Intent(HabilidadeActivity.this, MainActivity.class);
                entidade.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                entidade.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                finish();
                startActivity(entidade);
            }

        } catch (UsuarioException e){
            Toast.makeText(this, "Materia já cadastrada", Toast.LENGTH_LONG).show();
        }


    }

    private Boolean validaHabilidade(Materia materia){
        Boolean validacao=true;
        StringBuilder erro = new StringBuilder();
        if (materia.getNome() == null || materia.getNome().trim().length() == 0) {
            entradahabilidade.setError("Habilidade inválida");
            validacao = false;
        }
        String resultado = (erro.toString().trim());
        if (resultado!= "") {
            Toast.makeText(HabilidadeActivity.this, resultado, Toast.LENGTH_LONG).show();
        }
        return validacao;
    }
}
