package com.thelearningproject.combinacao.dominio;

/**
 * The type Combinacao.
 */
public class Combinacao {
    private int perfil1;
    private int perfil2;
    private int status;
    private static final int TRINTA_E_UM = 31;

    /**
     * Gets status.
     *
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param novo the novo
     */
    public void setStatus(int novo) {
        this.status = novo;
    }

    /**
     * Gets perfil 1.
     *
     * @return the perfil 1
     */
    public int getPerfil1() {
        return perfil1;
    }

    /**
     * Sets perfil 1.
     *
     * @param novo the novo
     */
    public void setPerfil1(int novo) {
        this.perfil1 = novo;
    }

    /**
     * Gets perfil 2.
     *
     * @return the perfil 2
     */
    public int getPerfil2() {
        return perfil2;
    }

    /**
     * Sets perfil 2.
     *
     * @param novo the novo
     */
    public void setPerfil2(int novo) {
        this.perfil2 = novo;
    }


    @Override
    public boolean equals(Object o) {
        boolean r = false;
        if (o instanceof Combinacao) {
            Combinacao that = (Combinacao) o;
            r = (this.getPerfil1() == that.getPerfil1() && this.getPerfil2() == that.getPerfil2());
        }
        return r;
    }

    @Override
    public int hashCode() {
        int result = perfil1;
        result = TRINTA_E_UM * result + perfil2;
        return result;
    }

    @Override
    public String toString() {
        return "Combinacao{" +
                "perfil1=" + perfil1 +
                ", perfil2=" + perfil2 +
                ", status=" + status +
                '}';
    }
}
