package com.thelearningproject.applogin.infraestrutura.gui;

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
import com.thelearningproject.applogin.perfil.negocio.PerfilServices;

public class CadastroHabilidadeActivity extends AppCompatActivity {
    private ControladorSessao sessao;
    private EditText entradaMateria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_habilidade);

        sessao = ControladorSessao.getInstancia(this.getApplicationContext());
        Button botaoCadastro = (Button) findViewById(R.id.BotaoInsereHabilidadeID);
        entradaMateria = (EditText) findViewById(R.id.entradaMateriaID);

        botaoCadastro.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                cadastrarMateria();
            }

        });

    }

    private void cadastrarMateria(){
        String nome = entradaMateria.getText().toString();
        MateriaServices materiaServices = MateriaServices.getInstancia(this.getApplicationContext());
        PerfilServices perfilServices = PerfilServices.getInstancia(this.getApplicationContext());
        Materia materia = new Materia();
        materia.setNome(nome);
        materia = materiaServices.cadastraMateria(materia);
        try {
            if (validaCadastro(materia)) {
                perfilServices.insereHabilidade(sessao.getPerfil(), materia);

                Auxiliar.criarToast(this, "Habilidade cadastrada com sucesso!");
                finish();
            }
        }catch (UsuarioException e){
            Auxiliar.criarToast(this, e.getMessage());
        }
    }

    private Boolean validaCadastro(Materia materia){
        Boolean validacao = true;

        if (materia.getNome() == null || materia.getNome().trim().length() == 0) {
            entradaMateria.setError("Habilidade inválida");
            validacao = false;
        }

        return validacao;
    }
}
