# 🏦 Instruções - Pagamentos Extras Compartilhados

## ⚠️ IMPORTANTE - Leia Primeiro!

**ANTES DE USAR**, você precisa executar o script SQL para atualizar o banco.

👉 Abra o arquivo `EXECUTAR_PRIMEIRO.md` e siga as instruções!

## ✅ O que foi modificado

### 1. **Banco de Dados** (`atualizar_banco.sql`)
Foram adicionadas duas modificações principais:

- **Tabela `pagamentos_extras`**: Agora inclui o campo `morador_pagador_id` para rastrear quem pagou
- **Tabela `pagamento_participantes`**: Nova tabela para registrar quem mais vai participar do pagamento

### 2. **Classe `Pagamento.java`**
- Agora armazena o morador pagador (`morador_pagador`)
- Armazena lista de participantes que vão contribuir
- Novos métodos getters/setters para acessar essas informações

### 3. **Classe `conexaoBanco.java`**
- Método `inserirPagamento()` atualizado para aceitar:
  - ID do morador pagador
  - Lista de IDs dos participantes
- Método `listarPagamentos()` atualizado para trazer o pagador e participantes junto com o pagamento

### 4. **Interface `republicaInterface.java`**
- Ao cadastrar um pagamento extra:
  - ✅ Permite selecionar quem vai pagar
  - ✅ Permite selecionar quem mais vai participar (separados por vírgula)
  - ✅ Calcula automaticamente quanto cada pessoa deve pagar

- Ao listar pagamentos:
  - ✅ Mostra quem pagou
  - ✅ Mostra quem mais vai participar
  - ✅ Mostra quanto cada pessoa precisa pagar

## 📝 Como usar

### Atualizar o banco de dados

**IMPORTANTE**: Execute o script `atualizar_banco.sql` NO SEU MYSQL antes de usar a interface!

```bash
mysql -u root -p republica < atualizar_banco.sql
```

Você será solicitado a digitar sua senha.

### Fluxo de uso

#### 1. **Cadastrar Pagamento Extra**
- Menu → 3 (Pagamentos extras) → 1 (Cadastrar)
- Informe a descrição (ex: "Compra de gás")
- Informe o valor total
- Selecione quem **pagou** (ex: João)
- Selecione quem **participa** (ex: 2, 3, 4)
  - Digite os números separados por **vírgula** (exemplo: `2,3,4`)
  - O sistema divide automaticamente o valor entre todos

#### 2. **Listar Pagamentos Extras**
- Menu → 3 (Pagamentos extras) → 2 (Listar)
- Mostra:
  - 📌 Descrição do pagamento
  - 💰 Valor total
  - 👤 Quem pagou
  - 👥 Quem vai precisar devolver
  - Quanto cada pessoa deve

## 💡 Exemplo Prático

**Cenário**: João pagou R$ 100 de compras de supermercado que será dividido com Maria e Pedro

1. **Cadastrar:**
   - Descrição: `Compras supermercado`
   - Valor: `100`
   - Pagador: `1` (João)
   - Participantes: `2,3` (Maria e Pedro)

2. **Resultado ao listar:**
   ```
   📌 Compras supermercado
     💰 Valor total: R$ 100.00
     👤 Pagador: João
     👥 Participantes que vão pagar:
        - Maria (R$ 33.33)
        - Pedro (R$ 33.33)
   ```

   *(Observe que é dividido por 3: João + Maria + Pedro)*

## 🔧 Tecnologia

- **Banco de dados**: MySQL
- **Tabelas relacionadas**:
  - `pagamentos_extras`: Armazena o pagamento principal
  - `pagamento_participantes`: Rastreia quem precisa pagar
  - `moradores`: Lista de moradores (relacionado)

## 📌 Notas Importantes

- ⚠️ **BACKUP**: Faça backup do seu banco antes de atualizar
- ⚠️ **Escript SQL**: O script `DROP TABLE IF EXISTS moradores` vai resetar seus moradores. Se você quer manter dados antigos, entre em contato antes de executar!
- ✅ Os valores são **divididos igualmente** entre todos os participantes + pagador
- ✅ Cada participante precisa pagar sua parte ao pagador
