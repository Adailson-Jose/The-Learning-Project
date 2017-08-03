package com.thelearningproject.applogin.infraestrutura.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.thelearningproject.applogin.R;
import com.thelearningproject.applogin.estudo.dominio.Materia;
import com.thelearningproject.applogin.perfil.dominio.Perfil;

import java.util.ArrayList;


public class PerfilAdapter extends ArrayAdapter<Perfil> {

    private ArrayList<Perfil> listaPerfil;
    private Context contexto;

    public PerfilAdapter(Context context, ArrayList<Perfil> perfils) {
        super(context, 0, perfils);
        this.listaPerfil = perfils;
        this.contexto = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.lista_perfil, parent, false);

        TextView nomePerfil = (TextView) view.findViewById(R.id.tv_perfil);
        TextView habilidades = (TextView) view.findViewById(R.id.tv_habilidades);
        TextView necessidades = (TextView) view.findViewById(R.id.tv_necessidades);

        Perfil perfil = listaPerfil.get(position);
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        ArrayList<Materia> listahabilidades = perfil.getHabilidades();
        ArrayList<Materia> listanecessidades = perfil.getNecessidades();
        String prefix = "";
        sb1.append("Habilidades: ");
        for (Materia m : listahabilidades) {
            sb1.append(prefix);
            prefix = ", ";
            sb1.append(m.getNome());
        }
        prefix = "";
        sb2.append("Necessidades: ");
        for (Materia m : listanecessidades) {
            sb2.append(prefix);
            prefix = ", ";
            sb2.append(m.getNome());
        }

        nomePerfil.setText(perfil.getPessoa().getNome());
        habilidades.setText(sb1.toString());
        necessidades.setText(sb2.toString());

        return view;
    }

}

