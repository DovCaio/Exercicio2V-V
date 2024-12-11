package src.java.com.sistemaingresso.service;

import src.java.com.sistemaingresso.IngressosInsuficientes;
import src.java.com.sistemaingresso.repository.IngressoRepository;
import src.java.com.sistemaingresso.repository.LoteRepository;
import src.java.com.sistemaingresso.model.*;

import java.util.HashMap;

public class LoteService {

    private LoteRepository loteRepository;
    private IngressoRepository ingressoRepository;

    public LoteService(IngressoRepository ingressoRepository) {
        loteRepository = new LoteRepository();
        this.ingressoRepository = ingressoRepository;
    }

    // A cada 5 normais vendidos, temos 10% de desconto, e acada 5 vips vendidos, 15%, porém podemos ter no máximo
    // 25% de desconto
    private Double calculaDesconto (Integer qtdNormal, Integer qtdVip){
        Double calculo = (double) ((qtdNormal / 5) * 10) + ((qtdVip / 5) * 15);
        Double desconto = 0D;
        if (calculo < 25D ) desconto = calculo;
        else desconto = 25D;

        return desconto;
    }

    private HashMap<Long, Ingresso> ingressosNaoVendidos(Integer qtdNormal, Integer qtdVip){
        HashMap<Long, Ingresso> ingressosNaoVendidos = ingressoRepository.getIngressoVipNaoVendido(qtdVip);
        if(ingressosNaoVendidos.size() < qtdVip) throw new IngressosInsuficientes();
        HashMap<Long, Ingresso> ingressosNormais = ingressoRepository.getIngressoNormalNaoVendido(qtdNormal);
        if(ingressosNormais.size() < qtdNormal) throw new IngressosInsuficientes();
        ingressosNaoVendidos.putAll(ingressosNormais);
        return ingressosNaoVendidos;
    }


    public Lote getLote(Integer qtdNormal, Integer qtdVip){
       Double desconto = calculaDesconto(qtdNormal, qtdVip);
       HashMap<Long, Ingresso> ingressos = ingressosNaoVendidos(qtdNormal, qtdVip);
       return loteRepository.addLote(ingressos, desconto);
    }

}
