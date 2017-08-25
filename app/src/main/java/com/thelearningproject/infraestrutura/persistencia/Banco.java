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

    //Metodo onCreate extenso apenas para população do banco de forma direta, por motivos de teste

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
                DESCRICAO_PERFIL + " TEXT)";

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

        banco.execSQL("INSERT INTO USUARIOS (ID, EMAIL, SENHA, STATUS)\n" +
                "VALUES (1, 'luciano@email.com.br',\n" +
                "'6B86B273FF34FCE19D6B804EFF5A3F5747ADA4EAA22F1D49C01E52DDB7875B4B', 0)");
        banco.execSQL("INSERT INTO USUARIOS (ID, EMAIL, SENHA, STATUS)\n" +
                "VALUES (2, 'gabriel@email.com.br',\n" +
                "'6B86B273FF34FCE19D6B804EFF5A3F5747ADA4EAA22F1D49C01E52DDB7875B4B', 0)");
        banco.execSQL("INSERT INTO USUARIOS (ID, EMAIL, SENHA, STATUS)\n" +
                "VALUES (3, 'heitor@email.com.br',\n" +
                "'6B86B273FF34FCE19D6B804EFF5A3F5747ADA4EAA22F1D49C01E52DDB7875B4B', 0)");
        banco.execSQL("INSERT INTO USUARIOS (ID, EMAIL, SENHA, STATUS)\n" +
                "VALUES (4, 'nicollas@email.com.br',\n" +
                "'6B86B273FF34FCE19D6B804EFF5A3F5747ADA4EAA22F1D49C01E52DDB7875B4B', 0)");
        banco.execSQL("INSERT INTO USUARIOS (ID, EMAIL, SENHA, STATUS)\n" +
                "VALUES (5, 'nicolas@email.com.br',\n" +
                "'6B86B273FF34FCE19D6B804EFF5A3F5747ADA4EAA22F1D49C01E52DDB7875B4B', 0)");
        banco.execSQL("INSERT INTO USUARIOS (ID, EMAIL, SENHA, STATUS)\n" +
                "VALUES (6, 'ebony@email.com.br',\n" +
                "'6B86B273FF34FCE19D6B804EFF5A3F5747ADA4EAA22F1D49C01E52DDB7875B4B', 0)");
        banco.execSQL("INSERT INTO USUARIOS (ID, EMAIL, SENHA, STATUS)\n" +
                "VALUES (7, 'renata@email.com.br',\n" +
                "'6B86B273FF34FCE19D6B804EFF5A3F5747ADA4EAA22F1D49C01E52DDB7875B4B', 0)");
        banco.execSQL("INSERT INTO USUARIOS (ID, EMAIL, SENHA, STATUS)\n" +
                "VALUES (8, 'marilia@email.com.br',\n" +
                "'6B86B273FF34FCE19D6B804EFF5A3F5747ADA4EAA22F1D49C01E52DDB7875B4B', 0)");
        banco.execSQL("INSERT INTO USUARIOS (ID, EMAIL, SENHA, STATUS)\n" +
                "VALUES (9, 'tracer@email.com.br',\n" +
                "'6B86B273FF34FCE19D6B804EFF5A3F5747ADA4EAA22F1D49C01E52DDB7875B4B', 0)");
        banco.execSQL("INSERT INTO USUARIOS (ID, EMAIL, SENHA, STATUS)\n" +
                "VALUES (10, 'andressa@email.com.br',\n" +
                "'6B86B273FF34FCE19D6B804EFF5A3F5747ADA4EAA22F1D49C01E52DDB7875B4B', 0)");
        banco.execSQL("INSERT INTO USUARIOS (ID, EMAIL, SENHA, STATUS)\n" +
                "VALUES (11, 'larissa@email.com.br',\n" +
                "'6B86B273FF34FCE19D6B804EFF5A3F5747ADA4EAA22F1D49C01E52DDB7875B4B', 0)");
        banco.execSQL("INSERT INTO USUARIOS (ID, EMAIL, SENHA, STATUS)\n" +
                "VALUES (12, 'mariana@email.com.br',\n" +
                "'6B86B273FF34FCE19D6B804EFF5A3F5747ADA4EAA22F1D49C01E52DDB7875B4B', 0)");

        banco.execSQL("INSERT INTO PESSOAS (ID, NOME, USUARIO, TELEFONE)\n" +
                "VALUES (1, 'Luciano Trigueiro', 1, '81987860852')");
        banco.execSQL("INSERT INTO PESSOAS (ID, NOME, USUARIO, TELEFONE)\n" +
                "VALUES (2, 'Gabriel Lourenço', 2, '81996716745')");
        banco.execSQL("INSERT INTO PESSOAS (ID, NOME, USUARIO, TELEFONE)\n" +
                "VALUES (3, 'Heitor Augusto', 3, '81999267720')");
        banco.execSQL("INSERT INTO PESSOAS (ID, NOME, USUARIO, TELEFONE)\n" +
                "VALUES (4, 'Nicollas Bastos', 4, '81991311564')");
        banco.execSQL("INSERT INTO PESSOAS (ID, NOME, USUARIO, TELEFONE)\n" +
                "VALUES (5, 'Nicolas Moura', 5, '81996906155')");
        banco.execSQL("INSERT INTO PESSOAS (ID, NOME, USUARIO, TELEFONE)\n" +
                "VALUES (6, 'Ebony Marques', 6, '81983695032')");
        banco.execSQL("INSERT INTO PESSOAS (ID, NOME, USUARIO, TELEFONE)\n" +
                "VALUES (7, 'Renata Albuquerque', 7, '81987860853')");
        banco.execSQL("INSERT INTO PESSOAS (ID, NOME, USUARIO, TELEFONE)\n" +
                "VALUES (8, 'Marilia Andrade', 8, '81996716746')");
        banco.execSQL("INSERT INTO PESSOAS (ID, NOME, USUARIO, TELEFONE)\n" +
                "VALUES (9, 'Tracer Augusto', 9, '81999267721')");
        banco.execSQL("INSERT INTO PESSOAS (ID, NOME, USUARIO, TELEFONE)\n" +
                "VALUES (10, 'Andressa Luna', 10, '81991311565')");
        banco.execSQL("INSERT INTO PESSOAS (ID, NOME, USUARIO, TELEFONE)\n" +
                "VALUES (11, 'Larissa Gondim', 11, '81996906156')");
        banco.execSQL("INSERT INTO PESSOAS (ID, NOME, USUARIO, TELEFONE)\n" +
                "VALUES (12, 'Mariana Brito', 12, '81983695033')");

        banco.execSQL("INSERT INTO MATERIAS (ID, NOME)\n" +
                "VALUES (1, 'matematica')");
        banco.execSQL("INSERT INTO MATERIAS (ID, NOME)\n" +
                "VALUES (2, 'portugues')");
        banco.execSQL("INSERT INTO MATERIAS (ID, NOME)\n" +
                "VALUES (3, 'ciencias')");
        banco.execSQL("INSERT INTO MATERIAS (ID, NOME)\n" +
                "VALUES (4, 'ingles')");
        banco.execSQL("INSERT INTO MATERIAS (ID, NOME)\n" +
                "VALUES (5, 'geografia')");
        banco.execSQL("INSERT INTO MATERIAS (ID, NOME)\n" +
                "VALUES (6, 'historia')");
        banco.execSQL("INSERT INTO MATERIAS (ID, NOME)\n" +
                "VALUES (7, 'biologia')");
        banco.execSQL("INSERT INTO MATERIAS (ID, NOME)\n" +
                "VALUES (8, 'quimica')");
        banco.execSQL("INSERT INTO MATERIAS (ID, NOME)\n" +
                "VALUES (9, 'literatura')");
        banco.execSQL("INSERT INTO MATERIAS (ID, NOME)\n" +
                "VALUES (10, 'programacao')");
        banco.execSQL("INSERT INTO MATERIAS (ID, NOME)\n" +
                "VALUES (11, 'robotica')");
        banco.execSQL("INSERT INTO MATERIAS (ID, NOME)\n" +
                "VALUES (12, 'alemao')");
        banco.execSQL("INSERT INTO MATERIAS (ID, NOME)\n" +
                "VALUES (13, 'espanhol')");
        banco.execSQL("INSERT INTO MATERIAS (ID, NOME)\n" +
                "VALUES (14, 'fisica')");
        banco.execSQL("INSERT INTO MATERIAS (ID, NOME)\n" +
                "VALUES (15, 'gramatica')");
        banco.execSQL("INSERT INTO MATERIAS (ID, NOME)\n" +
                "VALUES (16, 'redacao')");

        banco.execSQL("INSERT INTO PERFIS (ID, PESSOA, DESCRICAO)\n" +
                "VALUES (1, 1, 'Aenean nec tempor metus, vel')");
        banco.execSQL("INSERT INTO PERFIS (ID, PESSOA, DESCRICAO)\n" +
                "VALUES (2, 2, 'Nulla ultricies aliquam quam, vitae')");
        banco.execSQL("INSERT INTO PERFIS (ID, PESSOA, DESCRICAO)\n" +
                "VALUES (3, 3, 'Aliquam diam libero, maximus et')");
        banco.execSQL("INSERT INTO PERFIS (ID, PESSOA, DESCRICAO)\n" +
                "VALUES (4, 4, 'Duis pretium egestas diam, at')");
        banco.execSQL("INSERT INTO PERFIS (ID, PESSOA, DESCRICAO)\n" +
                "VALUES (5, 5, 'Cras fringilla, ante ac pharetra')");
        banco.execSQL("INSERT INTO PERFIS (ID, PESSOA, DESCRICAO)\n" +
                "VALUES (6, 6, 'Cras suscipit ipsum eget massa')");
        banco.execSQL("INSERT INTO PERFIS (ID, PESSOA, DESCRICAO)\n" +
                "VALUES (7, 7, 'Sed scelerisque, ex vitae accumsan')");
        banco.execSQL("INSERT INTO PERFIS (ID, PESSOA, DESCRICAO)\n" +
                "VALUES (8, 8, 'Quisque et euismod ligula, sit')");
        banco.execSQL("INSERT INTO PERFIS (ID, PESSOA, DESCRICAO)\n" +
                "VALUES (9, 9, 'Donec a lorem sed purus')");
        banco.execSQL("INSERT INTO PERFIS (ID, PESSOA, DESCRICAO)\n" +
                "VALUES (10, 10, 'Donec non diam pellentesque, varius')");
        banco.execSQL("INSERT INTO PERFIS (ID, PESSOA, DESCRICAO)\n" +
                "VALUES (11, 11, 'Praesent semper congue dignissim. Nulla')");
        banco.execSQL("INSERT INTO PERFIS (ID, PESSOA, DESCRICAO)\n" +
                "VALUES (12, 12, 'Suspendisse non mattis mi. Duis')");

        banco.execSQL("INSERT INTO CONEXAOHABILIDADE (PERFIL, MATERIA, STATUS)\n" +
                "VALUES (1, 1, 0)");
        banco.execSQL("INSERT INTO CONEXAOHABILIDADE (PERFIL, MATERIA, STATUS)\n" +
                "VALUES (1, 2, 0)");
        banco.execSQL("INSERT INTO CONEXAOHABILIDADE (PERFIL, MATERIA, STATUS)\n" +
                "VALUES (2, 2, 0)");
        banco.execSQL("INSERT INTO CONEXAOHABILIDADE (PERFIL, MATERIA, STATUS)\n" +
                "VALUES (3, 3, 0)");
        banco.execSQL("INSERT INTO CONEXAOHABILIDADE (PERFIL, MATERIA, STATUS)\n" +
                "VALUES (4, 2, 0)");
        banco.execSQL("INSERT INTO CONEXAOHABILIDADE (PERFIL, MATERIA, STATUS)\n" +
                "VALUES (4, 4, 0)");
        banco.execSQL("INSERT INTO CONEXAOHABILIDADE (PERFIL, MATERIA, STATUS)\n" +
                "VALUES (5, 2, 0)");
        banco.execSQL("INSERT INTO CONEXAOHABILIDADE (PERFIL, MATERIA, STATUS)\n" +
                "VALUES (5, 5, 0)");
        banco.execSQL("INSERT INTO CONEXAOHABILIDADE (PERFIL, MATERIA, STATUS)\n" +
                "VALUES (6, 6, 0)");
        banco.execSQL("INSERT INTO CONEXAOHABILIDADE (PERFIL, MATERIA, STATUS)\n" +
                "VALUES (7, 4, 0)");
        banco.execSQL("INSERT INTO CONEXAOHABILIDADE (PERFIL, MATERIA, STATUS)\n" +
                "VALUES (8, 1, 0)");
        banco.execSQL("INSERT INTO CONEXAOHABILIDADE (PERFIL, MATERIA, STATUS)\n" +
                "VALUES (9, 4, 0)");
        banco.execSQL("INSERT INTO CONEXAOHABILIDADE (PERFIL, MATERIA, STATUS)\n" +
                "VALUES (10, 5, 0)");
        banco.execSQL("INSERT INTO CONEXAOHABILIDADE (PERFIL, MATERIA, STATUS)\n" +
                "VALUES (11, 6, 0)");
        banco.execSQL("INSERT INTO CONEXAOHABILIDADE (PERFIL, MATERIA, STATUS)\n" +
                "VALUES (12, 1, 0)");

        banco.execSQL("INSERT INTO CONEXAONECESSIDADE (PERFIL, MATERIA, STATUS)\n" +
                "VALUES (1, 3, 0)");
        banco.execSQL("INSERT INTO CONEXAONECESSIDADE (PERFIL, MATERIA, STATUS)\n" +
                "VALUES (1, 4, 0)");
        banco.execSQL("INSERT INTO CONEXAONECESSIDADE (PERFIL, MATERIA, STATUS)\n" +
                "VALUES (2, 1, 0)");
        banco.execSQL("INSERT INTO CONEXAONECESSIDADE (PERFIL, MATERIA, STATUS)\n" +
                "VALUES (2, 3, 0)");
        banco.execSQL("INSERT INTO CONEXAONECESSIDADE (PERFIL, MATERIA, STATUS)\n" +
                "VALUES (3, 4, 0)");
        banco.execSQL("INSERT INTO CONEXAONECESSIDADE (PERFIL, MATERIA, STATUS)\n" +
                "VALUES (4, 1, 0)");
        banco.execSQL("INSERT INTO CONEXAONECESSIDADE (PERFIL, MATERIA, STATUS)\n" +
                "VALUES (5, 4, 0)");
        banco.execSQL("INSERT INTO CONEXAONECESSIDADE (PERFIL, MATERIA, STATUS)\n" +
                "VALUES (6, 1, 0)");
        banco.execSQL("INSERT INTO CONEXAONECESSIDADE (PERFIL, MATERIA, STATUS)\n" +
                "VALUES (7, 1, 0)");
        banco.execSQL("INSERT INTO CONEXAONECESSIDADE (PERFIL, MATERIA, STATUS)\n" +
                "VALUES (8, 2, 0)");
        banco.execSQL("INSERT INTO CONEXAONECESSIDADE (PERFIL, MATERIA, STATUS)\n" +
                "VALUES (9, 3, 0)");
        banco.execSQL("INSERT INTO CONEXAONECESSIDADE (PERFIL, MATERIA, STATUS)\n" +
                "VALUES (10, 4, 0)");
        banco.execSQL("INSERT INTO CONEXAONECESSIDADE (PERFIL, MATERIA, STATUS)\n" +
                "VALUES (11, 5, 0)");
        banco.execSQL("INSERT INTO CONEXAONECESSIDADE (PERFIL, MATERIA, STATUS)\n" +
                "VALUES (12, 6, 0)");

        banco.execSQL("INSERT INTO COMBINACAOPERFIL (ID_PERFIL1, ID_PERFIL2, STATUS)\n" +
                "VALUES (1, 7, 0)");
        banco.execSQL("INSERT INTO COMBINACAOPERFIL (ID_PERFIL1, ID_PERFIL2, STATUS)\n" +
                "VALUES (2, 8, 0)");
        banco.execSQL("INSERT INTO COMBINACAOPERFIL (ID_PERFIL1, ID_PERFIL2, STATUS)\n" +
                "VALUES (3, 9, 0)");
        banco.execSQL("INSERT INTO COMBINACAOPERFIL (ID_PERFIL1, ID_PERFIL2, STATUS)\n" +
                "VALUES (4, 10, 0)");
        banco.execSQL("INSERT INTO COMBINACAOPERFIL (ID_PERFIL1, ID_PERFIL2, STATUS)\n" +
                "VALUES (5, 11, 0)");
        banco.execSQL("INSERT INTO COMBINACAOPERFIL (ID_PERFIL1, ID_PERFIL2, STATUS)\n" +
                "VALUES (6, 12, 0)");
        banco.execSQL("INSERT INTO COMBINACAOPERFIL (ID_PERFIL1, ID_PERFIL2, STATUS)\n" +
                "VALUES (7, 1, 0)");
        banco.execSQL("INSERT INTO COMBINACAOPERFIL (ID_PERFIL1, ID_PERFIL2, STATUS)\n" +
                "VALUES (8, 2, 0)");
        banco.execSQL("INSERT INTO COMBINACAOPERFIL (ID_PERFIL1, ID_PERFIL2, STATUS)\n" +
                "VALUES (9, 3, 0)");
        banco.execSQL("INSERT INTO COMBINACAOPERFIL (ID_PERFIL1, ID_PERFIL2, STATUS)\n" +
                "VALUES (10, 4, 0)");
        banco.execSQL("INSERT INTO COMBINACAOPERFIL (ID_PERFIL1, ID_PERFIL2, STATUS)\n" +
                "VALUES (11, 5, 0)");
        banco.execSQL("INSERT INTO COMBINACAOPERFIL (ID_PERFIL1, ID_PERFIL2, STATUS)\n" +
                "VALUES (12, 6, 0)");
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

