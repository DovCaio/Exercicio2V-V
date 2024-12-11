package com.processador.contas.models;

import java.time.LocalDate;

import com.processador.contas.enums.StatusFatura;

public class Fatura {
    private String cliente;
    private double valorTotal;
    private LocalDate data;
    private StatusFatura status;

    public Fatura(String cliente, double valorTotal, LocalDate data) {
        this.cliente = cliente;
        this.valorTotal = valorTotal;
        this.data = data;
        this.status = StatusFatura.PENDENTE;
    }

    public String getCliente() {
        return cliente;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public LocalDate getData() {
        return data;
    }

    public StatusFatura getStatus() {
        return status;
    }

    public void setStatus(StatusFatura status) {
        this.status = status;
    }
}
