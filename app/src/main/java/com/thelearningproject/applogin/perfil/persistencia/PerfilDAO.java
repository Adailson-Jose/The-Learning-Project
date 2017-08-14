package com.thelearningproject.applogin.perfil.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.thelearningproject.applogin.infraestrutura.persistencia.Banco;
import com.thelearningproject.applogin.perfil.dominio.Perfil;
import com.thelearningproject.applogin.pessoa.dominio.Pessoa;

/**
 * Criado por Ebony Marques em 26/07/2017.
 */

public final class PerfilDAO {
    private Banco banco;
    private static PerfilDAO instancia;

    private static final String TABELA_PERFIS = "perfis";
    private static final String ID_PERFIL = "id";
    private static final String PESSOA_PERFIL = "pessoa";
    private static final String DESCRICAO_PERFIL = "descricao";

    public static synchronized PerfilDAO getInstancia(Context contexto) {
        if (instancia == null) {
            instancia = new PerfilDAO(contexto.getApplicationContext());
        }
        return instancia;
    }

    private PerfilDAO(Context contexto) {
        banco = Banco.getInstancia(contexto);
    }

    public void inserir(Perfil perfil) {
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(PESSOA_PERFIL, perfil.getPessoa().getId());
        valores.put(DESCRICAO_PERFIL, perfil.getDescricao());

        db.insert(TABELA_PERFIS, null, valores);
        db.close();
    }

    public Perfil retornaPerfil(int idPessoa) {
        String[] colunas = {ID_PERFIL, PESSOA_PERFIL, DESCRICAO_PERFIL};
        Cursor cursor = banco.getReadableDatabase().query(TABELA_PERFIS, colunas, PESSOA_PERFIL + " = ?", new String[]{Integer.toString(idPessoa)}, null, null, null);
        Perfil perfil = null;
        Pessoa pessoa;

        if (cursor.moveToFirst()) {
            perfil = new Perfil();
            pessoa = new Pessoa();

            perfil.setId(cursor.getInt(cursor.getColumnIndex(ID_PERFIL)));
            pessoa.setId(cursor.getInt(cursor.getColumnIndex(PESSOA_PERFIL)));
            perfil.setDescricao(cursor.getString(cursor.getColumnIndex(DESCRICAO_PERFIL)));
            perfil.setPessoa(pessoa);
        }

        cursor.close();
        return perfil;
    }

    public Perfil consultar(int id) {
        String[] colunas = {ID_PERFIL, PESSOA_PERFIL};
        Cursor cursor = banco.getReadableDatabase().query(TABELA_PERFIS, colunas, ID_PERFIL + " = ?", new String[]{Integer.toString(id)}, null, null, null);
        Perfil perfil = null;
        Pessoa pessoa;

        if (cursor.moveToFirst()) {
            perfil = new Perfil();
            pessoa = new Pessoa();
            pessoa.setId(cursor.getInt(cursor.getColumnIndex(PESSOA_PERFIL)));
            perfil.setId(cursor.getInt(cursor.getColumnIndex(ID_PERFIL)));
            perfil.setPessoa(pessoa);
        }

        cursor.close();
        return perfil;
    }

    public void alterarPerfil(Perfil perfil) {
        SQLiteDatabase db = banco.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DESCRICAO_PERFIL, perfil.getDescricao());

        db.update(TABELA_PERFIS, values, ID_PERFIL + " = ?",
                new String[]{String.valueOf(perfil.getId())});
        db.close();
    }
}
