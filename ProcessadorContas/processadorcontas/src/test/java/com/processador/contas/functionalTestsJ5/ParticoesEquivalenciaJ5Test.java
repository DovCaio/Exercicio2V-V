package com.processador.contas.functionalTestsJ5;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.processador.contas.ProcessadorContas;
import com.processador.contas.enums.StatusFatura;
import com.processador.contas.enums.TipoPagamento;
import com.processador.contas.models.Conta;
import com.processador.contas.models.Fatura;

@TestMethodOrder(OrderAnnotation.class)
public class ParticoesEquivalenciaJ5Test {

    private ProcessadorContas processador;

    @BeforeEach
    void setUp() {
        processador = new ProcessadorContas();
    }

    @Test
    @Order(1)
    @DisplayName("✅ Deve marcar a fatura como PAGA quando boleto for pago no prazo")
    public void deveMarcarFaturaComoPagaQuandoBoletoPagoNoPrazo() {
        Fatura fatura = new Fatura("Cliente A", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(
            new Conta("001", LocalDate.of(2023, 2, 19), 1500.00, TipoPagamento.BOLETO) // No prazo
        );

        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PAGA, fatura.getStatus());
    }

    @Test
    @Order(2)
    @DisplayName("⚠ Deve marcar a fatura como PENDENTE quando boleto for pago após a data da fatura")
    public void deveMarcarFaturaComoPendenteQuandoBoletoPagoAposDataDaFatura() {
        Fatura fatura = new Fatura("Cliente B", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(
            new Conta("001", LocalDate.of(2023, 2, 21), 1500.00, TipoPagamento.BOLETO) // Atrasado
        );

        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PENDENTE, fatura.getStatus());
    }

    @Test
    @Order(3)
    @DisplayName("✅ Deve aceitar pagamento por cartão de crédito se dentro do prazo")
    public void deveAceitarPagamentoPorCartaoDeCreditoSeDentroDoPrazo() {
        Fatura fatura = new Fatura("Cliente C", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(
            new Conta("001", LocalDate.of(2023, 2, 1), 1500.00, TipoPagamento.CARTAO_CREDITO) // Dentro do prazo (>= 15 dias antes)
        );

        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PAGA, fatura.getStatus());
    }

    @Test
    @Order(4)
    @DisplayName("⚠ Deve ignorar pagamento por cartão de crédito fora do prazo")
    public void deveIgnorarPagamentoPorCartaoDeCreditoForaDoPrazo() {
        Fatura fatura = new Fatura("Cliente D", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(
            new Conta("001", LocalDate.of(2023, 2, 10), 1500.00, TipoPagamento.CARTAO_CREDITO) // Fora do prazo (< 15 dias antes)
        );

        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PENDENTE, fatura.getStatus());
    }

    @Test
    @Order(5)
    @DisplayName("⚠ Deve marcar fatura como PENDENTE se soma dos pagamentos for inferior ao valor da fatura")
    public void deveMarcarFaturaComoPendenteSeSomaDosPagamentosForInferior() {
        Fatura fatura = new Fatura("Cliente E", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(
            new Conta("001", LocalDate.of(2023, 2, 17), 1499.00, TipoPagamento.TRANSFERENCIA_BANCARIA) // Menos que o necessário
        );

        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PENDENTE, fatura.getStatus());
    }

    @Test
    @Order(6)
    @DisplayName("✅ Deve marcar fatura como PAGA se soma dos pagamentos for maior que o valor da fatura")
    public void deveMarcarFaturaComoPagaSeSomaDosPagamentosForMaiorQueFatura() {
        Fatura fatura = new Fatura("Cliente F", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(
            new Conta("001", LocalDate.of(2023, 2, 17), 1501.00, TipoPagamento.TRANSFERENCIA_BANCARIA) // Maior que o necessário
        );

        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PAGA, fatura.getStatus());
    }
}
