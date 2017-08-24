package com.thelearningproject.applogin.pessoa.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.thelearningproject.applogin.R;
import com.thelearningproject.applogin.infraestrutura.utils.Auxiliar;
import com.thelearningproject.applogin.infraestrutura.utils.ControladorSessao;

public class ConfimaRecuperarActivity extends AppCompatActivity {
    private ControladorSessao sessao;
    private EditText entradaCodigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confima_recuperar);

        entradaCodigo = (EditText) findViewById(R.id.codigoID);
        sessao = ControladorSessao.getInstancia(this.getApplicationContext());
        Button botaoReenviar = (Button) findViewById(R.id.botaoReenviarID);
        Button botaoConfima = (Button) findViewById(R.id.botaoConfirmaID);

        botaoReenviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessao.setCodigo(Auxiliar.geraCodigo());
                enviaSms(sessao.getPessoa().getTelefone(), sessao.getCodigo());
                recreate();

            }
        });

        botaoConfima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processoVerificacao();
            }
        });
    }

    private void processoVerificacao() {
        String codigo = entradaCodigo.getText().toString();
        if (validaCodigo(codigo)) {
            executaVerificacao(codigo);
        }
    }

    private boolean validaCodigo(String codigo) {
        boolean validacao = true;

        if (codigo == null || codigo.trim().length() == 0) {
            entradaCodigo.setError("Código inválido");
            validacao = false;
        }
        return validacao;
    }

    private void executaVerificacao(String codigo) {
        if (codigo.equals(String.valueOf(sessao.getCodigo()))) {
            Intent entidade = new Intent(ConfimaRecuperarActivity.this, AlterarSenhaActivity.class);
            entidade.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            sessao.salvarSessao();
            sessao.iniciaSessao();
            startActivity(entidade);
        } else {
            entradaCodigo.setError("Código não reconhecido");

        }
    }

    public void enviaSms(String telefone, String codigo) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(telefone, null, getApplicationContext().getString(R.string.sms) + " " + codigo, null, null);
    }

}
