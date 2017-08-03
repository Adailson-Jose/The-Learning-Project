package com.thelearningproject.applogin.registrobusca.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Pichau on 02/08/2017.
 */

public class DadosBuscaDAO {

    private static DadosBuscaDAO instancia;
    private SQLiteOpenHelper banco;
    private static final String TABELA = "dadosbusca";
    private static final String IDPERFIL = "perfil";
    private static final String MATERIA = "materia";

    public static synchronized DadosBuscaDAO getInstancia(Context context){
        if(instancia == null){
            instancia = new DadosBuscaDAO(context.getApplicationContext());
        }
        return instancia;
    }

    private DadosBuscaDAO(Context context){
        this.banco = Banco.getInstancia(context);
    }

    public void insereBusca(int perfil, String materia){
        ContentValues values = new ContentValues();
        values.put(IDPERFIL, perfil);
        values.put(MATERIA, materia);
        banco.getWritableDatabase().insert(TABELA, null, values);
    }

    public boolean verificaExistencia(int perfil, String materia){
        Cursor cursor = banco.getReadableDatabase().query(TABELA,new String[]{IDPERFIL},IDPERFIL + " = ? AND " +MATERIA+ " = ?",new String[]{String.valueOf(perfil),materia},null,null,null);
        boolean resultado = cursor.moveToFirst();
        cursor.close();
        return resultado;
    }

    public ArrayList<String> retornaFrequencia(int perfil, String entrada){
        String subtabela = "SELECT " + IDPERFIL+ " FROM "+ TABELA + " WHERE "+ MATERIA + " = ? AND NOT "+IDPERFIL+" = ?";
        ArrayList<String> usuarios = new ArrayList<>();

        Cursor cursor = banco.getReadableDatabase().rawQuery(
                "SELECT " +MATERIA+ ", count(" +MATERIA+ ")" +
                        " FROM " +TABELA+
                        " WHERE " +IDPERFIL+ " IN ("+subtabela+")" +
                        " GROUP BY " +MATERIA+" ORDER BY count("+MATERIA+") DESC", new String[]{entrada,Integer.toString(perfil)}
        );

        while(cursor.moveToNext()){
            usuarios.add(cursor.getString(cursor.getColumnIndex(MATERIA)));
        }
        cursor.close();
        return usuarios;
    }

}
