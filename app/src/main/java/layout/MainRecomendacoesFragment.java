package layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.thelearningproject.applogin.R;
import com.thelearningproject.applogin.estudo.dominio.Materia;
import com.thelearningproject.applogin.infraestrutura.utils.ControladorSessao;
import com.thelearningproject.applogin.infraestrutura.utils.PerfilAdapter;
import com.thelearningproject.applogin.perfil.dominio.Perfil;
import com.thelearningproject.applogin.perfil.negocio.PerfilServices;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Criado por Gabriel on 03/08/2017
 */
public class MainRecomendacoesFragment extends Fragment {
    private FragmentActivity activity;
    private ListView listaRecomendados;
    private ControladorSessao sessao;

    public MainRecomendacoesFragment() {
        // Requer um construtor publico vazio
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_recomendacoes, container, false);
        activity = getActivity();
        sessao = ControladorSessao.getInstancia(activity);
        listaRecomendados = (ListView) view.findViewById(R.id.listViewRecomendaID);
        listar();
        return view;
    }

    private void listar() {
        PerfilServices perfilServices = PerfilServices.getInstancia(activity);
        Set<Perfil> listaPerfil = new LinkedHashSet<>();
        for (Materia materia:sessao.getPerfil().getNecessidades()){
            listaPerfil.addAll(perfilServices.listarPerfil(materia));
        }
        for (Materia materia : perfilServices.recomendaMateria(sessao.getPerfil())) {
            listaPerfil.addAll(perfilServices.listarPerfil(materia));
        }
        if (listaPerfil.contains(sessao.getPerfil())) {
            listaPerfil.remove(sessao.getPerfil());
        }


        ArrayAdapter adaptador = new PerfilAdapter(activity, new ArrayList<>(listaPerfil));


        adaptador.notifyDataSetChanged();
        listaRecomendados.setAdapter(adaptador);
    }

}
