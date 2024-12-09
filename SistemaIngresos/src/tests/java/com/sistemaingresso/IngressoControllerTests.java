package src.tests.java.com.sistemaingresso;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.java.com.sistemaingresso.controller.IngressoController;


public class IngressoControllerTests {

    IngressoController ingressoController;

    @BeforeEach
    public void setUp(){
        this.ingressoController = new IngressoController();

    }


    @Test
    public void testQttIngressoVip(){

        Double quantidadeTotal = (double) ingressoController.quantidadeTotal();
        Double quantidadeVip = (double) ingressoController.quantidadeVip();
        Double porcentagemVip = (quantidadeVip / quantidadeTotal) * 100 ;
        assertEquals(porcentagemVip, 30, 10);

    }

    @Test
    public void testQttIngressoNormal(){

        Double quantidadeTotal = (double) ingressoController.quantidadeTotal();
        Double quantidadeNormal = (double) ingressoController.quantidadeNormal();
        Double porcentagemNormal = (quantidadeNormal / quantidadeTotal) * 100 ;
        assertEquals(porcentagemNormal, 60, 10);


    }

}
