package com.thelearningproject.applogin.usuario.negocio;

import android.content.Context;

import com.thelearningproject.applogin.infra.UsuarioException;
import com.thelearningproject.applogin.usuario.persistencia.Banco;
import com.thelearningproject.applogin.usuario.dominio.Usuario;
import com.thelearningproject.applogin.usuario.persistencia.UsuarioDAO;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Created by Ebony Marques on 18/07/2017.
 */

public class UsuarioServices {

    private static UsuarioServices sInstance;
    private UsuarioDAO persistencia;

    public UsuarioServices(Context context){
        this.persistencia = UsuarioDAO.getInstance(context);
    }

    public static UsuarioServices getInstancia(Context context){
        if(sInstance == null){
            sInstance = new UsuarioServices(context);
        }
        return sInstance;
    }

    private void verificaEmailExistente(String email) throws UsuarioException{
        if (persistencia.consultaUsuarioEmail(email)){
            throw new UsuarioException("E-mail já cadastrado");
        }
    }

    public Usuario login(Usuario usuario) throws UsuarioException {
        usuario = persistencia.retornaUsuario(usuario.getEmail(), returnSenha(usuario.getSenha()));
        usuarioAtivo(usuario);
        return usuario;
    }

    public void inserirUsuario(Usuario usuario) throws UsuarioException {
        usuario.setSenha(returnSenha(usuario.getSenha()));
        verificaEmailExistente(usuario.getEmail());
        persistencia.inserir(usuario);
    }

    public void alterarUsuario(Usuario usuario) throws UsuarioException {
        usuario.setSenha(returnSenha(usuario.getSenha()));
        verificaEmailExistente(usuario.getEmail());
        persistencia.alterarUsuario(usuario);
    }

    public void deletarUsuario(String email){
        Usuario usuario = retornaUsuario(email);
        persistencia.deletaUsuario(usuario);
    }

    private String returnSenha(String senha){
        String criptografado  = null;
        try {
            criptografado = criptografaSenha(senha);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return criptografado;
    }

    private String criptografaSenha (String senha) throws NoSuchAlgorithmException {
        MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
        byte messageDigest[] = algorithm.digest(senha.getBytes());

        StringBuilder hexString = new StringBuilder();
        for (byte b : messageDigest) {
            hexString.append(String.format("%02X", 0xFF & b));
        }
        String senha1 = hexString.toString();
        return senha1;
    }

    public Usuario retornaUsuario(String email) {
        Usuario usuario = persistencia.retornaUsuarioPorEmail(email);

        return usuario;
    }

    private void usuarioAtivo(Usuario usuario) throws UsuarioException{
        if (usuario != null){
            if (usuario.getDesativado()){
                throw new UsuarioException("usuario-desativado");

            }
        }
    }


}
