package com.processador.contas.functionalTests;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import com.processador.contas.ProcessadorContas;
import com.processador.contas.enums.StatusFatura;
import com.processador.contas.enums.TipoPagamento;
import com.processador.contas.models.Conta;
import com.processador.contas.models.Fatura;

public class TabelasDecisaoTest {

    @Test
    public void deveMarcarFaturaComoPagaQuandoBoletoValidoEValorSuficiente() {
        Fatura fatura = new Fatura("Cliente A", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(
            new Conta("001", LocalDate.of(2023, 2, 20), 1500.00, TipoPagamento.BOLETO) // Dentro do prazo e valor suficiente
        );

        ProcessadorContas processador = new ProcessadorContas();
        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PAGA, fatura.getStatus());
    }

    @Test
    public void deveMarcarFaturaComoPendenteQuandoBoletoComDataInvalida() {
        Fatura fatura = new Fatura("Cliente B", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(
            new Conta("001", LocalDate.of(2023, 2, 21), 1500.00, TipoPagamento.BOLETO) // Data posterior à fatura
        );

        ProcessadorContas processador = new ProcessadorContas();
        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PENDENTE, fatura.getStatus());
    }

    @Test
    public void deveMarcarFaturaComoPendenteQuandoBoletoValidoMasValorInsuficiente() {
        Fatura fatura = new Fatura("Cliente C", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(
            new Conta("001", LocalDate.of(2023, 2, 20), 1400.00, TipoPagamento.BOLETO) // Valor menor que a fatura
        );

        ProcessadorContas processador = new ProcessadorContas();
        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PENDENTE, fatura.getStatus());
    }

    @Test
    public void deveMarcarFaturaComoPagaQuandoCartaoCreditoDentroDoPrazo() {
        Fatura fatura = new Fatura("Cliente D", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(
            new Conta("001", LocalDate.of(2023, 2, 1), 1500.00, TipoPagamento.CARTAO_CREDITO) // 19 dias antes da fatura (aceito)
        );

        ProcessadorContas processador = new ProcessadorContas();
        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PAGA, fatura.getStatus());
    }

    @Test
    public void deveMarcarFaturaComoPendenteQuandoCartaoCreditoForaDoPrazo() {
        Fatura fatura = new Fatura("Cliente E", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(
            new Conta("001", LocalDate.of(2023, 2, 10), 1500.00, TipoPagamento.CARTAO_CREDITO) // Apenas 10 dias antes da fatura (não aceito)
        );

        ProcessadorContas processador = new ProcessadorContas();
        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PENDENTE, fatura.getStatus());
    }

    @Test
    public void deveMarcarFaturaComoPendenteQuandoCartaoCreditoComValorInsuficiente() {
        Fatura fatura = new Fatura("Cliente F", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(
            new Conta("001", LocalDate.of(2023, 2, 1), 1400.00, TipoPagamento.CARTAO_CREDITO) // Dentro do prazo mas valor insuficiente
        );

        ProcessadorContas processador = new ProcessadorContas();
        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PENDENTE, fatura.getStatus());
    }

    @Test
    public void deveMarcarFaturaComoPagaQuandoTransferenciaBancariaDentroDoPrazo() {
        Fatura fatura = new Fatura("Cliente G", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(
            new Conta("001", LocalDate.of(2023, 2, 17), 1500.00, TipoPagamento.TRANSFERENCIA_BANCARIA)
        );

        ProcessadorContas processador = new ProcessadorContas();
        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PAGA, fatura.getStatus());
    }

    @Test
    public void deveMarcarFaturaComoPendenteQuandoTransferenciaBancariaComDataInvalida() {
        Fatura fatura = new Fatura("Cliente H", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(
            new Conta("001", LocalDate.of(2023, 2, 22), 1500.00, TipoPagamento.TRANSFERENCIA_BANCARIA) // Data posterior à fatura
        );

        ProcessadorContas processador = new ProcessadorContas();
        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PENDENTE, fatura.getStatus());
    }

    @Test
    public void deveMarcarFaturaComoPendenteQuandoTransferenciaBancariaComValorInsuficiente() {
        Fatura fatura = new Fatura("Cliente I", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(
            new Conta("001", LocalDate.of(2023, 2, 17), 1400.00, TipoPagamento.TRANSFERENCIA_BANCARIA) // Valor menor que a fatura
        );

        ProcessadorContas processador = new ProcessadorContas();
        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PENDENTE, fatura.getStatus());
    }
}
