package com.thelearningproject.applogin.combinacao.dominio;

/**
 * Created by Pichau on 17/08/2017.
 */

public class Combinacao {
    int perfil1;
    int perfil2;
    //Adicionar ENUM para isto
    //0 - pendente
    //1- ativo
    //2- desativo
    int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPerfil1() {
        return perfil1;
    }

    public void setPerfil1(int perfil1) {
        this.perfil1 = perfil1;
    }

    public int getPerfil2() {
        return perfil2;
    }

    public void setPerfil2(int perfil2) {
        this.perfil2 = perfil2;
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
        result = 31 * result + perfil2;
        return result;
    }
}
