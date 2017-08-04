package com.thelearningproject.applogin.perfil.negocio;

import android.content.Context;

import com.thelearningproject.applogin.estudo.dominio.Materia;
import com.thelearningproject.applogin.estudo.negocio.MateriaServices;
import com.thelearningproject.applogin.infraestrutura.utils.UsuarioException;
import com.thelearningproject.applogin.perfil.dominio.Perfil;
import com.thelearningproject.applogin.perfil.persistencia.ConexaoHabilidade;
import com.thelearningproject.applogin.perfil.persistencia.ConexaoNecessidade;
import com.thelearningproject.applogin.perfil.persistencia.PerfilDAO;
import com.thelearningproject.applogin.pessoa.dominio.Pessoa;
import com.thelearningproject.applogin.pessoa.persistencia.PessoaDAO;

import java.util.ArrayList;

/**
 * Criado por Ebony Marques on 26/07/2017.
 */

public class PerfilServices {
    private static PerfilServices instancia;
    private PerfilDAO persistencia;
    private PessoaDAO pessoaDAO;
    private MateriaServices materiaServices;
    private ConexaoHabilidade conexaoHabilidade;
    private ConexaoNecessidade conexaoNecessidade;

    private PerfilServices(Context contexto) {
        this.conexaoHabilidade = ConexaoHabilidade.getInstancia(contexto);
        this.conexaoNecessidade = ConexaoNecessidade.getInstancia(contexto);
        this.persistencia = PerfilDAO.getInstance(contexto);
        this.materiaServices = MateriaServices.getInstancia(contexto);
        this.pessoaDAO = PessoaDAO.getInstance(contexto);
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

    public Perfil retornaPerfil(int idPessoa) {
        Perfil perfil = persistencia.retornaPerfil(idPessoa);
        ArrayList<Integer> habilidadeId = conexaoHabilidade.retornaMateria(perfil.getId());
        ArrayList<Integer> necessidadeId = conexaoNecessidade.retornaMateria(perfil.getId());
        montaListaHabilidades(perfil, habilidadeId);
        montaListaNecessidades(perfil, necessidadeId);
        return perfil;
    }

    public void alterarPerfil(Perfil perfil) {
        persistencia.alterarPerfil(perfil);
    }

    private Perfil consulta(int id){
        Perfil perfil = persistencia.consultar(id);
        Pessoa pessoa = pessoaDAO.consultar(perfil.getPessoa().getId());
        perfil.setPessoa(pessoa);
        ArrayList<Integer> habilidadeId = conexaoHabilidade.retornaMateria(perfil.getId());
        ArrayList<Integer> necessidadeId = conexaoNecessidade.retornaMateria(perfil.getId());
        montaListaHabilidades(perfil, habilidadeId);
        montaListaNecessidades(perfil, necessidadeId);
        return perfil;
    }

    public void insereHabilidade(Perfil perfil, Materia materia) throws UsuarioException{
        verificaExistencia(perfil.getId(),materia.getId(),tipoConexao.HABILIDADE);
        conexaoHabilidade.insereConexao(perfil.getId(),materia.getId());
    }

    public void insereNecessidade(Perfil perfil, Materia materia) throws UsuarioException{
        verificaExistencia(perfil.getId(),materia.getId(),tipoConexao.NECESSIDADE);
        conexaoNecessidade.insereConexao(perfil.getId(),materia.getId());
    }

    public void deletarHabilidade(Perfil perfil, Materia materia) throws UsuarioException{
        conexaoHabilidade.removerConexao(perfil.getId(),materia.getId());
    }

    public void deletarNecessidade(Perfil perfil, Materia materia) throws UsuarioException{
        conexaoNecessidade.removerConexao(perfil.getId(),materia.getId());
    }

    public ArrayList<Perfil> listarPerfil(Materia materia){
        ArrayList<Perfil> usuarios = new ArrayList<>();
        ArrayList<Integer> listaIds = conexaoHabilidade.retornaUsuarios(materia.getId());
        for (int id:listaIds){
            usuarios.add(consulta(id));
        }
        return usuarios;
    }

    public ArrayList<Materia> listarHabilidade(Perfil perfil){
        ArrayList<Materia> listaMateria = new ArrayList<>();
        ArrayList<Integer> lista = conexaoHabilidade.retornaMateria(perfil.getId());

        if (!lista.isEmpty()) {
            for(int id:lista){
                listaMateria.add(materiaServices.consultar(id));
            }
        }
        return listaMateria;
    }
    public ArrayList<Materia> listarNecessidade(Perfil perfil){
        ArrayList<Materia> listaNecessidade = new ArrayList<>();
        ArrayList<Integer> lista = conexaoNecessidade.retornaMateria(perfil.getId());

        if (!lista.isEmpty()) {
            for(int id:lista){
                listaNecessidade.add(materiaServices.consultar(id));
            }
        }
        return listaNecessidade;
    }

    private void verificaExistencia(int perfil, int materia, tipoConexao tipo) throws UsuarioException{
        if (tipo == tipoConexao.HABILIDADE){
            if (conexaoHabilidade.verificatupla(perfil,materia)){
                throw new UsuarioException("Você já cadastrou essa habilidade");
            }
        }else{
            if (conexaoNecessidade.verificatupla(perfil, materia)) {
                throw new UsuarioException("Você já cadastrou essa necessidade");
            }
        }
    }

    private void montaListaHabilidades(Perfil perfil, ArrayList<Integer> habilidadeId) {
        for(int i:habilidadeId){
            perfil.addHabilidade(materiaServices.consultar(i));
        }
    }

    private void montaListaNecessidades(Perfil perfil, ArrayList<Integer> necessidadeId) {
        for(int j:necessidadeId){
            perfil.addNecessidade(materiaServices.consultar(j));
        }
    }

    public String retornaStringListaHabilidades(int id) {
        Perfil perfil = this.consulta(id);
        StringBuilder sb = new StringBuilder();

        ArrayList<Materia> lista = perfil.getHabilidades();

        String prefix = "";
        for (Materia mat: lista) {
            sb.append(prefix);
            prefix = ", ";
            sb.append(mat.getNome());
        }
        return sb.toString();
    }

    public String retornaStringListaNecessidades(int id) {
        Perfil perfil = this.consulta(id);
        StringBuilder sb = new StringBuilder();

        ArrayList<Materia> lista = perfil.getNecessidades();

        String prefix = "";
        for (Materia mat: lista) {
            sb.append(prefix);
            prefix = ", ";
            sb.append(mat.getNome());
        }
        return sb.toString();
    }

}
