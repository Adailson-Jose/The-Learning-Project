package com.thelearningproject.applogin.perfil.gui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.thelearningproject.applogin.R;
import com.thelearningproject.applogin.estudo.dominio.Materia;
import com.thelearningproject.applogin.estudo.negocio.MateriaServices;
import com.thelearningproject.applogin.infraestrutura.utils.Auxiliar;
import com.thelearningproject.applogin.infraestrutura.utils.ControladorSessao;
import com.thelearningproject.applogin.infraestrutura.utils.MateriaAdapter;
import com.thelearningproject.applogin.perfil.negocio.PerfilServices;

import java.util.ArrayList;

public class ListarNecessidadesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, DialogInterface.OnClickListener {
    private ControladorSessao sessao;
    private PerfilServices perfilNegocio;
    private MateriaServices materiaNegocio;
    private AlertDialog alertaExcluir;
    private int idposicao;
    private AdapterView adapterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_necessidades);
        setTitle("Minhas Necessidades");
    }

    @Override
    protected void onResume() {
        super.onResume();
        sessao = ControladorSessao.getInstancia(ListarNecessidadesActivity.this);
        perfilNegocio = PerfilServices.getInstancia(this);
        materiaNegocio = MateriaServices.getInstancia(this);
        FloatingActionButton botaoInserir = (FloatingActionButton) findViewById(R.id.btnInserirNecessidade);

        listarNecessidades();

        botaoInserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListarNecessidadesActivity.this, CadastroNecessidadeActivity.class));
            }
        });
    }

    private void listarNecessidades() {
        ArrayList<Materia> listaMateria = perfilNegocio.listarNecessidade(sessao.getPerfil());

        ListView listView1 = (ListView) findViewById(R.id.listaNecessidades);

        ArrayAdapter adaptador = new MateriaAdapter(
                this, listaMateria);

        listView1.setAdapter(adaptador);

        adaptador.notifyDataSetChanged();

        listView1.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        idposicao = position;
        adapterView = parent;
        alertaExcluir = Auxiliar.criarDialogConfirmacao(ListarNecessidadesActivity.this, "Remover necessidade", "Deseja remover esta necessidade?");
        alertaExcluir.show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                Materia mat = (Materia) adapterView.getItemAtPosition(idposicao);
                perfilNegocio.deletarNecessidade(sessao.getPerfil(), materiaNegocio.consultar(mat.getId()));
                listarNecessidades();
                Auxiliar.criarToast(this, "Necessidade removida com sucesso!!!!!!");
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                alertaExcluir.dismiss();
                break;
        }
    }
}
