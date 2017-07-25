package com.thelearningproject.applogin.usuario.negocio;

import android.content.Context;

import com.thelearningproject.applogin.infra.UsuarioException;
import com.thelearningproject.applogin.usuario.persistencia.Banco;
import com.thelearningproject.applogin.usuario.dominio.Usuario;
import com.thelearningproject.applogin.usuario.persistencia.UsuarioDAO;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;


/**
 * Created by Ebony Marques on 18/07/2017.
 */

public class UsuarioServices {

    private static UsuarioServices sInstance;
    private UsuarioDAO dao2;
    private Banco banco;

    public UsuarioServices(Context context){
        this.banco = Banco.getInstance(context);
        this.dao2 = UsuarioDAO.getInstance(context);
    }

    public static UsuarioServices getsInstance(Context context){
        if(sInstance == null){
            sInstance = new UsuarioServices(context);
        }
        return sInstance;
    }




    private void verificaEmailExistente(String email) throws UsuarioException{
        if (dao2.consultaUsuarioEmail(email)){
            throw new UsuarioException("E-mail já cadastrado");
        }
    }

    public Usuario login(Usuario usuario) throws UsuarioException {
        usuario.setEmail(usuario.getEmail());
        usuario.setSenha(usuario.getSenha());
        usuario = dao2.retornaUsuario(usuario.getEmail(), returnSenha(usuario.getSenha()));
        return usuario;
    }



    public void inserirUsuario(Usuario usuario) throws UsuarioException {
        usuario.setSenha(returnSenha(usuario.getSenha()));
        verificaEmailExistente(usuario.getEmail());
        dao2.inserir(usuario);
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
