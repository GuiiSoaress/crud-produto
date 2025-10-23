import dao.ProdutoDAO;
import model.Produto;
import util.ConnectionFactory;

public class App {
    public static void main(String[] args) throws Exception {

        //Conexão com o banco de dados
        // try {
        //     ConnectionFactory.getConnection();
        //     System.out.println("Conexão efetuada com sucesso");
        // } catch (Exception e) {
        //     System.out.println(e);
        // }

        //instancia o objeto produto dao
        ProdutoDAO produtoDAO = new ProdutoDAO();

        // //Itera sobre o retorno do produtoDAO (Lista de produtos)
        // for (Produto p : produtoDAO.buscarTodos()){
        //     System.out.println(p.toString());
        // }

        System.out.println(produtoDAO.buscarPorId(2L).toString());

    }
}
