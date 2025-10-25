package model;

public class Carro extends Veiculo {
    private int numeroPortas;
    private String tipoCombustivel;

    public Carro(String placa, String marca, String modelo, int ano, String cor,
                 int numeroPortas, String tipoCombustivel) {
        super(placa, marca, modelo, ano, cor);
        this.numeroPortas = numeroPortas;
        this.tipoCombustivel = tipoCombustivel;
    }

    public int getNumeroPortas() { return numeroPortas; }
    public void setNumeroPortas(int numeroPortas) { this.numeroPortas = numeroPortas; }

    public String getTipoCombustivel() { return tipoCombustivel; }
    public void setTipoCombustivel(String tipoCombustivel) { this.tipoCombustivel = tipoCombustivel; }

    @Override
    public String getTipo() {
        return "Carro";
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" (%s, %d portas, %s)", getTipo(), numeroPortas, tipoCombustivel);
    }
}