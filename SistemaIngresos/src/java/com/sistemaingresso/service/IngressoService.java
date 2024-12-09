package src.java.com.sistemaingresso.service;

import src.java.com.sistemaingresso.repository.IngressoRepository;

public class IngressoService {

    private IngressoRepository ingressoRepository;

    public IngressoService(Integer qtdTotal) {
        this.ingressoRepository = new IngressoRepository(qtdTotal);
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
}
