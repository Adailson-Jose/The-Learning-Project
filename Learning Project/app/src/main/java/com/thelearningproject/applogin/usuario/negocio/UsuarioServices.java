package com.thelearningproject.applogin.usuario.negocio;

import android.content.Context;

import com.thelearningproject.applogin.infra.UsuarioException;
import com.thelearningproject.applogin.usuario.persistencia.UsuarioDAO;
import com.thelearningproject.applogin.usuario.dominio.Usuario;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;


/**
 * Created by Ebony Marques on 18/07/2017.
 */

public class UsuarioServices {

    private static UsuarioServices sInstance;
    private UsuarioDAO banco;

    public UsuarioServices(Context context){
        this.banco = UsuarioDAO.getInstance(context);
    }

    public static UsuarioServices getsInstance(Context context){
        if(sInstance == null){
            sInstance = new UsuarioServices(context);
        }
        return sInstance;
    }

    private void verificarDados(Usuario usuario) throws UsuarioException{
        StringBuilder erro = new StringBuilder();
        if (usuario == null) {
            erro.append("Usuário não pode ser vazio\n");
        }
        if (usuario.getEmail() == null || usuario.getEmail().trim().length() == 0 || !Pattern.matches("^[A-Z0-9._%-]+@[A-Z0-9.-]+.[A-Z]{2,4}$",usuario.getEmail().toUpperCase())) {
            erro.append("Email inválido\n");
        }
        if (usuario.getSenha() == null || usuario.getSenha().trim().length() == 0) {
            erro.append("Senha inválida\n");
        }
        if (erro.length()>0) {
            throw new UsuarioException(erro.toString().trim());
        }

    }

    public Usuario validaLogin(String email, String senha) throws UsuarioException {
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setSenha(senha);
        verificarDados(usuario);
        usuario = banco.retornaUsuario(email, returnSenha(senha));
        return usuario;
    }


    public Usuario inserirUsuario(Usuario usuario) throws UsuarioException{
        verificarDados(usuario);
        usuario.setSenha(returnSenha(usuario.getSenha()));
        banco.inserir(usuario);
        return usuario;
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


}
