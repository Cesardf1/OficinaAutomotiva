package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Servico {
    private int id;
    private String placaVeiculo;
    private String tipoServico;
    private String descricao;
    private double valor;
    private LocalDateTime dataExecucao;
    private String status;

    public Servico(String placaVeiculo, String tipoServico, String descricao, double valor) {
        this.placaVeiculo = placaVeiculo;
        this.tipoServico = tipoServico;
        this.descricao = descricao;
        this.valor = valor;
        this.dataExecucao = LocalDateTime.now();
        this.status = "Executado";
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getPlacaVeiculo() { return placaVeiculo; }
    public void setPlacaVeiculo(String placaVeiculo) { this.placaVeiculo = placaVeiculo; }

    public String getTipoServico() { return tipoServico; }
    public void setTipoServico(String tipoServico) { this.tipoServico = tipoServico; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public LocalDateTime getDataExecucao() { return dataExecucao; }
    public void setDataExecucao(LocalDateTime dataExecucao) { this.dataExecucao = dataExecucao; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format("[%s] %s - %s - R$ %.2f (%s)",
                dataExecucao.format(formatter), tipoServico, descricao, valor, status);
    }
}