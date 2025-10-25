package controller;

import model.Veiculo;
import model.Carro;
import model.Moto;
import database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeiculoController {

    public void cadastrarVeiculo(Veiculo veiculo) {
        String sql = "INSERT INTO veiculos (placa, marca, modelo, ano, cor, tipo, " +
                "numero_portas, tipo_combustivel, cilindradas, tipo_partida) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, veiculo.getPlaca());
            pstmt.setString(2, veiculo.getMarca());
            pstmt.setString(3, veiculo.getModelo());
            pstmt.setInt(4, veiculo.getAno());
            pstmt.setString(5, veiculo.getCor());
            pstmt.setString(6, veiculo.getTipo());

            if (veiculo instanceof Carro) {
                Carro carro = (Carro) veiculo;
                pstmt.setInt(7, carro.getNumeroPortas());
                pstmt.setString(8, carro.getTipoCombustivel());
                pstmt.setNull(9, Types.INTEGER);
                pstmt.setNull(10, Types.VARCHAR);
            } else if (veiculo instanceof Moto) {
                Moto moto = (Moto) veiculo;
                pstmt.setNull(7, Types.INTEGER);
                pstmt.setNull(8, Types.VARCHAR);
                pstmt.setInt(9, moto.getCilindradas());
                pstmt.setString(10, moto.getTipoPartida());
            }

            pstmt.executeUpdate();
            System.out.println("Veículo cadastrado com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar veículo: " + e.getMessage());
        }
    }

    public List<Veiculo> listarVeiculos() {
        List<Veiculo> veiculos = new ArrayList<>();
        String sql = "SELECT * FROM veiculos";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String tipo = rs.getString("tipo");
                String placa = rs.getString("placa");
                String marca = rs.getString("marca");
                String modelo = rs.getString("modelo");
                int ano = rs.getInt("ano");
                String cor = rs.getString("cor");

                if ("Carro".equals(tipo)) {
                    int numeroPortas = rs.getInt("numero_portas");
                    String tipoCombustivel = rs.getString("tipo_combustivel");
                    veiculos.add(new Carro(placa, marca, modelo, ano, cor, numeroPortas, tipoCombustivel));
                } else if ("Moto".equals(tipo)) {
                    int cilindradas = rs.getInt("cilindradas");
                    String tipoPartida = rs.getString("tipo_partida");
                    veiculos.add(new Moto(placa, marca, modelo, ano, cor, cilindradas, tipoPartida));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar veículos: " + e.getMessage());
        }

        return veiculos;
    }

    public Veiculo buscarPorPlaca(String placa) {
        String sql = "SELECT * FROM veiculos WHERE placa = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, placa);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String tipo = rs.getString("tipo");
                String marca = rs.getString("marca");
                String modelo = rs.getString("modelo");
                int ano = rs.getInt("ano");
                String cor = rs.getString("cor");

                if ("Carro".equals(tipo)) {
                    int numeroPortas = rs.getInt("numero_portas");
                    String tipoCombustivel = rs.getString("tipo_combustivel");
                    return new Carro(placa, marca, modelo, ano, cor, numeroPortas, tipoCombustivel);
                } else if ("Moto".equals(tipo)) {
                    int cilindradas = rs.getInt("cilindradas");
                    String tipoPartida = rs.getString("tipo_partida");
                    return new Moto(placa, marca, modelo, ano, cor, cilindradas, tipoPartida);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar veículo: " + e.getMessage());
        }

        return null;
    }
}