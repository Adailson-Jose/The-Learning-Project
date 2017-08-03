package layout;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.thelearningproject.applogin.R;
import com.thelearningproject.applogin.estudo.dominio.Materia;
import com.thelearningproject.applogin.infraestrutura.gui.ConfiguracaoActivity;
import com.thelearningproject.applogin.infraestrutura.utils.Auxiliar;
import com.thelearningproject.applogin.infraestrutura.utils.ControladorSessao;
import com.thelearningproject.applogin.perfil.dominio.Perfil;
import com.thelearningproject.applogin.perfil.negocio.PerfilServices;
import com.thelearningproject.applogin.pessoa.dominio.Pessoa;
import com.thelearningproject.applogin.pessoa.negocio.PessoaServices;
import com.thelearningproject.applogin.usuario.dominio.Usuario;
import com.thelearningproject.applogin.usuario.negocio.UsuarioServices;

import java.util.ArrayList;

/**
 * Createdd by gabri on 02/08/2017.
 */

public class MainPerfilFragment extends Fragment implements AdapterView.OnItemClickListener {
    private FragmentActivity activity;
    private ControladorSessao sessao;
    private TextView donoConta;
    private PerfilServices perfilServices = PerfilServices.getInstancia(activity);
    private String todasHabilidades;
    private String todasNecessidades;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_perfil, container, false);

        activity = getActivity();
        sessao = ControladorSessao.getInstancia(activity);

        donoConta = (TextView) view.findViewById(R.id.nomeUsuarioID);

        todasHabilidades = perfilServices.retornaStringListaHabilidades(sessao.getPerfil().getId());
        todasNecessidades = perfilServices.retornaStringListaNecessidades(sessao.getPerfil().getId());

        String[] listaHabilidades = {todasHabilidades};
        String[] listaNecessidades = {todasNecessidades};
        String[] listaConfig = {activity.getString(R.string.config), activity.getString(R.string.logout)};

        ListView listView1 = (ListView) view.findViewById(R.id.listaPerfilHabilidades);
        ListView listView2 = (ListView) view.findViewById(R.id.listaPerfilNecessidades);
        ListView listView3 = (ListView) view.findViewById(R.id.listaPerfilConfiguracoes);

        ArrayAdapter<String> adapterOpcoesHabilidade = new ArrayAdapter<>(
                activity,
                android.R.layout.simple_list_item_1,
                listaHabilidades);

        ArrayAdapter<String> adapterOpcoesNecessidade = new ArrayAdapter<>(
                activity,
                android.R.layout.simple_list_item_1,
                listaNecessidades);

        ArrayAdapter<String> adapterOpcoesConfig = new ArrayAdapter<>(
                activity,
                android.R.layout.simple_list_item_1,
                listaConfig);

        listView1.setAdapter(adapterOpcoesHabilidade);
        listView2.setAdapter(adapterOpcoesNecessidade);
        listView3.setAdapter(adapterOpcoesConfig);

        listView1.setOnItemClickListener(this);
        listView2.setOnItemClickListener(this);
        listView3.setOnItemClickListener(this);

        if(sessao.verificaConexao()) {
            resumir();
        }

        if(sessao.verificaLogin()){
            activity.finish();

        } else {
            exibir();
        }


        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String opcao = String.valueOf(parent.getAdapter().getItem(position));
        String tipo = opcao.substring(opcao.length() - 1);

        switch (opcao) {
            case "Configurações":
                startActivity(new Intent(activity, ConfiguracaoActivity.class));
                break;
            case "Sair":
                sessao.encerraSessao();
                Auxiliar.criarToast(activity, "Logout efetuado com sucesso");
                activity.finish();
                break;
        }
        switch (tipo){
            case "h":
                Auxiliar.criarToast(activity, "habilidade");
                break;
            case "n":
                Auxiliar.criarToast(activity, "necessidade");
        }
    }

    private void resumir(){
        PessoaServices negocioPessoa = PessoaServices.getInstancia(activity);
        UsuarioServices negocioUsuario = UsuarioServices.getInstancia(activity);

        Pessoa pessoa = negocioPessoa.retornaPessoa(sessao.retornaIdUsuario());
        Usuario usuario = negocioUsuario.consulta(sessao.retornaIdUsuario());

        pessoa.setUsuario(usuario);
        sessao.iniciaSessao();
        sessao.setUsuario(usuario);
        sessao.setPessoa(pessoa);

    }

    private void exibir() {
        PerfilServices negocioperfil = PerfilServices.getInstancia(activity);

        Perfil perfil = negocioperfil.retornaPerfil(sessao.getPessoa().getId());
        perfil.setPessoa(sessao.getPessoa());
        sessao.setPerfil(perfil);

        String mensagem = "Oi, " + sessao.getPessoa().getNome() + ".";

        donoConta.setText(mensagem);
    }
}
