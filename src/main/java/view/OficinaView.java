package view;

import model.*;
import controller.*;
import java.util.List;
import java.util.Scanner;

public class OficinaView {
    private VeiculoController veiculoController;
    private CondutorController condutorController;
    private ServicoController servicoController;
    private ApiVeiculoController apiController;
    private Scanner scanner;

    public OficinaView() {
        this.veiculoController = new VeiculoController();
        this.condutorController = new CondutorController();
        this.servicoController = new ServicoController();
        this.apiController = new ApiVeiculoController();
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenu() {
        while (true) {
            System.out.println("\n=== SISTEMA DE OFICINA AUTOMOTIVA ===");
            System.out.println("1. Cadastrar Veículo");
            System.out.println("2. Cadastrar Condutor");
            System.out.println("3. Registrar Serviço");
            System.out.println("4. Criar Ordem de Serviço");
            System.out.println("5. Listar Veículos");
            System.out.println("6. Listar Condutores");
            System.out.println("7. Listar Ordens de Serviço");
            System.out.println("8. Relatório de Serviços por Veículo");
            System.out.println("9. Consultar API de Veículos");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1" -> cadastrarVeiculo();
                case "2" -> cadastrarCondutor();
                case "3" -> registrarServico();
                case "4" -> criarOrdemServico();
                case "5" -> listarVeiculos();
                case "6" -> listarCondutores();
                case "7" -> listarOrdensServico();
                case "8" -> gerarRelatorio();
                case "9" -> consultarApiVeiculos();
                case "0" -> {
                    System.out.println("Encerrando sistema...");
                    return;
                }
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private void cadastrarVeiculo() {
        System.out.println("\n=== CADASTRO DE VEÍCULO ===");

        System.out.print("Tipo (1-Carro, 2-Moto): ");
        String tipo = scanner.nextLine();

        System.out.print("Placa: ");
        String placa = scanner.nextLine();

        System.out.print("Marca: ");
        String marca = scanner.nextLine();

        System.out.print("Modelo: ");
        String modelo = scanner.nextLine();

        System.out.print("Ano: ");
        int ano = Integer.parseInt(scanner.nextLine());

        System.out.print("Cor: ");
        String cor = scanner.nextLine();

        if (tipo.equals("1")) {
            System.out.print("Número de portas: ");
            int numeroPortas = Integer.parseInt(scanner.nextLine());

            System.out.print("Tipo de combustível: ");
            String combustivel = scanner.nextLine();

            Carro carro = new Carro(placa, marca, modelo, ano, cor, numeroPortas, combustivel);
            veiculoController.cadastrarVeiculo(carro);

        } else if (tipo.equals("2")) {
            System.out.print("Cilindradas: ");
            int cilindradas = Integer.parseInt(scanner.nextLine());

            System.out.print("Tipo de partida: ");
            String partida = scanner.nextLine();

            Moto moto = new Moto(placa, marca, modelo, ano, cor, cilindradas, partida);
            veiculoController.cadastrarVeiculo(moto);
        }
    }

    private void cadastrarCondutor() {
        System.out.println("\n=== CADASTRO DE CONDUTOR ===");

        System.out.print("CPF: ");
        String cpf = scanner.nextLine();

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        Condutor condutor = new Condutor(cpf, nome, telefone, email);
        condutorController.cadastrarCondutor(condutor);
    }

    private void registrarServico() {
        System.out.println("\n=== REGISTRO DE SERVIÇO ===");

        System.out.print("Placa do veículo: ");
        String placa = scanner.nextLine();

        Veiculo veiculo = veiculoController.buscarPorPlaca(placa);
        if (veiculo == null) {
            System.out.println("Veículo não encontrado!");
            return;
        }

        System.out.print("Tipo de serviço: ");
        String tipo = scanner.nextLine();

        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();

        System.out.print("Valor: ");
        double valor = Double.parseDouble(scanner.nextLine());

        Servico servico = new Servico(placa, tipo, descricao, valor);
        servicoController.registrarServico(servico);
    }

    private void criarOrdemServico() {
        System.out.println("\n=== CRIAÇÃO DE ORDEM DE SERVIÇO ===");

        System.out.print("Placa do veículo: ");
        String placa = scanner.nextLine();

        Veiculo veiculo = veiculoController.buscarPorPlaca(placa);
        if (veiculo == null) {
            System.out.println("Veículo não encontrado!");
            return;
        }

        System.out.print("Problema relatado: ");
        String problema = scanner.nextLine();

        OrdemServico ordem = new OrdemServico(0, placa, problema);
        int numeroOS = servicoController.criarOrdemServico(ordem);

        if (numeroOS != -1) {
            System.out.println("Ordem de serviço #" + numeroOS + " criada com sucesso!");
        }
    }

    private void listarVeiculos() {
        System.out.println("\n=== VEÍCULOS CADASTRADOS ===");
        List<Veiculo> veiculos = veiculoController.listarVeiculos();

        if (veiculos.isEmpty()) {
            System.out.println("Nenhum veículo cadastrado.");
        } else {
            for (int i = 0; i < veiculos.size(); i++) {
                System.out.println((i + 1) + ". " + veiculos.get(i));
            }
        }
    }

    private void listarCondutores() {
        System.out.println("\n=== CONDUTORES CADASTRADOS ===");
        List<Condutor> condutores = condutorController.listarCondutores();

        if (condutores.isEmpty()) {
            System.out.println("Nenhum condutor cadastrado.");
        } else {
            for (int i = 0; i < condutores.size(); i++) {
                System.out.println((i + 1) + ". " + condutores.get(i));
            }
        }
    }

    private void listarOrdensServico() {
        System.out.println("\n=== ORDENS DE SERVIÇO ===");
        List<OrdemServico> ordens = servicoController.listarOrdensServico();

        if (ordens.isEmpty()) {
            System.out.println("Nenhuma ordem de serviço cadastrada.");
        } else {
            for (OrdemServico ordem : ordens) {
                System.out.println(ordem);
                List<Servico> servicos = servicoController.listarServicosPorVeiculo(ordem.getPlacaVeiculo());
                for (Servico servico : servicos) {
                    System.out.println("  → " + servico);
                }
                System.out.println();
            }
        }
    }

    private void gerarRelatorio() {
        servicoController.gerarRelatorioServicos();
    }

    private void consultarApiVeiculos() {
        System.out.println("\n=== CONSULTA API VEÍCULOS ===");
        System.out.println("Consultando marcas disponíveis...");

        String marcas = apiController.consultarMarcas();
        System.out.println("Marcas disponíveis na API:");
        System.out.println(marcas);
    }
}