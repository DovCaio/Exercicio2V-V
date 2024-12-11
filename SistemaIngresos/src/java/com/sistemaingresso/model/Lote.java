package src.java.com.sistemaingresso.model;

import java.util.HashMap;

public class Lote {

    private Long id;
    private HashMap<Long, Ingresso> ingressos;
    private Double desconto;

    public Lote(Long id, HashMap<Long, Ingresso> ingressos, Double desconto) {
        this.id = id;
        this.ingressos = ingressos;
        this.desconto = desconto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HashMap<Long, Ingresso> getIngressos() {
        return ingressos;
    }

    public Double getDesconto() {
        return desconto;
    }

}
