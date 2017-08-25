package com.thelearningproject.perfil.negocio;

import android.content.Context;

import com.thelearningproject.combinacao.dominio.Combinacao;
import com.thelearningproject.combinacao.negocio.CombinacaoServices;
import com.thelearningproject.estudo.dominio.Materia;
import com.thelearningproject.estudo.negocio.MateriaServices;
import com.thelearningproject.infraestrutura.utils.PesoRecomendacao;
import com.thelearningproject.infraestrutura.utils.Status;
import com.thelearningproject.infraestrutura.utils.UsuarioException;
import com.thelearningproject.infraestrutura.utils.VetorMateria;
import com.thelearningproject.perfil.dominio.Perfil;
import com.thelearningproject.perfil.persistencia.ConexaoHabilidade;
import com.thelearningproject.perfil.persistencia.ConexaoNecessidade;
import com.thelearningproject.perfil.persistencia.PerfilDAO;
import com.thelearningproject.pessoa.dominio.Pessoa;
import com.thelearningproject.pessoa.persistencia.PessoaDAO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Criado por Ebony Marques on 26/07/2017.
 */

public final class PerfilServices {
    private static PerfilServices instancia;
    private PerfilDAO persistencia;
    private PessoaDAO pessoaDAO;
    private MateriaServices materiaServices;
    private CombinacaoServices combinacaoServices;
    private ConexaoHabilidade conexaoHabilidade;
    private ConexaoNecessidade conexaoNecessidade;

    private PerfilServices(Context contexto) {
        this.conexaoHabilidade = ConexaoHabilidade.getInstancia(contexto);
        this.conexaoNecessidade = ConexaoNecessidade.getInstancia(contexto);
        this.persistencia = PerfilDAO.getInstancia(contexto);
        this.materiaServices = MateriaServices.getInstancia(contexto);
        this.combinacaoServices = CombinacaoServices.getInstancia(contexto);
        this.pessoaDAO = PessoaDAO.getInstancia(contexto);
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
        Perfil perfil = persistencia.retornaPerfilPorPessoa(idPessoa);
        ArrayList<Integer> habilidadeId = conexaoHabilidade.retornaMateriaAtivas(perfil.getId());
        ArrayList<Integer> necessidadeId = conexaoNecessidade.retornaMateriaAtivas(perfil.getId());
        ArrayList<Combinacao> combinacoes = combinacaoServices.retornaCombinacoesPendentes(perfil);
        perfil.setCombinacoes(combinacoes);
        montaListaHabilidades(perfil, habilidadeId);
        montaListaNecessidades(perfil, necessidadeId);
        return perfil;
    }

    public void alterarPerfil(Perfil perfil) {
        persistencia.alterarPerfil(perfil);
    }

    public Perfil consultaPendentes(int id) {
        Perfil perfil = persistencia.consultar(id);
        Pessoa pessoa = pessoaDAO.consultar(perfil.getPessoa().getId());
        ArrayList<Combinacao> combinacoes = combinacaoServices.retornaCombinacoesPendentes(perfil);
        perfil.setPessoa(pessoa);
        perfil.setCombinacoes(combinacoes);
        ArrayList<Integer> habilidadeId = conexaoHabilidade.retornaMateriaAtivas(perfil.getId());
        ArrayList<Integer> necessidadeId = conexaoNecessidade.retornaMateriaAtivas(perfil.getId());
        montaListaHabilidades(perfil, habilidadeId);
        montaListaNecessidades(perfil, necessidadeId);
        return perfil;
    }
    public Perfil consultar(int id) {
        Perfil perfil = persistencia.consultar(id);
        Pessoa pessoa = pessoaDAO.consultar(perfil.getPessoa().getId());
        ArrayList<Combinacao> combinacoes = combinacaoServices.retornaCombinacoes(perfil);
        perfil.setPessoa(pessoa);
        perfil.setCombinacoes(combinacoes);
        ArrayList<Integer> habilidadeId = conexaoHabilidade.retornaMateriaAtivas(perfil.getId());
        ArrayList<Integer> necessidadeId = conexaoNecessidade.retornaMateriaAtivas(perfil.getId());
        montaListaHabilidades(perfil, habilidadeId);
        montaListaNecessidades(perfil, necessidadeId);
        return perfil;
    }

    public void insereHabilidade(Perfil perfil, Materia materia) throws UsuarioException {
        if (!verificaExistencia(perfil, materia, tipoConexao.HABILIDADE)) {
            conexaoHabilidade.insereConexao(perfil.getId(), materia.getId());
        } else {
            conexaoHabilidade.restabeleceConexao(perfil.getId(), materia.getId());
        }
    }

    public void insereNecessidade(Perfil perfil, Materia materia) throws UsuarioException {
        if (!verificaExistencia(perfil, materia, tipoConexao.NECESSIDADE)) {
            conexaoNecessidade.insereConexao(perfil.getId(), materia.getId());
        } else {
            conexaoNecessidade.restabeleceConexao(perfil.getId(), materia.getId());
        }
    }

    public void deletarHabilidade(Perfil perfil, Materia materia) {
        conexaoHabilidade.desativarConexao(perfil.getId(), materia.getId());
    }

    public void deletarNecessidade(Perfil perfil, Materia materia) {
        conexaoNecessidade.desativarConexao(perfil.getId(), materia.getId());
    }

    public ArrayList<Perfil> listarPerfil(Materia materia) {
        ArrayList<Perfil> perfils = new ArrayList<>();
        ArrayList<Integer> listaIds = conexaoHabilidade.retornaUsuarios(materia.getId());
        for (int id : listaIds) {
            perfils.add(consultar(id));
        }
        return perfils;
    }

    public ArrayList<Perfil> listarPerfil2(Materia materia) {
        ArrayList<Perfil> perfils = new ArrayList<>();
        ArrayList<Integer> listaIds = conexaoNecessidade.retornaUsuarios(materia.getId());
        for (int id : listaIds) {
            perfils.add(consultar(id));
        }
        return perfils;
    }

    public ArrayList<Materia> listarHabilidade(Perfil perfil) {
        ArrayList<Materia> listaMateria = new ArrayList<>();
        ArrayList<Integer> lista = conexaoHabilidade.retornaMateriaAtivas(perfil.getId());

        if (!lista.isEmpty()) {
            for (int id : lista) {
                listaMateria.add(materiaServices.consultar(id));
            }
        }
        return listaMateria;
    }

    public ArrayList<Materia> listarNecessidade(Perfil perfil) {
        ArrayList<Materia> listaNecessidade = new ArrayList<>();
        ArrayList<Integer> lista = conexaoNecessidade.retornaMateriaAtivas(perfil.getId());

        if (!lista.isEmpty()) {
            for (int id : lista) {
                listaNecessidade.add(materiaServices.consultar(id));
            }
        }
        return listaNecessidade;
    }

    private boolean verificaExistencia(Perfil perfil, Materia materia, tipoConexao tipo) throws UsuarioException {
        boolean retorno = false;
        if (tipo == tipoConexao.HABILIDADE) {
            if (perfil.getHabilidades().contains(materia)) {
                if (conexaoHabilidade.retornaStatus(perfil.getId(), materia.getId()) == Status.ATIVADO.getValor()) {
                    throw new UsuarioException("Você já cadastrou essa habilidade");
                } else {
                    retorno = true;
                }
            }
        } else {
            if (perfil.getNecessidades().contains(materia)) {
                if (conexaoNecessidade.retornaStatus(perfil.getId(), materia.getId()) == Status.ATIVADO.getValor()) {
                    throw new UsuarioException("Você já cadastrou essa necessidade");
                } else {
                    retorno = true;
                }

            }
        }
        return retorno;
    }

    private void montaListaHabilidades(Perfil perfil, ArrayList<Integer> habilidadeId) {
        for (int i : habilidadeId) {
            perfil.addHabilidade(materiaServices.consultar(i));
        }
    }

    private void montaListaNecessidades(Perfil perfil, ArrayList<Integer> necessidadeId) {
        for (int j : necessidadeId) {
            perfil.addNecessidade(materiaServices.consultar(j));
        }
    }

    public String retornaStringListaHabilidades(int id) {
        Perfil perfil = this.consultar(id);
        StringBuilder sb = new StringBuilder();

        ArrayList<Materia> lista = perfil.getHabilidades();

        String prefix = "";
        for (Materia mat : lista) {
            sb.append(prefix);
            prefix = ", ";
            sb.append(mat.getNome());
        }
        return sb.toString();
    }

    public String retornaStringListaNecessidades(int id) {
        Perfil perfil = this.consultar(id);
        StringBuilder sb = new StringBuilder();

        ArrayList<Materia> lista = perfil.getNecessidades();

        String prefix = "";
        for (Materia mat : lista) {
            sb.append(prefix);
            prefix = ", ";
            sb.append(mat.getNome());
        }
        return sb.toString();
    }

    public ArrayList<Materia> recomendador(Materia materia) {
        ArrayList<PesoRecomendacao> recomendacaoValores = retornaVetorSimilaridade(materia.getId());
        ArrayList<Materia> materias = new ArrayList<>();
        Iterator iter = recomendacaoValores.iterator();
        while (iter.hasNext()) {
            PesoRecomendacao valor = (PesoRecomendacao) iter.next();
            if (valor.getValor().equals(0.0)) {
                iter.remove();
            }
        }
        Collections.sort(recomendacaoValores, new Comparator<PesoRecomendacao>() {
            @Override
            public int compare(PesoRecomendacao o1, PesoRecomendacao o2) {
                return o1.compareTo(o2);
            }
        });
        for (PesoRecomendacao pesos : recomendacaoValores) {
            materias.add(materiaServices.consultar(pesos.getMateriaId2()));
        }
        return materias;
    }


    private ArrayList<PesoRecomendacao> retornaVetorSimilaridade(int materiaId) {
        ArrayList<VetorMateria> materias = new ArrayList<>();
        ArrayList<PesoRecomendacao> cusines = new ArrayList<>();
        ArrayList<Integer> listaUsuarios = conexaoNecessidade.retornaUsuarios(materiaId);
        materias.addAll(conexaoNecessidade.retornaFrequencia(materiaId));
        VetorMateria materiaAtiva = null;

        for (VetorMateria vetorMateria : materias) {
            vetorMateria.setArrayCombinacao(transformaVetor(vetorMateria, listaUsuarios));
            if (vetorMateria.getMateria() == materiaId) {
                materiaAtiva = vetorMateria;
            }
        }
        for (VetorMateria vetorMateria : materias) {
            if (!vetorMateria.equals(materiaAtiva)) {
                PesoRecomendacao recomendacaoValor = new PesoRecomendacao();
                recomendacaoValor.setMateriaId1(materiaId);
                recomendacaoValor.setMateriaId2(vetorMateria.getMateria());
                recomendacaoValor.setValor(retornaCusineSimilaridade(materiaAtiva.getArrayCombinacao(), vetorMateria.getArrayCombinacao()));
                if (!cusines.contains(recomendacaoValor)) {
                    cusines.add(recomendacaoValor);
                }
            }
        }
        return cusines;
    }

    private Integer[] transformaVetor(VetorMateria freq, ArrayList<Integer> lista) {
        Integer[] listaPerfil = new Integer[lista.size()];
        listaPerfil = lista.toArray(listaPerfil);
        for (int i = 0; i < listaPerfil.length; i++) {
            if (!freq.getPerfisID().contains(listaPerfil[i])) {
                listaPerfil[i] = 0;
            }
        }
        for (int i = 0; i < listaPerfil.length; i++) {
            if (listaPerfil[i] > 0) {
                listaPerfil[i] = 1;
            }
        }

        return listaPerfil;
    }

    private Double retornaCusineSimilaridade(Integer[] vectorA, Integer[] vectorB) {
        double produto = 0.0;
        double normaA = 0.0;
        double normaB = 0.0;
        for (int i = 0; i < vectorA.length; i++) {
            produto += vectorA[i] * vectorB[i];
            normaA += Math.pow(vectorA[i], 2);
            normaB += Math.pow(vectorB[i], 2);
        }
        return produto / (Math.sqrt(normaA) * Math.sqrt(normaB));
    }

}
