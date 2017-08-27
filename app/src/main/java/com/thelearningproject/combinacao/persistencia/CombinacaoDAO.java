package com.thelearningproject.combinacao.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.thelearningproject.combinacao.dominio.Combinacao;
import com.thelearningproject.infraestrutura.persistencia.Banco;

import java.util.ArrayList;

/**
 * The type Combinacao dao.
 */
public final class CombinacaoDAO {
    private Banco banco;
    private static CombinacaoDAO instancia;

    private static final String TABELA_COMBINACAO = "combinacaoperfil";
    private static final String IDPERFIL1_COMBINACAO = "id_perfil1";
    private static final String IDPERFIL2_COMBINACAO = "id_perfil2";
    private static final String STATUS_COMBINACAO = "status";

    /**
     * Gets instancia.
     *
     * @param contexto the contexto
     * @return the instancia
     */
    public static synchronized CombinacaoDAO getInstancia(Context contexto) {
        if (instancia == null) {
            instancia = new CombinacaoDAO(contexto.getApplicationContext());
        }
        return instancia;
    }

    private CombinacaoDAO(Context contexto) {
        banco = Banco.getInstancia(contexto);
    }

    /**
     * Inserir.
     *
     * @param combinacao the combinacao
     */
    public void inserir(Combinacao combinacao) {
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(IDPERFIL1_COMBINACAO, combinacao.getPerfil1());
        valores.put(IDPERFIL2_COMBINACAO, combinacao.getPerfil2());
        valores.put(STATUS_COMBINACAO, combinacao.getStatus());
        db.insert(TABELA_COMBINACAO, null, valores);
        db.close();
    }

    /**
     * Retorna combinacoes tipo array list.
     *
     * @param perfil the perfil
     * @param tipo   the tipo
     * @return the array list
     */
    public ArrayList<Combinacao> retornaCombinacoesTipo(int perfil, int tipo) {
        String[] colunas = {IDPERFIL1_COMBINACAO, IDPERFIL2_COMBINACAO, STATUS_COMBINACAO};
        Cursor cursor = banco.getReadableDatabase().query(TABELA_COMBINACAO, colunas, IDPERFIL1_COMBINACAO + " = ? ", new String[]{Integer.toString(perfil)}, null, null, null);
        ArrayList<Combinacao> listaCombinacao = new ArrayList<>();
        while (cursor.moveToNext()) {
            if (cursor.getInt(cursor.getColumnIndex(STATUS_COMBINACAO)) == tipo) {
                Combinacao combinacao = new Combinacao();
                combinacao.setPerfil1(cursor.getInt(cursor.getColumnIndex(IDPERFIL1_COMBINACAO)));
                combinacao.setPerfil2(cursor.getInt(cursor.getColumnIndex(IDPERFIL2_COMBINACAO)));
                combinacao.setStatus(cursor.getInt(cursor.getColumnIndex(STATUS_COMBINACAO)));
                listaCombinacao.add(combinacao);
            }
        }
        cursor.close();
        return listaCombinacao;
    }

    /**
     * Retorna combinacoes array list.
     *
     * @param perfil the perfil
     * @return the array list
     */
    public ArrayList<Combinacao> retornaCombinacoes(int perfil) {
        String[] colunas = {IDPERFIL1_COMBINACAO, IDPERFIL2_COMBINACAO, STATUS_COMBINACAO};
        Cursor cursor = banco.getReadableDatabase().query(TABELA_COMBINACAO, colunas, IDPERFIL1_COMBINACAO + " = ? ", new String[]{Integer.toString(perfil)}, null, null, null);
        ArrayList<Combinacao> listaCombinacao = new ArrayList<>();
        while (cursor.moveToNext()) {
            Combinacao combinacao = new Combinacao();
            combinacao.setPerfil1(cursor.getInt(cursor.getColumnIndex(IDPERFIL1_COMBINACAO)));
            combinacao.setPerfil2(cursor.getInt(cursor.getColumnIndex(IDPERFIL2_COMBINACAO)));
            combinacao.setStatus(cursor.getInt(cursor.getColumnIndex(STATUS_COMBINACAO)));
            listaCombinacao.add(combinacao);
        }
        cursor.close();
        return listaCombinacao;
    }

    /**
     * Remove combinacao.
     *
     * @param combinacao the combinacao
     */
    public void removeCombinacao(Combinacao combinacao) {
        SQLiteDatabase db = banco.getWritableDatabase();
        db.delete(TABELA_COMBINACAO, "( " + IDPERFIL1_COMBINACAO + " = ? AND " + IDPERFIL2_COMBINACAO + " = ? )", new String[]{String.valueOf(combinacao.getPerfil1()), String.valueOf(combinacao.getPerfil2())});
        db.close();
    }

    /**
     * Atualiza status.
     *
     * @param combinacao the combinacao
     */
    public void atualizaStatus(Combinacao combinacao) {
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(STATUS_COMBINACAO, combinacao.getStatus());
        db.update(TABELA_COMBINACAO, valores, IDPERFIL1_COMBINACAO + " = ? AND " + IDPERFIL2_COMBINACAO + " = ?", new String[]{String.valueOf(combinacao.getPerfil1()), String.valueOf(combinacao.getPerfil2())});
        db.close();
    }


}
