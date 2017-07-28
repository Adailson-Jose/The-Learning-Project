package com.thelearningproject.applogin.infra.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.thelearningproject.applogin.perfil.dominio.Perfil;
import com.thelearningproject.applogin.usuario.dominio.Usuario;
import com.thelearningproject.applogin.usuario.gui.LoginActivity;

/**
 * Created by Pichau on 20/07/2017.
 */

public class SessionController {

    private static SessionController sInstance;
    private SharedPreferences preferences;
    private Editor editor;
    private Context context;
    private Perfil perfil;
    private Usuario usuario;
    private boolean sessaoAtiva;


    private static final String PREFERENCIA = "Sessao";
    private static final String USUARIO_LOGADO = "Logado";

    public static synchronized SessionController getInstance(Context context){
        if(sInstance == null){
            sInstance = new SessionController(context.getApplicationContext());
        }
        return sInstance;
    }

    private SessionController(Context context){
        this.context = context;
        this.sessaoAtiva = false;
        preferences = this.context.getSharedPreferences(PREFERENCIA,Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public void iniciaSessao(){
        editor.putBoolean(USUARIO_LOGADO,true);
        editor.commit();
    }

    public Usuario getUsuario(){
        return this.usuario;
    }

    public void setUsuario(Usuario user){
        this.usuario = user;
    }

    public boolean verificaLogin(){
        if(!verificarConectado()){
            Intent intent = new Intent(context, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

            return true;
        }
        return false;
    }

    public void encerraSessao(){
        editor.clear();
        editor.putBoolean(USUARIO_LOGADO, false);
        editor.commit();

        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);
    }

    private boolean verificaSessao() {
        return this.sessaoAtiva;
    }

    private boolean verificarConectado() {
        return preferences.getBoolean(USUARIO_LOGADO, false);
    }
}
