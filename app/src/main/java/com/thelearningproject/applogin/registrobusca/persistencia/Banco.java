package com.thelearningproject.applogin.registrobusca.persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Criado por Pichau em 26/07/2017.
 */

public final class Banco extends SQLiteOpenHelper{
    private static Banco instancia;
    private static final String NOME_BANCO = "DadosBusca";
    private static final int VERSION = 1;
    private static final String TABELA = "dadosbusca";
    private static final String IDPERFIL = "perfil";
    private static final String MATERIA = "materia";

    public static synchronized Banco getInstancia(Context context) {
        if (instancia == null) {
            instancia = new Banco(context.getApplicationContext());
        }
        return instancia;
    }

    private Banco(Context context) {
        super(context, NOME_BANCO, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABELA + " (" +
                IDPERFIL + " INTEGER, " +
                MATERIA + " VARCHAR, " +
                "PRIMARY KEY(" +IDPERFIL +", "+MATERIA+"))";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABELA;
        db.execSQL(sql);
        onCreate(db);
    }
}

