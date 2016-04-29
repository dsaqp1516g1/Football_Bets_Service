package edu.upc.eetac.dsa.football.entity;

import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.List;

/**
 * Created by toni on 28/04/16.
 */
public class apuesta {
    @InjectLinks({})
    private List<Link> links;
    private String id;
    private String partidoid;
    private float cuota1;
    private float cuotax;
    private float cuota2;
    private String ganadora;
    private String estado;

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

    public float getCuota1() {
        return cuota1;
    }

    public void setCuota1(float cuota1) {
        this.cuota1 = cuota1;
    }

    public String getPartidoid() {
        return partidoid;
    }

    public void setPartidoid(String partidoid) {
        this.partidoid = partidoid;
    }

    public float getCuotax() {
        return cuotax;
    }

    public void setCuotax(float cuotax) {
        this.cuotax = cuotax;
    }

    public float getCuota2() {
        return cuota2;
    }

    public void setCuota2(float cuota2) {
        this.cuota2 = cuota2;
    }

    public String getGanadora() {
        return ganadora;
    }

    public void setGanadora(String ganadora) {
        this.ganadora = ganadora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
