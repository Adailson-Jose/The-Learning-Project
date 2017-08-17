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
 * Criado por Pichau on 26/07/2017.
 */

public final class ConexaoHabilidade {
    private static ConexaoHabilidade instancia;
    private SQLiteOpenHelper banco;

    private static final String TABELA_CONEXAO_HABILIDADES = "conexaohabilidade";
    private static final String IDPERFIL_HABILIDADE = "perfil";
    private static final String IDMATERIA_HABILIDADE = "materia";
    private static final String STATUS_CONEXAO = "status";

    public static synchronized ConexaoHabilidade getInstancia(Context context) {
        if (instancia == null) {
            instancia = new ConexaoHabilidade(context.getApplicationContext());
        }
        return instancia;
    }

    private ConexaoHabilidade(Context context) {
        this.banco = Banco.getInstancia(context);
    }

    public void insereConexao(int perfil, int materia) {
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(IDPERFIL_HABILIDADE, perfil);
        values.put(IDMATERIA_HABILIDADE, materia);
        values.put(STATUS_CONEXAO, Status.ATIVADO.getValor());
        db.insert(TABELA_CONEXAO_HABILIDADES, null, values);
        db.close();
    }

    public void restabeleceConexao(int perfil, int materia) {
        SQLiteDatabase db = banco.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(STATUS_CONEXAO, Status.ATIVADO.getValor());
        db.update(TABELA_CONEXAO_HABILIDADES, values, IDPERFIL_HABILIDADE + " = ? AND " + IDMATERIA_HABILIDADE + " = ?", new String[]{String.valueOf(perfil), String.valueOf(materia)});
        db.close();
    }


    public void desativarConexao(int perfil, int materia) {
        SQLiteDatabase db = banco.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(STATUS_CONEXAO, Status.DESATIVADO.getValor());
        db.update(TABELA_CONEXAO_HABILIDADES, values, IDPERFIL_HABILIDADE + " = ? AND " + IDMATERIA_HABILIDADE + " = ?", new String[]{String.valueOf(perfil), String.valueOf(materia)});
        db.close();
    }

    public ArrayList<Integer> retornaUsuarios(int materia) {
        ArrayList<Integer> usuarios = new ArrayList<>();
        Cursor cursor = banco.getReadableDatabase().query(TABELA_CONEXAO_HABILIDADES, new String[]{IDPERFIL_HABILIDADE, STATUS_CONEXAO}, IDMATERIA_HABILIDADE + " = ?", new String[]{String.valueOf(materia)}, null, null, null);
        while (cursor.moveToNext()) {
            if (cursor.getInt(cursor.getColumnIndex(STATUS_CONEXAO)) == Status.ATIVADO.getValor()) {
                usuarios.add(cursor.getInt(cursor.getColumnIndex(IDPERFIL_HABILIDADE)));
            }
        }
        cursor.close();
        return usuarios;
    }

    public ArrayList<Integer> retornaMateriaAtivas(int perfil) {
        ArrayList<Integer> materias = new ArrayList<>();
        Cursor cursor = banco.getReadableDatabase().query(TABELA_CONEXAO_HABILIDADES, new String[]{IDMATERIA_HABILIDADE, STATUS_CONEXAO}, IDPERFIL_HABILIDADE + " = ?", new String[]{String.valueOf(perfil)}, null, null, null);
        while (cursor.moveToNext()) {
            if (cursor.getInt(cursor.getColumnIndex(STATUS_CONEXAO)) == Status.ATIVADO.getValor()) {
                materias.add(cursor.getInt(cursor.getColumnIndex(IDMATERIA_HABILIDADE)));
            }
        }
        cursor.close();
        return materias;
    }

    public int retornaStatus(int perfil, int materia) {
        int retorno = -1;
        Cursor cursor = banco.getReadableDatabase().query(TABELA_CONEXAO_HABILIDADES, new String[]{STATUS_CONEXAO}, IDPERFIL_HABILIDADE + " = ? AND " + IDMATERIA_HABILIDADE + " = ?", new String[]{String.valueOf(perfil), String.valueOf(materia)}, null, null, null);
        if (cursor.moveToFirst()) {
            retorno = cursor.getInt(cursor.getColumnIndex(STATUS_CONEXAO));
        }
        return retorno;
    }

    public ArrayList<Integer> retornaMaterias(int perfil) {
        ArrayList<Integer> materias = new ArrayList<>();
        Cursor cursor = banco.getReadableDatabase().query(TABELA_CONEXAO_HABILIDADES, new String[]{IDMATERIA_HABILIDADE}, IDPERFIL_HABILIDADE + " = ?", new String[]{String.valueOf(perfil)}, null, null, null);
        while (cursor.moveToNext()) {
            materias.add(cursor.getInt(cursor.getColumnIndex(IDMATERIA_HABILIDADE)));
        }
        cursor.close();
        return materias;
    }

}
