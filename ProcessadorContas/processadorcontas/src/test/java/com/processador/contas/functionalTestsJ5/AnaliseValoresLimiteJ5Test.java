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
public class AnaliseValoresLimiteJ5Test {

    private ProcessadorContas processador;

    @BeforeEach
    void setUp() {
        processador = new ProcessadorContas();
    }

    @Test
    @Order(1)
    @DisplayName("⚠ Deve marcar fatura como PENDENTE quando não há pagamentos")
    public void deveMarcarFaturaComoPendenteQuandoNaoHaPagamentos() {
        Fatura fatura = new Fatura("Cliente A", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(); // Sem pagamentos

        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PENDENTE, fatura.getStatus());
    }

    @Test
    @Order(2)
    @DisplayName("⚠ Deve marcar fatura como PENDENTE quando pagamento for insuficiente")
    public void deveMarcarFaturaComoPendenteQuandoPagamentoInsuficiente() {
        Fatura fatura = new Fatura("Cliente B", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(
            new Conta("001", LocalDate.of(2023, 2, 20), 1499.99, TipoPagamento.TRANSFERENCIA_BANCARIA)
        );

        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PENDENTE, fatura.getStatus());
    }

    @Test
    @Order(3)
    @DisplayName("✅ Deve marcar fatura como PAGA quando pagamento for exatamente igual")
    public void deveMarcarFaturaComoPagaQuandoPagamentoExatamenteIgual() {
        Fatura fatura = new Fatura("Cliente C", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(
            new Conta("001", LocalDate.of(2023, 2, 5), 1500.00, TipoPagamento.CARTAO_CREDITO)
        );

        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PAGA, fatura.getStatus());
    }

    @Test
    @Order(4)
    @DisplayName("✅ Deve marcar fatura como PAGA quando pagamento for maior que a fatura")
    public void deveMarcarFaturaComoPagaQuandoPagamentoMaiorQueFatura() {
        Fatura fatura = new Fatura("Cliente D", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(
            new Conta("001", LocalDate.of(2023, 2, 20), 5000.00, TipoPagamento.BOLETO)
        );

        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PAGA, fatura.getStatus());
    }

    @Test
    @Order(5)
    @DisplayName("⚠ Deve rejeitar boleto com valor acima do permitido")
    public void deveRejeitarBoletoComValorAcimaDoPermitido() {
        Fatura fatura = new Fatura("Cliente E", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(
            new Conta("001", LocalDate.of(2023, 2, 20), 5000.01, TipoPagamento.BOLETO)
        );

        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PENDENTE, fatura.getStatus());  // Pagamento não pode ser aceito
    }

    @Test
    @Order(6)
    @DisplayName("⚠ Deve marcar fatura como PENDENTE quando pagamento por cartão estiver fora do prazo")
    public void deveMarcarFaturaComoPendenteQuandoCartaoForaDoPrazo() {
        Fatura fatura = new Fatura("Cliente F", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(
            new Conta("001", LocalDate.of(2023, 2, 10), 1400.00, TipoPagamento.CARTAO_CREDITO)
        );

        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PENDENTE, fatura.getStatus());
    }

    @Test
    @Order(7)
    @DisplayName("✅ Deve marcar fatura como PAGA quando boleto for pago antes do vencimento")
    public void deveMarcarFaturaComoPagaQuandoBoletoPagoAntesDoVencimento() {
        Fatura fatura = new Fatura("Cliente G", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(
            new Conta("001", LocalDate.of(2023, 2, 19), 1600.00, TipoPagamento.BOLETO)
        );

        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PAGA, fatura.getStatus());
    }

    @Test
    @Order(8)
    @DisplayName("⚠ Deve marcar fatura como PENDENTE quando pagamento for menor que a fatura")
    public void deveMarcarFaturaComoPendenteQuandoPagamentoMenorQueFatura() {
        Fatura fatura = new Fatura("Cliente H", 1500.00, LocalDate.of(2023, 2, 20));
        List<Conta> contas = List.of(
            new Conta("001", LocalDate.of(2023, 2, 19), 1499.99, TipoPagamento.BOLETO)
        );

        processador.processarFatura(fatura, contas);

        assertEquals(StatusFatura.PENDENTE, fatura.getStatus());
    }
}
