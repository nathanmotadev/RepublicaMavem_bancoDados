# 🏠 República Maven - Sistema de Gerenciamento

Um aplicativo Java para gerenciar moradores, despesas e pagamentos em repúblicas (casas compartilhadas). O sistema utiliza Maven como gerenciador de dependências e MySQL como banco de dados.

## 📋 Funcionalidades

### 👥 Gerenciamento de Moradores
- **Cadastrar** novos moradores com nome, data de nascimento e data de entrada
- **Listar** todos os moradores cadastrados com idade calculada automaticamente
- **Remover** moradores da república

### 💰 Gerenciamento de Despesas
- **Cadastrar** despesas com descrição, valor, data, categoria, forma de pagamento e observações
- **Listar** despesas por mês/ano
- **Visualizar** total de despesas do mês
- Controlar se a despesa foi paga

### 🏦 Pagamentos Extras
- **Registrar** pagamentos extras (valores adicionais, multas, etc.)
- **Listar** todos os pagamentos extras realizados

## 🛠️ Requisitos

- **Java 21** ou superior
- **Maven 3.6+**
- **MySQL 8.0+**
- **Git**

## 🚀 Instalação

### 1. Clone o repositório
```bash
git clone https://github.com/nathanmotadev/RepublicaMavem_bancoDados.git
cd RepublicaMavem_bancoDados
```

### 2. Configure o banco de dados MySQL

Crie o banco de dados e as tabelas necessárias:

```sql
CREATE DATABASE republica;
USE republica;

-- Tabela de moradores
CREATE TABLE moradores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    dataNascimento DATE NOT NULL,
    data_entrada DATE NOT NULL
);

-- Tabela de despesas
CREATE TABLE despesas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL,
    valor DECIMAL(10, 2) NOT NULL,
    data_despesa DATE NOT NULL,
    categoria VARCHAR(50),
    pago BOOLEAN DEFAULT FALSE,
    forma_pagamento VARCHAR(50),
    observacoes TEXT,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de pagamentos extras
CREATE TABLE pagamentos_extras (
    id INT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL,
    valor DECIMAL(10, 2) NOT NULL,
    data_pagamento TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### 3. Configure as credenciais do banco

Edite o arquivo `src/main/java/dominioRepublica/conexaoBanco.java`:

```java
private static final String URL = "jdbc:mysql://localhost:3306/republica";
private static final String USUARIO = "root";
private static final String SENHA = "sua_senha_aqui";
```

### 4. Instale as dependências

```bash
mvn clean install
```

## 📱 Como Usar

### Executar a aplicação

```bash
mvn compile
mvn exec:java -Dexec.mainClass="dominioRepublica.republicaInterface"
```

Ou compile e execute o JAR:

```bash
mvn package
java -cp target/republica_Maven-1.0-SNAPSHOT.jar dominioRepublica.republicaInterface
```

### Interface do Programa

Ao iniciar, você verá o menu principal:

```
========================
       REPÚBLICA
========================

1 - Moradores
2 - Despesas
3 - Pagamentos extras
0 - Sair

Escolha uma opção:
```

Navegue pelos menus usando os números de opção.

## 📁 Estrutura do Projeto

```
RepublicaMavem_bancoDados/
├── src/
│   └── main/java/
│       └── dominioRepublica/
│           ├── republicaInterface.java    # Interface principal (menu)
│           ├── conexaoBanco.java          # Operações com banco de dados
│           ├── Morador.java               # Classe de modelo: Morador
│           ├── Despesa.java               # Classe de modelo: Despesa
│           ├── Pagamento.java             # Classe de modelo: Pagamento
│           ├── Republica.java             # Classe legada (descontinuada)
│           └── main.java                  # Classe legada (descontinuada)
├── pom.xml                               # Configuração Maven
├── README.md                             # Este arquivo
├── atualizar_banco.sql                   # Scripts SQL adicionais
└── RepublicaMavem_bancoDados/            # Diretório auxiliar
```

## 🗄️ Banco de Dados

### Tabelas

#### `moradores`
| Campo | Tipo | Descrição |
|-------|------|-----------|
| id | INT | ID único do morador (auto-incrementado) |
| nome | VARCHAR(100) | Nome do morador |
| dataNascimento | DATE | Data de nascimento |
| data_entrada | DATE | Data de entrada na república |

#### `despesas`
| Campo | Tipo | Descrição |
|-------|------|-----------|
| id | INT | ID único da despesa |
| descricao | VARCHAR(255) | Descrição da despesa |
| valor | DECIMAL(10,2) | Valor em reais |
| data_despesa | DATE | Data da despesa |
| categoria | VARCHAR(50) | Categoria (ex: aluguel, água) |
| pago | BOOLEAN | Se a despesa foi paga |
| forma_pagamento | VARCHAR(50) | Forma de pagamento |
| observacoes | TEXT | Observações adicionais |
| data_criacao | TIMESTAMP | Quando foi registrada |

#### `pagamentos_extras`
| Campo | Tipo | Descrição |
|-------|------|-----------|
| id | INT | ID único do pagamento |
| descricao | VARCHAR(255) | Descrição do pagamento |
| valor | DECIMAL(10,2) | Valor pago |
| data_pagamento | TIMESTAMP | Quando foi registrado |

## 🔧 Dependências Maven

- **mysql-connector-j 8.4.0** - Driver JDBC para MySQL

## 📝 Exemplos de Uso

### Cadastrar um novo morador
1. Selecione opção `1` no menu principal
2. Selecione `1` para cadastrar morador
3. Insira nome, data de nascimento e data de entrada (formato: dd/MM/yyyy)

### Registrar uma despesa
1. Selecione opção `2` no menu principal
2. Selecione `1` para cadastrar despesa
3. Insira descrição, valor, data, categoria, status de pagamento, forma de pagamento e observações

### Consultar despesas de um mês
1. Selecione opção `2` no menu principal
2. Selecione `2` para listar despesas
3. Insira mês (1-12) e ano

## ⚙️ Configuração Avançada

### Mudar a porta do MySQL
Se seu MySQL está em uma porta diferente, altere em `conexaoBanco.java`:
```java
private static final String URL = "jdbc:mysql://localhost:3307/republica";
```

### Usar banco remoto
Para conectar a um banco remoto:
```java
private static final String URL = "jdbc:mysql://seu_host:3306/republica";
```

## 🐛 Resolução de Problemas

### Erro: "Connection refused"
- Verifique se o MySQL está em execução
- Confirme se as credenciais estão corretas

### Erro: "No driver found"
- Garanta que o Maven instalou a dependência corretamente
- Execute `mvn clean install` novamente

### Erro: "Database not found"
- Crie o banco de dados `republica` conforme instruções acima
- Verifique se o nome do banco está correto em `conexaoBanco.java`

## 📄 Licença

Este projeto é de uso livre para fins educacionais e privados.

## 👨‍💻 Autor

**Nathan Mota**  
GitHub: [@nathanmotadev](https://github.com/nathanmotadev)

---

**Última atualização:** Setembro de 2026