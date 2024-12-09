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
        assertEquals(30, porcentagemVip, 10);

    }

    @Test
    public void testQttIngressoNormal() {

        Double quantidadeTotal = (double) ingressoController.quantidadeTotal();
        Double quantidadeNormal = (double) ingressoController.quantidadeNormal();
        Double porcentagemNormal = (quantidadeNormal / quantidadeTotal) * 100;
        assertEquals(60, porcentagemNormal, 10);


    }

    @Test
    public void testQttIngressoMeiaEntada() {


        Double quantidadeTotal = (double) ingressoController.quantidadeTotal();
        Double quantidadeMeia = (double) ingressoController.quantidadeMeia();
        Double porcentagemMeia = (quantidadeMeia / quantidadeTotal) * 100;
        assertEquals(10, porcentagemMeia, 5);
    }

    @Test
    public void testPrecoIngressoVip(){

        Double precoIngressoVip = ingressoController.precoVip();
        Double precoIngressoNormal = ingressoController.precoNormal();
        Double proporcaoIngressoVip =  precoIngressoVip / precoIngressoNormal;
        assertEquals(2, proporcaoIngressoVip);
    }

    @Test
    public void testPrecoIngressoMeia(){

        Double precoIngressoMeia = ingressoController.precoMeia();
        Double precoIngressoNormal = ingressoController.precoNormal();
        Double proporcaoIngressoMeia =  precoIngressoMeia / precoIngressoNormal;
        assertEquals(0.5, proporcaoIngressoMeia);
    }

    @Test
    public void testMarcarComoVendido(){
        ingressoController.marcarVendido(10);
        assertEquals(true, ingressoController.getIngresso(10));
    }
}