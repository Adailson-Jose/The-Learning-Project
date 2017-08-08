package com.thelearningproject.applogin.estudo.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.thelearningproject.applogin.estudo.dominio.Materia;
import com.thelearningproject.applogin.infraestrutura.persistencia.Banco;

/**
 * Criado por Nicollas em 25/07/2017.
 */

public final class MateriaDAO {
    private SQLiteOpenHelper banco;
    private static MateriaDAO instancia;

    private static final String TABELA_MATERIAS = "materias";
    private static final String ID_MATERIA = "id";
    private static final String NOME_MATERIA = "nome";

    public static synchronized MateriaDAO getInstancia(Context context){
        if(instancia == null){
            instancia = new MateriaDAO(context.getApplicationContext());
        }
        return instancia;
    }

    private MateriaDAO(Context context) {
        this.banco = Banco.getInstancia(context);
    }

    public void inserir(Materia materia) {
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOME_MATERIA, materia.getNome());
        db.insert(TABELA_MATERIAS, null, values);
        db.close();

    }

    public Materia consultar(int id){
        Cursor cursor = banco.getReadableDatabase().query(TABELA_MATERIAS, null, ID_MATERIA +" = ?", new String[]{Integer.toString(id)},null,null,null);
        Materia materia = null;
        if (cursor.moveToFirst()) {
            materia = new Materia();
            materia.setId(cursor.getInt(cursor.getColumnIndex(ID_MATERIA)));
            materia.setNome(cursor.getString(cursor.getColumnIndex(NOME_MATERIA)));
        }
        cursor.close();
        return materia;
    }

    public Materia consultaNome(String nome){
        Cursor cursor = banco.getReadableDatabase().query(TABELA_MATERIAS, null, "UPPER("+ NOME_MATERIA +") = ?", new String[]{nome.toUpperCase()},null,null,null);
        Materia materia = null;
        if (cursor.moveToFirst()) {
            materia = new Materia();
            materia.setId(cursor.getInt(cursor.getColumnIndex(ID_MATERIA)));
            materia.setNome(cursor.getString(cursor.getColumnIndex(NOME_MATERIA)));
        }
        cursor.close();
        return materia;
    }
}
