package src.tests.java.com.sistemaingresso;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class IngressoControllerTests {

    @BeforeEach
    public void setUp(){
        IngressoController ingressoController = new IngressoController();

    }


    @Test
    public void testQttEngressoVip(){

        Integer quantidadeTotal = ingressoController.quantidadeTotal();
        Integer quantidadeVip = ingressoController.quantidadeVip();

        Double porcentagemVip = (double) (quantidadeVip / quantidadeTotal) * 100;

        assertEquals(porcentagemVip, (double) 30);

    }

}
