package com.thelearningproject.pessoa.negocio;

import android.content.Context;

import com.thelearningproject.infraestrutura.utils.UsuarioException;
import com.thelearningproject.pessoa.dominio.Pessoa;
import com.thelearningproject.pessoa.persistencia.PessoaDAO;


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

    public Boolean verificaTelefoneExistente(String telefone) {
        Boolean resultado = false;

        if (persistencia.retornaPessoa(telefone) == null) {
            resultado = true;
        }

        return resultado;
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

    public Pessoa retornaPessoa(String telefone) throws UsuarioException {
        Pessoa pessoa = persistencia.retornaPessoa(telefone);
        if (pessoa != null) {
            return pessoa;
        } else {
            throw new UsuarioException("Telefone inexistente");
        }
    }

    public Pessoa retornaPessoa(int idUsuario) {
        return persistencia.retornaPessoa(idUsuario);
    }

    public Pessoa consulta(int id) {
        return persistencia.consultar(id);
    }

}
