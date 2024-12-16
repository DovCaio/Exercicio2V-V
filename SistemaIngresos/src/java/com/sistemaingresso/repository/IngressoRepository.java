package src.java.com.sistemaingresso.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

import src.java.com.sistemaingresso.enums.TIPOINGRESSO;
import src.java.com.sistemaingresso.model.*;

public class IngressoRepository {

    private HashMap<Long, Ingresso> ingressos;
    private Long currentId;
    private Integer qtdVip;
    private Integer qtdMeia;
    private Integer qtdNormal;

    //MUITA RESONSABILIDADE NO REPOSITORIO, MUDAR OS CALCULOS PARA O SERVICE.
    public IngressoRepository(Integer qttTotal,
                              Integer qtdVip,
                              Integer qtdMeia,
                              Double precoNormal,
                              Double precoVip,
                              Double precoMeia
    ) {
        currentId = 0L;
        this.ingressos = new HashMap<Long, Ingresso>();

        for (int i = 0 ; i < qtdVip ; i++){
            Long id = currentId++;
            Ingresso ingresso = new Ingresso(id, TIPOINGRESSO.VIP, precoVip);
            ingressos.put(id, ingresso);
        }

        for (int i = 0; i < qttTotal - (qtdMeia + qtdVip); i++){
            Long id = currentId++;
            Ingresso ingresso = new Ingresso(id, TIPOINGRESSO.NORMAL, precoNormal);
            ingressos.put(id, ingresso);
        }

        for (int i = 0 ; i < qtdMeia ; i++){
            Long id = currentId++;
            Ingresso ingresso = new Ingresso(id, TIPOINGRESSO.MEIAENTRADA, precoMeia);
            ingressos.put(id, ingresso);
        }

        this.qtdVip = qtdVip;
        this.qtdNormal = qttTotal - (qtdMeia + qtdVip);
        this.qtdMeia = qtdMeia;
    }


    public Integer qttTotal() {
        return this.ingressos.size();
    }

    public Integer qttVip (){
        return this.qtdVip;

    }

    public Integer qttNormal (){
        return this.qtdNormal;

    }

    public Integer qttMeia() {
        return this.qtdMeia;
    }

    public void setVendidoIngresso(Long id, Boolean estaodo) {
        ingressos.get(id).setVendido(estaodo);
    }

    public Ingresso getIngresso(Long id) {
        return ingressos.get(id);
    }
    public HashMap<Long, Ingresso> getIngressos() {
        return ingressos;
    }

    //Além de recuperar esses métodos, getIngressoVipNaoVendido e getIngressoNormalNaoVendodios, também já colocam os ingressos recuperados como vendidos.
    public HashMap<Long, Ingresso> getIngressoVipNaoVendido(Integer qtd){
        return ingressos.entrySet().stream()
                .filter(ingresso -> !ingresso.getValue().getVendido() &&
                        ingresso.getValue().getTipo().equals(TIPOINGRESSO.VIP))
                .limit(qtd)
                .peek(ingresso -> ingresso.getValue().setVendido(true))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2)-> e1, HashMap::new));
    }
    public HashMap<Long, Ingresso> getIngressoNormalNaoVendido(Integer qtd) {
        return ingressos.entrySet().stream()
                .filter(ingresso -> !ingresso.getValue().getVendido() &&
                        ingresso.getValue().getTipo().equals(TIPOINGRESSO.NORMAL))
                .limit(qtd)
                .peek(ingresso -> ingresso.getValue().setVendido(true))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2)-> e1, HashMap::new));
    }

    public HashMap<Long, Ingresso> getIngressoNormalVendido() {
        return ingressos.entrySet().stream()
                .filter(ingresso -> ingresso.getValue().getVendido() &&
                        ingresso.getValue().getTipo().equals(TIPOINGRESSO.NORMAL))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2)-> e1, HashMap::new));
    }

    public HashMap<Long, Ingresso> getIngressoMeiaVendido() {
        return ingressos.entrySet().stream()
                .filter(ingresso -> ingresso.getValue().getVendido() &&
                        ingresso.getValue().getTipo().equals(TIPOINGRESSO.MEIAENTRADA))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2)-> e1, HashMap::new));
    }

    public HashMap<Long, Ingresso> getIngressoVipVendido() {
        return ingressos.entrySet().stream()
                .filter(ingresso -> ingresso.getValue().getVendido() &&
                        ingresso.getValue().getTipo().equals(TIPOINGRESSO.VIP))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2)-> e1, HashMap::new));
    }
}
