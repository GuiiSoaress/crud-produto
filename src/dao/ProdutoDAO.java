package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
}
