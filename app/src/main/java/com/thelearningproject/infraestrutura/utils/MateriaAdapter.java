package com.thelearningproject.infraestrutura.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.thelearningproject.R;
import com.thelearningproject.estudo.dominio.Materia;
import com.thelearningproject.perfil.dominio.IExcluiMateria;

import java.util.ArrayList;

/**
 * The type Materia adapter.
 */
public class MateriaAdapter extends ArrayAdapter<Materia> {
    private ArrayList<Materia> listaMateria;
    private Context contexto;
    private IExcluiMateria interfaceMateria;

    /**
     * Instantiates a new Materia adapter.
     *
     * @param context  the context
     * @param materias the materias
     * @param inter    the inter
     */
    public MateriaAdapter(Context context, ArrayList<Materia> materias, IExcluiMateria inter) {
        super(context, 0, materias);
        this.listaMateria = materias;
        this.contexto = context;
        this.interfaceMateria = inter;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
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
