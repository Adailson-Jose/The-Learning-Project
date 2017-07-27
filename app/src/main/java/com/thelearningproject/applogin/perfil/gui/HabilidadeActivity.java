package com.thelearningproject.applogin.perfil.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.thelearningproject.applogin.R;
import com.thelearningproject.applogin.estudo.dominio.Materia;
import com.thelearningproject.applogin.estudo.negocio.MateriaServices;
import com.thelearningproject.applogin.infra.SessionController;
import com.thelearningproject.applogin.infra.UsuarioException;
import com.thelearningproject.applogin.perfil.dominio.Perfil;
import com.thelearningproject.applogin.perfil.negocio.PerfilServices;
import com.thelearningproject.applogin.usuario.dominio.Usuario;
import com.thelearningproject.applogin.infra.gui.MainActivity;
import com.thelearningproject.applogin.usuario.negocio.UsuarioServices;

public class HabilidadeActivity extends AppCompatActivity {
    private SessionController sessao;
    private EditText entradahabilidade;
    private EditText entradadescricao;
    private Button botaocriar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habilidade);

        sessao = SessionController.getInstance(this.getApplicationContext());
        entradahabilidade = (EditText) findViewById(R.id.habilidadeentrada);
        entradadescricao = (EditText) findViewById(R.id.descricaoentrada);

        botaocriar = (Button) findViewById(R.id.botaoContinuar2);
        botaocriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                criarPerfil(view);
            }
        });
    }

    private void criarPerfil(View view) {
        PerfilServices negocioperfil = PerfilServices.getInstancia(getBaseContext());
        UsuarioServices negociousuario = UsuarioServices.getInstancia(getBaseContext());
        MateriaServices materiaServices = MateriaServices.getInstancia(getBaseContext());

        String habilidade = entradahabilidade.getText().toString();

        Usuario usuario = negociousuario.retornaUsuario(sessao.getEmail());
        try {
            Materia materia = new Materia();
            materia.setNome(habilidade);
            materia = materiaServices.cadastraMateria(materia);

            Perfil perfil = new Perfil();
            perfil.setUsuarioID(usuario.getId());
            perfil.addHabilidade(materia);
            negocioperfil.inserirPerfil(perfil);
            perfil = negocioperfil.retornaPerfil(usuario.getId());
            negocioperfil.insereHabilidade(perfil, materia);
            Toast.makeText(this, "Cadastro efetuado com sucesso.", Toast.LENGTH_LONG).show();
            sessao.setPerfil(perfil);
            sessao.iniciaSessao();

            Intent entidade = new Intent(HabilidadeActivity.this, MainActivity.class);
            entidade.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            entidade.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(entidade);

            finish();

        } catch (UsuarioException e){
            Toast.makeText(this, "Materia já cadastrada", Toast.LENGTH_LONG).show();
        }




    }
}
