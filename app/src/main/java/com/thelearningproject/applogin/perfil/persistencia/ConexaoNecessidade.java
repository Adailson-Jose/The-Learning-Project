package com.thelearningproject.applogin.perfil.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;

/**
 * Created by Pichau on 26/07/2017.
 */

public class ConexaoNecessidade {
    private static ConexaoNecessidade sInstance;
    private SQLiteOpenHelper banco;
    private static final String TABELA = "conexaonecessidade";
    private static final String IDPERFIL = "perfil";
    private static final String IDMATERIA = "materia";

    public static synchronized ConexaoNecessidade getInstancia(Context context){
        if(sInstance == null){
            sInstance = new ConexaoNecessidade(context.getApplicationContext());
        }
        return sInstance;
    }

    private ConexaoNecessidade(Context context){
        this.banco = BancoNecessidade.getInstancia(context);
    }

    public void insereConexao(int perfil, int materia){
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(IDPERFIL, perfil);
        values.put(IDMATERIA, materia);
        db.insert(TABELA, null, values);
        db.close();
    }

    private void updateConexao(int perfil, int materia){
        SQLiteDatabase db = banco.getWritableDatabase();
        if (verificatupla(perfil, materia)){
            ContentValues values = new ContentValues();
            values.put(IDPERFIL, perfil);
            values.put(IDMATERIA, materia);
            db.update(TABELA,values,IDPERFIL + " = ? AND " +IDMATERIA+ " = ?",
                    new String[]{String.valueOf(perfil),Integer.toString(materia)});
            db.close();
        }
    }

    public void removerConexao(int perfil, int materia){
        SQLiteDatabase db = banco.getWritableDatabase();
        db.delete(TABELA,IDPERFIL + " = ? AND "+IDMATERIA+ " = ?",new String[]{String.valueOf(perfil),Integer.toString(materia)});
        db.close();
    }

    public boolean verificatupla(int perfil, int materia){
        Cursor cursor = banco.getReadableDatabase().query(TABELA,new String[]{IDPERFIL},IDPERFIL + " = ? AND " +IDMATERIA+ " = ?",new String[]{Integer.toString(perfil),Integer.toString(materia)},null,null,null);
        boolean resultado = cursor.moveToFirst();
        cursor.close();
        return resultado;
    }

    //Retorna todos os usuarios que buscaram a matéria de id = materia
    public ArrayList<Integer> retornaUsuarios(int materia){
        ArrayList<Integer> usuarios = new ArrayList<>();
        Cursor cursor = banco.getReadableDatabase().query(TABELA,new String[]{IDPERFIL},IDMATERIA+" = ?",new String[]{Integer.toString(materia)},null,null,null );

        while(cursor.moveToNext()){
            usuarios.add(cursor.getInt(cursor.getColumnIndex(IDPERFIL)));
        }
        cursor.close();
        return usuarios;
    }

    //Retorna todas as materias buscadas pelo usuario de id = id_perfil
    public ArrayList<Integer> retornaMateria(int perfil){
        ArrayList<Integer> materias = new ArrayList<>();
        Cursor cursor = banco.getReadableDatabase().query(TABELA,new String[]{IDMATERIA},IDPERFIL+" = ?",new String[]{Integer.toString(perfil)},null,null,null );

        while(cursor.moveToNext()){
            materias.add(cursor.getInt(cursor.getColumnIndex(IDMATERIA)));
        }
        cursor.close();
        return materias;
    }

    public ArrayList<Integer> retornaFrequencia(int materia){
        String subtabela = "SELECT " + IDPERFIL+ " FROM "+ TABELA + " WHERE " + IDMATERIA + " = " + materia;
        ArrayList<Integer> usuarios = new ArrayList<>();

        Cursor cursor = banco.getReadableDatabase().rawQuery(
                "SELECT " +IDMATERIA+ ", count(" +IDMATERIA+ ")" +
                " FROM " +TABELA+
                " WHERE " +IDPERFIL+ " IN ("+subtabela+")" +
                " GROUP BY " +IDMATERIA+" ORDER BY count("+IDMATERIA+") DESC", null
        );

        while(cursor.moveToNext()){
            usuarios.add(cursor.getInt(cursor.getColumnIndex(IDPERFIL)));
        }
        cursor.close();
        return usuarios;
    }


}
