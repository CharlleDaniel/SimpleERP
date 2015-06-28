package com.simpleerp.database;

/**
 * Created by Thainan on 28/06/15.
 */
public class ScriptSQL {

    public String getCreateInsumo(){
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE IF NOT EXISTS INSUMO ( ");
        sqlBuilder.append("_id          INTEGER      NOT NULL PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append("Nome         VARCHAR (60), ");
        sqlBuilder.append("Quantidade   FLOAT, ");
        sqlBuilder.append("CustoUnidade FLOAT ");
        sqlBuilder.append("); ");

        return sqlBuilder.toString();
    }


    public String getCreateProducao(){
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE IF NOT EXISTS PRODUCAO ( ");
        sqlBuilder.append("_id       INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append("idUsuario INTEGER REFERENCES USUARIO (_id) ");
        sqlBuilder.append("); ");

        return sqlBuilder.toString();
    }


    public String getCreateProducaoProduto(){
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE IF NOT EXISTS PRODUCAO_PRODUTO ( ");
        sqlBuilder.append("_id        INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append("idProducao INTEGER NOT NULL, ");
        sqlBuilder.append("idProduto  INTEGER NOT NULL, ");
        sqlBuilder.append("FOREIGN KEY (idProducao) REFERENCES PRODUCAO (_id), ");
        sqlBuilder.append("FOREIGN KEY (idProduto) REFERENCES PRODUTO (_id) ");
        sqlBuilder.append("); ");

        return sqlBuilder.toString();
    }


    public String getCreateProduto(){
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE IF NOT EXISTS PRODUTO ( ");
        sqlBuilder.append("_id   INTEGER      NOT NULL PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append("nome  VARCHAR (60), ");
        sqlBuilder.append("preco FLOAT ");
        sqlBuilder.append("); ");

        return sqlBuilder.toString();
    }


    public String getCreateProdutoInsumo(){
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE IF NOT EXISTS PRODUTO_INSUMO ( ");
        sqlBuilder.append("_id       INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append("idProduto INTEGER NOT NULL, ");
        sqlBuilder.append("idInsumo  INTEGET NOT NULL, ");
        sqlBuilder.append("FOREIGN KEY (idProduto) REFERENCES PRODUTO (_id), ");
        sqlBuilder.append("FOREIGN KEY (idInsumo) REFERENCES INSUMO (_id) ");
        sqlBuilder.append("); ");

        return sqlBuilder.toString();
    }


    public String getCreateUsuario(){
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE IF NOT EXISTS USUARIO ( ");
        sqlBuilder.append("_id   INTEGER      NOT NULL PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append("NOME  VARCHAR (60), ");
        sqlBuilder.append("LOGIN VARCHAR (30), ");
        sqlBuilder.append("SENHA VARCHAR (15) ");
        sqlBuilder.append("); ");

        return sqlBuilder.toString();
    }
}
