package com.thelearningproject.applogin.perfil.persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Criado por Pichau on 26/07/2017.
 */

public class BancoHabilidade extends SQLiteOpenHelper{
    private static BancoHabilidade instancia;
    private static final String NOME_BANCO = "RelacaoUsuarioMateria";
    private static final int VERSION = 2;
    private static final String TABELA = "usuario_materia";
    private static final String IDPERFIL = "id_perfil";
    private static final String IDMATERIA = "id_materia";

    public static synchronized BancoHabilidade getInstancia(Context context) {
        if (instancia == null) {
            instancia = new BancoHabilidade(context.getApplicationContext());
        }
        return instancia;
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
