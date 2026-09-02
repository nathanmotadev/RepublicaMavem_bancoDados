package dominioRepublica;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;


public class republicaInterface {
    public static void main(String[] args) {
        String interfaceRep = """
                ========================
                       REPÚBLICA
                ========================
                
                1 - Moradores
                2 - Despesas
                3 - Pagamentos extras
                0 - Sair
                
                Escolha uma opção:
                
                """;
        String interfaceRepMoradores = """
                ╔══════════════════════════════════╗
                ║          👥 MORADORES            ║
                ╠══════════════════════════════════╣
                ║                                  ║
                ║  1 → Cadastrar morador           ║
                ║  2 → Listar moradores            ║
                ║  3 → Remover morador             ║
                ║  0 → Voltar                      ║
                ║                                  ║
                ╚══════════════════════════════════╝
                """;
        String interfaceRepDespesas = """
                ╔══════════════════════════════════╗
                ║          DESPESAS                ║
                ╠══════════════════════════════════╣
                ║                                  ║
                ║  1 → Cadastrar despesa           ║
                ║  2 → Listar despesas do mês      ║
                ║  0 → Voltar                      ║
                ║                                  ║
                ╚══════════════════════════════════╝
                """;

        String interfaceRepPagamentosExtras = """
                ╔══════════════════════════════════╗
                ║          PAGAMENTOS EXTRAS       ║
                ╠══════════════════════════════════╣
                ║                                  ║
                ║  1 → Cadastrar pagamento extra   ║
                ║  2 → Listar pagamentos extras    ║
                ║  0 → Voltar                      ║
                ║                                  ║
                ╚══════════════════════════════════╝
                """;

        Scanner input = new Scanner(System.in);
        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Testar conexão com o banco ao iniciar
        conexaoBanco.testarConexao();


        while (true) {
            System.out.print(interfaceRep);

            int setOpcao = input.nextInt();
            input.nextLine();


            switch (setOpcao) {
                case 1:
                    System.out.println(interfaceRepMoradores);

                    System.out.println("Escolha uma opção: ");
                    int opcaoMorador = input.nextInt();
                    input.nextLine();


                    switch (opcaoMorador) {
                        case 1:
                            System.out.println("Digite o nome do novo morador: ");
                            String nome = input.nextLine();

                            System.out.print("Data de nascimento (dd/MM/yyyy): ");
                            String dataNascimentoStr = input.nextLine();
                            LocalDate dataNascimento = LocalDate.parse(dataNascimentoStr, formatoData);

                            System.out.print("Data de entrada (dd/MM/yyyy): ");
                            String data = input.nextLine();
                            LocalDate dataEntrada = LocalDate.parse(data, formatoData);
                            

                            conexaoBanco.inserirMorador(nome, dataNascimento, dataEntrada);
                            break;

                        case 2:
                            System.out.println("\n========== 👥 MORADORES CADASTRADOS ==========\n");
                            ArrayList<Morador> moradores = conexaoBanco.listarMoradores();
                            
                            if (moradores.isEmpty()) {
                                System.out.println("Nenhum morador cadastrado.");
                            } else {
                                for (int i = 0; i < moradores.size(); i++) {
                                    Morador m = moradores.get(i);
                                    System.out.println((i + 1) + " - " + m.getNome() + 
                                                     " | Idade: " + m.getIdade() + 
                                                     " | Nascimento: " + m.getDataNascimento().format(formatoData) +
                                                     " | Entrada: " + m.getDataEntrada().format(formatoData));
                                }
                            }
                            System.out.println();
                            break;

                        case 3:
                            System.out.println("\n========== ❌ REMOVER MORADOR ==========\n");
                            ArrayList<Morador> moradoresLista = conexaoBanco.listarMoradores();
                            
                            if (moradoresLista.isEmpty()) {
                                System.out.println("Nenhum morador cadastrado.");
                            } else {
                                for (int i = 0; i < moradoresLista.size(); i++) {
                                    System.out.println((i + 1) + " - " + moradoresLista.get(i).getNome());
                                }
                                System.out.println("Digite o número do morador a remover: ");
                                int numMorador = input.nextInt();
                                input.nextLine();

                                if (numMorador > 0 && numMorador <= moradoresLista.size()) {
                                    int idMorador = moradoresLista.get(numMorador - 1).getId();
                                    conexaoBanco.deletarMorador(idMorador);
                                } else {
                                    System.out.println("❌ Número inválido!");
                                }
                            }
                            System.out.println();
                            break;
                    }
                    break;

                case 2:
                    System.out.println(interfaceRepDespesas);

                    System.out.println("Escolha uma opção: ");
                    int opcaoDespesa = input.nextInt();
                    input.nextLine();

                    switch (opcaoDespesa) {
                        case 1:
                            System.out.println("Digite a descrição da despesa: ");
                            String descricao = input.nextLine();

                            System.out.println("Digite o valor da despesa: ");
                            double valor = input.nextDouble();
                            input.nextLine();

                            System.out.print("Data da despesa (dd/MM/yyyy): ");
                            String dataDespesaDigitada = input.nextLine();
                            LocalDate data_despesa = LocalDate.parse(dataDespesaDigitada, formatoData);

                            System.out.println("Digite a categoria da despesa: ");
                            String categoria = input.nextLine();

                            System.out.println("A despesa foi paga? (true/false): ");
                            boolean pago = input.nextBoolean();
                            input.nextLine();

                            System.out.println("Digite a forma de pagamento: ");
                            String forma_pagamento = input.nextLine();

                            System.out.println("Digite as observações: ");
                            String observacoes = input.nextLine();

                            LocalDate data_criacao = LocalDate.now();

                            conexaoBanco.inserirDespesa(descricao, valor, data_despesa, categoria, pago, forma_pagamento, observacoes);
                            break;

                        case 2:
                            System.out.println("Digite o mês (1 a 12): ");
                            int mes = input.nextInt();

                            System.out.println("Digite o ano: ");
                            int ano = input.nextInt();
                            input.nextLine();

                            System.out.println("\n========== 💰 DESPESAS DO MÊS " + mes + "/" + ano + " ==========\n");
                            ArrayList<Despesa> despesasMes = conexaoBanco.listarDespesasPorMes(mes, ano);
                            
                            if (despesasMes.isEmpty()) {
                                System.out.println("Nenhuma despesa encontrada para esse mês.");
                            } else {
                                double total = 0;
                                for (Despesa d : despesasMes) {
                                    System.out.println("📌 " + d.getDescricao() + 
                                                     " | R$ " + String.format("%.2f", d.getValor()) + 
                                                     " | " + d.getData().format(formatoData));
                                    total += d.getValor();
                                }
                                System.out.println("\n💵 TOTAL: R$ " + String.format("%.2f", total) + "\n");
                            }
                            break;
                    }
                    break;

                case 3:
                    System.out.println(interfaceRepPagamentosExtras);

                    System.out.println("Escolha uma opção: ");
                    int opcaoPagamentoExtra = input.nextInt();
                    input.nextLine();

                    switch (opcaoPagamentoExtra) {
                        case 1:
                            System.out.println("Do que se trata?: ");
                            String descricaoP = input.nextLine();

                            System.out.println("Digite o valor do pagamento extra: ");
                            double valorPago = input.nextDouble();
                            input.nextLine();

                            conexaoBanco.inserirPagamento(descricaoP, valorPago);
                            break;

                        case 2:
                            System.out.println("\n========== 🏦 PAGAMENTOS EXTRAS ==========\n");
                            ArrayList<Pagamento> pagamentosExtras = conexaoBanco.listarPagamentos();
                            
                            if (pagamentosExtras.isEmpty()) {
                                System.out.println("Nenhum pagamento registrado.");
                            } else {
                                for (int i = 0; i < pagamentosExtras.size(); i++) {
                                    System.out.println("Pagamento " + (i + 1) + ":");
                                    System.out.println("  💰 Valor: R$ " + String.format("%.2f", pagamentosExtras.get(i).getValorPago()));
                                }
                            }
                            System.out.println();
                            break;
                    }
                    break;

                case 0:
                    System.out.println("👋 Saindo...");
                    input.close();
                    return;

                default:
                    System.out.println("❌ Opção inválida!");
            }

        }
    }
}
