package com.thelearningproject.pessoa.negocio;

import android.content.Context;

import com.thelearningproject.infraestrutura.utils.UsuarioException;
import com.thelearningproject.pessoa.dominio.Pessoa;
import com.thelearningproject.pessoa.persistencia.PessoaDAO;


/**
 * The type Pessoa services.
 */
public class PessoaServices {
    private static PessoaServices instancia;
    private PessoaDAO persistencia;

    /**
     * Instantiates a new Pessoa services.
     *
     * @param contexto the contexto
     */
    public PessoaServices(Context contexto) {
        this.persistencia = PessoaDAO.getInstancia(contexto);
    }

    /**
     * Gets instancia.
     *
     * @param contexto the contexto
     * @return the instancia
     */
    public static PessoaServices getInstancia(Context contexto) {
        if (instancia == null) {
            instancia = new PessoaServices(contexto);
        }

        return instancia;
    }

    /**
     * Inserir pessoa.
     *
     * @param pessoa the pessoa
     */
    public void inserirPessoa(Pessoa pessoa) {
        if (validaAlterarPessoa(pessoa.getUsuario().getId())) {
            Pessoa pessoaAntiga = retornaPessoa(pessoa.getUsuario().getId());
            pessoaAntiga.setNome(pessoa.getNome());
            alterarPessoa(pessoaAntiga);
        } else {
            persistencia.inserir(pessoa);
        }

    }

    /**
     * Verifica telefone existente boolean.
     *
     * @param telefone the telefone
     * @return the boolean
     */
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

    /**
     * Alterar pessoa.
     *
     * @param pessoa the pessoa
     */
    public void alterarPessoa(Pessoa pessoa) {
        persistencia.alterarPessoa(pessoa);
    }

    /**
     * Retorna pessoa pessoa.
     *
     * @param telefone the telefone
     * @return the pessoa
     * @throws UsuarioException the usuario exception
     */
    public Pessoa retornaPessoa(String telefone) throws UsuarioException {
        Pessoa pessoa = persistencia.retornaPessoa(telefone);
        if (pessoa != null) {
            return pessoa;
        } else {
            throw new UsuarioException("Telefone inexistente");
        }
    }

    /**
     * Retorna pessoa pessoa.
     *
     * @param idUsuario the id usuario
     * @return the pessoa
     */
    public Pessoa retornaPessoa(int idUsuario) {
        return persistencia.retornaPessoa(idUsuario);
    }

    /**
     * Consulta pessoa.
     *
     * @param id the id
     * @return the pessoa
     */
    public Pessoa consulta(int id) {
        return persistencia.consultar(id);
    }

}
