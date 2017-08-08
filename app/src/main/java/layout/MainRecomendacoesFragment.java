package layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thelearningproject.applogin.R;

/**
 * Criado por Gabriel on 03/08/2017
 */
public class MainRecomendacoesFragment extends Fragment {
    public MainRecomendacoesFragment() {
        // Requer um construtor publico vazio
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_recomendacoes, container, false);
    }

}
