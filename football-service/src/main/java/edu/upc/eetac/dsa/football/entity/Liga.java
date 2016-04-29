package edu.upc.eetac.dsa.football.entity;

import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.List;

/**
 * Created by toni on 28/04/16.
 */
public class Liga {
    @InjectLinks({})
    private List<Link> links;
    private String id;
    private String equipoid;
    private String nombreequipo;
    private int ranking;
    private int puntos;
    private int golesfavor;
    private int golescontra;
    private int diferencia;

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

    public String getEquipoid() {
        return equipoid;
    }

    public void setEquipoid(String equipoid) {
        this.equipoid = equipoid;
    }

    public String getNombreequipo() {
        return nombreequipo;
    }

    public void setNombreequipo(String nombreequipo) {
        this.nombreequipo = nombreequipo;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getGolesfavor() {
        return golesfavor;
    }

    public void setGolesfavor(int golesfavor) {
        this.golesfavor = golesfavor;
    }

    public int getDiferencia() {
        return diferencia;
    }

    public void setDiferencia(int diferencia) {
        this.diferencia = diferencia;
    }

    public int getGolescontra() {
        return golescontra;
    }

    public void setGolescontra(int golescontra) {
        this.golescontra = golescontra;
    }
}
