package dominioRepublica;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class conexaoBanco {

    private static final String URL = "jdbc:mysql://localhost:3306/republica";
    private static final String USUARIO = "root";
    private static final String SENHA = "republica@2026";

    // Conectar ao banco de dados
    public static Connection conectar() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }

    // ======================== MORADORES ========================

    public static void inserirMorador(String nome, LocalDate dataNascimento, LocalDate dataEntrada) {
        String sql = "INSERT INTO moradores (nome, dataNascimento, data_entrada) VALUES (?, ?, ?)";
        try (Connection conexao = conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setDate(2, java.sql.Date.valueOf(dataNascimento));
            stmt.setDate(3, java.sql.Date.valueOf(dataEntrada));
            stmt.executeUpdate();
            System.out.println("✅ Morador cadastrado com sucesso!");

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("❌ Erro ao cadastrar morador: " + e.getMessage());
        }
    }

    public static ArrayList<Morador> listarMoradores() {
        ArrayList<Morador> moradores = new ArrayList<>();
        String sql = "SELECT * FROM moradores ORDER BY id";

        try (Connection conexao = conectar();
             Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                LocalDate dataNascimento = rs.getDate("dataNascimento").toLocalDate();
                LocalDate dataEntrada = rs.getDate("data_entrada").toLocalDate();

                Morador morador = new Morador(nome, id, dataNascimento, '\0', dataEntrada);
                moradores.add(morador);
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("❌ Erro ao listar moradores: " + e.getMessage());
        }

        return moradores;
    }

    public static void deletarMorador(int id) {
        String sql = "DELETE FROM moradores WHERE id = ?";

        try (Connection conexao = conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("✅ Morador removido com sucesso!");
            } else {
                System.out.println("⚠️ Morador não encontrado!");
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("❌ Erro ao remover morador: " + e.getMessage());
        }
    }

    // ======================== DESPESAS ========================

    public static void inserirDespesa(String descricao, double valor, LocalDate data) {
        String sql = "INSERT INTO despesas (descricao, valor, data_despesa) VALUES (?, ?, ?)";

        try (Connection conexao = conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, descricao);
            stmt.setDouble(2, valor);
            stmt.setDate(3, java.sql.Date.valueOf(data));
            stmt.executeUpdate();
            System.out.println("✅ Despesa cadastrada com sucesso!");

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("❌ Erro ao cadastrar despesa: " + e.getMessage());
        }
    }

    public static ArrayList<Despesa> listarDespesas() {
        ArrayList<Despesa> despesas = new ArrayList<>();
        String sql = "SELECT * FROM despesas ORDER BY data_despesa DESC";

        try (Connection conexao = conectar();
             Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String descricao = rs.getString("descricao");
                double valor = rs.getDouble("valor");
                LocalDate data = rs.getDate("data_despesa").toLocalDate();

                Despesa despesa = new Despesa(descricao, valor, data);
                despesas.add(despesa);
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("❌ Erro ao listar despesas: " + e.getMessage());
        }

        return despesas;
    }

    public static ArrayList<Despesa> listarDespesasPorMes(int mes, int ano) {
        ArrayList<Despesa> despesas = new ArrayList<>();
        String sql = "SELECT * FROM despesas WHERE MONTH(data_despesa) = ? AND YEAR(data_despesa) = ? ORDER BY data_despesa";

        try (Connection conexao = conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, mes);
            stmt.setInt(2, ano);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String descricao = rs.getString("descricao");
                double valor = rs.getDouble("valor");
                LocalDate data = rs.getDate("data_despesa").toLocalDate();

                Despesa despesa = new Despesa(descricao, valor, data);
                despesas.add(despesa);
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("❌ Erro ao listar despesas: " + e.getMessage());
        }

        return despesas;
    }

    // ======================== PAGAMENTOS EXTRAS ========================

    public static void inserirPagamento(String descricao, double valor) {
        String sql = "INSERT INTO pagamentos_extras (descricao, valor, data_pagamento) VALUES (?, ?, NOW())";

        try (Connection conexao = conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, descricao);
            stmt.setDouble(2, valor);
            stmt.executeUpdate();
            System.out.println("✅ Pagamento extra cadastrado com sucesso!");

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("❌ Erro ao cadastrar pagamento: " + e.getMessage());
        }
    }

    public static ArrayList<Pagamento> listarPagamentos() {
        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        String sql = "SELECT * FROM pagamentos_extras ORDER BY data_pagamento DESC";

        try (Connection conexao = conectar();
             Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                double valorPago = rs.getDouble("valor");
                ArrayList<Morador> pagadores = new ArrayList<>();

                Pagamento pagamento = new Pagamento(pagadores, valorPago);
                pagamentos.add(pagamento);
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("❌ Erro ao listar pagamentos: " + e.getMessage());
        }

        return pagamentos;
    }

    // Teste de conexão
    public static void testarConexao() {
        try (Connection conexao = conectar()) {
            System.out.println("✅ Conexão com banco de dados realizada com sucesso!");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("❌ Erro ao conectar: " + e.getMessage());
        }
    }
}
