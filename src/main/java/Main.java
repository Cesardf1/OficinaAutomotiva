import view.OficinaView;
import database.DatabaseConnection;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Iniciando Sistema de Oficina Automotiva...");

            // Testar conexão com banco
            DatabaseConnection.getConnection();
            System.out.println("Banco de dados conectado com sucesso!");

            OficinaView view = new OficinaView();
            view.exibirMenu();

        } catch (Exception e) {
            System.err.println("Erro ao iniciar a aplicação: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection();
        }
    }
}