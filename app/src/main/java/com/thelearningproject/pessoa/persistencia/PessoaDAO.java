package com.thelearningproject.pessoa.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.thelearningproject.infraestrutura.persistencia.Banco;
import com.thelearningproject.pessoa.dominio.Pessoa;
import com.thelearningproject.usuario.dominio.Usuario;

/**
 * Criado por Nícolas em 30/07/2017.
 */

public final class PessoaDAO {
    private Banco banco;
    private static PessoaDAO instancia;

    private static final String TABELA_PESSOAS = "pessoas";
    private static final String ID_PESSOA = "id";
    private static final String NOME_PESSOA = "nome";
    private static final String USUARIO_PESSOA = "usuario";
    private static final String TELEFONE_PESSOA = "telefone";

    public static synchronized PessoaDAO getInstancia(Context contexto) {
        if (instancia == null) {
            instancia = new PessoaDAO(contexto.getApplicationContext());
        }
        return instancia;
    }

    private PessoaDAO(Context contexto) {
        banco = Banco.getInstancia(contexto);
    }

    public void inserir(Pessoa pessoa) {
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(NOME_PESSOA, pessoa.getNome());
        valores.put(USUARIO_PESSOA, pessoa.getUsuario().getId());
        valores.put(TELEFONE_PESSOA, pessoa.getTelefone());

        db.insert(TABELA_PESSOAS, null, valores);
        db.close();
    }

    public Pessoa retornaPessoa(int idUsuario) {
        String[] colunas = {ID_PESSOA, NOME_PESSOA, USUARIO_PESSOA, TELEFONE_PESSOA};
        Cursor cursor = banco.getReadableDatabase().query(TABELA_PESSOAS, colunas, USUARIO_PESSOA + " = ?", new String[]{Integer.toString(idUsuario)}, null, null, null);
        Pessoa pessoa = null;
        Usuario usuario;

        if (cursor.moveToFirst()) {
            pessoa = new Pessoa();
            usuario = new Usuario();
            usuario.setId(cursor.getInt(cursor.getColumnIndex(USUARIO_PESSOA)));
            pessoa.setId(cursor.getInt(cursor.getColumnIndex(ID_PESSOA)));
            pessoa.setNome(cursor.getString(cursor.getColumnIndex(NOME_PESSOA)));
            pessoa.setTelefone(cursor.getString(cursor.getColumnIndex(TELEFONE_PESSOA)));
            pessoa.setUsuario(usuario);
        }

        cursor.close();
        return pessoa;
    }

    public Pessoa consultar(int id) {
        String[] colunas = {ID_PESSOA, NOME_PESSOA, USUARIO_PESSOA, TELEFONE_PESSOA};
        Cursor cursor = banco.getReadableDatabase().query(TABELA_PESSOAS, colunas, ID_PESSOA + " = ?", new String[]{Integer.toString(id)}, null, null, null);
        Pessoa pessoa = null;
        Usuario usuario;
        if (cursor.moveToFirst()) {
            pessoa = new Pessoa();
            usuario = new Usuario();
            usuario.setId(cursor.getInt(cursor.getColumnIndex(USUARIO_PESSOA)));
            pessoa.setId(cursor.getInt(cursor.getColumnIndex(ID_PESSOA)));
            pessoa.setNome(cursor.getString(cursor.getColumnIndex(NOME_PESSOA)));
            pessoa.setTelefone(cursor.getString(cursor.getColumnIndex(TELEFONE_PESSOA)));
            pessoa.setUsuario(usuario);
        }

        cursor.close();
        return pessoa;
    }

    public Pessoa retornaPessoa(String telefone) {
        String[] colunas = {ID_PESSOA, NOME_PESSOA, USUARIO_PESSOA, TELEFONE_PESSOA};
        Cursor cursor = banco.getReadableDatabase().query(TABELA_PESSOAS, colunas, TELEFONE_PESSOA + " = ?", new String[]{telefone}, null, null, null);
        Pessoa pessoa = null;
        Usuario usuario;

        if (cursor.moveToFirst()) {
            pessoa = new Pessoa();
            usuario = new Usuario();
            usuario.setId(cursor.getInt(cursor.getColumnIndex(USUARIO_PESSOA)));
            pessoa.setId(cursor.getInt(cursor.getColumnIndex(ID_PESSOA)));
            pessoa.setNome(cursor.getString(cursor.getColumnIndex(NOME_PESSOA)));
            pessoa.setTelefone(cursor.getString(cursor.getColumnIndex(TELEFONE_PESSOA)));
            pessoa.setUsuario(usuario);
        }

        cursor.close();
        return pessoa;
    }

    public void alterarPessoa(Pessoa pessoa) {
        SQLiteDatabase db = banco.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NOME_PESSOA, pessoa.getNome());
        values.put(TELEFONE_PESSOA, pessoa.getTelefone());

        db.update(TABELA_PESSOAS, values, ID_PESSOA + " = ?",
                new String[]{String.valueOf(pessoa.getId())});
        db.close();
    }
}

