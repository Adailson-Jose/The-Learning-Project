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
import com.thelearningproject.applogin.infraestrutura.utils.UsuarioException;
import com.thelearningproject.applogin.pessoa.dominio.Pessoa;
import com.thelearningproject.applogin.pessoa.negocio.PessoaServices;
import com.thelearningproject.applogin.usuario.dominio.Usuario;
import com.thelearningproject.applogin.usuario.negocio.UsuarioServices;


public class RecuperarContaActivity extends AppCompatActivity {
    private EditText entradaTelefone;
    private ControladorSessao sessao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_conta);

        sessao = ControladorSessao.getInstancia(this.getApplicationContext());
        entradaTelefone = (EditText) findViewById(R.id.telefoneID);
        Button botaoContinuar = (Button) findViewById(R.id.botaoConfirmaID);

        botaoContinuar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                processoRecuperacao();
            }
        });

    }

    private void processoRecuperacao() {
        try{
            String telefone = entradaTelefone.getText().toString();
            Pessoa pessoa = new Pessoa();
            pessoa.setTelefone(telefone);
            if (validaRecuperacao(pessoa)) {
                PessoaServices negocioPessoa = PessoaServices.getInstancia(getBaseContext());
                pessoa = negocioPessoa.retornaPessoa(telefone);
                executarRecuperacao(pessoa);
            }
        }catch (UsuarioException e){
            entradaTelefone.setError(e.getMessage());
        }

    }
    private boolean validaRecuperacao(Pessoa pessoa) {
        boolean validacao = true;
        if (!Auxiliar.telefonePattern(pessoa.getTelefone()) || pessoa.getTelefone().trim().length() == 0 || pessoa.getTelefone() == null) {
            entradaTelefone.setError("Telefone inválido");
            validacao = false;
        }

        return validacao;
    }

    private void executarRecuperacao(Pessoa pessoa){
        UsuarioServices negocioUsuario = UsuarioServices.getInstancia(getBaseContext());
        Usuario usuario = negocioUsuario.consulta(pessoa.getUsuario().getId());
        pessoa.setUsuario(usuario);

        String codigo = Auxiliar.geraCodigo();
        enviaSms(pessoa.getTelefone(),codigo);
        sessao.setCodigo(codigo);
        sessao.setPessoa(pessoa);
        Intent entidade = new Intent(RecuperarContaActivity.this, ConfimaRecuperarActivity.class);
        entidade.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(entidade);
    }

    public void enviaSms(String telefone, String codigo){
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(telefone,null,getApplicationContext().getString(R.string.sms)+" "+codigo,null,null);
    }
}