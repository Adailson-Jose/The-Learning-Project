package com.thelearningproject.applogin.perfil.negocio;

import android.content.Context;

import com.thelearningproject.applogin.estudo.dominio.Materia;
import com.thelearningproject.applogin.estudo.negocio.MateriaServices;
import com.thelearningproject.applogin.infraestrutura.utils.UsuarioException;
import com.thelearningproject.applogin.perfil.dominio.Perfil;
import com.thelearningproject.applogin.perfil.persistencia.ConexaoHabilidade;
import com.thelearningproject.applogin.perfil.persistencia.PerfilDAO;

import java.util.ArrayList;

/**
 * Created by Ebony Marques on 26/07/2017.
 */

public class PerfilServices {
    private static PerfilServices instancia;
    private PerfilDAO persistencia;
    private MateriaServices materiaServices;
    private ConexaoHabilidade conexaoHabilidade;

    public PerfilServices(Context contexto) {
        this.conexaoHabilidade = ConexaoHabilidade.getInstance(contexto);
        this.persistencia = PerfilDAO.getInstance(contexto);
        this.materiaServices = MateriaServices.getInstancia(contexto);
    }

    public static PerfilServices getInstancia(Context contexto) {
        if (instancia == null) {
            instancia = new PerfilServices(contexto);
        }

        return instancia;
    }

    public void inserirPerfil(Perfil perfil) {
        persistencia.inserir(perfil);
    }

    public Perfil retornaPerfil(int id_usuario) {
        Perfil perfil = persistencia.retornaPerfil(id_usuario);
        ArrayList<Integer> materia_id = conexaoHabilidade.retornaMateria(perfil.getId());
        for(int i:materia_id){
            perfil.addHabilidade(materiaServices.consultar(i));
        }
        return perfil;
    }

    public Perfil consultar(int id){
        Perfil perfil = persistencia.consultar(id);
        ArrayList<Integer> materia_id = conexaoHabilidade.retornaMateria(perfil.getId());
        for(int i:materia_id){
            perfil.addHabilidade(materiaServices.consultar(i));
        }
        return perfil;
    }

    public void insereHabilidade(Perfil perfil, Materia materia) throws UsuarioException{
        verificaExistencia(perfil.getId(),materia.getId());
        conexaoHabilidade.insereConexao(perfil.getId(),materia.getId());
    }

    public ArrayList<Perfil> listarPerfil(Materia materia){
        ArrayList<Perfil> usuarios = new ArrayList<Perfil>();
        ArrayList<Integer> lista_ids = conexaoHabilidade.retornaUsuarios(materia.getId());
        for (int id:lista_ids){
            usuarios.add(consultar(id));
        }
        return usuarios;
    }

    public ArrayList<Integer> listarMateria(Perfil perfil){
        ArrayList<Integer> lista_ids = conexaoHabilidade.retornaMateria(perfil.getId());
        return lista_ids;
    }

    private void verificaExistencia(int id_perfil, int id_materia) throws UsuarioException{
        if (conexaoHabilidade.verificatupla(id_perfil,id_materia)){
            throw new UsuarioException("Você já cadastrou essa Habilidade");
        }
    }

}
