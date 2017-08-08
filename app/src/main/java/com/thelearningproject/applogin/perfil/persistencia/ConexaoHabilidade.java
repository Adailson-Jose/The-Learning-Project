package com.thelearningproject.applogin.perfil.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.thelearningproject.applogin.infraestrutura.persistencia.Banco;

import java.util.ArrayList;

/**
 * Criado por Pichau on 26/07/2017.
 */

public final class ConexaoHabilidade {
    private static ConexaoHabilidade instancia;
    private SQLiteOpenHelper banco;

    private static final String TABELA_CONEXAO_HABILIDADES = "conexaohabilidade";
    private static final String IDPERFIL_HABILIDADE = "perfil";
    private static final String IDMATERIA_HABILIDADE = "materia";

    public static synchronized ConexaoHabilidade getInstancia(Context context){
        if(instancia == null){
            instancia = new ConexaoHabilidade(context.getApplicationContext());
        }
        return instancia;
    }

    private ConexaoHabilidade(Context context){
        this.banco = Banco.getInstancia(context);
    }

    public boolean verificaTupla(int perfil, int materia){
        Cursor cursor = banco.getReadableDatabase().query(TABELA_CONEXAO_HABILIDADES,new String[]{IDPERFIL_HABILIDADE}, IDPERFIL_HABILIDADE + " = ? AND " + IDMATERIA_HABILIDADE + " = ?",new String[]{String.valueOf(perfil),Integer.toString(materia)},null,null,null);
        boolean resultado = cursor.moveToFirst();
        cursor.close();
        return resultado;
    }

    public void insereConexao(int perfil, int materia){
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(IDPERFIL_HABILIDADE, perfil);
        values.put(IDMATERIA_HABILIDADE, materia);
        db.insert(TABELA_CONEXAO_HABILIDADES, null, values);
        db.close();
    }

    public void removerConexao(int perfil, int materia){
        SQLiteDatabase db = banco.getWritableDatabase();
        db.delete(TABELA_CONEXAO_HABILIDADES, IDPERFIL_HABILIDADE + " = ? AND "+ IDMATERIA_HABILIDADE + " = ?",new String[]{String.valueOf(perfil),Integer.toString(materia)});
        db.close();
    }

    public ArrayList<Integer> retornaUsuarios(int materia){
        ArrayList<Integer> usuarios = new ArrayList<>();
        Cursor cursor = banco.getReadableDatabase().query(TABELA_CONEXAO_HABILIDADES,new String[]{IDPERFIL_HABILIDADE}, IDMATERIA_HABILIDADE + " = ?",new String[]{String.valueOf(materia)},null,null,null);
        while (cursor.moveToNext()){
            usuarios.add(cursor.getInt(cursor.getColumnIndex(IDPERFIL_HABILIDADE)));
        }
        cursor.close();
        return usuarios;
    }

    public ArrayList<Integer> retornaMateria(int perfil){
        ArrayList<Integer> materias = new ArrayList<>();
        Cursor cursor = banco.getReadableDatabase().query(TABELA_CONEXAO_HABILIDADES,new String[]{IDMATERIA_HABILIDADE}, IDPERFIL_HABILIDADE + " = ?",new String[]{String.valueOf(perfil)},null,null,null);
        while (cursor.moveToNext()){
            materias.add(cursor.getInt(cursor.getColumnIndex(IDMATERIA_HABILIDADE)));
        }
        cursor.close();
        return materias;
    }

}
