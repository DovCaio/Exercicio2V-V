package src.java.com.sistemaingresso.model;

import src.java.com.sistemaingresso.enums.TIPOINGRESSO;

public class Ingresso {

    private Long id;
    private TIPOINGRESSO tipo;
    private Boolean vendido;
    private Double preco;

    public Ingresso(Long id, TIPOINGRESSO tipo){
        this.id = id;
        this.tipo = tipo;
        this.vendido = false;
    }

    public Ingresso(Long id, TIPOINGRESSO tipo, Double preco){
        this.id = id;
        this.tipo = tipo;
        this.vendido = false;
        this.preco = preco;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TIPOINGRESSO getTipo() {
        return tipo;
    }

    public void setTipo(TIPOINGRESSO tipo) {
        this.tipo = tipo;
    }

    public Boolean getVendido() {
        return vendido;
    }

    public void setVendido(Boolean vendido) {
        this.vendido = vendido;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }
}
