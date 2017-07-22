package com.thelearningproject.applogin.usuario.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.thelearningproject.applogin.usuario.dominio.Usuario;

/**
 * Created by Ebony Marques on 17/07/2017.
 */

public class UsuarioDAO extends SQLiteOpenHelper {

    private static UsuarioDAO sInstance;

    private static final String NOME_BANCO = "BDUsuario";
    private static final int VERSION = 1;
    private static final String TABELA = "usuarios";
    private static final String ID = "id";
    private static final String NOME = "nome";
    private static final String EMAIL = "email";
    private static final String SENHA = "senha";


    public static synchronized UsuarioDAO getInstance(Context context){
        if(sInstance == null){
            sInstance = new UsuarioDAO(context.getApplicationContext());
        }
        return sInstance;
    }

    public UsuarioDAO(Context context) {
        super(context, NOME_BANCO, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABELA + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NOME + " TEXT, " +
                EMAIL + " TEXT, " +
                SENHA + " TEXT);";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABELA;
        db.execSQL(sql);
        onCreate(db);
    }

    public long inserir(Usuario usuario) {
        ContentValues values = new ContentValues();
        long retorno;

        values.put(NOME, usuario.getNome());
        values.put(EMAIL, usuario.getEmail());
        values.put(SENHA, usuario.getSenha());

        retorno = getWritableDatabase().insert(TABELA, null, values);

        return retorno;
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
}
