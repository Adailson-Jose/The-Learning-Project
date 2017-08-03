package com.thelearningproject.applogin.perfil.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.thelearningproject.applogin.R;
import com.thelearningproject.applogin.estudo.dominio.Materia;
import com.thelearningproject.applogin.estudo.negocio.MateriaServices;
import com.thelearningproject.applogin.infraestrutura.utils.Auxiliar;
import com.thelearningproject.applogin.infraestrutura.utils.ControladorSessao;
import com.thelearningproject.applogin.infraestrutura.utils.UsuarioException;
import com.thelearningproject.applogin.perfil.dominio.Perfil;
import com.thelearningproject.applogin.perfil.negocio.PerfilServices;

public class HabilidadeActivity extends AppCompatActivity {
    private ControladorSessao sessao;
    private EditText entradaHabilidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habilidade);

        sessao = ControladorSessao.getInstancia(this.getApplicationContext());
        entradaHabilidade = (EditText) findViewById(R.id.habilidadeentrada);

        Button botaoContinuar = (Button) findViewById(R.id.botaoContinuar2);
        botaoContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processoCadastroHabilidade();
                Auxiliar.esconderTeclado(HabilidadeActivity.this);

                Intent entidade = new Intent(HabilidadeActivity.this, NecessidadeActivity.class);
                entidade.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                entidade.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(entidade);
                finish();
            }
        });
    }

    private Boolean validaHabilidade(Materia materia){
        Boolean validacao = true;

        if (materia.getNome() == null || materia.getNome().trim().length() == 0) {
            entradaHabilidade.setError("Habilidade inválida");
            validacao = false;
        }

        return validacao;
    }

    private void processoCadastroHabilidade() {
        String habilidade = entradaHabilidade.getText().toString();

        Materia materia = new Materia();
        materia.setNome(habilidade);

        try {
            if(validaHabilidade(materia)) {
                executarCadastroHabilidade(materia);
            }

        } catch (UsuarioException e){
            Auxiliar.criarToast(this, "Matéria já cadastrada");
        }
    }

    private void executarCadastroHabilidade(Materia materia) throws UsuarioException {
        PerfilServices negocioperfil = PerfilServices.getInstancia(getBaseContext());
        MateriaServices materiaServices = MateriaServices.getInstancia(getBaseContext());

        Perfil perfil = sessao.getPerfil();
        Materia novaMateria = materiaServices.cadastraMateria(materia);
        negocioperfil.insereHabilidade(perfil, novaMateria);
        perfil.addHabilidade(novaMateria);

        Auxiliar.criarToast(this, "Habilidade cadastrada com sucesso");
    }

}
