package com.thelearningproject.perfil.dominio;

import com.thelearningproject.combinacao.dominio.Combinacao;
import com.thelearningproject.estudo.dominio.Materia;
import com.thelearningproject.pessoa.dominio.Pessoa;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The type Perfil.
 */
public class Perfil implements Serializable {
    private int id;
    private Pessoa pessoa;
    private String descricao;
    private ArrayList<Materia> habilidades = new ArrayList<>();
    private ArrayList<Materia> necessidades = new ArrayList<>();
    private ArrayList<Combinacao> combinacoes = new ArrayList<>();

    /**
     * Gets combinacoes.
     *
     * @return the combinacoes
     */
    public ArrayList<Combinacao> getCombinacoes() {
        return combinacoes;
    }

    /**
     * Add combinacoes.
     *
     * @param combinacao the combinacao
     */
    public void addCombinacoes(Combinacao combinacao) {
        this.combinacoes.add(combinacao);
    }

    /**
     * Sets combinacoes.
     *
     * @param lista the lista
     */
    public void setCombinacoes(ArrayList<Combinacao> lista) {
        this.combinacoes = lista;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param novoId the novo id
     */
    public void setId(int novoId) {
        this.id = novoId;
    }

    /**
     * Gets pessoa.
     *
     * @return the pessoa
     */
    public Pessoa getPessoa() {
        return pessoa;
    }

    /**
     * Sets pessoa.
     *
     * @param novaPessoa the nova pessoa
     */
    public void setPessoa(Pessoa novaPessoa) {
        this.pessoa = novaPessoa;
    }

    /**
     * Gets descricao.
     *
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Sets descricao.
     *
     * @param novaDescricao the nova descricao
     */
    public void setDescricao(String novaDescricao) {
        this.descricao = novaDescricao;
    }

    /**
     * Gets habilidades.
     *
     * @return the habilidades
     */
    public ArrayList<Materia> getHabilidades() {
        return habilidades;
    }

    /**
     * Add habilidade.
     *
     * @param nova the nova
     */
    public void addHabilidade(Materia nova) {
        habilidades.add(nova);
    }

    /**
     * Gets necessidades.
     *
     * @return the necessidades
     */
    public ArrayList<Materia> getNecessidades() {
        return necessidades;
    }

    /**
     * Add necessidade.
     *
     * @param nova the nova
     */
    public void addNecessidade(Materia nova) {
        necessidades.add(nova);
    }

    public boolean equals(Object o) {
        boolean r = false;
        if (o instanceof Perfil) {
            Perfil that = (Perfil) o;
            r = (this.getId() == that.getId());
        }
        return r;
    }

    public int hashCode() {
        return (Integer.toString(this.getId()).hashCode());
    }
}
