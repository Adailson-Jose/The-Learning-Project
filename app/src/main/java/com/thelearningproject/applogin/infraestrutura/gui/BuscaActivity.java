package com.thelearningproject.applogin.infraestrutura.gui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.thelearningproject.applogin.R;
import com.thelearningproject.applogin.combinacao.dominio.Combinacao;
import com.thelearningproject.applogin.combinacao.dominio.ICriarCombinacao;
import com.thelearningproject.applogin.combinacao.negocio.CombinacaoServices;
import com.thelearningproject.applogin.estudo.dominio.Materia;
import com.thelearningproject.applogin.estudo.negocio.MateriaServices;
import com.thelearningproject.applogin.infraestrutura.utils.Auxiliar;
import com.thelearningproject.applogin.infraestrutura.utils.ControladorSessao;
import com.thelearningproject.applogin.infraestrutura.utils.PerfilAdapter;
import com.thelearningproject.applogin.infraestrutura.utils.RecentProvider;
import com.thelearningproject.applogin.perfil.dominio.Perfil;
import com.thelearningproject.applogin.perfil.gui.PerfilActivity;
import com.thelearningproject.applogin.perfil.negocio.PerfilServices;
import com.thelearningproject.applogin.registrobusca.negocio.DadosServices;

import java.util.ArrayList;

public class BuscaActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,ICriarCombinacao {
    private ListView listaUsuarios;
    private TextView informacaoResultado;
    private ControladorSessao sessao;
    private CombinacaoServices combinacaoServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca);
        sessao = ControladorSessao.getInstancia(this);
        combinacaoServices = CombinacaoServices.getInstancia(this);
        listaUsuarios = (ListView) findViewById(R.id.listViewID);
        informacaoResultado = (TextView) findViewById(R.id.tv_resultadoID);

        handleSearch(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pesquisar_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView;
        MenuItem item = menu.findItem(R.id.pesquisarBtn);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            searchView = (SearchView) item.getActionView();
        } else {
            searchView = (SearchView) MenuItemCompat.getActionView(item);
        }

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.pesquisarBtn) {
            return false;//aqui nicollas
        }
        Auxiliar.esconderTeclado(this);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleSearch(intent);
    }

    public void handleSearch(Intent intent) {
        if (Intent.ACTION_SEARCH.equalsIgnoreCase(intent.getAction())) {
            String s = intent.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions searchRecentSuggestions = new SearchRecentSuggestions(this, RecentProvider.AUTHORITY, RecentProvider.MODE);
            searchRecentSuggestions.saveRecentQuery(s, null);
            listar(s);
        }
    }

    private void listar(String entrada) {
        Auxiliar.esconderTeclado(this);
        informacaoResultado.setText("");

        MateriaServices materiaServices = MateriaServices.getInstancia(this);
        PerfilServices perfilServices = PerfilServices.getInstancia(this);
        DadosServices dadosServices = DadosServices.getInstancia(this);

        dadosServices.cadastraBusca(sessao.getPerfil(), entrada);
        Materia materia = materiaServices.consultarNome(entrada);
        ArrayList<Perfil> listaPerfil = new ArrayList<>();
        if (materia != null) {
            listaPerfil = perfilServices.listarPerfil(materia);
        }
        if (listaPerfil.isEmpty()) {
            listaPerfil = dadosServices.recomendaMateria(sessao.getPerfil(), entrada);
            if (!listaPerfil.isEmpty()) {
                informacaoResultado.setText("Sem resultados para " + entrada + "\nMas estes usuarios podem lhe ajudar com algo relacionado:");
                informacaoResultado.getHeight();
            }
        }
        for (Combinacao c: sessao.getPerfil().getCombinacoes()){
            if (c.getPerfil1() == sessao.getPerfil().getId()) {
                listaPerfil.remove(perfilServices.consulta(c.getPerfil2()));
            } else {
                listaPerfil.remove(perfilServices.consulta(c.getPerfil1()));
            }
        }

        //remove o perfil do usuario ativo da lista de busca
        if (listaPerfil.contains(sessao.getPerfil())) {
            listaPerfil.remove(sessao.getPerfil());
        }

        ArrayAdapter adaptador = new PerfilAdapter(this, listaPerfil, null, this, null);

        if (listaPerfil.isEmpty()) {
            Auxiliar.criarToast(this, "Sem Resultados");
            adaptador.clear();
        }
        adaptador.notifyDataSetChanged();
        listaUsuarios.setAdapter(adaptador);
        listaUsuarios.setOnItemClickListener(this);
    }
    public void criarCombinacao(Perfil pEstrangeiro) {
        combinacaoServices.inserirCombinacao(sessao.getPerfil(), pEstrangeiro);
        handleSearch(getIntent());
        Auxiliar.criarToast(BuscaActivity.this,"Você fez um match");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Perfil p = (Perfil) parent.getAdapter().getItem(position);
        sessao.setPerfilSelecionado(p);
        Intent intent = new Intent(BuscaActivity.this, PerfilActivity.class);
        startActivity(intent);
    }


}
