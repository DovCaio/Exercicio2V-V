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
    private final double PORCENTOVIP = 0.3;
    private final double PORCENTOMEIA = 0.1;
    private final double PROPORCAOPRECOVIP = 2.0;
    //MUITA RESONSABILIDADE NO REPOSITORIO, MUDAR OS CALCULOS PARA O SERVICE.
    public IngressoRepository(Integer qttTotal,  Double precoNormal, Double precoVip) {
        currentId = 0L;
        HashSet<Ingresso> ingressosVip = new HashSet<Ingresso>();
        HashSet<Ingresso> ingressosNormal = new HashSet<Ingresso>();
        HashSet<Ingresso> ingressosMeiaEntrada = new HashSet<Ingresso>();

        for (int i = 0 ; i <  Math.floor(qttTotal*PORCENTOVIP) ; i++){
            Ingresso ingresso = new Ingresso(currentId++, TIPOINGRESSO.VIP, precoVip);
            ingressosVip.add(ingresso);
        }

        for (int i = 0; i < Math.floor(qttTotal - (qttTotal*PORCENTOVIP + qttTotal*PORCENTOMEIA)); i++){
            Ingresso ingresso = new Ingresso(currentId++, TIPOINGRESSO.NORMAL, precoNormal);
            ingressosNormal.add(ingresso);
        }

        for (int i = 0 ; i < Math.floor(qttTotal*PORCENTOMEIA); i++){
            Ingresso ingresso = new Ingresso(currentId++, TIPOINGRESSO.MEIAENTRADA);
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
