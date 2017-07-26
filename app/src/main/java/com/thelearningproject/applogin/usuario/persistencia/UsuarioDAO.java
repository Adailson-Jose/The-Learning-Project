package com.thelearningproject.applogin.usuario.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.thelearningproject.applogin.usuario.dominio.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicolas on 22/07/2017.
 */

public class UsuarioDAO {
    private static UsuarioDAO sInstance;

    private static final String TABELA = "usuarios";
    private static final String ID = "id";
    private static final String NOME = "nome";
    private static final String EMAIL = "email";
    private static final String SENHA = "senha";
    private static final String DESATIVADO = "desativado";
    private SQLiteOpenHelper banco;

    public static synchronized UsuarioDAO getInstance(Context context){
        if(sInstance == null){
            sInstance = new UsuarioDAO(context.getApplicationContext());
        }
        return sInstance;
    }

    public UsuarioDAO(Context context) {
        this.banco = Banco.getInstance(context);
    }
    public void inserir(Usuario usuario) {
        ContentValues values = new ContentValues();
        values.put(NOME, usuario.getNome());
        values.put(EMAIL, usuario.getEmail());
        values.put(SENHA, usuario.getSenha());
        banco.getWritableDatabase().insert(TABELA, null, values);

    }

    public Boolean consultaUsuarioEmail(String email){
        Cursor cursor = banco.getReadableDatabase().query(TABELA,null,"email = ?",new String[]{email},null,null,null);
        return cursor.moveToFirst();
    }

    public Usuario retornaUsuarioPorEmail(String email) {
        String[] colunas = {ID, NOME, EMAIL};
        Cursor cursor = banco.getReadableDatabase().query(TABELA, colunas, "email = ?", new String[] {email}, null, null, null);
        Usuario usuario = null;

        if (cursor.moveToFirst()) {
            usuario = new Usuario();
            usuario.setId(cursor.getInt(cursor.getColumnIndex(ID)));
            usuario.setNome(cursor.getString(cursor.getColumnIndex(NOME)));
            usuario.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL)));
        }

        return usuario;
    }

    public Usuario retornaUsuario(String email,String senha) {
        String[] colunas = {ID, NOME, EMAIL, SENHA};
        Cursor cursor = banco.getReadableDatabase().query(TABELA,colunas,"email = ? AND senha = ?",new String[]{email, senha},null,null,null);
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
    void deletaUsuario(Usuario usuario){
        usuario.setAtivado(false);
        alterarUsuario(usuario);


    }
    //diferente da função retorna usuario essa aqui é so uma busca por id e não uma validação email e senha
    public Usuario pesquisarUsuario(int codigo){

        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor cursor = db.query(TABELA, new String[]{ID, NOME,
                        EMAIL, SENHA}, ID + " = ?",
                new String[]{String.valueOf(codigo)}, null, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        Usuario usuario = new Usuario();
        usuario.setId(Integer.parseInt(cursor.getString(0)));
        usuario.setNome(cursor.getString(1));
        usuario.setEmail(cursor.getString(2));
        usuario.setSenha(cursor.getString(3));
        db.close();
        return usuario;

    }
    public void alterarUsuario(Usuario usuario){
        SQLiteDatabase db = banco.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NOME, usuario.getNome());
        values.put(EMAIL, usuario.getEmail());
        values.put(SENHA, usuario.getSenha());
        values.put(DESATIVADO, usuario.getAtivado());

        db.update(TABELA, values, ID + " = ?",
                new String[]{String.valueOf(usuario.getId())});
        db.close();
    }

    public List<Usuario> listarTodosUsuarios(){
        List<Usuario> listaUsuarios = new ArrayList<Usuario>();
        SQLiteDatabase db = banco.getReadableDatabase();

        String query =  "SELECT * FROM "+ TABELA;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do{
                Usuario usuario = new Usuario();
                usuario.setId(Integer.parseInt(cursor.getString(0)));
                usuario.setNome(cursor.getString(1));
                usuario.setEmail(cursor.getString(2));
                usuario.setSenha(cursor.getString(3));

                listaUsuarios.add(usuario);
            }while(cursor.moveToNext());
        }
        return listaUsuarios;
    }


}
