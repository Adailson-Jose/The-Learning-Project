package com.thelearningproject.applogin.infraestrutura.gui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.thelearningproject.applogin.R;
import com.thelearningproject.applogin.infraestrutura.utils.ControladorSessao;

import layout.MainBuscaFragment;
import layout.MainPerfilFragment;
import layout.MainRecomendacoesFragment;

/**
 * Criado por gabri on 26/07/2017.
 */

public class HomeActivity extends AppCompatActivity {
    private static final String SELECTED_ITEM = "arg_selected_item";
    FragmentManager fm = getSupportFragmentManager();
    private BottomNavigationView mBottomNav;
    private int mSelectedItem;
    private ControladorSessao session;

    public ControladorSessao getSessao() {
        return this.session;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        session = ControladorSessao.getInstancia(this.getApplicationContext());


        mBottomNav = (BottomNavigationView) findViewById(R.id.navigation);

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
            MainPerfilFragment frag1 = new MainPerfilFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.container, frag1);
            ft.commit();
            selectedItem = mBottomNav.getMenu().getItem(0);
        }
        selectFragment(selectedItem);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_ITEM, mSelectedItem);
        super.onSaveInstanceState(outState);
    }

    private void selectFragment(MenuItem item) {
        FragmentTransaction ft = fm.beginTransaction();

        switch (item.getItemId()) {
            case R.id.menu_recomendacoes:
                MainRecomendacoesFragment frag1 = new MainRecomendacoesFragment();
                ft.replace(R.id.container, frag1, "frag1");
                ft.addToBackStack("pilha");
                ft.commit();
                break;
            case R.id.menu_buscar:
                MainBuscaFragment frag2 = new MainBuscaFragment();
                ft.replace(R.id.container, frag2, "frag2");
                ft.addToBackStack("pilha");
                ft.commit();
                break;
            case R.id.menu_perfil:
                MainPerfilFragment frag3 = new MainPerfilFragment();
                ft.replace(R.id.container, frag3, "frag3");
                ft.addToBackStack("pilha");
                ft.commit();
                break;
        }

        // update selected item
        mSelectedItem = item.getItemId();

        updateToolbarText(item.getTitle());
    }

    private void updateToolbarText(CharSequence text) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(text);
        }
    }
}
