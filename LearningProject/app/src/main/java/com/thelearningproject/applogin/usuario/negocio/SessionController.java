package com.thelearningproject.applogin.usuario.negocio;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.thelearningproject.applogin.usuario.gui.LoginActivity;

/**
 * Created by Pichau on 20/07/2017.
 */

public class SessionController {

    SharedPreferences preferences;

    Editor editor;

    Context context;

    private static final String PREFERENCIA = "Sessao";

    private static final String USUARIO_LOGADO = "Logado";

    public static final String NOME = "nome";

    public SessionController(Context context){
        this.context = context;
        preferences = this.context.getSharedPreferences(PREFERENCIA,Context.MODE_PRIVATE);
        editor = preferences.edit();
    }


    public void iniciaSessao(String nome){
        editor.putBoolean(USUARIO_LOGADO,true);
        editor.putString(NOME,nome);
        editor.commit();
    }

    public boolean verificaLogin(){
        if(!verificaSessao()){

            Intent intent = new Intent(context, LoginActivity.class);

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);

            return true;
        }
        return false;
    }

    public String getNome(){

        return preferences.getString(NOME, null);
    }

    public void encerraSessao(){
        editor.clear();
        editor.commit();

        Intent intent = new Intent(context, LoginActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);
    }

    private boolean verificaSessao() {
        return preferences.getBoolean(USUARIO_LOGADO, false);
    }
}
