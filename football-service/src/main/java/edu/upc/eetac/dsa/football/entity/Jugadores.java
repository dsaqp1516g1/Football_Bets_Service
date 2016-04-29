package edu.upc.eetac.dsa.football.entity;

import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.List;

/**
 * Created by toni on 28/04/16.
 */
public class Jugadores {
    @InjectLinks({})
    private List<Link> links;
    private String equipoid;
    private String nombre;
    private int dorsal;
    private String nacionalidad;
    private String valor;
    private String nacimiento;
    private String fincontrato;

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public String getEquipoid() {
        return equipoid;
    }

    public void setEquipoid(String equipoid) {
        this.equipoid = equipoid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDorsal() {
        return dorsal;
    }

    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(String nacimiento) {
        this.nacimiento = nacimiento;
    }

    public String getFincontrato() {
        return fincontrato;
    }

    public void setFincontrato(String fincontrato) {
        this.fincontrato = fincontrato;
    }
}
