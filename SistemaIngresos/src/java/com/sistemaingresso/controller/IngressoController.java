package src.java.com.sistemaingresso.controller;

import src.java.com.sistemaingresso.service.IngressoService;
import src.java.com.sistemaingresso.model.*;
import src.java.com.sistemaingresso.service.LoteService;
import src.java.com.sistemaingresso.service.RelatorioService;

public class IngressoController {

    private final IngressoService ingressoService;
    private final LoteService loteService;
    private final RelatorioService relatorioService;

    public IngressoController(Double precoIngressoNormal, Integer qtdIngresso, Double gastos){

        this.ingressoService = new IngressoService(qtdIngresso, precoIngressoNormal);
        this.loteService = new LoteService(ingressoService.getIngressoRepository());
        this.relatorioService = new RelatorioService(ingressoService.getIngressoRepository());
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


    public Integer qtdVendidosNormal(){
        return relatorioService.qtdVendidosNormal();
    }

    public Integer qtdVendidosVip() {
        return relatorioService.qtdVendidosVip();
    }

    public Integer qtdVendidosMeia() {
        return relatorioService.qtdVendidosMeia();
    }

    public Double getReceitaMeia(){
        return relatorioService.receitaMeia();
    }


    public Double getReceitaVip(){
        return relatorioService.receitaVip();
    }
    public Double getReceitaNormal(){
        return relatorioService.receitaNormal();
    }

    public String infos(){
        return "";
    }

}
