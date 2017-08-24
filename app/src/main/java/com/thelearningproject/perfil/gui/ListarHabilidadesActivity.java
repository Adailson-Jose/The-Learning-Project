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
import com.thelearningproject.infraestrutura.utils.Auxiliar;
import com.thelearningproject.infraestrutura.utils.ControladorSessao;
import com.thelearningproject.infraestrutura.utils.MateriaAdapter;
import com.thelearningproject.perfil.dominio.IExcluiMateria;
import com.thelearningproject.perfil.negocio.PerfilServices;

import java.util.ArrayList;

public class ListarHabilidadesActivity extends AppCompatActivity implements DialogInterface.OnClickListener, IExcluiMateria {
    private ControladorSessao sessao;
    private PerfilServices perfilNegocio;
    private Materia materiaAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_habilidades);
        setTitle("Minhas habilidades");
    }

    @Override
    protected void onResume() {
        super.onResume();
        sessao = ControladorSessao.getInstancia(ListarHabilidadesActivity.this);
        perfilNegocio = PerfilServices.getInstancia(this);
        FloatingActionButton botaoInserir = (FloatingActionButton) findViewById(R.id.btnInserirHabilidade);

        listarHabilidades();

        botaoInserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListarHabilidadesActivity.this, CadastroHabilidadeActivity.class));
            }
        });
    }

    private void listarHabilidades() {
        ArrayList<Materia> listaMateria = perfilNegocio.listarHabilidade(sessao.getPerfil());
        ListView listView1 = (ListView) findViewById(R.id.listaHabilidades);
        ArrayAdapter adaptador = new MateriaAdapter(this, listaMateria, this);
        listView1.setAdapter(adaptador);
        adaptador.notifyDataSetChanged();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                perfilNegocio.deletarHabilidade(sessao.getPerfil(), this.materiaAtual);
                listarHabilidades();
                Auxiliar.criarToast(this, "Habilidade removida com sucesso");
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                dialog.dismiss();
                break;
        }
    }

    @Override
    public void excluirMateria(Materia materia) {
        this.materiaAtual = materia;
        AlertDialog alertaExcluir = Auxiliar.criarDialogConfirmacao(ListarHabilidadesActivity.this, "Remover habilidade", "Deseja remover esta habilidade?");
        alertaExcluir.show();
    }
}
