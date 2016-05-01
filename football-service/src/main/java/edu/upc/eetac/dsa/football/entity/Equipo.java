package edu.upc.eetac.dsa.football.entity;

import edu.upc.eetac.dsa.football.EquipoResource;
import edu.upc.eetac.dsa.football.FootballMediaType;
import edu.upc.eetac.dsa.football.FootballRootAPIResource;
import edu.upc.eetac.dsa.football.UserResource;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.List;

/**
 * Created by toni on 28/04/16.
 */
public class Equipo {
    @InjectLinks({
            @InjectLink(resource = FootballRootAPIResource.class, style = InjectLink.Style.ABSOLUTE,
                    rel = "self bookmark home", title = "Football Root API"),
            @InjectLink(resource = EquipoResource.class, style = InjectLink.Style.ABSOLUTE,
                    rel = "creacion-equipo", title = "Creación", type= FootballMediaType.football_EQUIPO)
    })
    private List<Link> links;
    private String id;
    private String nombre;
    private String nomenclatura;
    private int valor;

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNomenclatura() {
        return nomenclatura;
    }

    public void setNomenclatura(String nomenclatura) {
        this.nomenclatura = nomenclatura;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}
