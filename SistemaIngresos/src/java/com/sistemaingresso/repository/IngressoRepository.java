package src.java.com.sistemaingresso.repository;

import java.util.ArrayList;
import java.util.HashSet;

import src.java.com.sistemaingresso.enums.TIPOINGRESSO;
import src.java.com.sistemaingresso.model.*;

public class IngressoRepository {

    private ArrayList<Ingresso> ingressoVip;
    private ArrayList<Ingresso> ingressoNormal;
    private ArrayList<Ingresso> ingressoMeiaEntrada;
    private Long currentId;
    private final float PORCENTOVIP = 0.3F;
    private final float PORCENTOMEIA = 0.1F;

    public IngressoRepository(Integer qttTotal) {
        currentId = 0L;
        ArrayList<Ingresso> ingressosVip = new ArrayList<Ingresso>();
        ArrayList<Ingresso> ingressosNormal = new ArrayList<Ingresso>();
        ArrayList<Ingresso> ingressosMeiaEntrada = new ArrayList<Ingresso>();

        for (int i = 0 ; i <  qttTotal*PORCENTOVIP ; i++){
            Ingresso ingresso = new Ingresso(currentId++, TIPOINGRESSO.VIP);
            ingressosVip.add(ingresso);
        }

        for (int i = 0; i < qttTotal - (qttTotal*PORCENTOVIP + qttTotal*PORCENTOMEIA); i++){
            Ingresso ingresso = new Ingresso(currentId++, TIPOINGRESSO.NORMAL);
            ingressosNormal.add(ingresso);
        }

        for (int i = 0 ; i < qttTotal*PORCENTOMEIA; i++){
            Ingresso ingresso = new Ingresso(currentId++, TIPOINGRESSO.MEIAENTRADA);
            ingressosMeiaEntrada.add(ingresso);
        }

        this.ingressoVip = ingressosVip;
        this.ingressoNormal = ingressosNormal;
        this.ingressoMeiaEntrada = ingressosMeiaEntrada;

    }

    public ArrayList<Ingresso> getIngressoVip() {
        return ingressoVip;
    }

    public ArrayList<Ingresso> getIngressoNormal() {
        return ingressoNormal;
    }

    public ArrayList<Ingresso> getIngressoMeiaEntrada() {
        return ingressoMeiaEntrada;
    }

    public Integer qttTotal() {
        return this.ingressoVip.size() + this.ingressoNormal.size() + this.ingressoMeiaEntrada.size();
    }

}
