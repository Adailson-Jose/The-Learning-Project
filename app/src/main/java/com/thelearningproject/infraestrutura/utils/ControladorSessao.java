package com.thelearningproject.infraestrutura.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.thelearningproject.infraestrutura.gui.LoginActivity;
import com.thelearningproject.perfil.dominio.Perfil;
import com.thelearningproject.pessoa.dominio.Pessoa;

/**
 * The type Controlador sessao.
 */
public final class ControladorSessao {
    private static ControladorSessao instancia;
    private SharedPreferences preferencias;
    private Editor editor;
    private Context contexto;

    private Perfil perfil;
    private Perfil perfilSelecionado;
    private Pessoa pessoa;
    private String codigo;


    private static final String PREFERENCIA = "Sessao";
    private static final String USUARIO_LOGADO = "Logado";
    private static final String ID_USUARIO = "ID_Usuario";
    private boolean sessaoAtiva;

    /**
     * Gets instancia.
     *
     * @param context the context
     * @return the instancia
     */
    public static synchronized ControladorSessao getInstancia(Context context) {
        if (instancia == null) {
            instancia = new ControladorSessao(context.getApplicationContext());
        }
        return instancia;
    }

    /**
     * Verifica login boolean.
     *
     * @return the boolean
     */
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

    /**
     * Encerra sessao.
     */
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

    /**
     * Salvar sessao.
     */
    public void salvarSessao() {
        editor.putBoolean(USUARIO_LOGADO, true);
        editor.putInt(ID_USUARIO, pessoa.getUsuario().getId());
        editor.commit();
    }

    /**
     * Inicia sessao.
     */
    public void iniciaSessao() {
        this.sessaoAtiva = true;
    }

    /**
     * Gets perfil.
     *
     * @return the perfil
     */
    public Perfil getPerfil() {
        return perfil;
    }

    /**
     * Sets perfil.
     *
     * @param novoPerfil the novo perfil
     */
    public void setPerfil(Perfil novoPerfil) {
        this.perfil = novoPerfil;
    }

    /**
     * Gets pessoa.
     *
     * @return the pessoa
     */
    public Pessoa getPessoa() {
        return pessoa;
    }

    /**
     * Sets pessoa.
     *
     * @param novaPessoa the nova pessoa
     */
    public void setPessoa(Pessoa novaPessoa) {
        this.pessoa = novaPessoa;
    }

    private boolean verificaSessao() {
        return this.sessaoAtiva;
    }

    /**
     * Retorna id usuario int.
     *
     * @return the int
     */
    public int retornaIdUsuario() {
        return preferencias.getInt(ID_USUARIO, 0);
    }

    /**
     * Verifica conexao boolean.
     *
     * @return the boolean
     */
    public boolean verificaConexao() {
        return preferencias.getBoolean(USUARIO_LOGADO, false);
    }

    /**
     * Gets codigo.
     *
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Sets codigo.
     *
     * @param novo the novo
     */
    public void setCodigo(String novo) {
        this.codigo = novo;
    }

    /**
     * Gets perfil selecionado.
     *
     * @return the perfil selecionado
     */
    public Perfil getPerfilSelecionado() {
        return perfilSelecionado;
    }

    /**
     * Sets perfil selecionado.
     *
     * @param novo the novo
     */
    public void setPerfilSelecionado(Perfil novo) {
        this.perfilSelecionado = novo;
    }
}
