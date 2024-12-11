package src.java.com.sistemaingresso;

public class IngressosInsuficientes extends RuntimeException {
    public IngressosInsuficientes() {
        super("Não temos ingressos suficientes.");
    }
}
