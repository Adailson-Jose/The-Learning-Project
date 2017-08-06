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

import com.thelearningproject.applogin.R;
import com.thelearningproject.applogin.pessoa.gui.ConfiguracaoActivity;
import com.thelearningproject.applogin.infraestrutura.utils.Auxiliar;
import com.thelearningproject.applogin.infraestrutura.utils.ControladorSessao;
import com.thelearningproject.applogin.perfil.dominio.Perfil;
import com.thelearningproject.applogin.perfil.gui.ListarHabilidadesActivity;
import com.thelearningproject.applogin.perfil.gui.ListarNecessidadesActivity;
import com.thelearningproject.applogin.perfil.negocio.PerfilServices;
import com.thelearningproject.applogin.pessoa.dominio.Pessoa;
import com.thelearningproject.applogin.pessoa.negocio.PessoaServices;
import com.thelearningproject.applogin.usuario.dominio.Usuario;
import com.thelearningproject.applogin.usuario.negocio.UsuarioServices;


/**
 * Createdd by gabri on 02/08/2017.
 */

public class MainPerfilFragment extends Fragment implements AdapterView.OnItemClickListener {
    private FragmentActivity activity;
    private ControladorSessao sessao;
    private TextView donoConta;
    private PerfilServices perfilServices = PerfilServices.getInstancia(activity);
    private ListView listViewHabilidades;
    private ListView listViewNecessidades;
    private ListView listViewConfiguracao;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        activity = this.getActivity();
        sessao = ControladorSessao.getInstancia(activity);
        donoConta = (TextView) activity.findViewById(R.id.nomeUsuarioID);


        return inflater.inflate(R.layout.fragment_main_perfil, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        activity = getActivity();
        sessao = ControladorSessao.getInstancia(activity);

        donoConta = (TextView) activity.findViewById(R.id.nomeUsuarioID);

        String[] listaHabilidades = {perfilServices.retornaStringListaHabilidades(sessao.getPerfil().getId())};
        String[] listaNecessidades = {perfilServices.retornaStringListaNecessidades(sessao.getPerfil().getId())};
        String[] listaConfig = {activity.getString(R.string.config), activity.getString(R.string.logout)};

        listViewHabilidades = (ListView) getActivity().findViewById(R.id.listaPerfilHabilidades);
        listViewNecessidades = (ListView) getActivity().findViewById(R.id.listaPerfilNecessidades);
        listViewConfiguracao = (ListView) getActivity().findViewById(R.id.listaPerfilConfiguracoes);

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

        listViewHabilidades.setAdapter(adapterOpcoesHabilidade);
        listViewNecessidades.setAdapter(adapterOpcoesNecessidade);
        listViewConfiguracao.setAdapter(adapterOpcoesConfig);

        listViewHabilidades.setOnItemClickListener(this);
        listViewNecessidades.setOnItemClickListener(this);
        listViewConfiguracao.setOnItemClickListener(this);

        if(sessao.verificaConexao()) {
            resumir();
        }

        if(sessao.verificaLogin()){
            activity.finish();

        } else {
            exibir();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getAdapter().equals(listViewHabilidades.getAdapter())){
            Intent entidade = new Intent(activity, ListarHabilidadesActivity.class);
            entidade.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(entidade);
        }else if(parent.getAdapter().equals(listViewNecessidades.getAdapter())){
            Intent entidade2 = new Intent(activity, ListarNecessidadesActivity.class);
            entidade2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(entidade2);
        }else{
            String opcao = String.valueOf(parent.getAdapter().getItem(position));
            switch (opcao) {
                case "Configurações":
                    Intent entidade = new Intent(activity, ConfiguracaoActivity.class);
                    entidade.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(entidade);
                    break;
                case "Sair":
                    sessao.encerraSessao();
                    Auxiliar.criarToast(activity, "Logout efetuado com sucesso");
                    activity.finish();
                    break;
            }
        }
    }

    private void resumir(){
        PessoaServices negocioPessoa = PessoaServices.getInstancia(activity);
        UsuarioServices negocioUsuario = UsuarioServices.getInstancia(activity);

        Pessoa pessoa = negocioPessoa.retornaPessoa(sessao.retornaIdUsuario());
        Usuario usuario = negocioUsuario.consulta(sessao.retornaIdUsuario());

        pessoa.setUsuario(usuario);
        sessao.iniciaSessao();
        sessao.setPessoa(pessoa);

    }

    private void exibir() {
        PerfilServices negocioperfil = PerfilServices.getInstancia(activity);

        Perfil perfil = negocioperfil.retornaPerfil(sessao.getPessoa().getId());
        perfil.setPessoa(sessao.getPessoa());
        sessao.setPerfil(perfil);

        donoConta.setText(sessao.getPessoa().getNome());
    }
}
