package com.thelearningproject.perfil.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.thelearningproject.R;
import com.thelearningproject.combinacao.dominio.Combinacao;
import com.thelearningproject.combinacao.dominio.IExcluirCombinacao;
import com.thelearningproject.combinacao.negocio.CombinacaoServices;
import com.thelearningproject.combinacao.negocio.StatusCombinacao;
import com.thelearningproject.infraestrutura.utils.Auxiliar;
import com.thelearningproject.infraestrutura.utils.ControladorSessao;
import com.thelearningproject.infraestrutura.utils.PerfilAdapter;
import com.thelearningproject.perfil.dominio.Perfil;
import com.thelearningproject.perfil.negocio.PerfilServices;

import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * The type Main interacoes fragment.
 */
public class MainInteracoesFragment extends Fragment implements AdapterView.OnItemClickListener, IExcluirCombinacao {
    private ListView listaInteracoes;
    private ControladorSessao sessao;
    private CombinacaoServices combinacaoServices;
    private PerfilServices perfilServices;
    private TextView tvMensagem;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_interacoes, null);

        sessao = ControladorSessao.getInstancia(getActivity());
        combinacaoServices = CombinacaoServices.getInstancia(getActivity());
        perfilServices = PerfilServices.getInstancia(getActivity());
        listaInteracoes = (ListView) view.findViewById(R.id.listViewInteracoesID);
        listaInteracoes.setOnItemClickListener(this);
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
        ArrayList<Perfil> perfils = new ArrayList<>();
        for (Combinacao c : sessao.getPerfil().getCombinacoes()) {
            if (c.getStatus() == StatusCombinacao.ATIVADO.getValor()){
                perfils.add(perfilServices.consultar(c.getPerfil2()));
            }
        }
        if (!perfils.isEmpty()) {
            tvMensagem.setVisibility(GONE);
        } else {
            tvMensagem.setVisibility(VISIBLE);
        }
        ArrayAdapter adaptador = new PerfilAdapter(getActivity(), new ArrayList<>(perfils), MainInteracoesFragment.this, null, this, null, null);

        adaptador.notifyDataSetChanged();
        listaInteracoes.setAdapter(adaptador);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Perfil p = (Perfil) parent.getAdapter().getItem(position);
        sessao.setPerfilSelecionado(p);
        Intent intent = new Intent(getActivity(), PerfilActivity.class);
        startActivity(intent);
    }

    @Override
    public void excluirCombinacao(int i) {
        for (Combinacao c : sessao.getPerfil().getCombinacoes()) {
            if (c.getPerfil2() == i) {
                combinacaoServices.removerCombinacao(sessao.getPerfil(), c);
                sessao.getPerfil().getCombinacoes().remove(c);
                break;
            }
        }
        Perfil perfil2 = perfilServices.consultar(i);
        for (Combinacao c : perfil2.getCombinacoes()) {
            if (c.getPerfil2() == sessao.getPerfil().getId()) {
                combinacaoServices.removerCombinacao(perfil2, c);
                sessao.getPerfil().getCombinacoes().remove(c);
                break;
            }
        }
        listar();
        Auxiliar.criarToast(getContext(), "Você recusou a solicitação");
    }
}
