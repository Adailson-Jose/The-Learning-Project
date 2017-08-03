package com.thelearningproject.applogin.perfil.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Criado por Pichau on 26/07/2017.
 */

public class ConexaoNecessidade {
    private static ConexaoNecessidade sInstance;
    private SQLiteOpenHelper banco;
    private static final String TABELA = "usuario_materia";
    private static final String IDPERFIL = "id_perfil";
    private static final String IDMATERIA = "id_materia";

    public static synchronized ConexaoNecessidade getInstancia(Context context){
        if(sInstance == null){
            sInstance = new ConexaoNecessidade(context.getApplicationContext());
        }
        return sInstance;
    }

    public ConexaoNecessidade(Context context){
        this.banco = BancoNecessidade.getInstancia(context);
    }

    public void insereConexao(int id_perfil, int id_materia){
        ContentValues values = new ContentValues();
        values.put(IDPERFIL, id_perfil);
        values.put(IDMATERIA, id_materia);
        banco.getWritableDatabase().insert(TABELA, null, values);
    }

    public boolean verificatupla(int id_perfil, int id_materia){
        Cursor cursor = banco.getReadableDatabase().query(TABELA,new String[]{IDPERFIL},IDPERFIL + " = ? AND " +IDMATERIA+ " = ?",new String[]{Integer.toString(id_perfil),Integer.toString(id_materia)},null,null,null);
        boolean resultado = cursor.moveToFirst();
        cursor.close();
        return resultado;
    }

    //Retorna todos os usuarios que buscaram a matéria de id = id_materia
    public ArrayList<Integer> retornaUsuarios(int id_materia){
        ArrayList<Integer> usuarios = new ArrayList<>();
        Cursor cursor = banco.getReadableDatabase().query(TABELA,new String[]{IDPERFIL},IDMATERIA+" = ?",new String[]{Integer.toString(id_materia)},null,null,null );

        while(cursor.moveToNext()){
            usuarios.add(cursor.getColumnIndex(IDPERFIL));
        }
        cursor.close();
        return usuarios;
    }

    //Retorna todas as materias buscadas pelo usuario de id = id_perfil
    public ArrayList<Integer> retornaMaterias(int id_perfil){
        ArrayList<Integer> materias = new ArrayList<>();
        Cursor cursor = banco.getReadableDatabase().query(TABELA,new String[]{IDMATERIA},IDPERFIL+" = ?",new String[]{Integer.toString(id_perfil)},null,null,null );

        while(cursor.moveToNext()){
            materias.add(cursor.getColumnIndex(IDPERFIL));
        }
        cursor.close();
        return materias;
    }

    public ArrayList<Integer> retornaFrequencia(int id_materia){
        String subtabela = "SELECT " + IDPERFIL+ " FROM "+ TABELA + " WHERE " + IDMATERIA + " = " + id_materia;
        ArrayList<Integer> usuarios = new ArrayList<>();

        Cursor cursor = banco.getReadableDatabase().rawQuery(
                "SELECT " +IDMATERIA+ ", count(" +IDMATERIA+ ")" +
                " FROM " +TABELA+
                " WHERE " +IDPERFIL+ " IN ("+subtabela+")" +
                " GROUP BY " +IDMATERIA+" ORDER BY count("+IDMATERIA+") DESC", null
        );

        while(cursor.moveToNext()){
            usuarios.add(cursor.getColumnIndex(IDPERFIL));
        }
        cursor.close();
        return usuarios;
    }


}
