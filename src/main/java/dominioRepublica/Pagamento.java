package dominioRepublica;

import java.util.ArrayList;
import java.time.LocalDateTime;

public class Pagamento {

    private int id;
    private String descricao;
    private double valor;
    private Morador morador_pagador;
    private ArrayList<Morador> participantes;
    private LocalDateTime dataPagamento;

    public Pagamento(int id, String descricao, double valor, Morador morador_pagador, 
                     ArrayList<Morador> participantes, LocalDateTime dataPagamento) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.morador_pagador = morador_pagador;
        this.participantes = participantes;
        this.dataPagamento = dataPagamento;
    }

    public Pagamento(String descricao, double valor, Morador morador_pagador) {
        this.descricao = descricao;
        this.valor = valor;
        this.morador_pagador = morador_pagador;
        this.participantes = new ArrayList<>();
    }

    public void adicionarParticipante(Morador morador) {
        participantes.add(morador);
    }

    public void mostrarPagamento() {
        System.out.println("Descrição: " + descricao);
        System.out.println("Pagador: " + morador_pagador.getNome());
        System.out.println("Valor total: " + valor);
        System.out.println("Participantes:");
        for (Morador m : participantes) {
            System.out.println("  - " + m.getNome());
        }
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Morador getMoradorPagador() {
        return morador_pagador;
    }

    public void setMoradorPagador(Morador morador_pagador) {
        this.morador_pagador = morador_pagador;
    }

    public ArrayList<Morador> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(ArrayList<Morador> participantes) {
        this.participantes = participantes;
    }

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public double getValorPago() {
        return valor;
    }

    public ArrayList<Morador> getPagadores() {
        ArrayList<Morador> lista = new ArrayList<>();
        lista.add(morador_pagador);
        return lista;
    }
}
