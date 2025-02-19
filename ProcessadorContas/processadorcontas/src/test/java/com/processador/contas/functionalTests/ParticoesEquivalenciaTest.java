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

public class ParticoesEquivalenciaTest {

    @Test
    public void deveMarcarFaturaComoPagaQuandoBoletoPagoNoPrazo() {
        Fatura fatura = new Fatura("Cliente A", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(
            new Conta("001", LocalDate.of(2023, 2, 19), 1500.00, TipoPagamento.BOLETO) // No prazo
        );

        ProcessadorContas processador = new ProcessadorContas();
        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PAGA, fatura.getStatus());
    }

    @Test
    public void deveMarcarFaturaComoPendenteQuandoBoletoPagoAposDataDaFatura() {
        Fatura fatura = new Fatura("Cliente B", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(
            new Conta("001", LocalDate.of(2023, 2, 21), 1500.00, TipoPagamento.BOLETO) // Atrasado
        );

        ProcessadorContas processador = new ProcessadorContas();
        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PENDENTE, fatura.getStatus());
    }

    @Test
    public void deveAceitarPagamentoPorCartaoDeCreditoSeDentroDoPrazo() {
        Fatura fatura = new Fatura("Cliente C", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(
            new Conta("001", LocalDate.of(2023, 2, 1), 1500.00, TipoPagamento.CARTAO_CREDITO) // Dentro do prazo (>= 15 dias antes)
        );

        ProcessadorContas processador = new ProcessadorContas();
        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PAGA, fatura.getStatus());
    }

    @Test
    public void deveIgnorarPagamentoPorCartaoDeCreditoForaDoPrazo() {
        Fatura fatura = new Fatura("Cliente D", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(
            new Conta("001", LocalDate.of(2023, 2, 10), 1500.00, TipoPagamento.CARTAO_CREDITO) // Fora do prazo (< 15 dias antes)
        );

        ProcessadorContas processador = new ProcessadorContas();
        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PENDENTE, fatura.getStatus());
    }

    @Test
    public void deveMarcarFaturaComoPendenteSeSomaDosPagamentosForInferior() {
        Fatura fatura = new Fatura("Cliente E", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(
            new Conta("001", LocalDate.of(2023, 2, 17), 1499.00, TipoPagamento.TRANSFERENCIA_BANCARIA) // Menos que o necessário
        );

        ProcessadorContas processador = new ProcessadorContas();
        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PENDENTE, fatura.getStatus());
    }

    @Test
    public void deveMarcarFaturaComoPagaSeSomaDosPagamentosForMaiorQueFatura() {
        Fatura fatura = new Fatura("Cliente F", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(
            new Conta("001", LocalDate.of(2023, 2, 17), 1501.00, TipoPagamento.TRANSFERENCIA_BANCARIA) // Maior que o necessário
        );

        ProcessadorContas processador = new ProcessadorContas();
        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PAGA, fatura.getStatus());
    }
}
