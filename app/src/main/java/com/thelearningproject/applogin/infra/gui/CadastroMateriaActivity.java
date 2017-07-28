package com.thelearningproject.applogin.infra.gui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.thelearningproject.applogin.R;
import com.thelearningproject.applogin.estudo.dominio.Materia;
import com.thelearningproject.applogin.estudo.negocio.MateriaServices;
import com.thelearningproject.applogin.infra.utils.SessionController;
import com.thelearningproject.applogin.infra.utils.UsuarioException;
import com.thelearningproject.applogin.perfil.negocio.PerfilServices;

public class CadastroMateriaActivity extends AppCompatActivity {
    private SessionController sessao;
    private Button botaoCadastro;
    private EditText entradaMateria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_materia);

        sessao = SessionController.getInstance(this.getApplicationContext());
        botaoCadastro = (Button) findViewById(R.id.BotaoCadastraMateriaID);
        entradaMateria = (EditText) findViewById(R.id.entradaMateriaID);

        botaoCadastro.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                cadastrarMateria(v);
            }

        });

    }

    private void cadastrarMateria(View v){
        String nome = entradaMateria.getText().toString();
        MateriaServices materiaServices = MateriaServices.getInstancia(this.getApplicationContext());
        PerfilServices perfilServices = PerfilServices.getInstancia(this.getApplicationContext());
        Materia materia = new Materia();
        materia.setNome(nome);
        materia = materiaServices.cadastraMateria(materia);
        try {
            if (validaCadastro(materia)) {
                perfilServices.insereHabilidade(sessao.getPerfil(), materia);

                Toast.makeText(CadastroMateriaActivity.this, "Habilidade cadastrada com sucesso!", Toast.LENGTH_LONG).show();
                finish();
            }
        }catch (UsuarioException e){
            Toast.makeText(this.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private Boolean validaCadastro(Materia materia){
        Boolean validacao=true;
        StringBuilder erro = new StringBuilder();
        if (materia.getNome() == null || materia.getNome().trim().length() == 0) {
            entradaMateria.setError("Habilidade inválida");
            validacao = false;
        }
        String resultado = (erro.toString().trim());
        if (resultado!= "") {
            Toast.makeText(CadastroMateriaActivity.this, resultado, Toast.LENGTH_LONG).show();
        }
        return validacao;
    }
}
