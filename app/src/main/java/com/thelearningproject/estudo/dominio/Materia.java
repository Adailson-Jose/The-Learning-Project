package com.thelearningproject.estudo.dominio;

/**
 * The type Materia.
 */
public class Materia {
    private int id;
    private String nome;

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
     * Gets nome.
     *
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Sets nome.
     *
     * @param novoNome the novo nome
     */
    public void setNome(String novoNome) {
        this.nome = novoNome;
    }

    public boolean equals(Object o) {
        boolean r = false;
        if (o instanceof Materia) {
            Materia that = (Materia) o;
            r = (this.getNome().equals(that.getNome()));
        }
        return r;
    }

    public int hashCode() {
        return (Integer.toString(this.getId()).hashCode());
    }

    @Override
    public String toString() {
        return this.getNome();
    }
}
