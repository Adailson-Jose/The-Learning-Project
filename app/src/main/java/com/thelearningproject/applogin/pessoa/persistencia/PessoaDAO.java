package com.thelearningproject.applogin.pessoa.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.thelearningproject.applogin.pessoa.dominio.Pessoa;
import com.thelearningproject.applogin.usuario.dominio.Usuario;

/**
 * Created by nicolas on 30/07/2017.
 */

public class PessoaDAO {
    private static Banco banco;
    private static PessoaDAO instancia;
    private static final String TABELA = "pessoas";

    private static final String ID = "id";
    private static final String NOME = "nome";
    private static final String USUARIO = "usuario";

    public static synchronized PessoaDAO getInstance(Context contexto) {
        if (instancia == null) {
            instancia = new PessoaDAO(contexto.getApplicationContext());
        }
        return instancia;
    }

    public PessoaDAO(Context contexto) {
        banco = Banco.getInstancia(contexto);
    }

    public void inserir(Pessoa pessoa) {
        ContentValues valores = new ContentValues();
        valores.put(NOME, pessoa.getNome());
        valores.put(USUARIO, pessoa.getUsuario().getId());

        banco.getWritableDatabase().insert(TABELA, null, valores);
    }

    public Pessoa retornaPessoa(int idUsuario) {
        String[] colunas = {ID, NOME, USUARIO};
        Cursor cursor = banco.getReadableDatabase().query(TABELA, colunas, USUARIO + " = ?", new String[]{Integer.toString(idUsuario)}, null, null, null);
        Pessoa pessoa = null;
        Usuario usuario = null;

        if (cursor.moveToFirst()) {
            pessoa = new Pessoa();
            usuario = new Usuario();
            usuario.setId(cursor.getInt(cursor.getColumnIndex(USUARIO)));
            pessoa.setId(cursor.getInt(cursor.getColumnIndex(ID)));
            pessoa.setNome(cursor.getString(cursor.getColumnIndex(NOME)));
            pessoa.setUsuario(usuario);
        }

        return pessoa;
    }

    public Pessoa consultar(int id) {
        String[] colunas = {ID, NOME, USUARIO};
        Cursor cursor = banco.getReadableDatabase().query(TABELA, colunas, ID + " = ?", new String[]{Integer.toString(id)}, null, null, null);
        Pessoa pessoa = null;
        Usuario usuario = null;
        if (cursor.moveToFirst()) {
            pessoa = new Pessoa();
            usuario = new Usuario();
            usuario.setId(cursor.getInt(cursor.getColumnIndex(USUARIO)));
            pessoa.setId(cursor.getInt(cursor.getColumnIndex(ID)));
            pessoa.setNome(cursor.getString(cursor.getColumnIndex(NOME)));
            pessoa.setUsuario(usuario);
        }

        return pessoa;
    }

    public void alterarPessoa(Pessoa pessoa){
        SQLiteDatabase db = banco.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NOME, pessoa.getNome());

        db.update(TABELA, values, ID + " = ?",
                new String[]{String.valueOf(pessoa.getId())});
        db.close();
    }
}

