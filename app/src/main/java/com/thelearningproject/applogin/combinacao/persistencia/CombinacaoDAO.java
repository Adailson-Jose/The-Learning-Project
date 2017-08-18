package com.thelearningproject.applogin.combinacao.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.thelearningproject.applogin.combinacao.dominio.Combinacao;
import com.thelearningproject.applogin.infraestrutura.persistencia.Banco;

import java.util.ArrayList;

/**
 * Created by Pichau on 17/08/2017.
 */

public class CombinacaoDAO {
    private Banco banco;
    private static CombinacaoDAO instancia;

    private static final String TABELA_COMBINACAO = "combinacaoperfil";
    private static final String IDPERFIL1_COMBINACAO = "id_perfil1";
    private static final String IDPERFIL2_COMBINACAO = "id_perfil2";
    private static final String STATUS_COMBINACAO = "status";

    public static synchronized CombinacaoDAO getInstancia(Context contexto) {
        if (instancia == null) {
            instancia = new CombinacaoDAO(contexto.getApplicationContext());
        }
        return instancia;
    }

    private CombinacaoDAO(Context contexto) {
        banco = Banco.getInstancia(contexto);
    }

    public void inserir(Combinacao combinacao) {
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(IDPERFIL1_COMBINACAO, combinacao.getPerfil1());
        valores.put(IDPERFIL2_COMBINACAO, combinacao.getPerfil2());
        valores.put(STATUS_COMBINACAO, combinacao.getStatus());
        db.insert(TABELA_COMBINACAO, null, valores);
        db.close();
    }

    public ArrayList<Combinacao> retornaCombinacoes(int perfil, int tipo) {
        String[] colunas = {IDPERFIL1_COMBINACAO, IDPERFIL2_COMBINACAO, STATUS_COMBINACAO};
        Cursor cursor = banco.getReadableDatabase().query(TABELA_COMBINACAO, colunas, IDPERFIL1_COMBINACAO + " = ?", new String[]{Integer.toString(perfil)}, null, null, null);
        ArrayList<Combinacao> listaCombinacao = new ArrayList<>();
        while (cursor.moveToNext()) {
            if (cursor.getInt(cursor.getColumnIndex(STATUS_COMBINACAO)) == tipo) {
                Combinacao combinacao = new Combinacao();
                combinacao.setPerfil1(perfil);
                combinacao.setPerfil2(cursor.getInt(cursor.getColumnIndex(IDPERFIL2_COMBINACAO)));
                combinacao.setStatus(cursor.getInt(cursor.getColumnIndex(STATUS_COMBINACAO)));
                listaCombinacao.add(combinacao);
            }
        }
        cursor.close();
        return listaCombinacao;
    }

    public void removeCombinacao(Combinacao combinacao) {
        SQLiteDatabase db = banco.getWritableDatabase();
        db.delete(TABELA_COMBINACAO, IDPERFIL1_COMBINACAO + " = ? AND " + IDPERFIL2_COMBINACAO + " = ?", new String[]{String.valueOf(combinacao.getPerfil1()), String.valueOf(combinacao.getPerfil2())});
        db.close();
    }

    public void atualizaStatus(Combinacao combinacao) {
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STATUS_COMBINACAO, combinacao.getStatus());
        db.update(TABELA_COMBINACAO, values, IDPERFIL1_COMBINACAO + " = ? AND " + IDPERFIL2_COMBINACAO + " = ?", new String[]{String.valueOf(combinacao.getPerfil1()), String.valueOf(combinacao.getPerfil2())});
        db.close();
    }


}
