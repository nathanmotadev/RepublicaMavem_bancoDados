# 📋 Resumo das Alterações - Pagamentos Extras com Participantes

## 🎯 Objetivo Alcançado
Adicionar funcionalidade de rastreamento de **quem pagou** e **quem participa** de um pagamento extra, permitindo divisão de despesas entre moradores.

---

## 📂 Arquivos Modificados

### 1️⃣ `atualizar_banco.sql`
**O que mudou:**
- Adicionada coluna `morador_pagador_id` à tabela `pagamentos_extras`
- Criada nova tabela `pagamento_participantes` para rastrear quem vai pagar

**Estrutura:**
```sql
-- Tabela pagamentos_extras
- id (Primary Key)
- descricao
- valor
- morador_pagador_id (Foreign Key → moradores)
- data_pagamento

-- Tabela pagamento_participantes
- id (Primary Key)
- pagamento_id (Foreign Key → pagamentos_extras)
- morador_id (Foreign Key → moradores)
- valor_devido
- pago (status do pagamento)
```

---

### 2️⃣ `Pagamento.java`
**Mudanças:**
- ✅ Adicionado campo `id` para identificar o pagamento
- ✅ Adicionado campo `descricao` 
- ✅ Adicionado campo `valor`
- ✅ Adicionado campo `morador_pagador` (tipo Morador)
- ✅ Adicionado campo `participantes` (ArrayList<Morador>)
- ✅ Adicionado campo `dataPagamento` (LocalDateTime)

**Novos Construtores:**
```java
// Construtor completo (para listar pagamentos do banco)
public Pagamento(int id, String descricao, double valor, 
                 Morador morador_pagador, 
                 ArrayList<Morador> participantes, 
                 LocalDateTime dataPagamento)

// Construtor simples (para criar novo pagamento)
public Pagamento(String descricao, double valor, Morador morador_pagador)
```

**Novos Métodos:**
- `getMoradorPagador()` - Retorna quem pagou
- `getParticipantes()` - Retorna lista de participantes
- `getDataPagamento()` - Retorna data do pagamento
- `adicionarParticipante(Morador)` - Adiciona um participante

---

### 3️⃣ `conexaoBanco.java`
**Mudanças:**
- ✅ Adicionado import `import java.time.LocalDateTime;`

**Método `inserirPagamento()` - ATUALIZADO**
```java
// Antes:
inserirPagamento(String descricao, double valor)

// Depois:
inserirPagamento(String descricao, double valor, 
                 int morador_pagador_id, 
                 ArrayList<Integer> participantesIds)
```

**Funcionalidades:**
- Insere o pagamento com ID do pagador
- Divide automaticamente o valor entre pagador + participantes
- Cria registros em `pagamento_participantes` para cada participante

**Método `listarPagamentos()` - TOTALMENTE REFEITO**
- Faz JOIN com tabela `moradores` para buscar dados do pagador
- Busca todos os participantes do pagamento
- Retorna ArrayList de Pagamento com todas as informações

---

### 4️⃣ `republicaInterface.java`
**Case 1 - Cadastrar Pagamento Extra - MODIFICADO**

Novo fluxo:
1. Solicita descrição
2. Solicita valor
3. **Lista moradores e pede para escolher PAGADOR**
4. **Lista moradores e pede para escolher PARTICIPANTES** (múltipla seleção com vírgulas)
5. Insere no banco com todas as informações

**Case 2 - Listar Pagamentos - MODIFICADO**

Nova exibição:
```
📌 Descrição do Pagamento
  💰 Valor total: R$ XXX.XX
  👤 Pagador: Nome do Morador
  👥 Participantes que vão pagar:
     - Nome 1 (R$ YY.YY)
     - Nome 2 (R$ YY.YY)
```

---

## 🚀 Como Usar

### Pré-requisito
Execute o script de atualização do banco:
```bash
mysql -u root -p republica < atualizar_banco.sql
```

### Fluxo de Uso

**Exemplo: Pizza de R$ 60 para 3 pessoas (João pagou, Maria e Pedro vão devolver)**

1. Menu → 3 (Pagamentos Extras) → 1 (Cadastrar)
2. Descrição: `Pizza para grupo`
3. Valor: `60`
4. Pagador: `1` (João)
5. Participantes: `2,3` (Maria e Pedro)
6. Sistema calcula: R$ 20 cada um (60 ÷ 3)

Resultado ao listar:
```
📌 Pizza para grupo
  💰 Valor total: R$ 60.00
  👤 Pagador: João
  👥 Participantes que vão pagar:
     - Maria (R$ 20.00)
     - Pedro (R$ 20.00)
```

---

## 📊 Relacionamento de Tabelas

```
moradores
    ↓
    ├─→ pagamentos_extras (morador_pagador_id)
    │       ↓
    │       └─→ pagamento_participantes (pagamento_id)
    │               ↓
    └─→ pagamento_participantes (morador_id)
```

---

## ✅ Validações Implementadas

- ✅ Validação de índice de morador
- ✅ Não permite selecionar pagador como participante
- ✅ Não permite duplicar participantes
- ✅ Valida entrada de múltiplos participantes (com vírgula)
- ✅ Avisa se nenhum participante foi selecionado
- ✅ Verifica se há moradores cadastrados

---

## 📝 Arquivos de Suporte

- `INSTRUÇÕES_PAGAMENTOS_EXTRAS.md` - Guia completo de uso

---

## 🔍 Próximas Funcionalidades Sugeridas

- [ ] Registrar quando um participante pagou sua parte
- [ ] Gerar relatório de débitos entre moradores
- [ ] Sistema de notificação de débitos pendentes
- [ ] Histórico de quem mais gasta em relação à média
- [ ] Função para "fechar" pagamentos

---

**Data da Implementação**: Setembro 2026
**Status**: ✅ Completo
