-- Script de criação do banco de dados para Oficina Automotiva

-- Tabela de veículos
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
);

-- Tabela de condutores
CREATE TABLE IF NOT EXISTS condutores (
                                          cpf TEXT PRIMARY KEY,
                                          nome TEXT NOT NULL,
                                          telefone TEXT NOT NULL,
                                          email TEXT NOT NULL
);

-- Tabela de serviços
CREATE TABLE IF NOT EXISTS servicos (
                                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                                        placa_veiculo TEXT NOT NULL,
                                        tipo_servico TEXT NOT NULL,
                                        descricao TEXT NOT NULL,
                                        valor REAL NOT NULL,
                                        data_execucao TEXT NOT NULL,
                                        status TEXT NOT NULL,
                                        FOREIGN KEY (placa_veiculo) REFERENCES veiculos (placa)
    );

-- Tabela de ordens de serviço
CREATE TABLE IF NOT EXISTS ordens_servico (
                                              numero INTEGER PRIMARY KEY AUTOINCREMENT,
                                              placa_veiculo TEXT NOT NULL,
                                              data_abertura TEXT NOT NULL,
                                              data_conclusao TEXT,
                                              problema_relatado TEXT NOT NULL,
                                              status TEXT NOT NULL,
                                              FOREIGN KEY (placa_veiculo) REFERENCES veiculos (placa)
    );

-- Dados de exemplo
INSERT OR IGNORE INTO veiculos VALUES
('ABC1234', 'Ford', 'Fiesta', 2020, 'Prata', 'Carro', 4, 'Flex', NULL, NULL),
('XYZ5678', 'Honda', 'CG 160', 2021, 'Vermelha', 'Moto', NULL, NULL, 160, 'Elétrica');

INSERT OR IGNORE INTO condutores VALUES
('123.456.789-00', 'João Silva', '(11) 99999-9999', 'joao@email.com'),
('987.654.321-00', 'Maria Santos', '(11) 88888-8888', 'maria@email.com');

INSERT OR IGNORE INTO servicos VALUES
(1, 'ABC1234', 'Troca de óleo', 'Troca de óleo do motor e filtro', 150.00, '2024-01-15T10:30:00', 'Executado'),
(2, 'XYZ5678', 'Revisão geral', 'Revisão completa da moto', 200.00, '2024-01-16T14:00:00', 'Executado');

INSERT OR IGNORE INTO ordens_servico VALUES
(1, 'ABC1234', '2024-01-15T09:00:00', NULL, 'Barulho no motor', 'Aberta'),
(2, 'XYZ5678', '2024-01-16T13:00:00', '2024-01-16T15:00:00', 'Revisão periódica', 'Concluída');