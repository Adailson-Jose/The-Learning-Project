package com.thelearningproject.applogin.perfil.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Criado por Pichau on 26/07/2017.
 */

public class ConexaoHabilidade {
    private static ConexaoHabilidade instancia;
    private SQLiteOpenHelper banco;
    private static final String TABELA = "conexaohabilidade";
    private static final String IDPERFIL = "perfil";
    private static final String IDMATERIA = "materia";

    public static synchronized ConexaoHabilidade getInstancia(Context context){
        if(instancia == null){
            instancia = new ConexaoHabilidade(context.getApplicationContext());
        }
        return instancia;
    }

    private ConexaoHabilidade(Context context){
        this.banco = BancoHabilidade.getInstancia(context);
    }

    public boolean verificaTupla(int perfil, int materia){
        Cursor cursor = banco.getReadableDatabase().query(TABELA,new String[]{IDPERFIL},IDPERFIL + " = ? AND " +IDMATERIA+ " = ?",new String[]{String.valueOf(perfil),Integer.toString(materia)},null,null,null);
        boolean resultado = cursor.moveToFirst();
        cursor.close();
        return resultado;
    }

    public void insereConexao(int perfil, int materia){
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(IDPERFIL, perfil);
        values.put(IDMATERIA, materia);
        db.insert(TABELA, null, values);
        db.close();
    }

    public void removerConexao(int perfil, int materia){
        SQLiteDatabase db = banco.getWritableDatabase();
        db.delete(TABELA,IDPERFIL + " = ? AND "+IDMATERIA+ " = ?",new String[]{String.valueOf(perfil),Integer.toString(materia)});
        db.close();
    }

    public ArrayList<Integer> retornaUsuarios(int materia){
        ArrayList<Integer> usuarios = new ArrayList<>();
        Cursor cursor = banco.getReadableDatabase().query(TABELA,new String[]{IDPERFIL},IDMATERIA + " = ?",new String[]{String.valueOf(materia)},null,null,null);
        while (cursor.moveToNext()){
            usuarios.add(cursor.getInt(cursor.getColumnIndex(IDPERFIL)));
        }
        cursor.close();
        return usuarios;
    }

    public ArrayList<Integer> retornaMateria(int perfil){
        ArrayList<Integer> materias = new ArrayList<>();
        Cursor cursor = banco.getReadableDatabase().query(TABELA,new String[]{IDMATERIA},IDPERFIL + " = ?",new String[]{String.valueOf(perfil)},null,null,null);
        while (cursor.moveToNext()){
            materias.add(cursor.getInt(cursor.getColumnIndex(IDMATERIA)));
        }
        cursor.close();
        return materias;
    }

}
