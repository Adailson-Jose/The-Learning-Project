package com.thelearningproject.applogin.infraestrutura.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.thelearningproject.applogin.infraestrutura.gui.LoginActivity;
import com.thelearningproject.applogin.perfil.dominio.Perfil;
import com.thelearningproject.applogin.pessoa.dominio.Pessoa;

/**
 * Criado por Nicollas on 20/07/2017.
 */

public final class ControladorSessao {
    private static ControladorSessao instancia;
    private SharedPreferences preferencias;
    private Editor editor;
    private Context contexto;

    private Perfil perfil;
    private Pessoa pessoa;
    private String codigo;


    private static final String PREFERENCIA = "Sessao";
    private static final String USUARIO_LOGADO = "Logado";
    private static final String ID_USUARIO = "ID_Usuario";
    private boolean sessaoAtiva;

    public static synchronized ControladorSessao getInstancia(Context context) {
        if (instancia == null) {
            instancia = new ControladorSessao(context.getApplicationContext());
        }
        return instancia;
    }

    public boolean verificaLogin() {
        if (!verificaSessao()) {
            Intent intent = new Intent(contexto, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            contexto.startActivity(intent);

            return true;
        }
        return false;
    }

    public void encerraSessao() {
        editor.clear();
        editor.putBoolean(USUARIO_LOGADO, false);
        editor.commit();

        this.sessaoAtiva = false;

        Intent intent = new Intent(contexto, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        contexto.startActivity(intent);
    }

    private ControladorSessao(Context context) {
        this.contexto = context;
        this.sessaoAtiva = false;
        preferencias = this.contexto.getSharedPreferences(PREFERENCIA, Context.MODE_PRIVATE);
        editor = preferencias.edit();
        editor.apply();
    }

    public void salvarSessao() {
        editor.putBoolean(USUARIO_LOGADO, true);
        editor.putInt(ID_USUARIO, pessoa.getUsuario().getId());
        editor.commit();
    }

    public void iniciaSessao() {
        this.sessaoAtiva = true;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil novoPerfil) {
        this.perfil = novoPerfil;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa novaPessoa) {
        this.pessoa = novaPessoa;
    }

    private boolean verificaSessao() {
        return this.sessaoAtiva;
    }

    public int retornaIdUsuario() {
        return preferencias.getInt(ID_USUARIO, 0);
    }

    public boolean verificaConexao() {
        return preferencias.getBoolean(USUARIO_LOGADO, false);
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
