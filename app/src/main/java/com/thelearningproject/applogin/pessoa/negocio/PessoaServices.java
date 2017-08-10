package com.thelearningproject.applogin.pessoa.negocio;

import android.content.Context;

import com.thelearningproject.applogin.pessoa.dominio.Pessoa;
import com.thelearningproject.applogin.pessoa.persistencia.PessoaDAO;


/**
 * Criado por Nícolas on 30/07/2017.
 */

public class PessoaServices {
    private static PessoaServices instancia;
    private PessoaDAO persistencia;

    public PessoaServices(Context contexto) {
        this.persistencia = PessoaDAO.getInstancia(contexto);
    }

    public static PessoaServices getInstancia(Context contexto) {
        if (instancia == null) {
            instancia = new PessoaServices(contexto);
        }

        return instancia;
    }

    public void inserirPessoa(Pessoa pessoa) {
        if (validaAlterarPessoa(pessoa.getUsuario().getId())) {
            Pessoa pessoaAntiga = retornaPessoa(pessoa.getUsuario().getId());
            pessoaAntiga.setNome(pessoa.getNome());
            alterarPessoa(pessoaAntiga);
        } else {
            persistencia.inserir(pessoa);
        }

    }

    private boolean validaAlterarPessoa(int usuarioId) {
        Boolean validacao = false;
        if (retornaPessoa(usuarioId) != null) {
            validacao = true;

        }
        return validacao;
    }

    public void alterarPessoa(Pessoa pessoa) {
        persistencia.alterarPessoa(pessoa);
    }

    public Pessoa retornaPessoa(int idUsuario) {
        return persistencia.retornaPessoa(idUsuario);
    }

    public Pessoa consulta(int id) {
        return persistencia.consultar(id);
    }

}
