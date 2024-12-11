package src.java.com.sistemaingresso.repository;

import java.util.HashMap;
import src.java.com.sistemaingresso.model.*;

public class LoteRepository {

    private HashMap<Long, Lote> lotes;
    private Long nextId;

    public LoteRepository() {
        nextId = 1L;
        lotes = new HashMap<Long, Lote>();
    }

    public Lote addLote(HashMap<Long, Ingresso> ingressos, Double desconto){
        Long id = nextId++;
        lotes.put(id, new Lote(id, ingressos, desconto));
        return lotes.get(id) ;
    }

}
