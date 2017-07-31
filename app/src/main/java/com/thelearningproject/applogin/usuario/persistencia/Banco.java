package com.thelearningproject.applogin.usuario.persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ebony Marques on 17/07/2017.
 */

public class Banco extends SQLiteOpenHelper {

    private static Banco instancia;

    private static final String NOME_BANCO = "BDUsuario";
    private static final int VERSION = 11;
    private static final String TABELA = "usuarios";
    private static final String ID = "id";
    private static final String EMAIL = "email";
    private static final String SENHA = "senha";
    private static final String STATUS = "status";



    public static synchronized Banco getInstance(Context context){
        if(instancia == null){
            instancia = new Banco(context.getApplicationContext());
        }
        return instancia;
    }

    public Banco(Context context) {
        super(context, NOME_BANCO, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABELA + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EMAIL + " TEXT, " +
                SENHA + " TEXT, " +
                STATUS + " INTEGER)";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABELA;
        db.execSQL(sql);
        onCreate(db);
    }


}
