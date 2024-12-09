package src.java.com.sistemaingresso.repository;

import java.util.ArrayList;
import java.util.HashSet;

import src.java.com.sistemaingresso.enums.TIPOINGRESSO;
import src.java.com.sistemaingresso.model.*;

public class IngressoRepository {

    private HashSet<Ingresso> ingressoVip;
    private HashSet<Ingresso> ingressoNormal;
    private HashSet<Ingresso> ingressoMeiaEntrada;
    private Long currentId;

    //MUITA RESONSABILIDADE NO REPOSITORIO, MUDAR OS CALCULOS PARA O SERVICE.
    public IngressoRepository(Integer qttTotal, Integer qtdVip, Integer qtdMeia,
                              Double precoNormal,
                              Double precoVip,
                              Double precoMeia) {
        currentId = 0L;
        HashSet<Ingresso> ingressosVip = new HashSet<Ingresso>();
        HashSet<Ingresso> ingressosNormal = new HashSet<Ingresso>();
        HashSet<Ingresso> ingressosMeiaEntrada = new HashSet<Ingresso>();

        for (int i = 0 ; i < qtdVip ; i++){
            Ingresso ingresso = new Ingresso(currentId++, TIPOINGRESSO.VIP, precoVip);
            ingressosVip.add(ingresso);
        }

        for (int i = 0; i < qttTotal - (qtdMeia + qtdVip); i++){
            Ingresso ingresso = new Ingresso(currentId++, TIPOINGRESSO.NORMAL, precoNormal);
            ingressosNormal.add(ingresso);
        }

        for (int i = 0 ; i < qtdMeia ; i++){
            Ingresso ingresso = new Ingresso(currentId++, TIPOINGRESSO.MEIAENTRADA, precoMeia);
            ingressosMeiaEntrada.add(ingresso);
        }

        this.ingressoVip = ingressosVip;
        this.ingressoNormal = ingressosNormal;
        this.ingressoMeiaEntrada = ingressosMeiaEntrada;

    }

    public HashSet<Ingresso> getIngressoVip() {
        return ingressoVip;
    }

    public HashSet<Ingresso> getIngressoNormal() {
        return ingressoNormal;
    }

    public HashSet<Ingresso> getIngressoMeiaEntrada() {
        return ingressoMeiaEntrada;
    }

    public Integer qttTotal() {
        return this.ingressoVip.size() + this.ingressoNormal.size() + this.ingressoMeiaEntrada.size();
    }

    public Integer qttVip (){
        return this.ingressoVip.size();

    }

    public Integer qttNormal (){
        return this.ingressoNormal.size();

    }

    public Integer qttMeia() {
        return this.ingressoMeiaEntrada.size();
    }


}
