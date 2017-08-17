package com.thelearningproject.applogin.perfil.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.thelearningproject.applogin.infraestrutura.persistencia.Banco;
import com.thelearningproject.applogin.infraestrutura.utils.Status;

import java.util.ArrayList;

/**
 * Criado por Pichau em 26/07/2017.
 */

public final class ConexaoNecessidade {
    private static ConexaoNecessidade instancia;
    private SQLiteOpenHelper banco;

    private static final String TABELA_CONEXAO_NECESSIDADES = "conexaonecessidade";
    private static final String IDPERFIL_NECESSIDADE = "perfil";
    private static final String IDMATERIA_NECESSIDADE = "materia";
    private static final String STATUS_CONEXAO = "status";

    public static synchronized ConexaoNecessidade getInstancia(Context context) {
        if (instancia == null) {
            instancia = new ConexaoNecessidade(context.getApplicationContext());
        }
        return instancia;
    }

    private ConexaoNecessidade(Context context) {
        this.banco = Banco.getInstancia(context);
    }

    public void insereConexao(int perfil, int materia) {
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(IDPERFIL_NECESSIDADE, perfil);
        values.put(IDMATERIA_NECESSIDADE, materia);
        values.put(STATUS_CONEXAO, Status.ATIVADO.getValor());
        db.insert(TABELA_CONEXAO_NECESSIDADES, null, values);
        db.close();
    }

    public void restabeleceConexao(int perfil, int materia) {
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STATUS_CONEXAO, Status.ATIVADO.getValor());
        db.update(TABELA_CONEXAO_NECESSIDADES, values, IDPERFIL_NECESSIDADE + " = ? AND " + IDMATERIA_NECESSIDADE + " = ?", new String[]{String.valueOf(perfil), String.valueOf(materia)});
        db.close();
    }

    public void desativarConexao(int perfil, int materia) {
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STATUS_CONEXAO, Status.DESATIVADO.getValor());
        db.update(TABELA_CONEXAO_NECESSIDADES, values, IDPERFIL_NECESSIDADE + " = ? AND " + IDMATERIA_NECESSIDADE + " = ?", new String[]{String.valueOf(perfil), String.valueOf(materia)});
        db.close();
    }


    //Retorna todos os usuarios que buscaram a matéria de id = materia
    public ArrayList<Integer> retornaUsuarios(int materia) {
        ArrayList<Integer> usuarios = new ArrayList<>();
        Cursor cursor = banco.getReadableDatabase().query(TABELA_CONEXAO_NECESSIDADES, new String[]{IDPERFIL_NECESSIDADE}, IDMATERIA_NECESSIDADE + " = ?", new String[]{Integer.toString(materia)}, null, null, null);

        while (cursor.moveToNext()) {
            usuarios.add(cursor.getInt(cursor.getColumnIndex(IDPERFIL_NECESSIDADE)));
        }
        cursor.close();
        return usuarios;
    }

    //Retorna todas as materias buscadas pelo usuario de id = id_perfil
    public ArrayList<Integer> retornaMateriaAtivas(int perfil) {
        ArrayList<Integer> materias = new ArrayList<>();
        Cursor cursor = banco.getReadableDatabase().query(TABELA_CONEXAO_NECESSIDADES, new String[]{IDMATERIA_NECESSIDADE, STATUS_CONEXAO}, IDPERFIL_NECESSIDADE + " = ?", new String[]{Integer.toString(perfil)}, null, null, null);

        while (cursor.moveToNext()) {
            if (cursor.getInt(cursor.getColumnIndex(STATUS_CONEXAO)) == Status.ATIVADO.getValor()) {
                materias.add(cursor.getInt(cursor.getColumnIndex(IDMATERIA_NECESSIDADE)));
            }
        }
        cursor.close();
        return materias;
    }

    public int retornaStatus(int perfil, int materia) {
        int retorno = -1;
        Cursor cursor = banco.getReadableDatabase().query(TABELA_CONEXAO_NECESSIDADES, new String[]{STATUS_CONEXAO}, IDPERFIL_NECESSIDADE + " = ? AND " + IDMATERIA_NECESSIDADE + " = ?", new String[]{String.valueOf(perfil), String.valueOf(materia)}, null, null, null);
        if (cursor.moveToFirst()) {
            retorno = cursor.getInt(cursor.getColumnIndex(STATUS_CONEXAO));
        }
        return retorno;
    }

    public ArrayList<Integer> retornaFrequencia(int materia) {
        String subtabela = "SELECT " + IDPERFIL_NECESSIDADE + " FROM " + TABELA_CONEXAO_NECESSIDADES + " WHERE " + IDMATERIA_NECESSIDADE + " = " + materia;
        ArrayList<Integer> materias = new ArrayList<>();

        Cursor cursor = banco.getReadableDatabase().rawQuery(
                "SELECT " + IDMATERIA_NECESSIDADE + ", count(" + IDMATERIA_NECESSIDADE + ")" +
                        " FROM " + TABELA_CONEXAO_NECESSIDADES +
                        " WHERE " + IDPERFIL_NECESSIDADE + " IN (" + subtabela + ")" +
                        " GROUP BY " + IDMATERIA_NECESSIDADE + " ORDER BY count(" + IDMATERIA_NECESSIDADE + ") DESC", null
        );

        while (cursor.moveToNext()) {
            materias.add(cursor.getInt(cursor.getColumnIndex(IDMATERIA_NECESSIDADE)));
        }
        cursor.close();
        return materias;
    }


}
