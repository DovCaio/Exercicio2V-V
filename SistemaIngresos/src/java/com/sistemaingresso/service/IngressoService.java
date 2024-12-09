package src.java.com.sistemaingresso.service;

import src.java.com.sistemaingresso.repository.IngressoRepository;

public class IngressoService {

    private IngressoRepository ingressoRepository;
    private Double precoNormal;
    private Double precoVip;
    private final Double PROPORCAOVIP = 2.0;

    public IngressoService(Integer qtdTotal, Double precoIngressoNormal) {
        precoNormal = precoIngressoNormal;
        precoVip = precoIngressoNormal*PROPORCAOVIP;
        this.ingressoRepository = new IngressoRepository(qtdTotal,
                precoIngressoNormal,
                precoVip);
    }


    public Integer qttTotal (){

        return ingressoRepository.qttTotal();

    }

    public Integer qttVip () {

        return ingressoRepository.qttVip();

    }

    public Integer qttNormal () {

        return ingressoRepository.qttNormal();

    }

    public Integer qttMeia(){
        return ingressoRepository.qttMeia();
    }

    public Double getPrecoNormal() {
        return precoNormal;
    }


    public Double getPrecoVip() {
        return precoVip;
    }


}
