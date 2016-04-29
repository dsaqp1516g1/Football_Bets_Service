package edu.upc.eetac.dsa.football.entity;

import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mbmarkus on 29/04/16.
 */
public class LigaCollection {
    @InjectLinks({})
    private List<Link> links;
    private List<Liga> listado_liga = new ArrayList<>();

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public List<Liga> getListado_liga() {
        return listado_liga;
    }

    public void setListado_liga(List<Liga> listado_liga) {
        this.listado_liga = listado_liga;
    }
}
