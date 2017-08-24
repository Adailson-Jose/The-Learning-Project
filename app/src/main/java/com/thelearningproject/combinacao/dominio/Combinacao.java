package com.thelearningproject.combinacao.dominio;

/**
 * Criado por Pichau em 17/08/2017.
 */

public class Combinacao {
    private int perfil1;
    private int perfil2;
    private int status;
    private static final int TRINTA_E_UM = 31;

    public int getStatus() {
        return status;
    }

    public void setStatus(int novo) {
        this.status = novo;
    }

    public int getPerfil1() {
        return perfil1;
    }

    public void setPerfil1(int novo) {
        this.perfil1 = novo;
    }

    public int getPerfil2() {
        return perfil2;
    }

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
}
