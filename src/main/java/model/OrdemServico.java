package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OrdemServico {
    private int numero;
    private String placaVeiculo;
    private LocalDateTime dataAbertura;
    private LocalDateTime dataConclusao;
    private String problemaRelatado;
    private List<Servico> servicos;
    private String status;

    public OrdemServico(int numero, String placaVeiculo, String problemaRelatado) {
        this.numero = numero;
        this.placaVeiculo = placaVeiculo;
        this.problemaRelatado = problemaRelatado;
        this.dataAbertura = LocalDateTime.now();
        this.servicos = new ArrayList<>();
        this.status = "Aberta";
    }

    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    public String getPlacaVeiculo() { return placaVeiculo; }
    public void setPlacaVeiculo(String placaVeiculo) { this.placaVeiculo = placaVeiculo; }

    public LocalDateTime getDataAbertura() { return dataAbertura; }
    public void setDataAbertura(LocalDateTime dataAbertura) { this.dataAbertura = dataAbertura; }

    public LocalDateTime getDataConclusao() { return dataConclusao; }
    public void setDataConclusao(LocalDateTime dataConclusao) { this.dataConclusao = dataConclusao; }

    public String getProblemaRelatado() { return problemaRelatado; }
    public void setProblemaRelatado(String problemaRelatado) { this.problemaRelatado = problemaRelatado; }

    public List<Servico> getServicos() { return servicos; }
    public void setServicos(List<Servico> servicos) { this.servicos = servicos; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public void adicionarServico(Servico servico) {
        this.servicos.add(servico);
    }

    public double getValorTotal() {
        double total = 0;
        for (Servico servico : servicos) {
            total += servico.getValor();
        }
        return total;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format("OS #%d - Veículo: %s - %s - Total: R$ %.2f",
                numero, placaVeiculo, status, getValorTotal());
    }
}