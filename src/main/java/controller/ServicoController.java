package controller;

import model.Servico;
import model.OrdemServico;
import database.DatabaseConnection;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ServicoController {

    public void registrarServico(Servico servico) {
        String sql = "INSERT INTO servicos (placa_veiculo, tipo_servico, descricao, valor, data_execucao, status) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, servico.getPlacaVeiculo());
            pstmt.setString(2, servico.getTipoServico());
            pstmt.setString(3, servico.getDescricao());
            pstmt.setDouble(4, servico.getValor());
            pstmt.setString(5, servico.getDataExecucao().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            pstmt.setString(6, servico.getStatus());

            pstmt.executeUpdate();
            System.out.println("Serviço registrado com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao registrar serviço: " + e.getMessage());
        }
    }

    public List<Servico> listarServicosPorVeiculo(String placa) {
        List<Servico> servicos = new ArrayList<>();
        String sql = "SELECT * FROM servicos WHERE placa_veiculo = ? ORDER BY data_execucao DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, placa);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Servico servico = new Servico(
                        rs.getString("placa_veiculo"),
                        rs.getString("tipo_servico"),
                        rs.getString("descricao"),
                        rs.getDouble("valor")
                );
                servico.setId(rs.getInt("id"));
                servico.setDataExecucao(LocalDateTime.parse(rs.getString("data_execucao")));
                servico.setStatus(rs.getString("status"));
                servicos.add(servico);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar serviços: " + e.getMessage());
        }

        return servicos;
    }

    public int criarOrdemServico(OrdemServico ordem) {
        String sql = "INSERT INTO ordens_servico (placa_veiculo, data_abertura, problema_relatado, status) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, ordem.getPlacaVeiculo());
            pstmt.setString(2, ordem.getDataAbertura().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            pstmt.setString(3, ordem.getProblemaRelatado());
            pstmt.setString(4, ordem.getStatus());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao criar ordem de serviço: " + e.getMessage());
        }

        return -1;
    }

    public List<OrdemServico> listarOrdensServico() {
        List<OrdemServico> ordens = new ArrayList<>();
        String sql = "SELECT os.*, " +
                "(SELECT SUM(valor) FROM servicos WHERE placa_veiculo = os.placa_veiculo) as total_servicos " +
                "FROM ordens_servico os ORDER BY data_abertura DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                OrdemServico ordem = new OrdemServico(
                        rs.getInt("numero"),
                        rs.getString("placa_veiculo"),
                        rs.getString("problema_relatado")
                );

                ordem.setDataAbertura(LocalDateTime.parse(rs.getString("data_abertura")));
                if (rs.getString("data_conclusao") != null) {
                    ordem.setDataConclusao(LocalDateTime.parse(rs.getString("data_conclusao")));
                }
                ordem.setStatus(rs.getString("status"));

                // Carregar serviços da ordem
                List<Servico> servicos = listarServicosPorVeiculo(ordem.getPlacaVeiculo());
                ordem.setServicos(servicos);

                ordens.add(ordem);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar ordens de serviço: " + e.getMessage());
        }

        return ordens;
    }

    public void gerarRelatorioServicos() {
        String sql = "SELECT placa_veiculo, COUNT(*) as total_servicos, SUM(valor) as valor_total " +
                "FROM servicos GROUP BY placa_veiculo ORDER BY valor_total DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n=== RELATÓRIO DE SERVIÇOS POR VEÍCULO ===");
            System.out.println("===========================================");

            double valorTotalGeral = 0;
            int servicosTotalGeral = 0;

            while (rs.next()) {
                String placa = rs.getString("placa_veiculo");
                int totalServicos = rs.getInt("total_servicos");
                double valorTotal = rs.getDouble("valor_total");

                System.out.printf("Placa: %s\n", placa);
                System.out.printf("Total de serviços: %d\n", totalServicos);
                System.out.printf("Valor total: R$ %.2f\n", valorTotal);
                System.out.println("-----------------------------------");

                valorTotalGeral += valorTotal;
                servicosTotalGeral += totalServicos;
            }

            System.out.printf("TOTAL GERAL: %d serviços - R$ %.2f\n",
                    servicosTotalGeral, valorTotalGeral);
            System.out.println("===========================================");

        } catch (SQLException e) {
            System.err.println("Erro ao gerar relatório: " + e.getMessage());
        }
    }
}