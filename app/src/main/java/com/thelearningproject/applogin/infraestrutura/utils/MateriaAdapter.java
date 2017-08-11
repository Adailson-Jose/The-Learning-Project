package com.thelearningproject.applogin.infraestrutura.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.thelearningproject.applogin.R;
import com.thelearningproject.applogin.estudo.dominio.Materia;
import com.thelearningproject.applogin.estudo.negocio.MateriaServices;
import com.thelearningproject.applogin.perfil.dominio.IMateria;
import com.thelearningproject.applogin.perfil.gui.ListarHabilidadesActivity;
import com.thelearningproject.applogin.perfil.negocio.PerfilServices;

import java.util.ArrayList;

/**
 * Created by Pichau on 09/08/2017.
 */

public class MateriaAdapter extends ArrayAdapter<Materia> {
    private ArrayList<Materia> listaMateria;
    private Context contexto;
    private Materia materiaAtual;
    private IMateria interfaceMateria;

    public MateriaAdapter(Context context, ArrayList<Materia> materias, IMateria inter) {
        super(context, 0, materias);
        this.listaMateria = materias;
        this.contexto = context;
        this.interfaceMateria = inter;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;

        LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.lista_materia, parent, false);

        TextView nomeMateria = (TextView) view.findViewById(R.id.tv_nome_materia);
        ImageButton botaoExcluir = (ImageButton) view.findViewById(R.id.btnExcluirMateria);
        Materia materia = listaMateria.get(position);

        nomeMateria.setText(materia.getNome());
        botaoExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceMateria.excluirMateria(listaMateria.get(position));
            }
        });

        return view;
    }
}
