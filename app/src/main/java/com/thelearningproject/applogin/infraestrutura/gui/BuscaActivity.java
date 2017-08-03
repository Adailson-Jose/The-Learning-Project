package com.thelearningproject.applogin.infraestrutura.gui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.thelearningproject.applogin.R;
import com.thelearningproject.applogin.estudo.dominio.Materia;
import com.thelearningproject.applogin.estudo.negocio.MateriaServices;
import com.thelearningproject.applogin.registrobusca.negocio.DadosServices;
import com.thelearningproject.applogin.infraestrutura.utils.Auxiliar;
import com.thelearningproject.applogin.infraestrutura.utils.ControladorSessao;
import com.thelearningproject.applogin.infraestrutura.utils.PerfilAdapter;
import com.thelearningproject.applogin.perfil.dominio.Perfil;
import com.thelearningproject.applogin.perfil.negocio.PerfilServices;

import java.util.ArrayList;

public class BuscaActivity extends AppCompatActivity {

    private ListView listaUsuarios;
    private EditText entradaBusca;
    private Button botaoBusca;
    private TextView informacaoResultado;
    private ControladorSessao sessao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca);

        sessao = ControladorSessao.getInstancia(this.getApplicationContext());
        listaUsuarios = (ListView) findViewById(R.id.listViewID);
        entradaBusca = (EditText) findViewById(R.id.editTextBuscaID);
        botaoBusca = (Button) findViewById(R.id.botaoBuscaID);
        informacaoResultado = (TextView) findViewById(R.id.tv_resultadoID);

        botaoBusca.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                listar();
                Auxiliar.esconderTeclado(BuscaActivity.this);
            }

        });
    }

    private void listar(){
        informacaoResultado.setText("");
        String nome = entradaBusca.getText().toString();

        MateriaServices materiaServices = MateriaServices.getInstancia(this.getApplicationContext());
        PerfilServices perfilServices = PerfilServices.getInstancia(this.getApplicationContext());
        DadosServices dadosServices = DadosServices.getInstancia(this.getApplicationContext());

        dadosServices.cadastraBusca(sessao.getPerfil(),nome);
        Materia materia = materiaServices.consultarNome(nome);
        ArrayList<Perfil> listaPerfil = new ArrayList<>();
        if (materia!= null) {
            listaPerfil = perfilServices.listarPerfil(materia);
        }
        if (listaPerfil.isEmpty()) {
            listaPerfil = dadosServices.recomendaMateria(sessao.getPerfil(),nome);
            if(!listaPerfil.isEmpty()){
                informacaoResultado.setText("Sem resultados para " +nome+ "\nMas estes usuarios podem lhe ajudar com algo relacionado:");
                informacaoResultado.getHeight();
            }
        }

        //remove o perfil do usuario ativo da lista de busca
        for (Perfil p : listaPerfil){
            if(p.getId() == sessao.getPerfil().getId()){
                listaPerfil.remove(p);
                break;
            }
        }

        ArrayAdapter adaptador = new PerfilAdapter(getApplicationContext(), listaPerfil);

        if(listaPerfil.isEmpty()) {
            Auxiliar.criarToast(this, "Sem Resultados");
        }
        listaUsuarios.setAdapter(adaptador);
    }


}
