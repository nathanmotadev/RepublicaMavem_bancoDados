package dominioRepublica;

import java.time.LocalDate;

/**
 * Classe de teste - descontinuada
 * @deprecated Use republicaInterface.java para interagir com o sistema
 */
@Deprecated
public class main {
    public static void main(String[] args) {
        System.out.println("Classe descontinuada! Use republicaInterface.java");
        System.out.println("\nPara iniciar a aplicação, execute: java dominioRepublica.republicaInterface");
    }

    public static class Despesa {

        private String descricao;
        private double valor;
        private LocalDate data;

        public Despesa(String descricao, double valor, LocalDate data) {
            this.descricao = descricao;
            this.valor = valor;
            this.data = data;
        }

        public String getDescricao() {
            return descricao;
        }

        public void setDescricao(String descricao) {
            this.descricao = descricao;
        }

        public double getValor() {
            return valor;
        }

        public void setValor(double valor) {
            this.valor = valor;
        }

        public LocalDate getData() {
            return data;
        }

        public void setData(LocalDate data) {
            this.data = data;
        }
    }
}
