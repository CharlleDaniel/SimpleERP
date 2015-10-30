package com.simpleerp.database;


/**
 * Created by Thainan on 28/06/15.
 */
public class ScriptSQL {

    public String getCreateInsumo(){
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("create table insumo(");
        sqlBuilder.append("_id integer primary key autoincrement,");
        sqlBuilder.append("Nome text not null,");
        sqlBuilder.append("Quantidade float not null,");
        sqlBuilder.append("CustoUnidade float not null,");
        sqlBuilder.append("Tipo text not null");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }


    public String getCreateProducao(){
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE IF NOT EXISTS PRODUCAO(");
        sqlBuilder.append("_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,");
        sqlBuilder.append("NOME VARCHAR(60)");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }


    public String getCreateProducaoProduto(){
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE IF NOT EXISTS PRODUCAO_PRODUTO(");
        sqlBuilder.append("_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,");
        sqlBuilder.append("idProducao INTEGER NOT NULL,");
        sqlBuilder.append("idProduto  INTEGER NOT NULL,");
        sqlBuilder.append("FOREIGN KEY (idProducao) REFERENCES PRODUCAO (_id),");
        sqlBuilder.append("FOREIGN KEY (idProduto) REFERENCES PRODUTO (_id)");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }


    public String getCreateProduto(){
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE IF NOT EXISTS PRODUTO(");
        sqlBuilder.append("_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,");
        sqlBuilder.append("NOME VARCHAR(60),");
        sqlBuilder.append("PRECO FLOAT");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }


    public String getCreateProdutoInsumo(){
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE IF NOT EXISTS PRODUTO_INSUMO (");
        sqlBuilder.append("_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,");
        sqlBuilder.append("idProduto INTEGER NOT NULL,");
        sqlBuilder.append("idInsumo INTEGER NOT NULL,");
        sqlBuilder.append("FOREIGN KEY (idProduto) REFERENCES PRODUTO (_id),");
        sqlBuilder.append("FOREIGN KEY (idInsumo) REFERENCES INSUMO (_id)");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }


    public String getCreateUsuario(){
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("create table usuario(");
        sqlBuilder.append("_id integer primary key autoincrement,");
        sqlBuilder.append("nome text not null,");
        sqlBuilder.append("islog text not null );");


        return sqlBuilder.toString();
    }
}
