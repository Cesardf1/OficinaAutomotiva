package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlite:oficina.db";
    private static Connection connection;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL);
                criarTabelas();
            }
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar com o banco de dados", e);
        }
    }

    private static void criarTabelas() {
        String sqlVeiculos = """
            CREATE TABLE IF NOT EXISTS veiculos (
                placa TEXT PRIMARY KEY,
                marca TEXT NOT NULL,
                modelo TEXT NOT NULL,
                ano INTEGER NOT NULL,
                cor TEXT NOT NULL,
                tipo TEXT NOT NULL,
                numero_portas INTEGER,
                tipo_combustivel TEXT,
                cilindradas INTEGER,
                tipo_partida TEXT
            )
            """;

        String sqlCondutores = """
            CREATE TABLE IF NOT EXISTS condutores (
                cpf TEXT PRIMARY KEY,
                nome TEXT NOT NULL,
                telefone TEXT NOT NULL,
                email TEXT NOT NULL
            )
            """;

        String sqlServicos = """
            CREATE TABLE IF NOT EXISTS servicos (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                placa_veiculo TEXT NOT NULL,
                tipo_servico TEXT NOT NULL,
                descricao TEXT NOT NULL,
                valor REAL NOT NULL,
                data_execucao TEXT NOT NULL,
                status TEXT NOT NULL,
                FOREIGN KEY (placa_veiculo) REFERENCES veiculos (placa)
            )
            """;

        String sqlOrdensServico = """
            CREATE TABLE IF NOT EXISTS ordens_servico (
                numero INTEGER PRIMARY KEY AUTOINCREMENT,
                placa_veiculo TEXT NOT NULL,
                data_abertura TEXT NOT NULL,
                data_conclusao TEXT,
                problema_relatado TEXT NOT NULL,
                status TEXT NOT NULL,
                FOREIGN KEY (placa_veiculo) REFERENCES veiculos (placa)
            )
            """;

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sqlVeiculos);
            stmt.execute(sqlCondutores);
            stmt.execute(sqlServicos);
            stmt.execute(sqlOrdensServico);
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabelas: " + e.getMessage());
        }
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }
}