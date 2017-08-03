package com.thelearningproject.applogin.pessoa.persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Criado por Nícolas on 30/07/2017.
 */

public class Banco extends SQLiteOpenHelper {
    private static Banco instancia;
    private static final String BANCO = "BDPessoa";
    private static final String TABELA = "pessoas";
    private static final int VERSAO = 2;

    private static final String ID = "id";
    private static final String NOME = "nome";
    private static final String USUARIO = "usuario";

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
                NOME + " TEXT, " +
                USUARIO + " INTEGER)";

        banco.execSQL(sql);
    }

    public void onUpgrade(SQLiteDatabase banco, int antigo, int novo) {
        String sql = "DROP TABLE IF EXISTS " + TABELA;
        banco.execSQL(sql);
        onCreate(banco);
    }
}