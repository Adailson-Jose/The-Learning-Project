package com.thelearningproject.perfil.gui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.thelearningproject.R;
import com.thelearningproject.estudo.dominio.Materia;
import com.thelearningproject.estudo.negocio.MateriaServices;
import com.thelearningproject.infraestrutura.utils.Auxiliar;
import com.thelearningproject.infraestrutura.utils.ControladorSessao;
import com.thelearningproject.infraestrutura.utils.MateriaAdapter;
import com.thelearningproject.perfil.dominio.IExcluiMateria;
import com.thelearningproject.perfil.negocio.PerfilServices;

import java.util.ArrayList;

/**
 * The type Listar necessidades activity.
 */
public class ListarNecessidadesActivity extends AppCompatActivity implements DialogInterface.OnClickListener, IExcluiMateria {
    private ControladorSessao sessao;
    private PerfilServices perfilNegocio;
    private MateriaServices materiaNegocio;
    private Materia materiaAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_necessidades);
        setTitle("Minhas necessidades");
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
        ArrayAdapter adaptador = new MateriaAdapter(this, listaMateria, this);
        listView1.setAdapter(adaptador);
        adaptador.notifyDataSetChanged();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                perfilNegocio.deletarNecessidade(sessao.getPerfil(), materiaNegocio.consultar(this.materiaAtual.getId()));
                listarNecessidades();
                Auxiliar.criarToast(this, "Necessidade removida com sucesso");
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                dialog.dismiss();
                break;
        }
    }

    @Override
    public void excluirMateria(Materia mat) {
        this.materiaAtual = mat;
        AlertDialog alertaExcluir = Auxiliar.criarDialogConfirmacao(ListarNecessidadesActivity.this, "Remover necessidade", "Deseja remover esta necessidade?");
        alertaExcluir.show();
    }
}
