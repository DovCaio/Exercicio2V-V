package src.java.com.sistemaingresso.service;

import src.java.com.sistemaingresso.repository.IngressoRepository;

public class RelatorioService {

    private IngressoRepository ingressoRepository;

    public RelatorioService(IngressoRepository ingressoRepository ){
        this.ingressoRepository = ingressoRepository;
    }

    public Integer qtdVendidosNormal(){

        return ingressoRepository.getIngressoNormalVendido().size();
    }

    public Integer qtdVendidosVip() {
        return ingressoRepository.getIngressoVipVendido().size();
    }

    public Integer qtdVendidosMeia() {
       return ingressoRepository.getIngressoMeiaVendido().size();

    }

    public Double receitaMeia(){
        return qtdVendidosMeia() * ingressoRepository.getPrecoMeia();
    }
    public Double receitaNormal(){
        return  qtdVendidosNormal() * ingressoRepository.getPrecoNormal();
    }
    public Double receitaVip(){
        return  qtdVendidosVip() * ingressoRepository.getPrecoVip();
    }
}
