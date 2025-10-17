import util.ConnectionFactory;

public class App {
    public static void main(String[] args) throws Exception {
        try {
            ConnectionFactory.getConnection();
            System.out.println("Conexão efetuada com sucesso");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
