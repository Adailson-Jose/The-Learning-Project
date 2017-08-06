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
import com.thelearningproject.applogin.infraestrutura.utils.UsuarioException;
import com.thelearningproject.applogin.perfil.negocio.PerfilServices;

import java.util.ArrayList;

public class ListarHabilidadesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, DialogInterface.OnClickListener {
    private ControladorSessao sessao;
    private ArrayList<String> lista = new ArrayList<>();
    private PerfilServices perfilNegocio;
    private MateriaServices materiaNegocio;
    private int idposicao;
    private AdapterView adapterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_habilidades);
        setTitle("Minhas Habilidades");
    }

    @Override
    protected void onResume() {
        super.onResume();
        sessao = ControladorSessao.getInstancia(ListarHabilidadesActivity.this);
        perfilNegocio = PerfilServices.getInstancia(this);
        materiaNegocio = MateriaServices.getInstancia(this);
        FloatingActionButton botaoInserir = (FloatingActionButton) findViewById(R.id.btnInserirHabilidade);

        listarHabilidades();

        botaoInserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListarHabilidadesActivity.this, CadastroHabilidadeActivity.class));
/*                AlertDialog ad = Auxiliar.criarDialogInsercao(ListarHabilidadesActivity.this, "Inserir habilidade", "informe sua hab");
                ad.show();*/
            }
        });
    }

    private void listarHabilidades() {
        lista.clear();
        ArrayList<Materia> listaMateria = perfilNegocio.listarHabilidade(sessao.getPerfil());

        ListView listView1 = (ListView) findViewById(R.id.listaHabilidades);

        ArrayAdapter<String> adapterListaHabilidade = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                lista);

        listView1.setAdapter(adapterListaHabilidade);

        for (Materia mat : listaMateria) {
            lista.add(mat.getNome());
            adapterListaHabilidade.notifyDataSetChanged();
        }

        listView1.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        idposicao = position;
        adapterView = parent;
        AlertDialog alertaExcluir = Auxiliar.criarDialogConfirmacao(ListarHabilidadesActivity.this, "Remover habilidade" ,"Deseja remover esta habilidade?");
        alertaExcluir.show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                String mat = adapterView.getItemAtPosition(idposicao).toString();
                try {
                    perfilNegocio.deletarHabilidade(sessao.getPerfil(), materiaNegocio.consultarNome(mat));
                    listarHabilidades();
                    Auxiliar.criarToast(this, "Habilidade removida com sucesso!!!!");
                } catch (UsuarioException e) {
                    Auxiliar.criarToast(this, e.getMessage());
                }
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                dialog.dismiss();
                break;
/*            case DialogInterface.BUTTON_NEUTRAL:
                Auxiliar.criarToast(this, "olaaa");
                break;*/
        }
    }
}
