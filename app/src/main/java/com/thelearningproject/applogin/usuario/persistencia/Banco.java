package com.thelearningproject.applogin.usuario.persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ebony Marques on 17/07/2017.
 */

public class Banco extends SQLiteOpenHelper {

    private static Banco sInstance;

    private static final String NOME_BANCO = "BDUsuario";
    private static final int VERSION = 4;
    private static final String TABELA = "usuarios";
    private static final String ID = "id";
    private static final String NOME = "nome";
    private static final String EMAIL = "email";
    private static final String SENHA = "senha";
    private static final String DESATIVADO = "desativado";



    public static synchronized Banco getInstance(Context context){
        if(sInstance == null){
            sInstance = new Banco(context.getApplicationContext());
        }
        return sInstance;
    }

    public Banco(Context context) {
        super(context, NOME_BANCO, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABELA + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NOME + " TEXT, " +
                EMAIL + " TEXT, " +
                SENHA + " TEXT, " +
                DESATIVADO + " TEXT)";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABELA;
        db.execSQL(sql);
        onCreate(db);
    }
    /*
    public void inserir(Usuario usuario) {
        ContentValues values = new ContentValues();
        values.put(NOME, usuario.getNome());
        values.put(EMAIL, usuario.getEmail());
        values.put(SENHA, usuario.getSenha());
        getWritableDatabase().insert(TABELA, null, values);

    }

    public Boolean consultaUsuarioEmail(String email){
        Cursor cursor = getReadableDatabase().query(TABELA,null,"email = ?",new String[]{email},null,null,null);
        return cursor.moveToFirst();
    }


    public Usuario retornaUsuario(String email,String senha) {
        String[] colunas = {ID, NOME, EMAIL, SENHA};
        Cursor cursor = getReadableDatabase().query(TABELA,colunas,"email = ? AND senha = ?",new String[]{email, senha},null,null,null);
        Usuario usuario = null;
        if(cursor.moveToFirst()) {

            usuario = new Usuario();
            usuario.setId(cursor.getInt(cursor.getColumnIndex(ID)));
            usuario.setNome(cursor.getString(cursor.getColumnIndex(NOME)));
            usuario.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL)));
            usuario.setSenha(cursor.getString(cursor.getColumnIndex(SENHA)));

        }

        return usuario;

    }
    */

}