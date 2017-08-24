package com.thelearningproject.applogin.infraestrutura.utils;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.thelearningproject.applogin.estudo.persistencia.MateriaDAO;

import java.util.HashMap;
import java.util.Map;

/**
 * Creado by Pichau on 22/08/2017.
 */

public class Provider extends ContentProvider {

    private MateriaDAO materiaDAO;
/*    private static final String AUTHORITY = "com.thelearningproject.applogin.infraestrutura.utils.Provider";
    private static final Uri CONTENT_URI = Uri.parse("content://com.thelearningproject.applogin.infraestrutura.utils.Provider/materias");
    private static final int MATERIAS = 1;
    private static final int MATERIAS_ID = 2;

    private static UriMatcher uriMaterias;

    static {
        uriMaterias = new UriMatcher(UriMatcher.NO_MATCH);

        //content://com.thelearningproject.applogin.infraestrutura.utils.Provider/materias
        uriMaterias.addURI(AUTHORITY, "materias/", MATERIAS);
        //content://com.thelearningproject.applogin.infraestrutura.utils.Provider/materias/2
        uriMaterias.addURI(AUTHORITY, "materias/#", MATERIAS_ID);
    }*/

    @Override
    public boolean onCreate() {
        materiaDAO = MateriaDAO.getInstancia(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor = null;
        if (selectionArgs != null && selectionArgs.length > 0 && selectionArgs[0].length() > 0) {
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables("materias");
            Map<String, String> projectionMap = new HashMap<>();
            projectionMap.put("nome", "nome AS " + SearchManager.SUGGEST_COLUMN_TEXT_1);
            projectionMap.put("id", "id AS " + BaseColumns._ID);
            projectionMap.put("value_data", "nome AS " + SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID);
            queryBuilder.setProjectionMap(projectionMap);
            selectionArgs[0] = "%" + selectionArgs[0] + "%";
            cursor = materiaDAO.queryCursorLista(queryBuilder, projection, selection, selectionArgs, sortOrder);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
