package com.thelearningproject.applogin.infraestrutura.gui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.thelearningproject.applogin.R;

import layout.MainBuscaFragment;
import layout.MainPerfilFragment;
import layout.MainRecomendacoesFragment;

/**
 * Criado por gabri on 26/07/2017.
 */

public class HomeActivity extends AppCompatActivity {
    private static final String SELECTED_ITEM = "arg_selected_item";
    FragmentManager fm = getSupportFragmentManager();
    private int mSelectedItem;
    private String ultimoFrag;
    private MenuItem selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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
                alterarFragment("2", ft, getPerfilFragment());
                break;
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
    private void monitorarPilha(FragmentManager fm) {
        if(fm.getBackStackEntryCount() >= 3){
            fm.popBackStack();
        }
    }
    private void alterarFragment(String frag, FragmentTransaction ft, Fragment f){
        if (!frag.equals(this.ultimoFrag)){
            ft.replace(R.id.container, f, frag);
            ft.addToBackStack("pilha");
            monitorarPilha(fm);
            ft.commit();
            ultimoFrag = frag;
        }
    }
    private MainPerfilFragment getPerfilFragment(){
        return new MainPerfilFragment();
    }
    private MainBuscaFragment getBuscaFragment(){
        return new MainBuscaFragment();
    }
    private MainRecomendacoesFragment getRecomFragment(){
        return new MainRecomendacoesFragment();
    }
}
