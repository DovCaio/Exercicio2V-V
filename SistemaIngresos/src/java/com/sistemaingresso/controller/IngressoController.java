package src.java.com.sistemaingresso.controller;

import src.java.com.sistemaingresso.service.IngressoService;

public class IngressoController {

    private IngressoService ingressoService;

    public IngressoController(Double precoIngressoNormal){

        this.ingressoService = new IngressoService(100, precoIngressoNormal);

    }

    public Integer quantidadeTotal() {
        return ingressoService.qttTotal();

    }

    public Integer quantidadeVip (){
        return ingressoService.qttVip();
    }

    public Integer quantidadeNormal(){
        return ingressoService.qttNormal();
    }

    public Integer quantidadeMeia(){
        return ingressoService.qttMeia();
    }

    public Double precoVip(){
        return ingressoService.getPrecoVip();
    }

    public Double precoNormal(){
        return ingressoService.getPrecoNormal();
    }

    public Double precoMeia(){
        return ingressoService.getPrecoMeia();
    }

}
