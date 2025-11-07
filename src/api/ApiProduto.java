package api;

import static spark.Spark.after;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;

import com.google.gson.Gson;

import dao.CategoriaDAO;
import dao.ProdutoDAO;
import model.Produto;
import model.Categoria;
import spark.Filter;
import spark.Request;
import spark.Response;
import spark.Route;

public class ApiProduto {

    private static final ProdutoDAO produtoDAO = new ProdutoDAO();
    private static final CategoriaDAO categoriaDAO = new CategoriaDAO();
    private static final Gson gson = new Gson();

    private static final String APPLICATION_JSON = "application/json";

    public static void main(String[] args) {
        port(4567);

        after(new Filter() {
            @Override
            public void handle(Request request, Response response) {
                response.type(APPLICATION_JSON);

            }
        });

        // GET /produtos - Buscar Todos
        get("/produtos", new Route() {
            @Override
            public Object handle(Request request, Response response) {
                return gson.toJson(produtoDAO.buscarTodos());
            }
        });

        // GET /produtos/id
        get("/produtos/:id", new Route() {
            @Override
            public Object handle(Request request, Response response) {
                try {
                    Long id = Long.parseLong(request.params(":id"));

                    Produto produto = produtoDAO.buscarPorId(id);

                    if (produto != null) {
                        return gson.toJson(produto);
                    } else {
                        response.status(404);
                        return "{\"mensagem\": \"Produto com ID " + id + "  Não encontrado\"}";
                    }
                } catch (NumberFormatException e) {
                    response.status(400);
                    return "{\"mensagem\": \"Id Invalido\"}";
                }
            }
        });

        // POST /produtos - Criar novo produto
        post("/produtos", new Route() {
            @Override
            public Object handle(Request request, Response response) {
                try {
                    Produto novoProduto = gson.fromJson(request.body(), Produto.class);
                    produtoDAO.inserir(novoProduto);

                    response.status(201);
                    return gson.toJson(novoProduto);

                } catch (Exception e) {
                    response.status(500);
                    System.out.println("Erro ao processar requisição post");
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                    return "{\"mensagem\": \"Erro ao cadastrar o produto\"}";
                }

            }
        });

        // PUT /produtos/:id - Atualizar produto existente
        put("/produtos/:id", new Route() {
            @Override
            public Object handle(Request request, Response response) {
                try {
                    Long id = Long.parseLong(request.params(":id")); // Usa Long

                    if (produtoDAO.buscarPorId(id) == null) {
                        response.status(404);
                        return "{\"mensagem\": \"Produto não encontrado para atualização.\"}";
                    }

                    Produto produtoParaAtualizar = gson.fromJson(request.body(), Produto.class);
                    produtoParaAtualizar.setId(id); // garante que o ID da URL seja usado

                    produtoDAO.atualizar(produtoParaAtualizar);

                    response.status(200); // OK
                    return gson.toJson(produtoParaAtualizar);

                } catch (NumberFormatException e) {
                    response.status(400); // Bad Request
                    return "{\"mensagem\": \"Formato de ID inválido.\"}";
                } catch (Exception e) {
                    response.status(500);
                    System.err.println("Erro ao processar requisição PUT: " + e.getMessage());
                    e.printStackTrace();
                    return "{\"mensagem\": \"Erro ao atualizar produto.\"}";
                }
            }
        });

        // DELETE /produtos/:id - Deletar um produto
        delete("/produtos/:id", new Route() {
            @Override
            public Object handle(Request request, Response response) {
                try {
                    Long id = Long.parseLong(request.params(":id")); // Usa Long

                    if (produtoDAO.buscarPorId(id) == null) {
                        response.status(404);
                        return "{\"mensagem\": \"Produto não encontrado para exclusão.\"}";
                    }

                    produtoDAO.deletar(id); // Usa o Long ID

                    response.status(204); // No Content
                    return ""; // Corpo vazio

                } catch (NumberFormatException e) {
                    response.status(400);
                    return "{\"mensagem\": \"Formato de ID inválido.\"}";
                }
            }
        });

        // ----------------------------------------------------------------------------------------------
        // APIs Categoria
        // ----------------------------------------------------------------------------------------------

        // get /categorias - Buscar todas as categorias
        get("/categorias", (request, response) -> {
            return gson.toJson(categoriaDAO.buscarTodos());
        });

        // GET /categorias/id
        get("/categorias/:id", (request, response) -> {
            try {
                Long id = Long.parseLong(request.params(":id"));

                Categoria categoria = categoriaDAO.buscarPorId(id);

                if (categoria != null) {
                    return gson.toJson(categoria);
                } else {
                    response.status(404);
                    return "{\"mensagem\": \"Categoria com ID " + id + "  Não encontrado\"}";
                }
            } catch (NumberFormatException e) {
                response.status(400);
                return "{\"mensagem\": \"Id Invalido\"}";
            }

        });

        // POST /categorias - Criar nova categoria
        post("/categorias", new Route() {
            @Override
            public Object handle(Request request, Response response) {
                try {
                    Categoria novaCategoria = gson.fromJson(request.body(), Categoria.class);
                    categoriaDAO.inserir(novaCategoria);

                    response.status(201);
                    return gson.toJson(novaCategoria);

                } catch (Exception e) {
                    response.status(500);
                    System.out.println("Erro ao processar requisição POST /categorias");
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                    return "{\"mensagem\": \"Erro ao cadastrar a categoria.\"}";
                }
            }
        });

        // PUT /categorias/:id - Atualizar categoria existente
        put("/categorias/:id", new Route() {
            @Override
            public Object handle(Request request, Response response) {
                try {
                    Long id = Long.parseLong(request.params(":id"));

                    if (categoriaDAO.buscarPorId(id) == null) {
                        response.status(404);
                        return "{\"mensagem\": \"Categoria não encontrada para atualização.\"}";
                    }

                    Categoria categoriaParaAtualizar = gson.fromJson(request.body(), Categoria.class);
                    categoriaParaAtualizar.setId(id);

                    categoriaDAO.atualizar(categoriaParaAtualizar);

                    response.status(200); // OK
                    return gson.toJson(categoriaParaAtualizar);

                } catch (NumberFormatException e) {
                    response.status(400); // Bad Request
                    return "{\"mensagem\": \"Formato de ID inválido.\"}";
                } catch (Exception e) {
                    response.status(500);
                    System.err.println("Erro ao processar requisição PUT /categorias: " + e.getMessage());
                    e.printStackTrace();
                    return "{\"mensagem\": \"Erro ao atualizar a categoria.\"}";
                }
            }
        });

        // DELETE /categorias/:id - Deletar uma categoria
        delete("/categorias/:id", new Route() {
            @Override
            public Object handle(Request request, Response response) {
                try {
                    Long id = Long.parseLong(request.params(":id"));

                    if (categoriaDAO.buscarPorId(id) == null) {
                        response.status(404);
                        return "{\"mensagem\": \"Categoria não encontrada para exclusão.\"}";
                    }

                    categoriaDAO.deletar(id);

                    response.status(204); // No Content
                    return ""; // Corpo vazio

                } catch (NumberFormatException e) {
                    response.status(400);
                    return "{\"mensagem\": \"Formato de ID inválido.\"}";
                } catch (Exception e) {
                    response.status(500);
                    System.err.println("Erro ao processar requisição DELETE /categorias: " + e.getMessage());
                    e.printStackTrace();
                    return "{\"mensagem\": \"Erro ao excluir a categoria.\"}";
                }
            }
        });

        System.out.println("API de Produtos iniciada na porta 4567. Acesse: http://localhost:4567/produtos");

    }

}
