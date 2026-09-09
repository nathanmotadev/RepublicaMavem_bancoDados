-- Script para atualizar estrutura do banco de dados
-- Executar no MySQL antes de usar a interface

USE republica;

-- 1. Se a tabela moradores já existe com coluna 'idade', remover e recriar
DROP TABLE IF EXISTS moradores;

-- 2. Criar tabela moradores com data_nascimento
CREATE TABLE moradores (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    data_nascimento DATE NOT NULL,
    data_entrada DATE NOT NULL
);

-- 3. Se as outras tabelas não existem, criar
CREATE TABLE IF NOT EXISTS despesas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    descricao VARCHAR(255),
    valor DOUBLE,
    data_despesa DATE
);

CREATE TABLE IF NOT EXISTS pagamentos_extras (
    id INT PRIMARY KEY AUTO_INCREMENT,
    descricao VARCHAR(255),
    valor DOUBLE,
    morador_pagador_id INT NOT NULL,
    data_pagamento TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (morador_pagador_id) REFERENCES moradores(id)
);

CREATE TABLE IF NOT EXISTS pagamento_participantes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    pagamento_id INT NOT NULL,
    morador_id INT NOT NULL,
    valor_devido DOUBLE,
    pago BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (pagamento_id) REFERENCES pagamentos_extras(id) ON DELETE CASCADE,
    FOREIGN KEY (morador_id) REFERENCES moradores(id)
);

-- Confirmação
SELECT 'Banco de dados atualizado com sucesso!' as status;
SHOW TABLES;
