package com.thelearningproject.estudo.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import com.thelearningproject.estudo.dominio.Materia;
import com.thelearningproject.infraestrutura.persistencia.Banco;

import java.util.ArrayList;

/**
 * The type Materia dao.
 */
public final class MateriaDAO {
    private SQLiteOpenHelper banco;
    private static MateriaDAO instancia;

    private static final String TABELA_MATERIAS = "materias";
    private static final String ID_MATERIA = "id";
    private static final String NOME_MATERIA = "nome";

    /**
     * Gets instancia.
     *
     * @param context the context
     * @return the instancia
     */
    public static synchronized MateriaDAO getInstancia(Context context) {
        if (instancia == null) {
            instancia = new MateriaDAO(context.getApplicationContext());
        }
        return instancia;
    }

    private MateriaDAO(Context context) {
        this.banco = Banco.getInstancia(context);
    }

    /**
     * Inserir.
     *
     * @param materia the materia
     */
    public void inserir(Materia materia) {
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOME_MATERIA, materia.getNome());
        db.insert(TABELA_MATERIAS, null, values);
        db.close();

    }

    /**
     * Consultar materia.
     *
     * @param id the id
     * @return the materia
     */
    public Materia consultar(int id) {
        Cursor cursor = banco.getReadableDatabase().query(TABELA_MATERIAS, null, ID_MATERIA + " = ?", new String[]{Integer.toString(id)}, null, null, null);
        Materia materia = null;
        if (cursor.moveToFirst()) {
            materia = new Materia();
            materia.setId(cursor.getInt(cursor.getColumnIndex(ID_MATERIA)));
            materia.setNome(cursor.getString(cursor.getColumnIndex(NOME_MATERIA)));
        }
        cursor.close();
        return materia;
    }

    /**
     * Consulta nome materia.
     *
     * @param nome the nome
     * @return the materia
     */
    public Materia consultaNome(String nome) {
        Cursor cursor = banco.getReadableDatabase().query(TABELA_MATERIAS, null, "UPPER(" + NOME_MATERIA + ") = ?", new String[]{nome.toUpperCase()}, null, null, null);
        Materia materia = null;
        if (cursor.moveToFirst()) {
            materia = new Materia();
            materia.setId(cursor.getInt(cursor.getColumnIndex(ID_MATERIA)));
            materia.setNome(cursor.getString(cursor.getColumnIndex(NOME_MATERIA)));
        }
        cursor.close();
        return materia;
    }

    /**
     * Retorna materias nome array list.
     *
     * @param nome the nome
     * @return the array list
     */
    public ArrayList<String> retornaMateriasNome(String nome) {
        Cursor cursor = banco.getReadableDatabase().query(TABELA_MATERIAS, new String[]{NOME_MATERIA}, "UPPER(" + NOME_MATERIA + ") LIKE ?", new String[]{"%" + nome.toUpperCase() + "%"}, null, null, null);
        ArrayList<String> listaMateria = new ArrayList<>();
        while (cursor.moveToNext()) {
            listaMateria.add(cursor.getString(cursor.getColumnIndex(NOME_MATERIA)));
        }
        cursor.close();
        return listaMateria;
    }

    /**
     * Retorna todas materias array list.
     *
     * @return the array list
     */
    public ArrayList<Materia> retornaTodasMaterias() {
        Cursor cursor = banco.getReadableDatabase().query(TABELA_MATERIAS, null, null, null, null, null, ID_MATERIA + " ASC");
        ArrayList<Materia> listaMateria = new ArrayList<>();
        while (cursor.moveToNext()) {
            Materia m = new Materia();
            m.setId(cursor.getInt(cursor.getColumnIndex(ID_MATERIA)));
            m.setNome(cursor.getString(cursor.getColumnIndex(NOME_MATERIA)));
            listaMateria.add(m);
        }
        cursor.close();
        return listaMateria;
    }

    /**
     * Query cursor lista cursor.
     *
     * @param queryBuilder  the query builder
     * @param projection    the projection
     * @param selecion      the selecion
     * @param selectionArgs the selection args
     * @param orderBy       the order by
     * @return the cursor
     */
//Metodo retorna um cursor, objeto da persistencia pois o searchview exige um CursorAdapter, para utilizar a tabela pela persistencia,
    // passamos o cursor para a classe Provide, e ela os utiliza para criar as sugestões do SearchView
    public Cursor queryCursorLista(SQLiteQueryBuilder queryBuilder, String[] projection, String selecion, String[] selectionArgs, String orderBy) {

        return queryBuilder.query(banco.getReadableDatabase(), projection, selecion, selectionArgs, null, null, orderBy);
    }

}
