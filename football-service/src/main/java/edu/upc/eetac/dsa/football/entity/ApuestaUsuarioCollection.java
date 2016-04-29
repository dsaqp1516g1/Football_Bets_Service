package edu.upc.eetac.dsa.football.entity;

import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mbmarkus on 29/04/16.
 */
public class ApuestaUsuarioCollection {
    @InjectLinks({})
    private List<Link> links;
    private List<ApuestaUsuario> apuestasUsuario = new ArrayList<>();

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public List<ApuestaUsuario> getApuestasUsuario() {
        return apuestasUsuario;
    }

    public void setApuestasUsuario(List<ApuestaUsuario> apuestasUsuario) {
        this.apuestasUsuario = apuestasUsuario;
    }
}
