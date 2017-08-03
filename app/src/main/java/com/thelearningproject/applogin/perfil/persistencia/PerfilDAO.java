package com.thelearningproject.applogin.perfil.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.thelearningproject.applogin.perfil.dominio.Perfil;
import com.thelearningproject.applogin.pessoa.dominio.Pessoa;

/**
 * Criado por Ebony Marques em 26/07/2017.
 */

public class PerfilDAO {
    private Banco banco;
    private static PerfilDAO instancia;
    private static final String TABELA = "perfis";

    private static final String ID = "id";
    private static final String PESSOA = "pessoa";
    private static final String DESCRICAO = "descricao";

    public static synchronized PerfilDAO getInstance(Context contexto) {
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
        valores.put(PESSOA, perfil.getPessoa().getId());

        db.insert(TABELA, null, valores);
        db.close();
    }

    public Perfil retornaPerfil(int idPessoa) {
        String[] colunas = {ID, PESSOA};
        Cursor cursor = banco.getReadableDatabase().query(TABELA, colunas, PESSOA + " = ?", new String[]{Integer.toString(idPessoa)}, null, null, null);
        Perfil perfil = null;
        Pessoa pessoa;

        if (cursor.moveToFirst()) {
            perfil = new Perfil();
            pessoa = new Pessoa();
            pessoa.setId(cursor.getInt(cursor.getColumnIndex(PESSOA)));
            perfil.setId(cursor.getInt(cursor.getColumnIndex(ID)));
            perfil.setPessoa(pessoa);
        }

        cursor.close();
        return perfil;
    }

    public Perfil consultar(int id) {
        String[] colunas = {ID, PESSOA};
        Cursor cursor = banco.getReadableDatabase().query(TABELA, colunas, ID + " = ?", new String[]{Integer.toString(id)}, null, null, null);
        Perfil perfil = null;
        Pessoa pessoa;

        if (cursor.moveToFirst()) {
            perfil = new Perfil();
            pessoa = new Pessoa();
            pessoa.setId(cursor.getInt(cursor.getColumnIndex(PESSOA)));
            perfil.setId(cursor.getInt(cursor.getColumnIndex(ID)));
            perfil.setPessoa(pessoa);
        }

        cursor.close();
        return perfil;
    }

    public void alterarPerfil(Perfil perfil){
        SQLiteDatabase db = banco.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DESCRICAO, perfil.getDescricao());

        db.update(TABELA, values, ID + " = ?",
                new String[]{String.valueOf(perfil.getId())});
        db.close();
    }
}
