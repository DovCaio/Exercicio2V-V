package src.tests.java.com.sistemaingresso;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.java.com.sistemaingresso.controller.IngressoController;


public class IngressoControllerTests {

    private IngressoController ingressoController;
    private final Double preco = 200.0;

    @BeforeEach
    public void setUp() {
        this.ingressoController = new IngressoController(preco);

    }


    @Test
    public void testQttIngressoVip() {

        Double quantidadeTotal = (double) ingressoController.quantidadeTotal();
        Double quantidadeVip = (double) ingressoController.quantidadeVip();
        Double porcentagemVip = (quantidadeVip / quantidadeTotal) * 100;
        assertEquals(porcentagemVip, 30, 10);

    }

    @Test
    public void testQttIngressoNormal() {

        Double quantidadeTotal = (double) ingressoController.quantidadeTotal();
        Double quantidadeNormal = (double) ingressoController.quantidadeNormal();
        Double porcentagemNormal = (quantidadeNormal / quantidadeTotal) * 100;
        assertEquals(porcentagemNormal, 60, 10);


    }

    @Test
    public void testQttIngressoMeiaEntada() {


        Double quantidadeTotal = (double) ingressoController.quantidadeTotal();
        Double quantidadeMeia = (double) ingressoController.quantidadeMeia();
        Double porcentagemMeia = (quantidadeMeia / quantidadeTotal) * 100;
        assertEquals(porcentagemMeia, 10, 5);
    }

    @Test
    public void testPrecoIngressoVip(){

        Double precoIngressoVip = ingressoController.precoVip();
        Double precoIngressoNormal = ingressoController.precoNormal();
        Double proporcaoIngressoVip =  precoIngressoVip / precoIngressoNormal;
        assertEquals(proporcaoIngressoVip, 2);
    }

    @Test
    public void testPrecoIngressoMeia(){

        Double precoIngressoMeia = ingressoController.precoMeia();
        Double precoIngressoNormal = ingressoController.precoNormal();
        Double proporcaoIngressoMeia =  precoIngressoMeia / precoIngressoNormal;
        assertEquals(proporcaoIngressoMeia, 0.5);
    }
}