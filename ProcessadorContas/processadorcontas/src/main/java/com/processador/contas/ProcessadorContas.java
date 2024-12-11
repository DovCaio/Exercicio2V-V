package com.processador.contas;

import java.util.ArrayList;
import java.util.List;

import com.processador.contas.enums.StatusFatura;
import com.processador.contas.enums.TipoPagamento;
import com.processador.contas.models.Conta;
import com.processador.contas.models.Fatura;
import com.processador.contas.models.Pagamento;

public class ProcessadorContas {

    public void processarFatura(Fatura fatura, List<Conta> contas) {
        List<Pagamento> pagamentos = gerarPagamentos(contas, fatura);
        double somaPagamentos = pagamentos.stream().mapToDouble(Pagamento::getValor).sum();

        if (somaPagamentos >= fatura.getValorTotal()) {
            fatura.setStatus(StatusFatura.PAGA);
        } else {
            fatura.setStatus(StatusFatura.PENDENTE);
        }
    }

    private List<Pagamento> gerarPagamentos(List<Conta> contas, Fatura fatura) {
        List<Pagamento> pagamentos = new ArrayList<>();

        for (Conta conta : contas) {
            double valor = 0;
            TipoPagamento tipo = conta.getTipoPagamento();

            if (tipo.equals(TipoPagamento.BOLETO)) {
                valor = processarBoleto(conta, fatura);
            } else if (tipo.equals(TipoPagamento.CARTAO_CREDITO)) {
                valor = processarCartaoCredito(conta, fatura);
            } else if (tipo.equals(TipoPagamento.TRANSFERENCIA_BANCARIA)) {
                valor = processarTransferencia(conta, fatura);
            }

            if (valor > 0) {
                Pagamento pagamento = new Pagamento(valor, conta.getData(), tipo);
                pagamentos.add(pagamento);
            }
        }
        return pagamentos;
    }

    private double processarBoleto(Conta conta, Fatura fatura) {
        double valor = conta.getValorPago();
        
        if (valor >= 0.01 && valor <= 5000.00) {
            if (conta.getData().isAfter(fatura.getData())) {
                valor += valor * 0.10;
            }
            return valor;
        }
        return 0;
    }

    private double processarCartaoCredito(Conta conta, Fatura fatura) {
        if (conta.getData().isBefore(fatura.getData().minusDays(15))) {
            return conta.getValorPago();
        }
        return 0;
    }

    private double processarTransferencia(Conta conta, Fatura fatura) {
        if (!conta.getData().isAfter(fatura.getData())) {
            return conta.getValorPago();
        }
        return 0;
    }
}
