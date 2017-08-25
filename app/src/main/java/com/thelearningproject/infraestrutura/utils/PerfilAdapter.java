package com.thelearningproject.infraestrutura.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thelearningproject.R;
import com.thelearningproject.combinacao.dominio.ICriarCombinacao;
import com.thelearningproject.combinacao.dominio.IExcluirCombinacao;
import com.thelearningproject.estudo.dominio.Materia;
import com.thelearningproject.perfil.dominio.Perfil;
import com.thelearningproject.perfil.gui.MainInteracoesFragment;
import com.thelearningproject.perfil.gui.NotificacoesActivity;

import java.util.ArrayList;

public class PerfilAdapter extends ArrayAdapter<Perfil> {
    private ArrayList<Perfil> listaPerfil;
    private Context contexto;
    private Object tela;
    private ICriarCombinacao interfaceCriarComb;
    private IExcluirCombinacao interfaceExcluirComb;

    public PerfilAdapter(Context context, ArrayList<Perfil> perfils, Object obj, ICriarCombinacao inter, IExcluirCombinacao inter2) {
        super(context, 0, perfils);
        this.listaPerfil = perfils;
        this.contexto = context;
        this.tela = obj;
        this.interfaceCriarComb = inter;
        this.interfaceExcluirComb = inter2;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        View view;
        LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.lista_perfil, parent, false);

        TextView nomePerfil = (TextView) view.findViewById(R.id.tv_perfil);
        TextView habilidades = (TextView) view.findViewById(R.id.tv_habilidades);
        TextView necessidades = (TextView) view.findViewById(R.id.tv_necessidades);

        ImageButton btnNovaInteracao = (ImageButton) view.findViewById(R.id.criarInteracao);
        ImageButton btnDesfazerInteracao = (ImageButton) view.findViewById(R.id.desfazerInteracao);
        ImageButton btnAceitarMatch = (ImageButton) view.findViewById(R.id.aceitarMatchBtn);
        ImageButton btnRecusarMatch = (ImageButton) view.findViewById(R.id.recusarMatchBtn);

        if (this.tela instanceof NotificacoesActivity) {
            btnNovaInteracao.setVisibility(View.GONE);
            btnDesfazerInteracao.setVisibility(View.GONE);
            LinearLayout ly = (LinearLayout) view.findViewById(R.id.botoesAcao);
            ly.setVisibility(View.VISIBLE);

        } else if (this.tela instanceof MainInteracoesFragment) {
            btnNovaInteracao.setVisibility(View.GONE);
            btnDesfazerInteracao.setVisibility(View.VISIBLE);
        } else {
            btnDesfazerInteracao.setVisibility(View.GONE);
            btnNovaInteracao.setVisibility(View.VISIBLE);
        }

        Perfil perfil = listaPerfil.get(position);
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        ArrayList<Materia> listahabilidades = perfil.getHabilidades();
        ArrayList<Materia> listanecessidades = perfil.getNecessidades();
        String prefix = "";
        for (Materia m : listahabilidades) {
            sb1.append(prefix);
            prefix = ", ";
            sb1.append(m.getNome());
        }
        prefix = "";
        for (Materia m : listanecessidades) {
            sb2.append(prefix);
            prefix = ", ";
            sb2.append(m.getNome());
        }
        nomePerfil.setText(perfil.getPessoa().getNome());
        habilidades.setText(sb1.toString());
        necessidades.setText(sb2.toString());

        btnNovaInteracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceCriarComb.criarCombinacao(listaPerfil.get(position));
            }
        });

        btnDesfazerInteracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceExcluirComb.excluirCombinacao(listaPerfil.get(position).getId());
            }
        });

        btnAceitarMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auxiliar.criarToast(getContext(), "Vou aceitar");
            }
        });

        btnRecusarMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auxiliar.criarToast(getContext(), "Vou recusar");
            }
        });

        return view;
    }
}

