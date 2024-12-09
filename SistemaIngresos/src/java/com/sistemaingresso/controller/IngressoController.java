package src.java.com.sistemaingresso.controller;

import src.java.com.sistemaingresso.service.IngressoService;

public class IngressoController {

    private IngressoService ingressoService;

    public IngressoController(){

        this.ingressoService = new IngressoService(100);

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

}
