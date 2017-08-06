package layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.thelearningproject.applogin.R;
import com.thelearningproject.applogin.estudo.dominio.Materia;
import com.thelearningproject.applogin.estudo.negocio.MateriaServices;
import com.thelearningproject.applogin.infraestrutura.utils.Auxiliar;
import com.thelearningproject.applogin.infraestrutura.utils.ControladorSessao;
import com.thelearningproject.applogin.infraestrutura.utils.PerfilAdapter;
import com.thelearningproject.applogin.perfil.dominio.Perfil;
import com.thelearningproject.applogin.perfil.negocio.PerfilServices;
import com.thelearningproject.applogin.registrobusca.negocio.DadosServices;

import java.util.ArrayList;

public class MainBuscaFragment extends Fragment {
    private FragmentActivity activity;
    private ListView listaUsuarios;
    private EditText entradaBusca;
    private Button botaoBusca;
    private TextView informacaoResultado;
    private ControladorSessao sessao;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_busca, container, false);

        activity = getActivity();

        sessao = ControladorSessao.getInstancia(activity);
        listaUsuarios = (ListView) view.findViewById(R.id.listViewID);
        entradaBusca = (EditText) view.findViewById(R.id.editTextBuscaID);
        botaoBusca = (Button) view.findViewById(R.id.botaoBuscaID);
        informacaoResultado = (TextView) view.findViewById(R.id.tv_resultadoID);

        botaoBusca.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                listar();
                Auxiliar.esconderTecladoFragment(activity,v);
            }

        });

        return view;
    }

    private void listar() {
        informacaoResultado.setText("");
        String nome = entradaBusca.getText().toString();

        MateriaServices materiaServices = MateriaServices.getInstancia(activity);
        PerfilServices perfilServices = PerfilServices.getInstancia(activity);
        DadosServices dadosServices = DadosServices.getInstancia(activity);

        dadosServices.cadastraBusca(sessao.getPerfil(), nome);
        Materia materia = materiaServices.consultarNome(nome);
        ArrayList<Perfil> listaPerfil = new ArrayList<>();
        if (materia != null) {
            listaPerfil = perfilServices.listarPerfil(materia);
        }
        if (listaPerfil.isEmpty()) {
            listaPerfil = dadosServices.recomendaMateria(sessao.getPerfil(), nome);
            if (!listaPerfil.isEmpty()) {
                informacaoResultado.setText("Sem resultados para " + nome + "\nMas estes usuarios podem lhe ajudar com algo relacionado:");
                informacaoResultado.getHeight();
            }
        }

        //remove o perfil do usuario ativo da lista de busca
        if (listaPerfil.contains(sessao.getPerfil())) {
            listaPerfil.remove(sessao.getPerfil());
        }

        ArrayAdapter adaptador = new PerfilAdapter(activity, listaPerfil);

        if (listaPerfil.isEmpty()) {
            Auxiliar.criarToast(activity, "Sem Resultados");
        }
        adaptador.notifyDataSetChanged();
        listaUsuarios.setAdapter(adaptador);
    }

}
