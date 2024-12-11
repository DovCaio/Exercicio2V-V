package src.tests.java.com.sistemaingresso;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.java.com.sistemaingresso.IngressosInsuficientes;
import src.java.com.sistemaingresso.controller.IngressoController;
import src.java.com.sistemaingresso.model.Lote;


public class IngressoControllerTests {

    private IngressoController ingressoController;
    private final Double preco = 200.0;

    @BeforeEach
    public void setUp() {
        this.ingressoController = new IngressoController(preco, 200);

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
        ingressoController.marcarComoVendido(10L);
        assertEquals(true, ingressoController.getIngresso(10L).getVendido());
    }

    @Test
    public void testMarcarComoVendidoNegativoException(){

        assertThrows(NumberFormatException.class, () -> {
            ingressoController.marcarComoVendido(-20L);
        });
        assertThrows(NumberFormatException.class, () -> {
            ingressoController.getIngresso(-20L);
        });

    }

    @Test
    public void testLoteDesconto(){
        Lote lote1 = ingressoController.getLote(10, 20);
        Lote lote2 = ingressoController.getLote(5, 0);
        Lote lote3 = ingressoController.getLote(10, 0);
        Lote lote4 = ingressoController.getLote(0, 5);
        Lote lote5 = ingressoController.getLote(0, 10);

        assertEquals( 25 ,lote1.getDesconto());
        assertEquals( 10 ,lote2.getDesconto());
        assertEquals( 20 ,lote3.getDesconto());
        assertEquals( 15 ,lote4.getDesconto());
        assertEquals( 25 ,lote5.getDesconto());

    }

    @Test
    public void testLoteNaoSuficiente(){

        assertThrows(IngressosInsuficientes.class, () -> {
            Lote lote1 = ingressoController.getLote(200, 0);
        });

        assertThrows(IngressosInsuficientes.class, () -> {
            Lote lote1 = ingressoController.getLote(25, 0);
            Lote lote2 = ingressoController.getLote(25, 0);
            Lote lote3 = ingressoController.getLote(25, 0);
        });


        assertThrows(IngressosInsuficientes.class, () -> {
            Lote lote1 = ingressoController.getLote(0, 30);
            Lote lote2 = ingressoController.getLote(0, 30);
            Lote lote3 = ingressoController.getLote(0, 30);
        });

    }
}