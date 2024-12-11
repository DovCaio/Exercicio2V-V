package src.java.com.sistemaingresso.exceptions;

public class QuantidadeIncorretaIngressos extends RuntimeException {
    public QuantidadeIncorretaIngressos() {
        super("Quantidade de ingressos incorretas, provávelmente número negativo");
    }
}
