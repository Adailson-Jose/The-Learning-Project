package layout;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.thelearningproject.applogin.R;
import com.thelearningproject.applogin.infraestrutura.utils.Auxiliar;

/**
 * Createdd by gabri on 02/08/2017.
 */

public class MainPerfilFragment extends Fragment implements AdapterView.OnItemClickListener, DialogInterface.OnClickListener {
    private AlertDialog alertaLogout;
    private FragmentActivity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_perfil, container, false);

        activity = getActivity();

        String[] listaMaterias = {"O que eu sei", "O que quero aprender"};
        String[] listaConfig = {"Configurações", "Sair"};

        ListView listaMateria = (ListView) view.findViewById(R.id.listaPerfil);
        ListView listaConta = (ListView) view.findViewById(R.id.listaPerfilConfiguracoes);

        ArrayAdapter<String> adapterOpcoesPerfil = new ArrayAdapter<>(
                activity,
                android.R.layout.simple_list_item_1,
                listaMaterias);

        ArrayAdapter<String> adapterOpcoesConfig = new ArrayAdapter<>(
                activity,
                android.R.layout.simple_list_item_1,
                listaConfig);

        listaMateria.setAdapter(adapterOpcoesPerfil);
        listaConta.setAdapter(adapterOpcoesConfig);
        listaMateria.setOnItemClickListener(this);
        listaConta.setOnItemClickListener(this);

        alertaLogout = Auxiliar.criarDialogConfirmacao(activity, "Deseja realmente desconectar sua conta?");


        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String opcao = String.valueOf(parent.getAdapter().getItem(position));

        switch (opcao) {
            case "Configurações":
                Toast.makeText(activity, opcao , Toast.LENGTH_SHORT).show();
                break;
            case "Sair":
                alertaLogout.show();
                break;
            case "O que eu sei":
                Toast.makeText(activity, "dale boy", Toast.LENGTH_SHORT).show();
                break;
            case "O que quero aprender":
                Toast.makeText(activity, "e ai, vai encarar?" , Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                Auxiliar.criarToast(getActivity(), "Logout efetuado com sucesso");
                getActivity().finish();
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                alertaLogout.dismiss();
                break;
        }
    }
}
