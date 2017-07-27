package com.thelearningproject.applogin.perfil.persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Pichau on 26/07/2017.
 */

public class BancoHabilidade extends SQLiteOpenHelper{

    private static BancoHabilidade sInstance;
    private static final String NOME_BANCO = "RelacaoUsuarioMateria";
    private static final int VERSION = 2;
    private static final String TABELA = "usuario_materia";
    private static final String IDPERFIL = "id_perfil";
    private static final String IDMATERIA = "id_materia";

    public static synchronized BancoHabilidade getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new BancoHabilidade(context.getApplicationContext());
        }
        return sInstance;
    }

    BancoHabilidade(Context context) {
        super(context, NOME_BANCO, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABELA + " (" +
                IDPERFIL + " INTEGER, " +
                IDMATERIA + " INTEGER, " +
                "PRIMARY KEY(" +IDPERFIL +", "+IDMATERIA+"))";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABELA;
        db.execSQL(sql);
        onCreate(db);
    }
}
