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

public class AnaliseValoresLimiteTest {

    @Test
    public void deveMarcarFaturaComoPendenteQuandoNaoHaPagamentos() {
        Fatura fatura = new Fatura("Cliente A", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(); // Sem pagamentos

        ProcessadorContas processador = new ProcessadorContas();
        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PENDENTE, fatura.getStatus());
    }

    @Test
    public void deveMarcarFaturaComoPendenteQuandoPagamentoInsuficiente() {
        Fatura fatura = new Fatura("Cliente B", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(
            new Conta("001", LocalDate.of(2023, 2, 20), 1499.99, TipoPagamento.TRANSFERENCIA_BANCARIA)
        );

        ProcessadorContas processador = new ProcessadorContas();
        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PENDENTE, fatura.getStatus());
    }

    @Test
    public void deveMarcarFaturaComoPagaQuandoPagamentoExatamenteIgual() {
        Fatura fatura = new Fatura("Cliente C", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(
            new Conta("001", LocalDate.of(2023, 2, 5), 1500.00, TipoPagamento.CARTAO_CREDITO)
        );

        ProcessadorContas processador = new ProcessadorContas();
        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PAGA, fatura.getStatus());
    }

    @Test
    public void deveMarcarFaturaComoPagaQuandoPagamentoMaiorQueFatura() {
        Fatura fatura = new Fatura("Cliente D", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(
            new Conta("001", LocalDate.of(2023, 2, 20), 5000.00, TipoPagamento.BOLETO)
        );

        ProcessadorContas processador = new ProcessadorContas();
        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PAGA, fatura.getStatus());
    }

    @Test
    public void deveRejeitarBoletoComValorAcimaDoPermitido() {
        Fatura fatura = new Fatura("Cliente E", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(
            new Conta("001", LocalDate.of(2023, 2, 20), 5000.01, TipoPagamento.BOLETO)
        );

        ProcessadorContas processador = new ProcessadorContas();
        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PENDENTE, fatura.getStatus());  // Pagamento não pode ser aceito
    }

    @Test
    public void deveMarcarFaturaComoPendenteQuandoCartaoForaDoPrazo() {
        Fatura fatura = new Fatura("Cliente F", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(
            new Conta("001", LocalDate.of(2023, 2, 10), 1400.00, TipoPagamento.CARTAO_CREDITO)
        );

        ProcessadorContas processador = new ProcessadorContas();
        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PENDENTE, fatura.getStatus());
    }

    @Test
    public void deveMarcarFaturaComoPagaQuandoBoletoPagoAntesDoVencimento() {
        Fatura fatura = new Fatura("Cliente G", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(
            new Conta("001", LocalDate.of(2023, 2, 19), 1600.00, TipoPagamento.BOLETO)
        );

        ProcessadorContas processador = new ProcessadorContas();
        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PAGA, fatura.getStatus());
    }

    @Test
    public void deveMarcarFaturaComoPendenteQuandoPagamentoMenorQueFatura() {
        Fatura fatura = new Fatura("Cliente H", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(
            new Conta("001", LocalDate.of(2023, 2, 19), 1499.99, TipoPagamento.BOLETO)
        );

        ProcessadorContas processador = new ProcessadorContas();
        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PENDENTE, fatura.getStatus());
    }
}
