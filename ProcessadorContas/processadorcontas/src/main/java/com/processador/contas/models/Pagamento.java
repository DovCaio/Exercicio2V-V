package com.processador.contas.models;

import java.time.LocalDate;

import com.processador.contas.enums.TipoPagamento;

public class Pagamento {
    private double valor;
    private LocalDate data;
    private TipoPagamento tipo;

    public Pagamento(double valor, LocalDate data, TipoPagamento tipo) {
        this.valor = valor;
        this.data = data;
        this.tipo = tipo;

    }

    public double getValor() {
        return valor;
    }

    public LocalDate getData() {
        return data;
    }

    public TipoPagamento getTipo() {
        return tipo;
    }
}
