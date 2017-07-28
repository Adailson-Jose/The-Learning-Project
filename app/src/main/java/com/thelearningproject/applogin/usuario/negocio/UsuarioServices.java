package com.thelearningproject.applogin.usuario.negocio;

import android.content.Context;

import com.thelearningproject.applogin.infra.utils.UsuarioException;
import com.thelearningproject.applogin.usuario.dominio.Status;
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

    private Boolean verificaEmailExistente(String email){
        Boolean resultado = false;
        if (persistencia.consultaUsuarioEmail(email)){
            resultado = true;

        }
        return resultado;
    }

    private Boolean verificaEmailExistenteStatus(String email){
        Boolean resultado = false;
        if (persistencia.consultaUsuarioEmailStatus(email,"1")){
            resultado = true;
        }
        return resultado;
    }

    public Usuario consulta(int id){
        Usuario usuario = persistencia.pesquisarUsuario(id);
        return usuario;
    }

    public Usuario login(Usuario usuario) throws UsuarioException {
        usuario = persistencia.retornaUsuario(usuario.getEmail(), returnSenha(usuario.getSenha()));
        usuarioAtivo(usuario);
        return usuario;
    }

    public void inserirUsuario(Usuario usuario) throws UsuarioException {
        usuario.setSenha(returnSenha(usuario.getSenha()));
        if(verificaEmailExistenteStatus(usuario.getEmail())){
            Usuario user = persistencia.retornaUsuarioPorEmail(usuario.getEmail());
            user.setEmail(usuario.getEmail());
            user.setNome(usuario.getNome());
            user.setSenha(usuario.getSenha());
            user.setStatus(Status.ATIVADO);
            persistencia.alterarUsuario(user);
        }else if(verificaEmailExistente(usuario.getEmail())){
            throw new UsuarioException("E-mail já cadastrado");
        }else{
            persistencia.inserir(usuario);
        }

    }

    public void alterarUsuario(Usuario usuario) throws UsuarioException {
        usuario.setSenha(returnSenha(usuario.getSenha()));
        verificaEmailExistente(usuario.getEmail());
        persistencia.alterarUsuario(usuario);
    }

    public void deletarUsuario(Usuario usuario){
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
            if (Status.DESATIVADO.equals(usuario.getStatus())){
                throw new UsuarioException("usuario-desativado");

            }
        }
    }


}
