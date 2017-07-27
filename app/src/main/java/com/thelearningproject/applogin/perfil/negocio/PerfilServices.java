package com.thelearningproject.applogin.perfil.negocio;

import android.content.Context;

import com.thelearningproject.applogin.estudo.dominio.Materia;
import com.thelearningproject.applogin.estudo.negocio.MateriaServices;
import com.thelearningproject.applogin.estudo.persistencia.MateriaDAO;
import com.thelearningproject.applogin.estudo.persistencia.TipoConexao;
import com.thelearningproject.applogin.infra.UsuarioException;
import com.thelearningproject.applogin.perfil.dominio.Perfil;
import com.thelearningproject.applogin.perfil.persistencia.Banco;
import com.thelearningproject.applogin.perfil.persistencia.ConexaoHabilidade;
import com.thelearningproject.applogin.perfil.persistencia.ConexaoNecessidades;
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
    private ConexaoNecessidades conexaoNecessidades;

    public PerfilServices(Context contexto) {
        this.conexaoHabilidade = ConexaoHabilidade.getInstance(contexto);
        this.conexaoNecessidades = ConexaoNecessidades.getInstance(contexto);
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
//        ArrayList<Integer> materia_id = conexaoHabilidade.retornaMateria(perfil.getId());
//        for(int i:materia_id){
//            perfil.addHabilidade(materiaServices.consultar(i));
//        }
        return perfil;
    }

    public void insereHabilidade(Perfil perfil, Materia materia) throws UsuarioException{
        verificaExistencia(perfil.getId(),materia.getId(), TipoConexao.HABILIDADE);
        conexaoHabilidade.insereConexao(perfil.getId(),materia.getId());
    }

    public void insereNecessidade(Perfil perfil, Materia materia) throws UsuarioException{
        verificaExistencia(perfil.getId(),materia.getId(),TipoConexao.NECESSIDADE);
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

    public ArrayList<Perfil> recommender(Materia materia){
        ArrayList<Perfil> lista_perfils = new ArrayList<Perfil>();
        ArrayList<Integer> lista_ids = conexaoNecessidades.retornaFrequencia(materia.getId());

        for (int id : lista_ids){
            lista_perfils.add(retornaPerfil(id));
        }
        return lista_perfils;
    }

    private void verificaExistencia(int id_perfil, int id_materia, TipoConexao tipo) throws UsuarioException{
        boolean existe;
        if(tipo == TipoConexao.HABILIDADE){
            existe = conexaoHabilidade.verificatupla(id_perfil,id_materia);
        }else {
            existe = conexaoNecessidades.verificatupla(id_perfil,id_materia);
        }
        if (existe){
            throw new UsuarioException("Materia já cadastrada");
        }
    }

}
