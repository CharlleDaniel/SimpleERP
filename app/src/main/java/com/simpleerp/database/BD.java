package com.simpleerp.database;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.simpleerp.entidades.Insumo;
import com.simpleerp.entidades.Producao;
import com.simpleerp.entidades.Producao_Produto;
import com.simpleerp.entidades.Produto;
import com.simpleerp.entidades.Produto_Insumo;
import com.simpleerp.entidades.Usuario;

//TODO Alterar os nomes dos métodos para manter o padrão do nome das classes e do BD. Ex: buscarInsumoProduto mudar para buscarProdutoInsumo
//TODO Alterar os nomes dos métodos de relações para o mesmo padrão, com relaçnao à plural e singular. Manter consistência de padrões de nomenclaturas
public class BD {
    private SQLiteDatabase bd;

    public BD(Context context) {
        BDCore auxBd = new BDCore(context);
        bd = auxBd.getWritableDatabase();
    }


    public void inserirUsuario(Usuario usuario) {
        ContentValues valores = new ContentValues();
        valores.put("nome", usuario.getNome());
        valores.put("email", usuario.getEmail());
        valores.put("islog", usuario.isLog());

        bd.insert("usuario", null, valores);
    }


    public void atualizarUsuario(Usuario usuario) {
        ContentValues valores = new ContentValues();
        valores.put("nome", usuario.getNome());
        valores.put("email", usuario.getEmail());
        valores.put("islog", usuario.isLog());

        bd.update("usuario", valores, "_id = ?", new String[]{"" + usuario.getId()});
    }


    public void deletarUsuario(Usuario usuario) {
        bd.delete("usuario", "_id = " + usuario.getId(), null);
    }


    public List<Usuario> buscarUsuario() {
        List<Usuario> list = new ArrayList<Usuario>();
        String[] colunas = new String[]{"_id", "nome","email","islog"};

        Cursor cursor = bd.query("usuario", colunas, null, null, null, null, "nome ASC");

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                Usuario u = new Usuario();
                u.setId(cursor.getInt(0));
                u.setNome(cursor.getString(1));
                u.setEmail(cursor.getString(2));
                u.setIsLog(cursor.getString(3));
                list.add(u);

            } while (cursor.moveToNext());
        }

        return (list);
    }

    public void inserirInsumo(Insumo insumo) {
        ContentValues valores = new ContentValues();
        valores.put("Nome", insumo.getNome());
        valores.put("CustoUnidade", insumo.getCustoUnidade());
        valores.put("Quantidade", insumo.getQuantidade());
        valores.put("Tipo", insumo.getTipo());

        bd.insert("insumo", null, valores);
    }


    public void atualizarInsumo(Insumo insumo) {
        ContentValues valores = new ContentValues();
        valores.put("Nome", insumo.getNome());
        valores.put("CustoUnidade", insumo.getCustoUnidade());
        valores.put("Quantidade", insumo.getQuantidade());
        valores.put("Tipo", insumo.getTipo());

        bd.update("insumo", valores, "_id = ?", new String[]{"" + insumo.getId()});
    }


    public void deletarInsumo(Insumo insumo) {
        bd.delete("insumo", "_id = " + insumo.getId(), null);
    }


    public List<Insumo> buscarInsumos() {
        List<Insumo> list = new ArrayList<Insumo>();
        String[] colunas = new String[]{"_id", "Nome", "CustoUnidade", "Quantidade", "Tipo"};

        Cursor cursor = bd.query("insumo", colunas, null, null, null, null, "Nome ASC");

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                Insumo i = new Insumo();
                i.setId(cursor.getInt(0));
                i.setNome(cursor.getString(1));
                i.setCustoUnidade(cursor.getFloat(2));
                i.setQuantidade(cursor.getFloat(3));
                i.setTipo(cursor.getString(4));
                list.add(i);

            } while (cursor.moveToNext());
        }

        return (list);
    }

    public void inserirProduto(Produto produto) {
        ContentValues valores = new ContentValues();
        valores.put("Nome", produto.getNome());
        valores.put("PRECO", produto.getPreco());
        bd.insert("PRODUTO", null, valores);
    }

    public void atualizarProduto(Produto produto) {
        ContentValues valores = new ContentValues();
        valores.put("Nome", produto.getNome());
        valores.put("PRECO", produto.getPreco());

        bd.update("PRODUTO", valores, "_id = ?", new String[]{"" + produto.getId()});
    }

    public void deletarProduto(Produto produto) {
        bd.delete("PRODUTO", "_id = " + produto.getId(), null);
    }

    public List<Produto> buscarProdutos() {
        List<Produto> list = new ArrayList<Produto>();
        String[] colunas = new String[]{"_id", "NOME", "PRECO"};

        Cursor cursor = bd.query("PRODUTO", colunas, null, null, null, null, "NOME ASC");

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                Produto p = new Produto();
                p.setId(cursor.getInt(0));
                p.setNome(cursor.getString(1));
                p.setPreco(cursor.getFloat(2));

                list.add(p);

            } while (cursor.moveToNext());
        }

        return (list);
    }
    public void inserirInsumoProduto(Produto produto , Insumo i){
        ContentValues valores = new ContentValues();
        valores.put("idProduto", produto.getId());
        valores.put("idInsumo", i.getId());

        bd.insert("PRODUTO_INSUMO", null, valores);
    }
    public void deletarInsumoProduto(Produto produto,Insumo insumo) {
        bd.delete("PRODUTO_INSUMO", "idProduto = " + produto.getId() + " and " + "idInsumo = " + insumo.getId(), null);
    }

    public List<Insumo> buscarInsumoProduto(Produto produto) {
        List<Insumo> list = new ArrayList<Insumo>();
        String[] colunas = new String[]{"idInsumo"};
        Cursor cursor = bd.query("PRODUTO_INSUMO",colunas, "idProduto ="+produto.getId(), null, null, null,null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                String[] colunas2 = new String[]{"_id", "Nome", "CustoUnidade", "Quantidade", "Tipo"};

                Cursor cursor2 = bd.query("insumo", colunas2,"_id = "+cursor.getLong(0), null, null, null, "Nome ASC");
                if (cursor2.getCount() > 0) {

                    cursor2.moveToFirst();
                    Insumo i = new Insumo();
                    i.setId(cursor2.getInt(0));
                    i.setNome(cursor2.getString(1));
                    i.setCustoUnidade(cursor2.getFloat(2));
                    i.setQuantidade(cursor2.getFloat(3));
                    i.setTipo(cursor2.getString(4));
                    list.add(i);

                }
            } while (cursor.moveToNext());
        }

        return (list);
    }

    /* Retorna a lista de Produto_Insumo armazenados no Banco de Dados

       Não foi guardado o ID da tabela para o backup porque ele não tem
       utilidade nesse sistema. Foi criado apenas porque a lógica de
       Banco de Dados exige que toda classe tenha chave primária(ID),
       mas ele não nos será útil, logo seria um desperdício de espaço
       e processamento o guardar no backup.
     */

    private List<Produto_Insumo> buscarInsumoProduto() {
        List<Produto_Insumo> list = new ArrayList<Produto_Insumo>();
        Cursor cursor = bd.query("PRODUTO_INSUMO",null, null, null, null, null,null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                Produto_Insumo produto_insumo = new Produto_Insumo();
                produto_insumo.setIdProduto(cursor.getInt(1));
                produto_insumo.setIdInsumo(cursor.getInt(2));
                list.add(produto_insumo);
            } while (cursor.moveToNext());
        }

        return (list);
    }

    public void inserirProducao(Producao producao) {
        ContentValues valores = new ContentValues();
        valores.put("nome", producao.getNome());

        bd.insert("PRODUCAO", null, valores);
    }

    public void atualizarProducao(Producao producao) {
        ContentValues valores = new ContentValues();
        valores.put("nome", producao.getNome());
        bd.update("PRODUCAO", valores, "_id = ?", new String[]{"" + producao.getId()});
    }


    public void deletarProducao(Producao producao) {
        bd.delete("PRODUCAO", "_id = " + producao.getId(), null);
    }


    public List<Producao> buscarProducoes() {
        List<Producao> list = new ArrayList<Producao>();
        String[] colunas = new String[]{"_id", "Nome"};

        Cursor cursor = bd.query("PRODUCAO", colunas, null, null, null, null, "Nome ASC");

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                Producao p = new Producao();
                p.setId(cursor.getInt(0));
                p.setNome(cursor.getString(1));

                list.add(p);

            } while (cursor.moveToNext());
        }

        return (list);
    }

    public void inserirProdutoProducao(Producao producao, Produto produto){
        ContentValues valores = new ContentValues();
        valores.put("idProducao", producao.getId());
        valores.put("idProduto", produto.getId());


        bd.insert("PRODUCAO_PRODUTO", null, valores);
    }

    public void deletarProdutoProducao(Producao producao, Produto produto) {
        bd.delete("PRODUCAO_PRODUTO", "idProducao = " + producao.getId() + " and " + "idProduto = " + produto.getId(), null);
    }

    public List<Produto> buscarProdutosProducao(Producao producao) {
        List<Produto> list = new ArrayList<Produto>();
        String[] colunas = new String[]{"idProduto"};
        Cursor cursor = bd.query("PRODUCAO_PRODUTO",colunas, "idProducao ="+producao.getId(), null, null, null,null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                String[] colunas2 = new String[]{"_id", "NOME", "PRECO"};

                Cursor cursor2 = bd.query("PRODUTO", colunas2,"_id = "+cursor.getLong(0), null, null, null, "Nome ASC");
                if (cursor2.getCount() > 0) {

                    cursor2.moveToFirst();
                    Produto p = new Produto();
                    p.setId(cursor2.getInt(0));
                    p.setNome(cursor2.getString(1));
                    p.setPreco(cursor2.getFloat(2));

                    list.add(p);

                }
            } while (cursor.moveToNext());
        }

        return (list);
    }

    /* Retorna a lista de Producao_Produto armazenados no Banco de Dados

        Não foi guardado o ID da tabela para o backup porque ele não tem
    utilidade nesse sistema. Foi criado apenas porque a lógica de
    Banco de Dados exige que toda classe tenha chave primária(ID),
    mas ele não nos será útil, logo seria um desperdício de espaço
    e processamento o guardar no backup.
    */
    private List<Producao_Produto> buscarProdutosProducao() {
        List<Producao_Produto> list = new ArrayList<Producao_Produto>();
        Cursor cursor = bd.query("PRODUCAO_PRODUTO",null, null, null, null, null,null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                Producao_Produto producao_produto = new Producao_Produto();
                producao_produto.setIdProducao(cursor.getInt(1));
                producao_produto.setIdProduto(cursor.getInt(2));
                list.add(producao_produto);
            } while (cursor.moveToNext());
        }

        return (list);
    }





    /* Os métodos arquivar"X" irão gravar os dados em arquivos txt para backup
       Os arquivos serão guardados pelos dados separados por ";" e uma entidade por linha */

    //Deveria lançar a exceção "UsuariosNaoCadastradosException"
    public boolean arquivarUsuarios() throws IOException {
        FileWriter arq = new FileWriter("usuarios.txt");
        List<Usuario> usuarios = this.buscarUsuario();

        if(usuarios != null){
            for(Usuario user: usuarios){
                arq.write(user.getId() + ";");
                arq.write(user.getNome() + ";");
                arq.write(user.getEmail() + ";");
                arq.write("\n");
            }
            return true;
        }
        //Lançaria a exceção aqui
        return false;
    }

    public boolean arquivarInsumos() throws IOException {
        FileWriter arq = new FileWriter("insumos.txt");
        List<Insumo> insumos = this.buscarInsumos();

        if(insumos != null){
            for(Insumo ins: insumos){
                arq.write(ins.getId() + ";");
                arq.write(ins.getNome() + ";");
                arq.write(ins.getTipo() + ";");
                arq.write(ins.getCustoUnidade() + ";");
                arq.write(ins.getQuantidade() + ";");
                arq.write("\n");
            }
            return true;
        }

        return false;
    }

    public boolean arquivarProdutos() throws IOException {
        FileWriter arq = new FileWriter("produtos.txt");
        List<Produto> produtos = this.buscarProdutos();

        if(produtos != null){
            for(Produto pro: produtos){
                arq.write(pro.getId() + ";");
                arq.write(pro.getNome() + ";");
                arq.write(pro.getPreco() + ";");
                arq.write("\n");
            }
            return true;
        }

        return false;
    }

    public boolean arquivarProducoes() throws IOException {
        FileWriter arq = new FileWriter("producoes.txt");
        List<Producao> producoes = this.buscarProducoes();

        if(producoes != null){
            for(Producao pro: producoes){
                arq.write(pro.getId() + ";");
                arq.write(pro.getNome() + ";");
                arq.write("\n");
            }
            return true;
        }

        return false;
    }

    public boolean arquivarInsumoProdutos() throws IOException{
        FileWriter arq = new FileWriter("produtoInsumos.txt");
        List<Produto_Insumo> produto_insumos = this.buscarInsumoProduto();

        if(produto_insumos != null){
            for(Produto_Insumo pi: produto_insumos){
                arq.write(pi.getIdProduto() + ";");
                arq.write(pi.getIdInsumo() + ";");
                arq.write("\n");
            }
            return true;
        }

        return false;
    }

    public boolean arquivarProdutoProducoes() throws IOException{
        FileWriter arq = new FileWriter("producaoProdutos.txt");
        List<Producao_Produto> producao_produtos = this.buscarProdutosProducao();

        if(producao_produtos != null){
            for(Producao_Produto pp: producao_produtos){
                arq.write(pp.getIdProducao() + ";");
                arq.write(pp.getIdProduto() + ";");
                arq.write("\n");
            }
            return true;
        }

        return false;
    }

    //Separa as informações do string por ";"
    public List<String> tokenizer(String s){
        List<String> retorno = new ArrayList<String>();
        StringTokenizer tokenizer = new StringTokenizer(s,";");

        while(tokenizer.hasMoreTokens()){
            retorno.add(tokenizer.nextToken());
        }

        return retorno;
    }

    public BufferedReader carregarArquivo(String path) throws FileNotFoundException {
        FileReader arq = new FileReader(path);
        BufferedReader lerArq = new BufferedReader(arq);
        return lerArq;
    }


    public boolean carregarUsuarios() throws IOException {
        String path = "usuarios.txt";
        BufferedReader lerArq = this.carregarArquivo(path);

        String dados = lerArq.readLine();

        boolean flag = false; //Se for true, os usuários foram adicionados
        while(dados != null){
            List<String> dadosUsuario = this.tokenizer(dados);

            Usuario usuario = new Usuario();
            usuario.setId(Long.parseLong(dadosUsuario.get(0)));
            usuario.setNome(dadosUsuario.get(1));
            usuario.setEmail(dadosUsuario.get(2));
            this.inserirUsuario(usuario);

            flag = true;

            dados = lerArq.readLine();
        }

        return flag;
    }

    public boolean carregarInsumos() throws IOException {
        String path = "insumos.txt";
        BufferedReader lerArq = this.carregarArquivo(path);

        String dados = lerArq.readLine();

        boolean flag = false; //Se for true, os insumos foram adicionados
        while(dados != null){
            List<String> dadosInsumo = this.tokenizer(dados);

            Insumo insumo = new Insumo();
            insumo.setId(Long.parseLong(dadosInsumo.get(0)));
            insumo.setNome(dadosInsumo.get(1));
            insumo.setTipo(dadosInsumo.get(2));
            insumo.setCustoUnidade(Float.parseFloat(dadosInsumo.get(3)));
            insumo.setQuantidade(Float.parseFloat(dadosInsumo.get(4)));
            this.inserirInsumo(insumo);

            flag = true;

            dados = lerArq.readLine();
        }

        return flag;
    }

    public boolean carregarProdutos() throws IOException {
        String path = "produtos.txt";
        BufferedReader lerArq = this.carregarArquivo(path);

        String dados = lerArq.readLine();

        boolean flag = false; //Se for true, os insumos foram adicionados
        while(dados != null){
            List<String> dadosProduto = this.tokenizer(dados);

            Produto produto = new Produto();
            produto.setId(Long.parseLong(dadosProduto.get(0)));
            produto.setNome(dadosProduto.get(1));
            produto.setPreco(Float.parseFloat(dadosProduto.get(2)));
            this.inserirProduto(produto);

            flag = true;

            dados = lerArq.readLine();
        }

        return flag;
    }

    public boolean carregarProducoes() throws IOException {
        String path = "producoes.txt";
        BufferedReader lerArq = this.carregarArquivo(path);

        String dados = lerArq.readLine();

        boolean flag = false; //Se for true, os insumos foram adicionados
        while(dados != null){
            List<String> dadosProducao = this.tokenizer(dados);

            Producao producao = new Producao();
            producao.setId(Long.parseLong(dadosProducao.get(0)));
            producao.setNome(dadosProducao.get(1));
            this.inserirProducao(producao);

            flag = true;

            dados = lerArq.readLine();
        }

        return flag;
    }

    //TODO Implementar o método inserirInsumoProduto(Produto_Insumo produto_insumo)
    public boolean carregarInsumoProdutos() throws IOException {
        String path = "produtoInsumos.txt";
        BufferedReader lerArq = this.carregarArquivo(path);

        String dados = lerArq.readLine();

        boolean flag = false; //Se for true, os insumos foram adicionados
        while(dados != null){
            List<String> dadosInsumoProdutos = this.tokenizer(dados);

            Produto_Insumo produto_insumo = new Produto_Insumo();
            produto_insumo.setIdProduto(Long.parseLong(dadosInsumoProdutos.get(0)));
            produto_insumo.setIdInsumo(Long.parseLong(dadosInsumoProdutos.get(1)));
            //this.inserirInsumoProduto(produto_insumo);

            flag = true;

            dados = lerArq.readLine();
        }

        return flag;
    }

    //TODO Implementar o método inserirProdutoProducao(Producao_Produto producao_produto)
    public boolean carregarProdutoProducoes() throws IOException {
        String path = "producaoProdutos.txt";
        BufferedReader lerArq = this.carregarArquivo(path);

        String dados = lerArq.readLine();

        boolean flag = false; //Se for true, os insumos foram adicionados
        while(dados != null){
            List<String> dadosProdutoProducoes = this.tokenizer(dados);

            Producao_Produto producao_produto = new Producao_Produto();
            producao_produto.setIdProducao(Long.parseLong(dadosProdutoProducoes.get(0)));
            producao_produto.setIdProduto(Long.parseLong(dadosProdutoProducoes.get(1)));
            //this.inserirProdutoProducao(producao_produto);

            flag = true;

            dados = lerArq.readLine();
        }

        return flag;
    }
}