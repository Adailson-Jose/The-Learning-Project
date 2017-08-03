package com.thelearningproject.applogin.perfil.persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Criado por Ebony Marques on 26/07/2017.
 */

public class Banco extends SQLiteOpenHelper {
    private static Banco instancia;
    private static final String BANCO = "BDPerfil";
    private static final String TABELA = "perfis";
    private static final int VERSAO = 4;

    private static final String ID = "id";
    private static final String PESSOA = "pessoa";
    private static final String DESCRICAO = "descricao";
    private static final String HABILIDADES = "habilidade";

    public static synchronized Banco getInstancia(Context contexto) {
        if(instancia == null) {
            instancia = new Banco(contexto.getApplicationContext());
        }

        return instancia;
    }

    public Banco(Context contexto) {
        super(contexto, BANCO, null, VERSAO);
    }

    public void onCreate(SQLiteDatabase banco) {
        String sql = "CREATE TABLE " + TABELA + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PESSOA + " INTEGER, " +
                DESCRICAO + " TEXT, " +
                HABILIDADES + " INTEGER)";

        banco.execSQL(sql);
    }

    public void onUpgrade(SQLiteDatabase banco, int antigo, int novo) {
        String sql = "DROP TABLE IF EXISTS " + TABELA;
        banco.execSQL(sql);
        onCreate(banco);
    }
}
