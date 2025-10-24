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


        // Busca Lista de Produtos
        // //Itera sobre o retorno do produtoDAO (Lista de produtos)
        // for (Produto p : produtoDAO.buscarTodos()){
        //     System.out.println(p.toString());
        // }


        //Busca Produtos por id
        // Produto produto = produtoDAO.buscarPorId(1L);
        // if (produto != null){
        //     System.out.println(produto.toString());
        // }else{
        //     System.out.println("Produto não encontrado");
        // }
        

        // Inserir produto
        Produto p1 = new Produto("Notebook", 2500.00, 2);
        // produtoDAO.inserir(p1);

        //atualizar produto
        // Produto produtoParaAtualizar = produtoDAO.buscarPorId(3L);
        //     if (produtoParaAtualizar != null) {
        //         produtoParaAtualizar.setNome("Teclado Gamer Atualizado");
        //         produtoParaAtualizar.setPreco(249.99);
        //         produtoParaAtualizar.setEstoque(30);
        //         produtoDAO.atualizar(produtoParaAtualizar);
        //         System.out.println("Produto atualizado: " + produtoParaAtualizar);
        //     } else {
        //         System.out.println("Produto para atualização não encontrado.");
        //     }   

        //deletar produto
        produtoDAO.deletar(3L);


    }
}
