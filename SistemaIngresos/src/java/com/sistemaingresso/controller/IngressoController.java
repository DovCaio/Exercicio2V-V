package src.java.com.sistemaingresso.controller;

import src.java.com.sistemaingresso.service.IngressoService;
import src.java.com.sistemaingresso.model.*;
import src.java.com.sistemaingresso.service.LoteService;

public class IngressoController {

    private IngressoService ingressoService;
    private LoteService loteService;

    public IngressoController(Double precoIngressoNormal){

        this.ingressoService = new IngressoService(100, precoIngressoNormal);
        this.loteService = new LoteService(ingressoService.getIngressoRepository());

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

    public void marcarComoVendido(Long id) {
        if (id < 0) throw new NumberFormatException();
        ingressoService.marcarVendido(id);
    }

    public Ingresso getIngresso(Long id) {
        if (id < 0) throw new NumberFormatException();
        return ingressoService.getIngresso(id);
    }

    public Lote getLote(Integer qtdNormal, Integer qtdVip){
        return loteService.getLote(qtdNormal, qtdVip);
    }


}
