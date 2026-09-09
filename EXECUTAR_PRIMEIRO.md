# ⚠️ EXECUTAR PRIMEIRO - Atualizar Banco de Dados

## 🔴 Problema
O banco de dados ainda não foi atualizado com as novas colunas necessárias para pagamentos extras.

Você recebeu este erro:
```
❌ Erro ao cadastrar pagamento: Unknown column 'morador_pagador_id' in 'field list'
```

## ✅ Solução

### Opção 1: Via Terminal (Recomendado)

Abra o terminal e execute:

```bash
cd "/Users/nathanmota/projetos java/reoublica_Mavem"
mysql -u root -p republica < atualizar_banco_preservar_dados.sql
```

Quando pedir senha, digite: `republica@2026`

### Opção 2: Via MySQL Workbench ou PhpMyAdmin

1. Abra seu cliente MySQL (Workbench, PhpMyAdmin, etc)
2. Copie todo o conteúdo do arquivo `atualizar_banco_preservar_dados.sql`
3. Cole na janela de query
4. Clique em "Execute" ou pressione Ctrl+Enter

### Opção 3: Via MySQL Client Direto

```bash
mysql -u root -p
```

Depois cole o comando:
```sql
USE republica;
source /Users/nathanmota/projetos\ java/reoublica_Mavem/atualizar_banco_preservar_dados.sql;
```

---

## 📊 O que o script faz

✅ Cria as tabelas necessárias:
- `pagamentos_extras` - com coluna `morador_pagador_id`
- `pagamento_participantes` - para rastrear quem participa

✅ **Preserva** todos seus moradores cadastrados
✅ **Preserva** todas suas despesas

---

## 🎯 Próximos Passos

1. Execute o script SQL ☝️
2. Volte para a interface do programa
3. Teste cadastrando um novo pagamento extra
4. Funciona! 🎉

---

## ❓ Dúvidas

**P: Vou perder meus dados?**
A: Não! O script usa `CREATE TABLE IF NOT EXISTS` e preserva tudo.

**P: E se eu usar o arquivo `atualizar_banco.sql` em vez deste?**
A: Esse arquivo faz DROP TABLE na tabela moradores, então **vai limpar tudo**.

**P: Qual arquivo devo usar?**
A: Use sempre `atualizar_banco_preservar_dados.sql` ✅

---

**⏰ Tempo estimado**: 2 segundos
**Status**: 🔴 BLOQUEANTE - Necessário antes de usar pagamentos extras
