#!/bin/bash

# Script para atualizar o banco de dados automaticamente

echo "=================================="
echo "🔄 Atualizando banco de dados..."
echo "=================================="

# Executar o script SQL
mysql -u root -p"republica@2026" < "$(dirname "$0")/atualizar_banco_forcar.sql"

if [ $? -eq 0 ]; then
    echo ""
    echo "=================================="
    echo "✅ Banco atualizado com SUCESSO!"
    echo "=================================="
else
    echo ""
    echo "=================================="
    echo "❌ ERRO ao atualizar banco!"
    echo "=================================="
    exit 1
fi
