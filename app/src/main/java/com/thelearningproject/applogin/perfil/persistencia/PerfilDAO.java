package com.thelearningproject.applogin.perfil.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.thelearningproject.applogin.estudo.dominio.Materia;
import com.thelearningproject.applogin.perfil.dominio.Perfil;

/**
 * Created by Ebony Marques on 26/07/2017.
 */

public class PerfilDAO {
    private static Banco banco;
    private static PerfilDAO instancia;
    private static final String BANCO = "BDPerfil";
    private static final String TABELA = "perfis";
    private static final int VERSAO = 1;

    private static final String ID = "id";
    private static final String USUARIO = "usuario";
    private static final String DESCRICAO = "descricao";
    private static final String HABILIDADES = "habilidade";

    public static synchronized PerfilDAO getInstance(Context contexto){
        if(instancia == null){
            instancia = new PerfilDAO(contexto.getApplicationContext());
        }
        return instancia;
    }

    public PerfilDAO(Context contexto) {
        this.banco = Banco.getInstancia(contexto);
    }

    public void inserir(Perfil perfil) {
        ContentValues valores = new ContentValues();
        valores.put(USUARIO, perfil.getUsuario());
        valores.put(HABILIDADES, "habilidades");

        banco.getWritableDatabase().insert(TABELA, null, valores);
    }

    public Perfil retornaPerfil(String id){
        String[] colunas = {ID, USUARIO, HABILIDADES};
        Cursor cursor = banco.getReadableDatabase().query(TABELA, colunas, "usuario = ?", new String[] {id}, null, null, null);
        Perfil perfil = null;

        if (cursor.moveToFirst()) {
            perfil = new Perfil();
            perfil.setId(cursor.getInt(cursor.getColumnIndex(ID)));
            perfil.setUsuario(cursor.getInt(cursor.getColumnIndex(USUARIO)));

            Materia habilidade = new Materia();
            habilidade.setId(cursor.getInt(cursor.getColumnIndex(HABILIDADES)));
            perfil.addHabilidade(habilidade);

            //Precisamos setar nome e descrição da habilidade, mas Nicollas nos dirá onde e como fazer.
        }

        return perfil;
    }
}
