package src.java.com.sistemaingresso.service;

import src.java.com.sistemaingresso.repository.IngressoRepository;
import src.java.com.sistemaingresso.model.*;

public class IngressoService {

    private IngressoRepository ingressoRepository;
    private Double precoNormal;
    private Double precoVip;
    private Double precoMeia;
    private final double PORCENTOVIP = 0.3;
    private final double PORCENTOMEIA = 0.1;
    private final Double PROPORCAOVIP = 2.0;
    private final Double PROPORCAOMEIA = 0.5;

    public IngressoService(Integer qtdTotal, Double precoIngressoNormal) {
        precoNormal = precoIngressoNormal;
        precoVip = precoIngressoNormal*PROPORCAOVIP;
        precoMeia  = precoIngressoNormal*PROPORCAOMEIA;
        this.ingressoRepository = new IngressoRepository(qtdTotal,
                (int) Math.floor(qtdTotal*PORCENTOVIP),
                (int) Math.floor(qtdTotal*PORCENTOMEIA),
                precoIngressoNormal,
                precoVip,
                precoNormal
        );
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


    public Double getPrecoMeia() {
        return precoMeia;
    }

    public void marcarVendido(Long id) {
        ingressoRepository.setVendidoIngresso(id, true);
    }

    public Ingresso getIngresso(Long id) {
        return ingressoRepository.getIngresso(id);
    }

    public IngressoRepository getIngressoRepository(){
        return ingressoRepository;
    }
}
