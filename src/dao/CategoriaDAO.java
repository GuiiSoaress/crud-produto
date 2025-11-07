package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Categoria;
import util.ConnectionFactory;

public class CategoriaDAO {
    
    // -----------------------------------------------------------
    // READ 
    // -----------------------------------------------------------

    public List<Categoria> buscarTodos() {

        List<Categoria> categorias = new ArrayList<>();

        String sql = "SELECT * FROM categoria";

        try (
            Connection conn = ConnectionFactory.getConnection(); 
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                Categoria categoria = new Categoria(
                    rs.getLong("id"),
                    rs.getString("nome")
                );
                categorias.add(categoria);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar categorias: " + e.getMessage());
            e.printStackTrace();
        }

        return categorias;
    }

    // --------------------------------------------
    // READ BY ID
    // -------------------------------------------

    public Categoria buscarPorId(Long id) {

        Categoria categoria = null;

        String sql = "SELECT id, nome FROM categoria WHERE id = ?";

        try (
            Connection conn = ConnectionFactory.getConnection(); 
            PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    categoria = new Categoria(
                        rs.getLong("id"),
                        rs.getString("nome")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar categoria. ID: " + id);
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return categoria;
    }

    // ------------------------------------
    // CREATE
    // ------------------------------------

    public void inserir(Categoria categoria) {

        String sql = "INSERT INTO categoria(nome) VALUES (?)";

        try (
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            stmt.setString(1, categoria.getNome());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    categoria.setId(rs.getLong(1));
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao inserir categoria: " + categoria.getNome());
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    // ------------------------------------
    // UPDATE
    // ------------------------------------

    public void atualizar(Categoria categoria) {

        String sql = "UPDATE categoria SET nome = ? WHERE id = ?";

        try (
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, categoria.getNome());
            stmt.setLong(2, categoria.getId());

            int linhasAfetadas = stmt.executeUpdate();
            System.out.println("Categoria ID: " + categoria.getId() + " atualizada.");
            System.out.println("Linhas afetadas: " + linhasAfetadas);

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar a categoria: " + categoria.getNome());
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    // -----------------------------------------------------------
    // DELETE 
    // -----------------------------------------------------------

    public void deletar(Long id) {
        String sql = "DELETE FROM categoria WHERE id = ?";

        try (
            Connection conn = ConnectionFactory.getConnection(); 
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setLong(1, id);

            int linhasAfetadas = stmt.executeUpdate();
            System.out.println("Categoria excluída.");
            System.out.println("Linhas afetadas: " + linhasAfetadas);

        } catch (SQLException e) {
            System.out.println("Erro ao excluir a categoria ID: " + id);
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
