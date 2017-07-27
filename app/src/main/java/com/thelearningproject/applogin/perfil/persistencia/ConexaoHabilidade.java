package com.thelearningproject.applogin.perfil.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Pichau on 26/07/2017.
 */

public class ConexaoHabilidade {
    private static ConexaoHabilidade sInstance;
    private SQLiteOpenHelper banco;
    private static final String TABELA = "usuario_materia";
    private static final String IDPERFIL = "id_perfil";
    private static final String IDMATERIA = "id_materia";

    public static synchronized ConexaoHabilidade getInstance(Context context){
        if(sInstance == null){
            sInstance = new ConexaoHabilidade(context.getApplicationContext());
        }
        return sInstance;
    }

    public ConexaoHabilidade(Context context){
        this.banco = BancoHabilidade.getInstance(context);
    }

    public boolean verificatupla(int id_perfil, int id_materia){
        Cursor cursor = banco.getReadableDatabase().query(TABELA,new String[]{IDPERFIL},IDPERFIL + " = ? AND " +IDMATERIA+ " = ?",new String[]{String.valueOf(id_perfil),Integer.toString(id_materia)},null,null,null);
        return cursor.moveToFirst();
    }

    public void insereConexao(int id_perfil, int id_materia){
        ContentValues values = new ContentValues();
        values.put(IDPERFIL, id_perfil);
        values.put(IDMATERIA, id_materia);
        banco.getWritableDatabase().insert(TABELA, null, values);
    }

    public ArrayList<Integer> retornaUsuarios(int id_materia){
        ArrayList<Integer> usuarios = new ArrayList<Integer>();
        Cursor cursor = banco.getReadableDatabase().query(TABELA,new String[]{IDPERFIL},IDMATERIA + " = ?",new String[]{String.valueOf(id_materia)},null,null,null);
        while (cursor.moveToNext()){
            usuarios.add(cursor.getInt(cursor.getColumnIndex(IDPERFIL)));
        }
        return usuarios;
    }

    public ArrayList<Integer> retornaMateria(int id_perfil){
        ArrayList<Integer> materias = new ArrayList<Integer>();
        Cursor cursor = banco.getReadableDatabase().query(TABELA,new String[]{IDMATERIA},IDPERFIL + " = ?",new String[]{String.valueOf(id_perfil)},null,null,null);
        while (cursor.moveToNext()){
            materias.add(cursor.getInt(cursor.getColumnIndex(IDMATERIA)));
        }
        return materias;
    }

}
