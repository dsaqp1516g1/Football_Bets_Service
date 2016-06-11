package edu.upc.eetac.dsa.football.DAO;

/**
 * Created by toni on 9/06/16.
 */
public interface JugadoresDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_JUGADORES = "insert into jugadores (equipoid, nombre, dorsal, nacionalidad, valor, nacimiento, fincontrato) values (UNHEX(?), ?, ?, ?, ?, ?, ?)";
    public final static String GET_JUGADORESBYNOM = "select hex(equipoid) as equipoid, nombre, dorsal, nacionalidad, valor, nacimiento, fincontrato from jugadores where nombre=?";
    public final static String GET_JUGADORES_COLLECTION = "select hex(equipoid) as equipoid, nombre, dorsal, nacionalidad, valor, nacimiento, fincontrato from jugadores where equipoid=?";
    public final static String UPDATE_JUGADORES = "update jugadores set equipoid=unhex(?), dorsal=?, nacionalidad=?, valor=?, nacimiento=?, fincontrato=? where nombre=?";
    public final static String DELETE_JUGADORES = "delete from jugadores where nombre=?";
}
