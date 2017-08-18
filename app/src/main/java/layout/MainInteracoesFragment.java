package layout;

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

import com.thelearningproject.applogin.R;
import com.thelearningproject.applogin.combinacao.dominio.Combinacao;
import com.thelearningproject.applogin.combinacao.dominio.IExcluirCombinacao;
import com.thelearningproject.applogin.combinacao.negocio.CombinacaoServices;
import com.thelearningproject.applogin.infraestrutura.utils.Auxiliar;
import com.thelearningproject.applogin.infraestrutura.utils.ControladorSessao;
import com.thelearningproject.applogin.infraestrutura.utils.PerfilAdapter;
import com.thelearningproject.applogin.perfil.dominio.Perfil;
import com.thelearningproject.applogin.perfil.gui.PerfilActivity;
import com.thelearningproject.applogin.perfil.negocio.PerfilServices;

import java.util.ArrayList;

public class MainInteracoesFragment extends Fragment implements AdapterView.OnItemClickListener, IExcluirCombinacao {
    private ListView listaInteracoes;
    private ControladorSessao sessao;
    private CombinacaoServices combinacaoServices;
    private PerfilServices perfilServices;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_interacoes, null);

        sessao = ControladorSessao.getInstancia(getActivity());
        combinacaoServices = CombinacaoServices.getInstancia(getActivity());
        perfilServices = PerfilServices.getInstancia(getActivity());
        listaInteracoes = (ListView) view.findViewById(R.id.listViewInteracoesID);
        listaInteracoes.setOnItemClickListener(this);
        listar();

        return view;
    }

    private void listar() {
        ArrayList<Perfil> perfils = new ArrayList<>();
        for (Combinacao c : sessao.getPerfil().getCombinacoes()) {
            if (c.getPerfil1() == sessao.getPerfil().getId()) {
                perfils.add(perfilServices.consulta(c.getPerfil2()));
            } else {
                perfils.add(perfilServices.consulta(c.getPerfil1()));
            }
        }


        ArrayAdapter adaptador = new PerfilAdapter(getActivity(), new ArrayList<>(perfils), MainInteracoesFragment.this, null, this);

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
        Combinacao com = new Combinacao();
        com.setPerfil1(sessao.getPerfil().getId());
        com.setPerfil2(i);
        combinacaoServices.removerCombinacao(sessao.getPerfil(), com);
        listar();
        Auxiliar.criarToast(getContext(),"Você desfez o match");
    }
}
