package com.thelearningproject.applogin.pessoa.negocio;

import android.content.Context;

import com.thelearningproject.applogin.pessoa.dominio.Pessoa;
import com.thelearningproject.applogin.pessoa.persistencia.PessoaDAO;
import com.thelearningproject.applogin.infraestrutura.utils.UsuarioException;


/**
 * Created by nicolas on 30/07/2017.
 */

public class PessoaServices {
    private static PessoaServices instancia;
    private PessoaDAO persistencia;

    public PessoaServices(Context contexto) {
        this.persistencia = PessoaDAO.getInstance(contexto);
    }

    public static PessoaServices getInstancia(Context contexto) {
        if (instancia == null) {
            instancia = new PessoaServices(contexto);
        }

        return instancia;
    }

    public void inserirPessoa(Pessoa pessoa) {
        persistencia.inserir(pessoa);

    }

    public void alterarPessoa(Pessoa pessoa) throws UsuarioException {
        persistencia.alterarPessoa(pessoa);
    }

    public Pessoa retornaPessoa(int idUsuario) {
        Pessoa pessoa = persistencia.retornaPessoa(idUsuario);

        return pessoa;
    }

    public Pessoa consulta(int id){
        Pessoa pessoa = persistencia.consultar(id);
        return pessoa;
    }

}
