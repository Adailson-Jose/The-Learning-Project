package com.thelearningproject.applogin.perfil.gui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.thelearningproject.applogin.R;
import com.thelearningproject.applogin.estudo.dominio.Materia;
import com.thelearningproject.applogin.estudo.negocio.MateriaServices;
import com.thelearningproject.applogin.infraestrutura.utils.Auxiliar;
import com.thelearningproject.applogin.infraestrutura.utils.ControladorSessao;
import com.thelearningproject.applogin.infraestrutura.utils.UsuarioException;
import com.thelearningproject.applogin.perfil.negocio.PerfilServices;

import java.util.ArrayList;

public class ListarNecessidadesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, DialogInterface.OnClickListener {
    private ControladorSessao sessao;
    private ArrayList<String> lista = new ArrayList<>();
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
        Button botaoInserir = (Button) findViewById(R.id.btnInserirNecessidade);

        listarNecessidades();

        botaoInserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListarNecessidadesActivity.this, CadastroNecessidadeActivity.class));
            }
        });
    }

    private void listarNecessidades() {
        lista.clear();
        ArrayList<Materia> listaMateria = perfilNegocio.listarNecessidade(sessao.getPerfil());

        ListView listView1 = (ListView) findViewById(R.id.listaNecessidades);

        ArrayAdapter<String> adapterListaNecessidade = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                lista);

        listView1.setAdapter(adapterListaNecessidade);

        for (Materia mat : listaMateria) {
            lista.add(mat.getNome());
            adapterListaNecessidade.notifyDataSetChanged();
        }

        listView1.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        idposicao = position;
        adapterView = parent;
        alertaExcluir = Auxiliar.criarDialogConfirmacao(ListarNecessidadesActivity.this, "Deseja remover esta necessidade?");
        alertaExcluir.show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                String mat = adapterView.getItemAtPosition(idposicao).toString();
                try {
                    perfilNegocio.deletarNecessidade(sessao.getPerfil(), materiaNegocio.consultarNome(mat));
                    listarNecessidades();
                    Auxiliar.criarToast(this, "Necessidade removida com sucesso!!!!!!");
                } catch (UsuarioException e) {
                    Auxiliar.criarToast(this, e.getMessage());
                }
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                alertaExcluir.dismiss();
                break;
        }
    }
}
