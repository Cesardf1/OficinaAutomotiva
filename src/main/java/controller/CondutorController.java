package controller;

import model.Condutor;
import database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CondutorController {

    public void cadastrarCondutor(Condutor condutor) {
        String sql = "INSERT INTO condutores (cpf, nome, telefone, email) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, condutor.getCpf());
            pstmt.setString(2, condutor.getNome());
            pstmt.setString(3, condutor.getTelefone());
            pstmt.setString(4, condutor.getEmail());

            pstmt.executeUpdate();
            System.out.println("Condutor cadastrado com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar condutor: " + e.getMessage());
        }
    }

    public List<Condutor> listarCondutores() {
        List<Condutor> condutores = new ArrayList<>();
        String sql = "SELECT * FROM condutores";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String cpf = rs.getString("cpf");
                String nome = rs.getString("nome");
                String telefone = rs.getString("telefone");
                String email = rs.getString("email");

                condutores.add(new Condutor(cpf, nome, telefone, email));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar condutores: " + e.getMessage());
        }

        return condutores;
    }

    public Condutor buscarPorCpf(String cpf) {
        String sql = "SELECT * FROM condutores WHERE cpf = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cpf);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String nome = rs.getString("nome");
                String telefone = rs.getString("telefone");
                String email = rs.getString("email");
                return new Condutor(cpf, nome, telefone, email);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar condutor: " + e.getMessage());
        }

        return null;
    }
}