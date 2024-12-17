package com.processador.contas.validators;

import java.util.List;

import com.processador.contas.models.Conta;
import com.processador.contas.models.Fatura;

public class ProcessadorContasValidator {

    public void validarFatura(Fatura fatura) {
        if (fatura == null) {
            throw new IllegalArgumentException("A fatura não pode ser nula");
        }
        if (fatura.getCliente() == null || fatura.getCliente().isBlank()) {
            throw new IllegalArgumentException("O cliente da fatura não pode ser nulo ou vazio");
        }
        if (fatura.getValorTotal() <= 0) {
            throw new IllegalArgumentException("O valor total da fatura deve ser maior que zero");
        }
        if (fatura.getData() == null) {
            throw new IllegalArgumentException("A data da fatura não pode ser nula");
        }
    }

    public void validarContas(List<Conta> contas) {
        if (contas == null || contas.isEmpty()) {
            throw new IllegalArgumentException("A lista de contas não pode ser nula ou vazia");
        }
    }

    public void validarConta(Conta conta) {
        if (conta == null) {
            throw new IllegalArgumentException("A conta não pode ser nula");
        }
        if (conta.getCodigo() == null || conta.getCodigo().isBlank()) {
            throw new IllegalArgumentException("O Codigo da conta não pode ser nulo ou vazio");
        }
        if (conta.getValorPago() < 0) {
            throw new IllegalArgumentException("O valor pago da conta não pode ser negativo");
        }
        if (conta.getTipoPagamento() == null) {
            throw new IllegalArgumentException("O tipo de pagamento da conta não pode ser nulo");
        }
        if (conta.getData() == null) {
            throw new IllegalArgumentException("A data da conta não pode ser nula");
        }
    }
}
