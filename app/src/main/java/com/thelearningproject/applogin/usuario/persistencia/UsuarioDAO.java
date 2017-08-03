package com.thelearningproject.applogin.usuario.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.thelearningproject.applogin.infraestrutura.utils.Status;
import com.thelearningproject.applogin.usuario.dominio.Usuario;

/**
 * Criado por Nícolas on 22/07/2017.
 */

public class UsuarioDAO {
    private static UsuarioDAO instancia;

    private static final String TABELA = "usuarios";
    private static final String ID = "id";
    private static final String EMAIL = "email";
    private static final String SENHA = "senha";
    private static final String STATUS = "status";
    private SQLiteOpenHelper banco;
    private Status[] valores = Status.values();


    public static synchronized UsuarioDAO getInstance(Context context){
        if(instancia == null){
            instancia = new UsuarioDAO(context.getApplicationContext());
        }
        return instancia;
    }

    private UsuarioDAO(Context context) {
        this.banco = Banco.getInstance(context);
    }

    public void inserir(Usuario usuario) {
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EMAIL, usuario.getEmail());
        values.put(SENHA, usuario.getSenha());
        values.put(STATUS,usuario.getStatus().getValor());
        db.insert(TABELA, null, values);
        db.close();

    }

    public Boolean consultaUsuarioEmail(String email){
        Cursor cursor = banco.getReadableDatabase().query(TABELA,null,"email = ?",new String[]{email},null,null,null);
        boolean resultado = cursor.moveToFirst();
        cursor.close();
        return resultado;
    }
    public Boolean consultaUsuarioEmailStatus(String email, String status){
        Cursor cursor = banco.getReadableDatabase().query(TABELA,null,"email = ? AND status = ?",new String[]{email, status},null,null,null);
        boolean resultado = cursor.moveToFirst();
        cursor.close();
        return resultado;
    }

    public Usuario retornaUsuarioPorEmail(String email) {
        String[] colunas = {ID, EMAIL, STATUS};
        Cursor cursor = banco.getReadableDatabase().query(TABELA, colunas, "email = ?", new String[] {email}, null, null, null);
        Usuario usuario = null;

        if (cursor.moveToFirst()) {
            usuario = new Usuario();
            usuario.setId(cursor.getInt(cursor.getColumnIndex(ID)));
            usuario.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL)));
            usuario.setStatus(valores[(cursor.getInt(cursor.getColumnIndex(STATUS)))]);
        }

        cursor.close();
        return usuario;
    }

    public int retornaUsuarioID(String email) {
        String[] colunas = {ID, EMAIL, SENHA, STATUS};
        Cursor cursor = banco.getReadableDatabase().query(TABELA,colunas,"email = ? ",new String[]{email},null,null,null);

        int id = -1;

        if(cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndex(ID));
        }

        cursor.close();
        return id;
    }

    public Usuario retornaUsuario(String email,String senha) {
        String[] colunas = {ID, EMAIL, SENHA, STATUS};
        Cursor cursor = banco.getReadableDatabase().query(TABELA,colunas,"email = ? AND senha = ?",new String[]{email, senha},null,null,null);
        Usuario usuario = null;

        if(cursor.moveToFirst()) {
            usuario = new Usuario();
            usuario.setId(cursor.getInt(cursor.getColumnIndex(ID)));
            usuario.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL)));
            usuario.setSenha(cursor.getString(cursor.getColumnIndex(SENHA)));
            usuario.setStatus(valores[(cursor.getInt(cursor.getColumnIndex(STATUS)))]);
        }

        cursor.close();
        return usuario;
    }

    public void deletaUsuario(Usuario usuario){
        usuario.setStatus(Status.DESATIVADO);
        alterarUsuario(usuario);

    }

    //diferente da função retorna usuario essa aqui é so uma busca por id e não uma validação email e senha
    public Usuario pesquisarUsuario(int codigo){
        String[] colunas = {ID, EMAIL, SENHA, STATUS};
        Cursor cursor = banco.getReadableDatabase().query(TABELA, colunas, ID + " = ?",
                new String[]{String.valueOf(codigo)}, null, null, null, null);
        Usuario usuario = null;

        if (cursor.moveToFirst()) {
            usuario = new Usuario();
            usuario.setId(cursor.getInt(cursor.getColumnIndex(ID)));
            usuario.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL)));
            usuario.setSenha(cursor.getString(cursor.getColumnIndex(SENHA)));
            usuario.setStatus(valores[(cursor.getInt(cursor.getColumnIndex(STATUS)))]);
        }
        cursor.close();
        return usuario;
    }

    public void alterarUsuario(Usuario usuario){
        SQLiteDatabase db = banco.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EMAIL, usuario.getEmail());
        values.put(SENHA, usuario.getSenha());
        values.put(STATUS, usuario.getStatus().getValor());

        db.update(TABELA, values, ID + " = ?",
                new String[]{String.valueOf(usuario.getId())});
        db.close();
    }
}
