package com.thelearningproject.applogin.infraestrutura.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.thelearningproject.applogin.R;
import com.thelearningproject.applogin.infraestrutura.utils.Auxiliar;
import com.thelearningproject.applogin.infraestrutura.utils.ControladorSessao;
import com.thelearningproject.applogin.infraestrutura.utils.UsuarioException;
import com.thelearningproject.applogin.pessoa.dominio.Pessoa;
import com.thelearningproject.applogin.pessoa.negocio.PessoaServices;
import com.thelearningproject.applogin.usuario.negocio.UsuarioServices;

public class AlterarEmailActivity extends AppCompatActivity {
    private EditText alterarEmail;
    private Auxiliar auxiliar = new Auxiliar();

    private ControladorSessao sessao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_email);

        Button btAlterar;
        Button btCancelar;

        sessao = ControladorSessao.getInstancia(this.getApplicationContext());

        alterarEmail = (EditText) findViewById(R.id.emailID);
        btAlterar = (Button) findViewById(R.id.alterarEmail);
        btCancelar = (Button) findViewById(R.id.Cancelar);

        btAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alterar(v);
                Auxiliar.esconderTeclado(AlterarEmailActivity.this);

            }
        });
        btCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AlterarEmailActivity.this,ConfiguracaoActivity.class));
            }
        });

        alterarEmail.setText(sessao.getPessoa().getUsuario().getEmail());
    }

    private void executarAlterar(Pessoa pessoa) throws UsuarioException {
        UsuarioServices negocioUsuario = UsuarioServices.getInstancia(getBaseContext());
        PessoaServices negocioPessoa = PessoaServices.getInstancia(getBaseContext());

        negocioUsuario.alterarEmailUsuario(pessoa.getUsuario());
        negocioPessoa.alterarPessoa(pessoa);

        sessao.setUsuario(pessoa.getUsuario());
        sessao.setPessoa(pessoa);

        Auxiliar.criarToast(this, "Dados atualizados com sucesso");

        Intent entidade = new Intent(AlterarEmailActivity.this, ConfiguracaoActivity.class);
        entidade.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(entidade);
    }

    public void alterar(View view){
        String email = alterarEmail.getText().toString();

        Pessoa pessoa = sessao.getPessoa();

        pessoa.setUsuario(sessao.getUsuario());
        pessoa.getUsuario().setEmail(email);
        pessoa.getUsuario().setStatus(com.thelearningproject.applogin.infraestrutura.utils.Status.ATIVADO);

        try {
            if(validaAlterar(pessoa)){
                executarAlterar(pessoa);
            }

        } catch (UsuarioException e){
            alterarEmail.setError("E-mail já cadastrado");
        }
    }

    private Boolean validaAlterar(Pessoa pessoa) {
        Boolean validacao = true;
        if (pessoa.getUsuario().getEmail() == null || pessoa.getUsuario().getEmail().trim().length() == 0 || !auxiliar.aplicaPattern(pessoa.getUsuario().getEmail().toUpperCase())) {
            alterarEmail.setError("E-mail inválido");
            validacao = false;
        }
        return validacao;
    }
}
