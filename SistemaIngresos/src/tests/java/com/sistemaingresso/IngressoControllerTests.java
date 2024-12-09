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
    public void testQttEngressoVip(){

        Integer quantidadeTotal = ingressoController.quantidadeTotal();
        Integer quantidadeVip = ingressoController.quantidadeVip();

        Double porcentagemVip = (double) (quantidadeVip / quantidadeTotal) * 100;

        assertEquals(porcentagemVip, (double) 30);

    }

}
