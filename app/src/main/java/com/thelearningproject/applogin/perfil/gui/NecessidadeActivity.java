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
import com.thelearningproject.applogin.infraestrutura.gui.MainActivity;
import com.thelearningproject.applogin.infraestrutura.utils.Auxiliar;
import com.thelearningproject.applogin.infraestrutura.utils.ControladorSessao;
import com.thelearningproject.applogin.infraestrutura.utils.UsuarioException;
import com.thelearningproject.applogin.perfil.dominio.Perfil;
import com.thelearningproject.applogin.perfil.negocio.PerfilServices;
import com.thelearningproject.applogin.pessoa.negocio.PessoaServices;
import com.thelearningproject.applogin.usuario.negocio.UsuarioServices;

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
            public void onClick(View v) {
                processoCadastroNecessidade();
                Auxiliar.esconderTeclado(NecessidadeActivity.this);

                Intent entidade = new Intent(NecessidadeActivity.this, MainActivity.class);
                entidade.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                entidade.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                finish();
                startActivity(entidade);
            }
        });

    }

    private Boolean validaNecessidade(Materia materia){
        Boolean validacao=true;
        StringBuilder erro = new StringBuilder();

        if (materia.getNome() == null || materia.getNome().trim().length() == 0) {
            entradaNecessidade.setError("Habilidade inválida");
            validacao = false;
        }

        String resultado = (erro.toString().trim());

        if (!resultado.equals("")) {
            Toast.makeText(NecessidadeActivity.this, resultado, Toast.LENGTH_LONG).show();
        }

        return validacao;
    }

    private void processoCadastroNecessidade() {
        String necessidade = entradaNecessidade.getText().toString();

        Materia materia = new Materia();
        materia.setNome(necessidade);

        try {
            if(validaNecessidade(materia)) {
                executarCadastroNecessidade(materia);
            }

        } catch (UsuarioException e){
            Toast.makeText(this, "Matéria já cadastrada", Toast.LENGTH_LONG).show();
        }
    }

    private void executarCadastroNecessidade(Materia materia) throws UsuarioException {
        PerfilServices negocioperfil = PerfilServices.getInstancia(getBaseContext());
        MateriaServices materiaServices = MateriaServices.getInstancia(getBaseContext());

        Perfil perfil = negocioperfil.retornaPerfil(sessao.getPessoa().getId());
        materia = materiaServices.cadastraMateria(materia);
        negocioperfil.insereNecessidade(perfil, materia);
        perfil.addNecessidade(materia);

        Toast.makeText(this, "Necessidade cadastrada com sucesso.", Toast.LENGTH_LONG).show();


    }
}
