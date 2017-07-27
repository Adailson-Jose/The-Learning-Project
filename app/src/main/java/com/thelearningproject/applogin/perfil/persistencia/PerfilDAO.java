package com.thelearningproject.applogin.perfil.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.thelearningproject.applogin.perfil.dominio.Perfil;

/**
 * Created by Ebony Marques on 26/07/2017.
 */

public class PerfilDAO {
    private static Banco banco;
    private static PerfilDAO instancia;
    private static final String TABELA = "perfis";

    private static final String ID = "id";
    private static final String USUARIO = "usuario";
    private static final String DESCRICAO = "descricao";

    public static synchronized PerfilDAO getInstance(Context contexto) {
        if (instancia == null) {
            instancia = new PerfilDAO(contexto.getApplicationContext());
        }
        return instancia;
    }

    public PerfilDAO(Context contexto) {
        this.banco = Banco.getInstancia(contexto);
    }

    public void inserir(Perfil perfil) {
        ContentValues valores = new ContentValues();
        valores.put(USUARIO, perfil.getUsuarioID());

        banco.getWritableDatabase().insert(TABELA, null, valores);
    }

    public Perfil retornaPerfil(int id_usuario) {
        String[] colunas = {ID, USUARIO};
        Cursor cursor = banco.getReadableDatabase().query(TABELA, colunas, USUARIO + " = ?", new String[]{Integer.toString(id_usuario)}, null, null, null);
        Perfil perfil = null;

        if (cursor.moveToFirst()) {
            perfil = new Perfil();
            perfil.setId(cursor.getInt(cursor.getColumnIndex(ID)));
            perfil.setUsuarioID(cursor.getInt(cursor.getColumnIndex(USUARIO)));
        }
        return perfil;
    }

    public Perfil consultar(int id) {
        String[] colunas = {ID, USUARIO};
        Cursor cursor = banco.getReadableDatabase().query(TABELA, colunas, ID + " = ?", new String[]{Integer.toString(id)}, null, null, null);
        Perfil perfil = null;
        if (cursor.moveToFirst()) {
            perfil = new Perfil();
            perfil.setId(cursor.getInt(cursor.getColumnIndex(ID)));
            perfil.setUsuarioID(cursor.getInt(cursor.getColumnIndex(USUARIO)));
        }

        return perfil;
    }
}
