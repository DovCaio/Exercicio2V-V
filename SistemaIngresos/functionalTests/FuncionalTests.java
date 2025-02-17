package functionalTests;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import src.java.com.sistemaingresso.controller.IngressoController;

public class FuncionalTests {

    final private Integer qttMaxima = 10000;
    final private Double valorMaximo = 5000D;
    final private Double gastoMaximo = 100000D;
    @Nested
    class qttIngressoVariacao{
        private IngressoController ingressoController;
        final private Double preco = 100D;
        final private Double gastos = 0D;
        @Test
        public void case1() {

            ingressoController = new IngressoController(preco, 0, gastos);

            assertEquals(ingressoController.infos(), "Estável");
        }

        @Test
        public void case2() {

            ingressoController = new IngressoController(preco, 1, gastos);

            ingressoController.marcarComoVendido(1L);

            assertEquals(ingressoController.infos(), "Estável");
        }

        @Test
        public void case3() {

            ingressoController = new IngressoController(preco, 100, gastos);

            for (long i = 1 ; i < 101; i++){
                ingressoController.marcarComoVendido(i);
            }

            assertEquals(ingressoController.infos(), "Estável");
        }

        @Test
        public void case4() {

            ingressoController = new IngressoController(preco, qttMaxima - 1 , gastos);

            for (long i = 1 ; i < qttMaxima - 1; i++){
                ingressoController.marcarComoVendido(i);
            }

            assertEquals(ingressoController.infos(), "Estável");
        }


        @Test
        public void case5() {

            ingressoController = new IngressoController(preco, qttMaxima , gastos);

            for (long i = 1 ; i < qttMaxima ; i++){
                ingressoController.marcarComoVendido(i);
            }

            assertEquals(ingressoController.infos(), "Estável");
        }

        @Test
        public void case6() {

            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> ingressoController = new IngressoController(preco, -1, gastos));
        }

    }

    @Nested
    class PrecoIngressoNormalVariation {
        private IngressoController ingressoController;
        final private Double gastos = 0D;
        final Integer qttIngresso = 100;
        @Test
        public void case7() {

            ingressoController = new IngressoController(0D, qttIngresso , gastos);

            for (long i = 1 ; i < qttIngresso ; i++){
                ingressoController.marcarComoVendido(i);
            }

            assertEquals(ingressoController.infos(), "Lucro");
        }
        @Test

        public void case8() {

            ingressoController = new IngressoController(1D, qttIngresso , gastos);

            for (long i = 1 ; i < qttIngresso ; i++){
                ingressoController.marcarComoVendido(i);
            }

            assertEquals(ingressoController.infos(), "Lucro");
        }
        @Test

        public void case9() {

            ingressoController = new IngressoController(100D, qttIngresso , gastos);

            for (long i = 1 ; i < qttIngresso ; i++){
                ingressoController.marcarComoVendido(i);
            }

            assertEquals(ingressoController.infos(), "Lucro");
        }
        @Test

        public void case10() {

            ingressoController = new IngressoController(valorMaximo - 1, qttIngresso , gastos);

            for (long i = 1 ; i < qttIngresso ; i++){
                ingressoController.marcarComoVendido(i);
            }

            assertEquals(ingressoController.infos(), "Lucro");
        }
        @Test

        public void case11() {

            ingressoController = new IngressoController(valorMaximo, qttIngresso , gastos);

            for (long i = 1 ; i < qttIngresso ; i++){
                ingressoController.marcarComoVendido(i);
            }

            assertEquals(ingressoController.infos(), "Lucro");
        }
        @Test

        public void case12() {

            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> ingressoController = new IngressoController(-1D, qttIngresso , gastos));

        }


    }

    @Nested
    class QttVendidosVariation{

        private IngressoController ingressoController;
        final private Double preco = 100D;
        final private Integer qttIngresso = 100;
        final private Double gastos = 1000D;
        @Test

        public void case13() {

            ingressoController = new IngressoController(preco, qttIngresso , gastos);

            assertEquals(ingressoController.infos(), "Prejuízo");
        }
        @Test

        public void case14() {

            ingressoController = new IngressoController(preco, qttIngresso , gastos);

            ingressoController.marcarComoVendido(1L);

            assertEquals(ingressoController.infos(), "Prejuízo");
        }
        @Test

        public void case15() {

            ingressoController = new IngressoController(preco, qttIngresso , gastos);

            for(long i = 1; i < 9 ; i++){
                ingressoController.marcarComoVendido(i);
            }

            assertEquals(ingressoController.infos(), "Prejuízo");
        }
        @Test

        public void case16() {

            ingressoController = new IngressoController(preco, qttIngresso , gastos);

            for(long i = 1; i < 99 ; i++){
                ingressoController.marcarComoVendido(i);
            }

            assertEquals(ingressoController.infos(), "Lucro");
        }
        @Test

        public void case17() {

            ingressoController = new IngressoController(preco, qttIngresso , gastos);

            for(long i = 1; i < 100 ; i++){
                ingressoController.marcarComoVendido(i);
            }

            assertEquals(ingressoController.infos(), "Lucro");
        }

        @Test

        public void case19() {

            ingressoController = new IngressoController(preco, qttIngresso , gastos);

            for(long i = 1; i < 100 ; i++){
                ingressoController.marcarComoVendido(i);
            }

            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> ingressoController.marcarComoVendido(101L));

        }


    }

    @Nested
    class GastosVariation{

        private IngressoController ingressoController;
        final private Integer qttIngressos = 100;
        final private Double preco = 100D;
        final private Integer qttVendidos = 100;

        @Test
        public void case20(){
            ingressoController = new IngressoController(preco, qttIngressos, 0D);

            for(long i = 0; i < qttVendidos; i++){
                ingressoController.marcarComoVendido(i);
            }

            assertEquals(ingressoController.infos(), "Lucro");
        }

        @Test
        public void case21(){
            ingressoController = new IngressoController(preco, qttIngressos, 1D);

            for(long i = 0; i < qttVendidos; i++){
                ingressoController.marcarComoVendido(i);
            }

            assertEquals(ingressoController.infos(), "Lucro");
        }

        @Test
        public void case22(){
            ingressoController = new IngressoController(preco, qttIngressos, 100D);

            for(long i = 0; i < qttVendidos; i++){
                ingressoController.marcarComoVendido(i);
            }

            assertEquals(ingressoController.infos(), "Estável");
        }


        @Test
        public void case23(){
            ingressoController = new IngressoController(preco, qttIngressos, gastoMaximo - 1);

            for(long i = 0; i < qttVendidos; i++){
                ingressoController.marcarComoVendido(i);
            }

            assertEquals(ingressoController.infos(), "Prejuízo");
        }


        @Test
        public void case24(){
            ingressoController = new IngressoController(preco, qttIngressos, gastoMaximo);

            for(long i = 0; i < qttVendidos; i++){
                ingressoController.marcarComoVendido(i);
            }

            assertEquals(ingressoController.infos(), "Prejuízo");
        }



        @Test
        public void case25(){
            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> ingressoController = new IngressoController(preco, qttIngressos, gastoMaximo));

        }

    }
}
