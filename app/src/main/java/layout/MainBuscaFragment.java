package layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
    private AutoCompleteTextView entradaBusca;
    private TextView informacaoResultado;
    private ControladorSessao sessao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_busca, container, false);
        activity = getActivity();

        sessao = ControladorSessao.getInstancia(activity);
        listaUsuarios = (ListView) view.findViewById(R.id.listViewID);
        entradaBusca = (AutoCompleteTextView) view.findViewById(R.id.autoBuscaID);
        informacaoResultado = (TextView) view.findViewById(R.id.tv_resultadoID);

        entradaBusca.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Metodo obrigatorio do TextWatcher
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sugerir();
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Metodo obrigatorio do TextWatcher
            }
        });

        entradaBusca.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                listar();
                return false;
            }
        });

        return view;
    }

    private void sugerir(){
        String nome = entradaBusca.getText().toString();
        MateriaServices materiaServices = MateriaServices.getInstancia(activity);
        ArrayList<String> listaMateria = new ArrayList<>();
        listaMateria.addAll(materiaServices.retornaLista(nome));

        ArrayAdapter adapter = new ArrayAdapter(this.getContext(), android.R.layout.select_dialog_item,materiaServices.retornaLista(nome));

        entradaBusca.setThreshold(1);
        entradaBusca.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listar();
            }
        });
        entradaBusca.setAdapter(adapter);
    }

    private void listar() {
        Auxiliar.esconderTecladoFragment(this.getContext(),this.getView());
        entradaBusca.dismissDropDown();
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

        ArrayAdapter adaptador = new PerfilAdapter(activity, listaPerfil, null,null,null);

        if (listaPerfil.isEmpty()) {
            Auxiliar.criarToast(activity, "Sem Resultados");
            adaptador.clear();
        }
        adaptador.notifyDataSetChanged();
        listaUsuarios.setAdapter(adaptador);
    }

}
