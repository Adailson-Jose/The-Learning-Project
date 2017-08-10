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
import com.thelearningproject.applogin.infraestrutura.gui.MainActivity;
import com.thelearningproject.applogin.infraestrutura.utils.Auxiliar;
import com.thelearningproject.applogin.infraestrutura.utils.ControladorSessao;
import com.thelearningproject.applogin.infraestrutura.utils.UsuarioException;
import com.thelearningproject.applogin.perfil.dominio.Perfil;
import com.thelearningproject.applogin.perfil.negocio.PerfilServices;

public class NecessidadeActivity extends AppCompatActivity {
    private ControladorSessao sessao;
    private EditText entradaNecessidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_necessidade);

        sessao = ControladorSessao.getInstancia(this.getApplicationContext());
        entradaNecessidade = (EditText) findViewById(R.id.necessidadeentrada);

        Button botaoFinalizar = (Button) findViewById(R.id.botaoContinuar3);
        botaoFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processoCadastroNecessidade();
                Auxiliar.esconderTeclado(NecessidadeActivity.this);
            }
        });
    }

    private Boolean validaNecessidade(Materia materia) {
        Boolean validacao = true;

        if (materia.getNome() == null || materia.getNome().trim().length() == 0) {
            entradaNecessidade.setError("Necessidade inválida");
            validacao = false;
        }

        return validacao;
    }

    private void processoCadastroNecessidade() {
        String necessidade = entradaNecessidade.getText().toString();

        Materia materia = new Materia();
        materia.setNome(necessidade);

        try {
            if (validaNecessidade(materia)) {
                executarCadastroNecessidade(materia);
            }

        } catch (UsuarioException e) {
            Auxiliar.criarToast(this, e.getMessage());
        }
    }

    private void executarCadastroNecessidade(Materia materia) throws UsuarioException {
        PerfilServices negocioperfil = PerfilServices.getInstancia(getBaseContext());
        MateriaServices materiaServices = MateriaServices.getInstancia(getBaseContext());

        Perfil perfil = sessao.getPerfil();
        Materia novaMateria = materiaServices.cadastraMateria(materia);
        negocioperfil.insereNecessidade(perfil, novaMateria);
        perfil.addNecessidade(novaMateria);

        Auxiliar.criarToast(this, "Necessidade cadastrada com sucesso");
        Intent entidade = new Intent(NecessidadeActivity.this, MainActivity.class);
        entidade.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        entidade.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(entidade);
        finish();
    }
}
