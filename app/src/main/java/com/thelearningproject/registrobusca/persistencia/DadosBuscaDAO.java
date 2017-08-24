package com.thelearningproject.registrobusca.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.thelearningproject.infraestrutura.persistencia.Banco;

import java.util.ArrayList;

/**
 * Criado por Pichau em 02/08/2017.
 */

public final class DadosBuscaDAO {
    private static DadosBuscaDAO instancia;
    private SQLiteOpenHelper banco;

    private static final String TABELA_DADOS_BUSCA = "dadosbusca";
    private static final String IDPERFIL_BUSCA = "perfil";
    private static final String MATERIA_BUSCA = "materia";

    public static synchronized DadosBuscaDAO getInstancia(Context context) {
        if (instancia == null) {
            instancia = new DadosBuscaDAO(context.getApplicationContext());
        }
        return instancia;
    }

    private DadosBuscaDAO(Context context) {
        this.banco = Banco.getInstancia(context);
    }

    public void insereBusca(int perfil, String materia) {
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(IDPERFIL_BUSCA, perfil);
        values.put(MATERIA_BUSCA, materia);
        db.insert(TABELA_DADOS_BUSCA, null, values);
        db.close();
    }

    public boolean verificaExistencia(int perfil, String materia) {
        Cursor cursor = banco.getReadableDatabase().query(TABELA_DADOS_BUSCA, new String[]{IDPERFIL_BUSCA}, IDPERFIL_BUSCA + " = ? AND " + MATERIA_BUSCA + " = ?", new String[]{String.valueOf(perfil), materia}, null, null, null);
        boolean resultado = cursor.moveToFirst();
        cursor.close();
        return resultado;
    }

    public ArrayList<String> retornaFrequencia(int perfil, String entrada) {
        String subtabela = "SELECT " + IDPERFIL_BUSCA + " FROM " + TABELA_DADOS_BUSCA + " WHERE " + MATERIA_BUSCA + " = ? AND NOT " + IDPERFIL_BUSCA + " = ?";
        ArrayList<String> usuarios = new ArrayList<>();

        Cursor cursor = banco.getReadableDatabase().rawQuery(
                "SELECT " + MATERIA_BUSCA + ", count(" + MATERIA_BUSCA + ")" +
                        " FROM " + TABELA_DADOS_BUSCA +
                        " WHERE " + IDPERFIL_BUSCA + " IN (" + subtabela + ")" +
                        " GROUP BY " + MATERIA_BUSCA + " ORDER BY count(" + MATERIA_BUSCA + ") DESC", new String[]{entrada, Integer.toString(perfil)}
        );

        while (cursor.moveToNext()) {
            usuarios.add(cursor.getString(cursor.getColumnIndex(MATERIA_BUSCA)));
        }
        cursor.close();
        return usuarios;
    }

}
