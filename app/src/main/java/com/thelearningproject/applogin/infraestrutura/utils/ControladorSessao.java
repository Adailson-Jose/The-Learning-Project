package com.thelearningproject.applogin.infraestrutura.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.thelearningproject.applogin.perfil.dominio.Perfil;
import com.thelearningproject.applogin.usuario.dominio.Usuario;
import com.thelearningproject.applogin.infraestrutura.gui.LoginActivity;

/**
 * Created by Pichau on 20/07/2017.
 */

public class ControladorSessao {
    private static ControladorSessao instancia;
    private SharedPreferences preferencias;
    private Editor editor;
    private Context contexto;
    private Perfil perfil;
    private Usuario usuario;

    private static final String PREFERENCIA = "Sessao", USUARIO_LOGADO = "Logado", ID_USUARIO = "ID_Usuario";
    private boolean sessaoAtiva;

    public static synchronized ControladorSessao getInstancia(Context context){
        if(instancia == null){
            instancia = new ControladorSessao(context.getApplicationContext());
        } return instancia;
    }


    public boolean verificaLogin(){
        if(!verificaSessao()){
            Intent intent = new Intent(contexto, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            contexto.startActivity(intent);

            return true;
        }
        return false;
    }

    public void encerraSessao(){
        editor.clear();
        editor.putBoolean(USUARIO_LOGADO, false);
        editor.commit();

        Intent intent = new Intent(contexto, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        contexto.startActivity(intent);
    }

    private ControladorSessao(Context contexto){
        this.contexto = contexto;
        this.sessaoAtiva = false;
        preferencias = this.contexto.getSharedPreferences(PREFERENCIA,Context.MODE_PRIVATE);
        editor = preferencias.edit();
    }

    public void salvaSessao(){
        editor.putBoolean(USUARIO_LOGADO,true);
        editor.putInt(ID_USUARIO,usuario.getId());
        editor.commit();
    }

    public void iniciaSessao(){
        this.sessaoAtiva = true;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Usuario getUsuario(){
        return this.usuario;
    }

    public void setUsuario(Usuario user){
        this.usuario = user;
    }

    public boolean verificaSessao() {
        return this.sessaoAtiva;
    }

    public int retornaID(){
        return preferencias.getInt(ID_USUARIO, 0);
    }

    public boolean verificaConexao() {
        return preferencias.getBoolean(USUARIO_LOGADO, false);
    }
}
