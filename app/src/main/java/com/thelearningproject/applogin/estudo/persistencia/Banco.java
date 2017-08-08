package com.thelearningproject.applogin.estudo.persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Criado por Nicollas on 26/07/2017.
 */

public class Banco extends SQLiteOpenHelper {
    private static Banco instancia;
    private static final String BANCO_DADOS = "BDMateria";
    private static final String TABELA = "materias";
    private static final int VERSAO = 2;
    private static final String ID = "id";
    private static final String NOME = "nome";

    public static synchronized Banco getInstance(Context contexto) {
        if(instancia == null) {
            instancia = new Banco(contexto.getApplicationContext());
        }
        return instancia;
    }

    public Banco(Context contexto) {
        super(contexto, BANCO_DADOS, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABELA + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NOME + " TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABELA;
        db.execSQL(sql);
        onCreate(db);
    }
}
