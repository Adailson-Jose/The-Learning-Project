package com.thelearningproject.applogin.infraestrutura.gui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.thelearningproject.applogin.R;
import com.thelearningproject.applogin.infraestrutura.utils.Auxiliar;
import com.thelearningproject.applogin.infraestrutura.utils.ControladorSessao;
import com.thelearningproject.applogin.perfil.dominio.Perfil;
import com.thelearningproject.applogin.perfil.negocio.PerfilServices;
import com.thelearningproject.applogin.pessoa.dominio.Pessoa;
import com.thelearningproject.applogin.pessoa.negocio.PessoaServices;
import com.thelearningproject.applogin.usuario.dominio.Usuario;
import com.thelearningproject.applogin.usuario.negocio.UsuarioServices;

import layout.MainBuscaFragment;
import layout.MainInteracoesFragment;
import layout.MainPerfilFragment;
import layout.MainRecomendacoesFragment;

/**
 * Criado por gabri on 26/07/2017.
 */

public class MainActivity extends AppCompatActivity {
    private ControladorSessao sessao;
    private static final String SELECTED_ITEM = "arg_selected_item";
    private FragmentManager fm = getSupportFragmentManager();
    private int mSelectedItem;
    private String ultimoFrag;
    private static final int TRES = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessao = ControladorSessao.getInstancia(this.getApplicationContext());

        if (sessao.verificaConexao()) {
            resumir();
        }

        if (sessao.verificaLogin()) {
            finish();
        } else {
            exibir();
        }

        BottomNavigationView mBottomNav = (BottomNavigationView) findViewById(R.id.navigation);

        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectFragment(item);
                return true;
            }
        });

        MenuItem selectedItem;
        if (savedInstanceState != null) {
            mSelectedItem = savedInstanceState.getInt(SELECTED_ITEM, 0);
            selectedItem = mBottomNav.getMenu().findItem(mSelectedItem);

        } else {
            MainRecomendacoesFragment frag1 = new MainRecomendacoesFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.container, frag1);
            ft.commit();
            selectedItem = mBottomNav.getMenu().getItem(0);
        }
        selectFragment(selectedItem);
        fm.popBackStack();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_ITEM, mSelectedItem);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pesquisar_menu, menu);
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

    private void resumir() {
        PessoaServices negocioPessoa = PessoaServices.getInstancia(getApplicationContext());
        UsuarioServices negocioUsuario = UsuarioServices.getInstancia(getApplicationContext());

        Pessoa pessoa = negocioPessoa.retornaPessoa(sessao.retornaIdUsuario());
        Usuario usuario = negocioUsuario.consulta(sessao.retornaIdUsuario());

        pessoa.setUsuario(usuario);
        sessao.iniciaSessao();
        sessao.setPessoa(pessoa);

    }

    private void exibir() {
        PerfilServices negocioperfil = PerfilServices.getInstancia(getBaseContext());

        Perfil perfil = negocioperfil.retornaPerfil(sessao.getPessoa().getId());
        perfil.setPessoa(sessao.getPessoa());
        sessao.setPerfil(perfil);

    }

    private void selectFragment(MenuItem item) {
        FragmentTransaction ft = fm.beginTransaction();

        switch (item.getItemId()) {
            case R.id.menu_recomendacoes:
                alterarFragment("0", ft, getRecomFragment());
                break;
            case R.id.menu_buscar:
                alterarFragment("1", ft, getBuscaFragment());
                break;
            case R.id.menu_perfil:
                Toast.makeText(this, sessao.getPerfil().getDescricao(), Toast.LENGTH_SHORT).show();
                alterarFragment("2", ft, getPerfilFragment());
                break;
            case R.id.menu_interacoes:
                alterarFragment("3", ft, getInteracoesFragment());
        }

        mSelectedItem = item.getItemId();
        updateToolbarText(item.getTitle());
    }

    private void updateToolbarText(CharSequence text) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(text);
        }
    }

    private void monitorarPilha(FragmentManager fragmento) {
        if (fragmento.getBackStackEntryCount() >= TRES) {
            fragmento.popBackStack();
        }
    }

    private void alterarFragment(String frag, FragmentTransaction ft, Fragment f) {
        if (!frag.equals(this.ultimoFrag)) {
            ft.replace(R.id.container, f, frag);
            ft.addToBackStack("pilha");
            monitorarPilha(fm);
            ft.commit();
            ultimoFrag = frag;
        }
    }

    private MainPerfilFragment getPerfilFragment() {
        return new MainPerfilFragment();
    }

    private MainBuscaFragment getBuscaFragment() {
        return new MainBuscaFragment();
    }

    private MainRecomendacoesFragment getRecomFragment() {
        return new MainRecomendacoesFragment();
    }

    private MainInteracoesFragment getInteracoesFragment() { return new MainInteracoesFragment(); }
}
