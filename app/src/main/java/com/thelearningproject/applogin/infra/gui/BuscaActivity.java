package com.thelearningproject.applogin.infra.gui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.thelearningproject.applogin.R;
import com.thelearningproject.applogin.estudo.dominio.Materia;
import com.thelearningproject.applogin.estudo.negocio.MateriaServices;
import com.thelearningproject.applogin.infra.utils.SessionController;
import com.thelearningproject.applogin.perfil.dominio.Perfil;
import com.thelearningproject.applogin.perfil.negocio.PerfilServices;

import java.util.ArrayList;

public class BuscaActivity extends AppCompatActivity {

    private ListView listaUsuarios;
    private EditText entradaBusca;
    private Button botaoBusca;
    private SessionController sessao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca);

        sessao = SessionController.getInstance(this.getApplicationContext());
        listaUsuarios = (ListView) findViewById(R.id.listViewID);
        entradaBusca = (EditText) findViewById(R.id.editTextBuscaID);
        botaoBusca = (Button) findViewById(R.id.botaoBuscaID);

        botaoBusca.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                listar(v);
            }

        });
    }

    private void listar(View v){
        String nome = entradaBusca.getText().toString();
        Materia materia = new Materia();
        materia.setNome(nome);
        MateriaServices materiaServices = MateriaServices.getInstancia(this.getApplicationContext());
        PerfilServices perfilServices = PerfilServices.getInstancia(this.getApplicationContext());
        materia = materiaServices.cadastraMateria(materia);
        ArrayList<Perfil> lista_perfil = perfilServices.listarPerfil(materia);
        if (lista_perfil.size() > 0) {
            ArrayAdapter<Perfil> adaptador = new ArrayAdapter<>(
                    getApplicationContext(),
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    lista_perfil
            );
            listaUsuarios.setAdapter(adaptador);
        }else{
            Toast.makeText(this, "Sem resultados", Toast.LENGTH_LONG).show();
        }
    }


}
