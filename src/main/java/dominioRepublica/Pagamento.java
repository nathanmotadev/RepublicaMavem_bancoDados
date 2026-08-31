package dominioRepublica;

import java.util.ArrayList;

public class Pagamento {

    private ArrayList<Morador> pagadores;
    private double valorPago;

    public Pagamento(ArrayList<Morador> pagadores, double valorPago) {
        this.pagadores = pagadores;
        this.valorPago = valorPago;
    }

    public void mostrarPagamento() {
        System.out.println("Pagadores:");
        for (int i = 0; i < pagadores.size(); i++) {
            System.out.println("- " + pagadores.get(i).nome);
        }
        System.out.println("Valor pago: " + valorPago);
    }

    public double getValorPago() {
        return valorPago;
    }

    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }

    public ArrayList<Morador> getPagadores() {
        return pagadores;
    }

    public void setPagadores(ArrayList<Morador> pagadores) {
        this.pagadores = pagadores;
    }
}
