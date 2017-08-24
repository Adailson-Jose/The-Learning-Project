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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.thelearningproject.applogin.R;
import com.thelearningproject.applogin.infraestrutura.utils.Auxiliar;
import com.thelearningproject.applogin.infraestrutura.utils.ControladorSessao;
import com.thelearningproject.applogin.perfil.dominio.Perfil;
import com.thelearningproject.applogin.perfil.gui.ListarHabilidadesActivity;
import com.thelearningproject.applogin.perfil.gui.ListarNecessidadesActivity;
import com.thelearningproject.applogin.perfil.negocio.PerfilServices;
import com.thelearningproject.applogin.pessoa.dominio.Pessoa;
import com.thelearningproject.applogin.pessoa.gui.ConfiguracaoActivity;
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
    private TextView donoDescricao;
    private PerfilServices perfilServices = PerfilServices.getInstancia(activity);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        activity = this.getActivity();
        sessao = ControladorSessao.getInstancia(activity);
        donoConta = (TextView) activity.findViewById(R.id.nomeUsuarioID);
        donoDescricao = (TextView) activity.findViewById(R.id.descricaoUsuarioID);


        return inflater.inflate(R.layout.fragment_main_perfil, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        activity = getActivity();
        sessao = ControladorSessao.getInstancia(activity);

        donoConta = (TextView) activity.findViewById(R.id.nomeUsuarioID);
        donoDescricao = (TextView) activity.findViewById(R.id.descricaoUsuarioID);

        Button btnHabilidades = (Button) activity.findViewById(R.id.btnHabilidade);
        btnHabilidades.setText(perfilServices.retornaStringListaHabilidades(sessao.getPerfil().getId()));
        btnHabilidades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent entidade = new Intent(activity, ListarHabilidadesActivity.class);
                entidade.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(entidade);
            }
        });

        Button btnNecessidades = (Button) activity.findViewById(R.id.btnNecessidade);
        btnNecessidades.setText(perfilServices.retornaStringListaNecessidades(sessao.getPerfil().getId()));
        btnNecessidades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent entidade = new Intent(activity, ListarNecessidadesActivity.class);
                entidade.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(entidade);
            }
        });

        String[] listaConfig = {activity.getString(R.string.config), activity.getString(R.string.logout)};

        ListView listViewConfiguracao = (ListView) getActivity().findViewById(R.id.listaPerfilConfiguracoes);


        ArrayAdapter<String> adapterOpcoesConfig = new ArrayAdapter<>(
                activity,
                android.R.layout.simple_list_item_1,
                listaConfig);

        listViewConfiguracao.setAdapter(adapterOpcoesConfig);
        listViewConfiguracao.setOnItemClickListener(this);

        if (sessao.verificaConexao()) {
            resumir();
        }

        if (sessao.verificaLogin()) {
            activity.finish();

        } else {
            exibir();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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

    private void resumir() {
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
        donoDescricao.setText(sessao.getPerfil().getDescricao());
    }
}
