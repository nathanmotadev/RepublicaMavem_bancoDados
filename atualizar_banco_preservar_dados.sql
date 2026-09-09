-- Script para atualizar estrutura do banco de dados SEM perder dados
-- Executar no MySQL antes de usar a interface

USE republica;

-- 1. Verifica se a tabela pagamentos_extras já existe
-- Se existir e tiver a estrutura antiga, drop e recria
DROP TABLE IF EXISTS pagamento_participantes;
DROP TABLE IF EXISTS pagamentos_extras;

-- 2. Criar tabela despesas (se não existir)
CREATE TABLE IF NOT EXISTS despesas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    descricao VARCHAR(255),
    valor DOUBLE,
    data_despesa DATE
);

-- 3. Criar tabela moradores (se não existir)
-- Se já existe, não faz nada
CREATE TABLE IF NOT EXISTS moradores (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    data_nascimento DATE NOT NULL,
    data_entrada DATE NOT NULL
);

-- 4. Criar tabela pagamentos_extras COM a nova estrutura
CREATE TABLE IF NOT EXISTS pagamentos_extras (
    id INT PRIMARY KEY AUTO_INCREMENT,
    descricao VARCHAR(255),
    valor DOUBLE,
    morador_pagador_id INT NOT NULL,
    data_pagamento TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (morador_pagador_id) REFERENCES moradores(id)
);

-- 5. Criar tabela pagamento_participantes
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
DESCRIBE pagamentos_extras;
DESCRIBE pagamento_participantes;
