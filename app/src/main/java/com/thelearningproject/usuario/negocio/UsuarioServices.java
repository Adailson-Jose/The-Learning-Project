package com.thelearningproject.usuario.negocio;

import android.content.Context;
import android.util.Log;

import com.thelearningproject.infraestrutura.utils.Status;
import com.thelearningproject.infraestrutura.utils.UsuarioException;
import com.thelearningproject.usuario.dominio.Usuario;
import com.thelearningproject.usuario.persistencia.UsuarioDAO;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The type Usuario services.
 */
public final class UsuarioServices {
    private static UsuarioServices instancia;
    private UsuarioDAO persistencia;

    private static final int CONSTANTE_CRIPTOGRAFIA = 0xFF;

    private UsuarioServices(Context context) {
        this.persistencia = UsuarioDAO.getInstancia(context);
    }

    /**
     * Gets instancia.
     *
     * @param context the context
     * @return the instancia
     */
    public static UsuarioServices getInstancia(Context context) {
        if (instancia == null) {
            instancia = new UsuarioServices(context);
        }
        return instancia;
    }

    private Boolean verificaEmailExistente(String email) {
        Boolean resultado = false;

        if (persistencia.consultaUsuarioEmail(email)) {
            resultado = true;
        }

        return resultado;
    }

    //Retorna Verdadeiro se o usuario estiver desativo
    private Boolean verificaEmailExistenteStatus(String email) {
        Boolean resultado = false;

        if (persistencia.consultaUsuarioEmailStatus(email, "1")) {
            resultado = true;
        }

        return resultado;
    }

    /**
     * Consulta usuario.
     *
     * @param id the id
     * @return the usuario
     */
    public Usuario consulta(int id) {
        return persistencia.pesquisarUsuario(id);
    }

    /**
     * Logar usuario.
     *
     * @param usuario the usuario
     * @return the usuario
     * @throws UsuarioException the usuario exception
     */
    public Usuario logar(Usuario usuario) throws UsuarioException {
        Usuario logado = persistencia.retornaUsuario(usuario.getEmail(), retornaSenhaCriptografada(usuario.getSenha()));
        usuarioAtivo(logado);
        return logado;
    }

    /**
     * Retorna usuario id int.
     *
     * @param email the email
     * @return the int
     */
    public int retornaUsuarioID(String email) {
        return persistencia.retornaUsuarioID(email);
    }

    /**
     * Inserir usuario.
     *
     * @param usuario the usuario
     * @throws UsuarioException the usuario exception
     */
    public void inserirUsuario(Usuario usuario) throws UsuarioException {
        usuario.setSenha(retornaSenhaCriptografada(usuario.getSenha()));

        if (verificaEmailExistenteStatus(usuario.getEmail())) {
            Usuario user = persistencia.retornaUsuarioPorEmail(usuario.getEmail());
            user.setEmail(usuario.getEmail());
            user.setSenha(usuario.getSenha());
            user.setStatus(Status.ATIVADO);
            persistencia.alterarUsuario(user);
        } else if (verificaEmailExistente(usuario.getEmail())) {
            throw new UsuarioException("E-mail já cadastrado");
        } else {
            persistencia.inserir(usuario);
        }

    }

    /**
     * Alterar email usuario.
     *
     * @param usuario the usuario
     * @throws UsuarioException the usuario exception
     */
    public void alterarEmailUsuario(Usuario usuario) throws UsuarioException {
        if (verificaEmailExistente(usuario.getEmail())) {
            throw new UsuarioException("E-mail já cadastrado");
        }
        persistencia.alterarUsuario(usuario);
    }

    /**
     * Alterar senha usuario.
     *
     * @param usuario the usuario
     */
    public void alterarSenhaUsuario(Usuario usuario) {
        usuario.setSenha(retornaSenhaCriptografada(usuario.getSenha()));
        persistencia.alterarUsuario(usuario);
    }

    /**
     * Deletar usuario.
     *
     * @param usuario the usuario
     */
    public void deletarUsuario(Usuario usuario) {
        persistencia.deletaUsuario(usuario);
    }

    private String retornaSenhaCriptografada(String senha) {
        String criptografado = null;
        try {
            criptografado = criptografaSenha(senha);
        } catch (NoSuchAlgorithmException e) {
            Log.e("UsuarioServices", "Algoritmo de criptografia não encontrado");
        }
        return criptografado;
    }

    private String criptografaSenha(String senha) throws NoSuchAlgorithmException {
        MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
        byte messageDigest[] = algorithm.digest(senha.getBytes());

        StringBuilder hexString = new StringBuilder();
        for (byte b : messageDigest) {
            hexString.append(String.format("%02X", CONSTANTE_CRIPTOGRAFIA & b));
        }
        return hexString.toString();
    }

    private void usuarioAtivo(Usuario usuario) throws UsuarioException {
        if (usuario != null && Status.DESATIVADO.equals(usuario.getStatus())) {
            throw new UsuarioException("Usuário ou senha incorretos");

        }
    }


}
