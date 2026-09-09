package dominioRepublica;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
        String sql = "INSERT INTO moradores (nome, data_nascimento, data_entrada) VALUES (?, ?, ?)";
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
                LocalDate dataNascimento = rs.getDate("data_nascimento").toLocalDate();
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

    public static void inserirPagamento(String descricao, double valor, int morador_pagador_id, ArrayList<Integer> participantesIds) {
        String sql = "INSERT INTO pagamentos_extras (descricao, valor, morador_pagador_id, data_pagamento) VALUES (?, ?, ?, NOW())";

        try (Connection conexao = conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, descricao);
            stmt.setDouble(2, valor);
            stmt.setInt(3, morador_pagador_id);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int pagamentoId = rs.getInt(1);
                
                double valorPorPessoa = valor / (participantesIds.size() + 1);
                
                String sqlParticipantes = "INSERT INTO pagamento_participantes (pagamento_id, morador_id, valor_devido, pago) VALUES (?, ?, ?, FALSE)";
                try (PreparedStatement stmtPart = conexao.prepareStatement(sqlParticipantes)) {
                    for (int morador_id : participantesIds) {
                        stmtPart.setInt(1, pagamentoId);
                        stmtPart.setInt(2, morador_id);
                        stmtPart.setDouble(3, valorPorPessoa);
                        stmtPart.addBatch();
                    }
                    stmtPart.executeBatch();
                }
                
                System.out.println("✅ Pagamento extra cadastrado com sucesso!");
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("❌ Erro ao cadastrar pagamento: " + e.getMessage());
        }
    }

    public static ArrayList<Pagamento> listarPagamentos() {
        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        String sql = "SELECT pe.*, m.nome as pagador_nome, m.data_nascimento, m.data_entrada FROM pagamentos_extras pe " +
                     "JOIN moradores m ON pe.morador_pagador_id = m.id " +
                     "ORDER BY pe.data_pagamento DESC";

        try (Connection conexao = conectar();
             Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String descricao = rs.getString("descricao");
                double valor = rs.getDouble("valor");
                int morador_id = rs.getInt("morador_pagador_id");
                String morador_nome = rs.getString("pagador_nome");
                LocalDate dataNascimento = rs.getDate("data_nascimento").toLocalDate();
                LocalDate dataEntrada = rs.getDate("data_entrada").toLocalDate();
                LocalDateTime dataPagamento = rs.getTimestamp("data_pagamento").toLocalDateTime();
                
                Morador pagador = new Morador(morador_nome, morador_id, dataNascimento, '\0', dataEntrada);
                
                String sqlParticipantes = "SELECT m.* FROM pagamento_participantes pp " +
                                         "JOIN moradores m ON pp.morador_id = m.id " +
                                         "WHERE pp.pagamento_id = ?";
                ArrayList<Morador> participantes = new ArrayList<>();
                
                try (PreparedStatement stmtPart = conexao.prepareStatement(sqlParticipantes)) {
                    stmtPart.setInt(1, id);
                    ResultSet rsPart = stmtPart.executeQuery();
                    
                    while (rsPart.next()) {
                        int part_id = rsPart.getInt("id");
                        String part_nome = rsPart.getString("nome");
                        LocalDate part_dataNasc = rsPart.getDate("data_nascimento").toLocalDate();
                        LocalDate part_dataEntrada = rsPart.getDate("data_entrada").toLocalDate();
                        
                        Morador participante = new Morador(part_nome, part_id, part_dataNasc, '\0', part_dataEntrada);
                        participantes.add(participante);
                    }
                }
                
                Pagamento pagamento = new Pagamento(id, descricao, valor, pagador, participantes, dataPagamento);
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
