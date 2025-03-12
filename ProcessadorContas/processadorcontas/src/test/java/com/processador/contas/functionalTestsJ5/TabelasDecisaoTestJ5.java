package com.processador.contas.functionalTestsJ5;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;

import com.processador.contas.ProcessadorContas;
import com.processador.contas.enums.StatusFatura;
import com.processador.contas.enums.TipoPagamento;
import com.processador.contas.models.Conta;
import com.processador.contas.models.Fatura;

@TestMethodOrder(OrderAnnotation.class)
public class TabelasDecisaoTestJ5 {

    private ProcessadorContas processador;

    @BeforeEach
    void setUp() {
        processador = new ProcessadorContas();
    }

    @Test
    @Order(1)
    @DisplayName("Deve marcar a fatura como PAGA quando boleto válido e valor suficiente")
    public void deveMarcarFaturaComoPagaQuandoBoletoValidoEValorSuficiente() {
        Fatura fatura = new Fatura("Cliente A", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(
            new Conta("001", LocalDate.of(2023, 2, 20), 1500.00, TipoPagamento.BOLETO)
        );

        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PAGA, fatura.getStatus());
    }

    @Test
    @Order(2)
    @DisplayName("Deve marcar a fatura como PENDENTE quando boleto com data inválida")
    public void deveMarcarFaturaComoPendenteQuandoBoletoComDataInvalida() {
        Fatura fatura = new Fatura("Cliente B", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(
            new Conta("001", LocalDate.of(2023, 2, 21), 1500.00, TipoPagamento.BOLETO)
        );

        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PENDENTE, fatura.getStatus());
    }

    @Test
    @Order(3)
    @DisplayName("Deve marcar a fatura como PENDENTE quando boleto válido mas valor insuficiente")
    public void deveMarcarFaturaComoPendenteQuandoBoletoValidoMasValorInsuficiente() {
        Fatura fatura = new Fatura("Cliente C", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(
            new Conta("001", LocalDate.of(2023, 2, 20), 1400.00, TipoPagamento.BOLETO)
        );

        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PENDENTE, fatura.getStatus());
    }

    @Test
    @Order(4)
    @DisplayName("Deve marcar a fatura como PAGA quando pagamento com cartão de crédito dentro do prazo")
    public void deveMarcarFaturaComoPagaQuandoCartaoCreditoDentroDoPrazo() {
        Fatura fatura = new Fatura("Cliente D", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(
            new Conta("001", LocalDate.of(2023, 2, 1), 1500.00, TipoPagamento.CARTAO_CREDITO)
        );

        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PAGA, fatura.getStatus());
    }

    @Test
    @Order(5)
    @DisplayName("Deve marcar a fatura como PENDENTE quando pagamento com cartão de crédito fora do prazo")
    public void deveMarcarFaturaComoPendenteQuandoCartaoCreditoForaDoPrazo() {
        Fatura fatura = new Fatura("Cliente E", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(
            new Conta("001", LocalDate.of(2023, 2, 10), 1500.00, TipoPagamento.CARTAO_CREDITO)
        );

        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PENDENTE, fatura.getStatus());
    }

    @Test
    @Order(6)
    @DisplayName("Deve marcar a fatura como PENDENTE quando pagamento com cartão de crédito tem valor insuficiente")
    public void deveMarcarFaturaComoPendenteQuandoCartaoCreditoComValorInsuficiente() {
        Fatura fatura = new Fatura("Cliente F", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(
            new Conta("001", LocalDate.of(2023, 2, 1), 1400.00, TipoPagamento.CARTAO_CREDITO)
        );

        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PENDENTE, fatura.getStatus());
    }

    @Test
    @Order(7)
    @DisplayName("Deve marcar a fatura como PAGA quando pagamento por transferência bancária dentro do prazo")
    public void deveMarcarFaturaComoPagaQuandoTransferenciaBancariaDentroDoPrazo() {
        Fatura fatura = new Fatura("Cliente G", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(
            new Conta("001", LocalDate.of(2023, 2, 17), 1500.00, TipoPagamento.TRANSFERENCIA_BANCARIA)
        );

        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PAGA, fatura.getStatus());
    }

    @Test
    @Order(8)
    @DisplayName("Deve marcar a fatura como PENDENTE quando pagamento por transferência bancária tem data inválida")
    public void deveMarcarFaturaComoPendenteQuandoTransferenciaBancariaComDataInvalida() {
        Fatura fatura = new Fatura("Cliente H", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(
            new Conta("001", LocalDate.of(2023, 2, 22), 1500.00, TipoPagamento.TRANSFERENCIA_BANCARIA)
        );

        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PENDENTE, fatura.getStatus());
    }

    @Test
    @Order(9)
    @DisplayName("Deve marcar a fatura como PENDENTE quando pagamento por transferência bancária tem valor insuficiente")
    public void deveMarcarFaturaComoPendenteQuandoTransferenciaBancariaComValorInsuficiente() {
        Fatura fatura = new Fatura("Cliente I", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(
            new Conta("001", LocalDate.of(2023, 2, 17), 1400.00, TipoPagamento.TRANSFERENCIA_BANCARIA)
        );

        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PENDENTE, fatura.getStatus());
    }
}
