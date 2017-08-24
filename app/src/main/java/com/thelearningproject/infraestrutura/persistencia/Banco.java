package com.thelearningproject.infraestrutura.persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Criado por Ebony Marques on 17/07/2017.
 */

public final class Banco extends SQLiteOpenHelper {
    private static Banco instancia;
    private static final String NOME_BANCO = "dados";
    private static final int VERSION = 1;

    private static final String PERFIL = "perfil";
    private static final String MATERIA = "materia";
    private static final String STATUS = "status";

    private static final String TABELA_PESSOAS = "pessoas";
    private static final String ID_PESSOA = "id";
    private static final String NOME_PESSOA = "nome";
    private static final String USUARIO_PESSOA = "usuario";
    private static final String TELEFONE_PESSOA = "telefone";

    private static final String TABELA_USUARIOS = "usuarios";
    private static final String ID_USUARIO = "id";
    private static final String EMAIL_USUARIO = "email";
    private static final String SENHA_USUARIO = "senha";
    private static final String STATUS_USUARIO = STATUS;

    private static final String TABELA_PERFIS = "perfis";
    private static final String ID_PERFIL = "id";
    private static final String PESSOA_PERFIL = "pessoa";
    private static final String DESCRICAO_PERFIL = "descricao";
    private static final String HABILIDADES_PERFIL = "habilidade";

    private static final String TABELA_MATERIAS = "materias";
    private static final String ID_MATERIA = "id";
    private static final String NOME_MATERIA = "nome";

    private static final String TABELA_CONEXAO_NECESSIDADES = "conexaonecessidade";
    private static final String IDPERFIL_NECESSIDADE = PERFIL;
    private static final String IDMATERIA_NECESSIDADE = MATERIA;
    private static final String STATUS_CONEXAO_NECESSIDADE = STATUS;

    private static final String TABELA_CONEXAO_HABILIDADES = "conexaohabilidade";
    private static final String IDPERFIL_HABILIDADE = PERFIL;
    private static final String IDMATERIA_HABILIDADE = MATERIA;
    private static final String STATUS_CONEXAO_HABILIDADE = STATUS;

    private static final String TABELA_DADOS_BUSCA = "dadosbusca";
    private static final String IDPERFIL_BUSCA = PERFIL;
    private static final String MATERIA_BUSCA = MATERIA;

    private static final String TABELA_COMBINACAO = "combinacaoperfil";
    private static final String IDPERFIL1_COMBINACAO = "id_perfil1";
    private static final String IDPERFIL2_COMBINACAO = "id_perfil2";
    private static final String STATUS_COMBINACAO = STATUS;

    public static synchronized Banco getInstancia(Context context) {
        if (instancia == null) {
            instancia = new Banco(context.getApplicationContext());
        }
        return instancia;
    }

    private Banco(Context context) {
        super(context, NOME_BANCO, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase banco) {
        String usuarios = "CREATE TABLE " + TABELA_USUARIOS + "(" +
                ID_USUARIO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EMAIL_USUARIO + " TEXT, " +
                SENHA_USUARIO + " TEXT, " +
                STATUS_USUARIO + " INTEGER)";

        String dadosBusca = "CREATE TABLE " + TABELA_DADOS_BUSCA + " (" +
                IDPERFIL_BUSCA + " INTEGER, " +
                MATERIA_BUSCA + " VARCHAR, " +
                "PRIMARY KEY(" + IDPERFIL_BUSCA + ", " + MATERIA_BUSCA + "))";

        String pessoas = "CREATE TABLE " + TABELA_PESSOAS + "(" +
                ID_PESSOA + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NOME_PESSOA + " TEXT, " +
                USUARIO_PESSOA + " INTEGER, " +
                TELEFONE_PESSOA + " TEXT)";

        String perfis = "CREATE TABLE " + TABELA_PERFIS + "(" +
                ID_PERFIL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PESSOA_PERFIL + " INTEGER, " +
                DESCRICAO_PERFIL + " TEXT, " +
                HABILIDADES_PERFIL + " INTEGER)";

        String habilidades = "CREATE TABLE " + TABELA_CONEXAO_HABILIDADES + " (" +
                IDPERFIL_HABILIDADE + " INTEGER, " +
                IDMATERIA_HABILIDADE + " INTEGER, " +
                STATUS_CONEXAO_HABILIDADE + " INTEGER, " +
                "PRIMARY KEY(" + IDPERFIL_HABILIDADE + ", " + IDMATERIA_HABILIDADE + "))";

        String necessidades = "CREATE TABLE " + TABELA_CONEXAO_NECESSIDADES + " (" +
                IDPERFIL_NECESSIDADE + " INTEGER, " +
                IDMATERIA_NECESSIDADE + " INTEGER, " +
                STATUS_CONEXAO_NECESSIDADE + " INTEGER, " +
                "PRIMARY KEY(" + IDPERFIL_NECESSIDADE + ", " + IDMATERIA_NECESSIDADE + "))";

        String materias = "CREATE TABLE " + TABELA_MATERIAS + "(" +
                ID_MATERIA + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NOME_MATERIA + " TEXT)";

        String combinacao = "CREATE TABLE " + TABELA_COMBINACAO + "(" +
                IDPERFIL1_COMBINACAO + " INTEGER, " +
                IDPERFIL2_COMBINACAO + " INTEGER, " +
                STATUS_COMBINACAO + " INTEGER, " +
                "PRIMARY KEY(" + IDPERFIL1_COMBINACAO + ", " + IDPERFIL2_COMBINACAO + "))";

        banco.execSQL(usuarios);
        banco.execSQL(dadosBusca);
        banco.execSQL(pessoas);
        banco.execSQL(perfis);
        banco.execSQL(habilidades);
        banco.execSQL(necessidades);
        banco.execSQL(materias);
        banco.execSQL(combinacao);

    }

    @Override
    public void onUpgrade(SQLiteDatabase banco, int oldVersion, int newVersion) {
        String usuarios = "DROP TABLE IF EXISTS " + TABELA_USUARIOS;
        banco.execSQL(usuarios);

        String dadosBusca = "DROP TABLE IF EXISTS " + TABELA_DADOS_BUSCA;
        banco.execSQL(dadosBusca);

        String pessoas = "DROP TABLE IF EXISTS " + TABELA_PESSOAS;
        banco.execSQL(pessoas);

        String perfis = "DROP TABLE IF EXISTS " + TABELA_PERFIS;
        banco.execSQL(perfis);

        String habilidades = "DROP TABLE IF EXISTS " + TABELA_CONEXAO_HABILIDADES;
        banco.execSQL(habilidades);

        String necessidades = "DROP TABLE IF EXISTS " + TABELA_CONEXAO_NECESSIDADES;
        banco.execSQL(necessidades);

        String materias = "DROP TABLE IF EXISTS " + TABELA_MATERIAS;
        banco.execSQL(materias);

        String combinacao = "DROP TABLE IF EXISTS " + TABELA_COMBINACAO;
        banco.execSQL(combinacao);

        onCreate(banco);
    }


}

