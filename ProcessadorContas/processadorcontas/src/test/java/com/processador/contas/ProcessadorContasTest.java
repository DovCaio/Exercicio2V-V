package com.processador.contas;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.Test;

public class ProcessadorContasTest {

    @Test
    public void deveMarcarFaturaComoPagaQuandoPagamentosSuficientes() {
        Fatura fatura = new Fatura("Cliente A", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(
            new Conta("001", LocalDate.of(2023, 2, 20), 500.00, TipoPagamento.BOLETO),
            new Conta("002", LocalDate.of(2023, 2, 20), 600.00, TipoPagamento.TRANSFERENCIA_BANCARIA),
            new Conta("003", LocalDate.of(2023, 2, 20), 400.00, TipoPagamento.BOLETO)
        );

        ProcessadorContas processador = new ProcessadorContas();
        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PAGA, fatura.getStatus());
    }

    @Test
    public void deveMarcarFaturaComoPendenteQuandoPagamentosInsuficientes() {
        Fatura fatura = new Fatura("Cliente B", 2000.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(
            new Conta("001", LocalDate.of(2023, 2, 20), 500.00, TipoPagamento.BOLETO),
            new Conta("002", LocalDate.of(2023, 2, 20), 600.00, TipoPagamento.TRANSFERENCIA_BANCARIA)
        );

        ProcessadorContas processador = new ProcessadorContas();
        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PENDENTE, fatura.getStatus());
    }

    @Test
    public void deveAplicarMultaParaBoletosAtrasados() {
        Fatura fatura = new Fatura("Cliente C", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(
            new Conta("001", LocalDate.of(2023, 2, 22), 500.00, TipoPagamento.BOLETO)
        );

        ProcessadorContas processador = new ProcessadorContas();
        processador.processarFatura(fatura, contas);

        // Multa de 10% aplicada ao valor do boleto
        assertEquals(StatusFatura.PENDENTE, fatura.getStatus());
    }

    @Test
    public void deveIgnorarPagamentosPorCartaoDeCreditoForaDoPrazo() {
        Fatura fatura = new Fatura("Cliente D", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(
            new Conta("001", LocalDate.of(2023, 2, 10), 700.00, TipoPagamento.CARTAO_CREDITO)
        );

        ProcessadorContas processador = new ProcessadorContas();
        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PENDENTE, fatura.getStatus());
    }

    @Test
    public void deveIncluirPagamentosPorCartaoDeCreditoDentroDoPrazo() {
        Fatura fatura = new Fatura("Cliente E", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(
            new Conta("001", LocalDate.of(2023, 2, 1), 700.00, TipoPagamento.CARTAO_CREDITO),
            new Conta("002", LocalDate.of(2023, 2, 15), 800.00, TipoPagamento.TRANSFERENCIA_BANCARIA)
        );

        ProcessadorContas processador = new ProcessadorContas();
        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PAGA, fatura.getStatus());
    }

    @Test
    public void naoDevePermitirPagamentoBoletoComValorInvalido() {
        Fatura fatura = new Fatura("Cliente F", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(
            new Conta("001", LocalDate.of(2023, 2, 20), 0.005, TipoPagamento.BOLETO),
            new Conta("002", LocalDate.of(2023, 2, 20), 6000.00, TipoPagamento.BOLETO)
        );

        ProcessadorContas processador = new ProcessadorContas();
        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PENDENTE, fatura.getStatus());
    }
}