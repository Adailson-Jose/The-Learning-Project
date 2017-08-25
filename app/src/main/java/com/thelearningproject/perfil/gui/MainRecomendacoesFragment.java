package com.thelearningproject.perfil.gui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.thelearningproject.R;
import com.thelearningproject.combinacao.dominio.Combinacao;
import com.thelearningproject.combinacao.dominio.ICriarCombinacao;
import com.thelearningproject.combinacao.negocio.CombinacaoServices;
import com.thelearningproject.estudo.dominio.Materia;
import com.thelearningproject.infraestrutura.utils.Auxiliar;
import com.thelearningproject.infraestrutura.utils.ControladorSessao;
import com.thelearningproject.infraestrutura.utils.PerfilAdapter;
import com.thelearningproject.perfil.dominio.Perfil;
import com.thelearningproject.perfil.negocio.PerfilServices;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Criado por Gabriel on 03/08/2017
 */
public class MainRecomendacoesFragment extends Fragment implements AdapterView.OnItemClickListener, ICriarCombinacao {
    private FragmentActivity activity;
    private ListView listaRecomendados;
    private ControladorSessao sessao;
    private CombinacaoServices combinacaoServices;
    private TextView tvMensagem;

    public MainRecomendacoesFragment() {
        // Requer um construtor publico vazio
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_recomendacoes, container, false);
        activity = getActivity();
        sessao = ControladorSessao.getInstancia(activity);
        combinacaoServices = CombinacaoServices.getInstancia(getContext());
        listaRecomendados = (ListView) view.findViewById(R.id.listViewRecomendaID);
        listaRecomendados.setOnItemClickListener(this);

        tvMensagem = (TextView) view.findViewById(R.id.tv1);
        listar();

        return view;
    }

    @Override
    public void onResume() {
        listar();
        super.onResume();
    }

    private void listar() {
        PerfilServices perfilServices = PerfilServices.getInstancia(activity);
        Set<Perfil> listaPerfil = new LinkedHashSet<>();
        for (Materia materia : sessao.getPerfil().getNecessidades()) {
            listaPerfil.addAll(perfilServices.listarPerfil(materia));
        }
        for (Materia materia : sessao.getPerfil().getNecessidades()) {
            for (Materia materia2 : perfilServices.recomendador(materia)) {
                listaPerfil.addAll(perfilServices.listarPerfil(materia2));
            }
        }
        if (listaPerfil.contains(sessao.getPerfil())) {
            listaPerfil.remove(sessao.getPerfil());
        }
        for (Combinacao c : sessao.getPerfil().getCombinacoes()) {
            listaPerfil.remove(perfilServices.consultar(c.getPerfil2()));

        }

        if (!listaPerfil.isEmpty()) {
            tvMensagem.setVisibility(GONE);
        } else {
            tvMensagem.setVisibility(VISIBLE);
        }

        ArrayAdapter adaptador = new PerfilAdapter(activity, new ArrayList<>(listaPerfil), MainRecomendacoesFragment.this, this, null, null, null);


        adaptador.notifyDataSetChanged();
        listaRecomendados.setAdapter(adaptador);
    }

    public void criarCombinacao(Perfil pEstrangeiro) {
        combinacaoServices.requererCombinacao(sessao.getPerfil(), pEstrangeiro);
        Auxiliar.criarToast(getContext(), sessao.getPerfil().getCombinacoes().toString());
        listar();
        Auxiliar.criarToast(getContext(), "Você fez um match");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Perfil p = (Perfil) parent.getAdapter().getItem(position);
        sessao.setPerfilSelecionado(p);
        Intent intent = new Intent(getActivity(), PerfilActivity.class);
        startActivity(intent);
    }
}
