package com.thelearningproject.applogin.perfil.gui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.thelearningproject.applogin.R;
import com.thelearningproject.applogin.estudo.dominio.Materia;
import com.thelearningproject.applogin.estudo.negocio.MateriaServices;
import com.thelearningproject.applogin.infraestrutura.utils.Auxiliar;
import com.thelearningproject.applogin.infraestrutura.utils.ControladorSessao;
import com.thelearningproject.applogin.infraestrutura.utils.UsuarioException;
import com.thelearningproject.applogin.perfil.negocio.PerfilServices;

import java.util.ArrayList;

public class CadastroHabilidadeActivity extends AppCompatActivity {
    private ControladorSessao sessao;
    private AutoCompleteTextView entradaMateria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_habilidade);
        setTitle("Cadastrar nova habilidade");

        sessao = ControladorSessao.getInstancia(this.getApplicationContext());
        entradaMateria = (AutoCompleteTextView) findViewById(R.id.entradaMateriaID);
        entradaMateria.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Metodo obrigatorio do TextWatcher
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sugerir();
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Metodo obrigatorio do TextWatcher
            }
        });
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

    private void sugerir() {
        String nome = entradaMateria.getText().toString();
        MateriaServices materiaServices = MateriaServices.getInstancia(this);
        ArrayList<String> listaMateria = new ArrayList<>();
        listaMateria.addAll(materiaServices.retornaLista(nome));

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.select_dialog_item, materiaServices.retornaLista(nome));

        entradaMateria.setThreshold(1);
        entradaMateria.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                entradaMateria.setText(parent.getAdapter().getItem(position).toString());
            }
        });
        entradaMateria.setAdapter(adapter);
    }

    private void cadastrarMateria() {
        String nome = entradaMateria.getText().toString();
        MateriaServices materiaServices = MateriaServices.getInstancia(this.getApplicationContext());
        PerfilServices perfilServices = PerfilServices.getInstancia(this.getApplicationContext());
        Materia materia = new Materia();
        materia.setNome(nome);
        Materia novaMateria = materiaServices.cadastraMateria(materia);

        try {
            if (validaCadastro(materia)) {
                perfilServices.insereHabilidade(sessao.getPerfil(), novaMateria);
                sessao.getPerfil().addHabilidade(novaMateria);
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
