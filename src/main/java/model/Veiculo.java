package model;

public abstract class Veiculo {
    protected String placa;
    protected String marca;
    protected String modelo;
    protected int ano;
    protected String cor;
    protected Condutor condutor;

    public Veiculo(String placa, String marca, String modelo, int ano, String cor) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.cor = cor;
    }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public int getAno() { return ano; }
    public void setAno(int ano) { this.ano = ano; }

    public String getCor() { return cor; }
    public void setCor(String cor) { this.cor = cor; }

    public Condutor getCondutor() { return condutor; }
    public void setCondutor(Condutor condutor) { this.condutor = condutor; }

    public abstract String getTipo();

    @Override
    public String toString() {
        return String.format("%s %s %s - Placa: %s", marca, modelo, ano, placa);
    }
}