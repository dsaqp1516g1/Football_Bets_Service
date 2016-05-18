package edu.upc.eetac.dsa.football.entity;

import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mbmarkus on 29/04/16.
 */
public class CampeonatoCollection {
    @InjectLinks({})
    private List<Link> links;
    private List<Campeonato> listado_campeonato = new ArrayList<>();

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public List<Campeonato> getListado_campeonato() {
        return listado_campeonato;
    }

    public void setListado_campeonato(List<Campeonato> listado_campeonato) {
        this.listado_campeonato = listado_campeonato;
    }
}
