package com.thelearningproject.applogin.perfil.gui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
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
        setTitle("Cadastrar nova habilidade");

        sessao = ControladorSessao.getInstancia(this.getApplicationContext());
        entradaMateria = (EditText) findViewById(R.id.entradaMateriaID);
        Auxiliar.abrirTeclado(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.salvar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.salvarBtn) {
            cadastrarMateria();
        }
        Auxiliar.esconderTeclado(this);
        return super.onOptionsItemSelected(item);
    }

    private void cadastrarMateria() {
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
        } catch (UsuarioException e) {
            Auxiliar.criarToast(this, e.getMessage());
        }
    }

    private Boolean validaCadastro(Materia materia) {
        Boolean validacao = true;

        if (materia.getNome() == null || materia.getNome().trim().length() == 0) {
            entradaMateria.setError("Habilidade inválida");
            validacao = false;
        }

        return validacao;
    }
}
