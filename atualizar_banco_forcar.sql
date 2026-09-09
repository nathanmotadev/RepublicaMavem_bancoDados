-- Script AGRESSIVO para forçar a atualização do banco
-- Este script remove tudo e recria do zero

USE republica;

-- 1. Desabilitar verificação de constraint temporariamente
SET FOREIGN_KEY_CHECKS=0;

-- 2. Remover tabelas existentes
DROP TABLE IF EXISTS pagamento_participantes;
DROP TABLE IF EXISTS pagamentos_extras;
DROP TABLE IF EXISTS despesas;
DROP TABLE IF EXISTS moradores;

-- 3. Reabilitar verificação de constraint
SET FOREIGN_KEY_CHECKS=1;

-- 4. Criar tabela moradores
CREATE TABLE moradores (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    data_nascimento DATE NOT NULL,
    data_entrada DATE NOT NULL
);

-- 5. Criar tabela despesas
CREATE TABLE despesas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    descricao VARCHAR(255),
    valor DOUBLE,
    data_despesa DATE
);

-- 6. Criar tabela pagamentos_extras COM ESTRUTURA NOVA
CREATE TABLE pagamentos_extras (
    id INT PRIMARY KEY AUTO_INCREMENT,
    descricao VARCHAR(255),
    valor DOUBLE,
    morador_pagador_id INT NOT NULL,
    data_pagamento TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (morador_pagador_id) REFERENCES moradores(id) ON DELETE CASCADE
);

-- 7. Criar tabela pagamento_participantes
CREATE TABLE pagamento_participantes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    pagamento_id INT NOT NULL,
    morador_id INT NOT NULL,
    valor_devido DOUBLE,
    pago BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (pagamento_id) REFERENCES pagamentos_extras(id) ON DELETE CASCADE,
    FOREIGN KEY (morador_id) REFERENCES moradores(id) ON DELETE CASCADE
);

-- 8. Confirmação
SELECT '✅ Banco de dados resetado e atualizado com sucesso!' as status;
SHOW TABLES;
DESCRIBE pagamentos_extras;
