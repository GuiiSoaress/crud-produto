package api;

import static spark.Spark.after;
import static spark.Spark.get;
import static spark.Spark.port;

import com.google.gson.Gson;

import dao.ProdutoDAO;
import spark.Filter;
import spark.Request;
import spark.Response;
import spark.Route;

public class ApiProduto {
    
    private static final ProdutoDAO dao = new ProdutoDAO();
    private static final Gson gson = new Gson();

    private static final String APPLICATION_JSON = "application/json";

    public static void main(String[] args) {
        port(4567);

        after(new Filter(){
            @Override
            public void handle(Request request, Response response){
                response.type(APPLICATION_JSON);

            }
        });

        get("/produtos", new Route(){
            @Override
            public Object handle(Request request, Response response){
                return gson.toJson(dao.buscarTodos());
            }
        });

    }

}
