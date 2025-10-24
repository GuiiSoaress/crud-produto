package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.xdevapi.Schema.CreateCollectionOptions;

import model.Produto;
import util.ConnectionFactory;

public class ProdutoDAO {
    
    // -----------------------------------------------------------
    // READ 
    // -----------------------------------------------------------

    public List<Produto> buscarTodos(){

        List<Produto> produtos = new ArrayList<>();

        String sql = "SELECT * FROM produtos";

        try (
            Connection conn = ConnectionFactory.getConnection(); 
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()       
        ) {
            while(rs.next()){
                Produto produto = new Produto(
                    rs.getLong("id"),
                    rs.getString("nome"),
                    rs.getDouble("preco"),
                    rs.getInt("estoque")
                );
                produtos.add(produto);

            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar produtos: " + e.getMessage());
            e.printStackTrace();
        }

        return produtos;

    }

    // --------------------------------------------
    // READ BY id
    // -------------------------------------------

    public Produto buscarPorId(Long id){
        
        Produto produto = null;
        
        String sql = "SELECT id, nome, preco, estoque FROM produtos WHERE id = ?";

        try (
            Connection conn = ConnectionFactory.getConnection(); 
            PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setLong(1, id);
            
            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    produto = new Produto(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getDouble("preco"),
                        rs.getInt("estoque")
                    );
                }
            }
        } catch (SQLException e) {
           System.out.println("Erro ao buscar produto. ID: " + id);
           System.out.println(e.getMessage());
           e.printStackTrace();
        }

        return produto;
    }

    // ------------------------------------
    // CREATE
    // ------------------------------------

    public void inserir(Produto produto){

        String sql = "INSERT INTO produtos(nome, preco, estoque) VALUES (?,?,?)";

        try (
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setInt(3, produto.getEstoque());
            
            stmt.executeUpdate();

            try(ResultSet rs = stmt.getGeneratedKeys()){
                if(rs.next()){
                    produto.setId(rs.getLong(1));
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao inserir produto: " + produto.getNome());
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

     // ------------------------------------
    // Update
    // ------------------------------------

    public void atualizar(Produto produto){

        String sql = "update produtos SET nome = ?, preco = ?, estoque = ? WHERE id = ?";

        try (
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setInt(3, produto.getEstoque());
            stmt.setLong(4, produto.getId());
            
            int linhasAfetadas = stmt.executeUpdate();
            System.out.println("Produto iD: " + produto.getId() + " Atualizado");
            System.out.println("Linhas afetadas: " + linhasAfetadas);

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar o produto: " + produto.getNome());
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    // -----------------------------------------------------------
    // DELETE 
    // -----------------------------------------------------------

    public void deletar(Long id){
        String sql = "DELETE FROM produtos WHERE id = ?";

        try (
            Connection conn = ConnectionFactory.getConnection(); 
            PreparedStatement stmt = conn.prepareStatement(sql)  
        ) {
           stmt.setLong(1, id);
            
           int linhasAfetadas = stmt.executeUpdate();
           System.out.println("Produto Excluido");
           System.out.println("Linhas afetadas: " + linhasAfetadas);
        
        } catch (SQLException e) {
            System.out.println("Erro ao excluir o produto ID: " + id);
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }
}
