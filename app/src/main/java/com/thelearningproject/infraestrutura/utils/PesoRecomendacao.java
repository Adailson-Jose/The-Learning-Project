package com.thelearningproject.infraestrutura.utils;

import android.support.annotation.NonNull;


public class PesoRecomendacao implements Comparable<PesoRecomendacao> {
    private int materiaId1;
    private int materiaId2;
    private Double valor;

    public int getMateriaId1() {
        return materiaId1;
    }

    public void setMateriaId1(int materiaId1) {
        this.materiaId1 = materiaId1;
    }

    public int getMateriaId2() {
        return materiaId2;
    }

    public void setMateriaId2(int materiaId2) {
        this.materiaId2 = materiaId2;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PesoRecomendacao that = (PesoRecomendacao) o;
        boolean r;
        if (this.getMateriaId1() == that.getMateriaId1() && this.getMateriaId2() == that.getMateriaId2() && this.getValor().equals(that.getValor())) {
            r = true;
        } else if (this.getMateriaId1() == that.getMateriaId2() && this.getMateriaId2() == that.getMateriaId1() && this.getValor().equals(that.getValor())) {
            r = true;
        } else {
            r = false;
        }

        return r;
    }

    @Override
    public int hashCode() {
        return valor.hashCode();
    }

    @Override
    public int compareTo(@NonNull PesoRecomendacao o) {
        return o.getValor().compareTo(this.getValor());
    }
}
