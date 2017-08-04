package com.thelearningproject.applogin.estudo.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.thelearningproject.applogin.estudo.dominio.Materia;

/**
 * Criado por Nicollas on 25/07/2017.
 */

public class MateriaDAO {
    private SQLiteOpenHelper banco;
    private static MateriaDAO sInstance;
    private static final String TABELA = "materias";
    private static final String ID = "id";
    private static final String NOME = "nome";


    public static synchronized MateriaDAO getInstance(Context context){
        if(sInstance == null){
            sInstance = new MateriaDAO(context.getApplicationContext());
        }
        return sInstance;
    }

    private MateriaDAO(Context context) {
        this.banco = Banco.getInstance(context);
    }

    public void inserir(Materia materia) {
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOME, materia.getNome());
        db.insert(TABELA, null, values);
        db.close();

    }

    public Materia consultar(int id){
        Cursor cursor = banco.getReadableDatabase().query(TABELA, null, ID+" = ?", new String[]{Integer.toString(id)},null,null,null);
        Materia materia = null;
        if (cursor.moveToFirst()) {
            materia = new Materia();
            materia.setId(cursor.getInt(cursor.getColumnIndex(ID)));
            materia.setNome(cursor.getString(cursor.getColumnIndex(NOME)));
        }
        cursor.close();
        return materia;
    }

    public Materia consultaNome(String nome){
        Cursor cursor = banco.getReadableDatabase().query(TABELA, null, NOME+" = ?", new String[]{nome},null,null,null);
        Materia materia = null;
        if (cursor.moveToFirst()) {
            materia = new Materia();
            materia.setId(cursor.getInt(cursor.getColumnIndex(ID)));
            materia.setNome(cursor.getString(cursor.getColumnIndex(NOME)));
        }
        cursor.close();
        return materia;
    }
}
