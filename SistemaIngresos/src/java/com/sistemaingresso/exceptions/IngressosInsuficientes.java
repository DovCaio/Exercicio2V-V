package src.java.com.sistemaingresso.exceptions;

public class IngressosInsuficientes extends RuntimeException {
    public IngressosInsuficientes() {
        super("Não temos ingressos suficientes.");
    }
}
