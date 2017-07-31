package com.thelearningproject.applogin.usuario.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.thelearningproject.applogin.R;
import com.thelearningproject.applogin.infraestrutura.utils.Auxiliar;
import com.thelearningproject.applogin.infraestrutura.utils.ControladorSessao;
import com.thelearningproject.applogin.infraestrutura.utils.UsuarioException;
import com.thelearningproject.applogin.infraestrutura.gui.TermosActivity;
import com.thelearningproject.applogin.pessoa.dominio.Pessoa;
import com.thelearningproject.applogin.pessoa.negocio.PessoaServiços;
import com.thelearningproject.applogin.usuario.dominio.Status;
import com.thelearningproject.applogin.usuario.dominio.Usuario;
import com.thelearningproject.applogin.usuario.negocio.UsuarioServices;

public class CadastroActivity extends Activity {
    private ControladorSessao sessao;
    private Auxiliar auxiliar = new Auxiliar();
    private EditText entradaNome;
    private EditText entradaEmail;
    private EditText entradaSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        sessao = ControladorSessao.getInstancia(this.getApplicationContext());
        entradaNome = (EditText) findViewById(R.id.nomeEntradaID);
        entradaEmail = (EditText) findViewById(R.id.emailEntradaID);
        entradaSenha = (EditText) findViewById(R.id.senhaEntradaID);
        Button botaoCadastrar = (Button) findViewById(R.id.botaoCadastroID);

        botaoCadastrar.setOnClickListener( new View.OnClickListener(){

            @Override
            public void onClick(View view){
                processoCadastro();
             }
        });
    }

    private boolean validaCampos(Pessoa pessoa){
        boolean validacao = true;
        StringBuilder erro = new StringBuilder();

        if (pessoa.getNome() == null || pessoa.getNome().trim().length() == 0) {
            entradaNome.setError("Nome inválido");
            validacao = false;
        }

        if (pessoa.getUsuario().getEmail() == null || pessoa.getUsuario().getEmail().trim().length() == 0 || !auxiliar.aplicaPattern(pessoa.getUsuario().getEmail().toUpperCase())) {
            entradaEmail.setError("Email inválido");
            validacao = false;
        }

        if (pessoa.getUsuario().getSenha() == null || pessoa.getUsuario().getSenha().trim().length() == 0) {
            entradaSenha.setError("Senha inválida");
            validacao = false;
        }

        String resultado = (erro.toString().trim());

        if (!resultado.equals("")) {
            Toast.makeText(CadastroActivity.this, resultado, Toast.LENGTH_LONG).show();
        }

        return validacao;

    }

    private void processoCadastro(){
        String nome = entradaNome.getText().toString();
        String email = entradaEmail.getText().toString();
        String senha = entradaSenha.getText().toString();

        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuario.setStatus(Status.ATIVADO);

        Pessoa pessoa = new Pessoa();
        pessoa.setNome(nome);
        pessoa.setUsuario(usuario);

        try {
            executarCadastro(pessoa);

        } catch (UsuarioException e){
            entradaEmail.setError("Email já cadastrado");
        }
    }

    private void executarCadastro(Pessoa pessoa) throws UsuarioException{
        if (validaCampos(pessoa)) {
            UsuarioServices negocioUsuario = UsuarioServices.getInstancia(getBaseContext());
            negocioUsuario.inserirUsuario(pessoa.getUsuario());

            pessoa.setUsuarioID(negocioUsuario.retornaUsuario(pessoa.getUsuario().getEmail()).getId());
            PessoaServiços negocioPessoa = PessoaServiços.getInstancia(getBaseContext());
            negocioPessoa.inserirPessoa(pessoa);

            Intent entidade = new Intent(CadastroActivity.this, TermosActivity.class);
            entidade.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            sessao.setUsuario(negocioUsuario.retornaUsuario(pessoa.getUsuario().getEmail()));
            Pessoa pessoa2 = negocioPessoa.retornaPessoa(pessoa.getUsuarioID());
            pessoa2.setUsuario(sessao.getUsuario());
            sessao.setPessoa(pessoa2);
            sessao.salvaSessao();
            sessao.iniciaSessao();

            startActivity(entidade);
            finish();
        }
    }



}
