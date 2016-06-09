package edu.upc.eetac.dsa.football.DAO;

/**
 * Created by toni on 12/05/16.
 */
public interface PartidoDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_PARTIDO = "insert into partido (id, local, visitante, jornada, fecha, goleslocal, golesvisitante, estado) values (UNHEX(?), UNHEX(?), UNHEX(?), ?, ?, ?, ?, ?);";
    public final static String GET_PARTIDOBYID = "select hex(id) as id, hex(local) as local, hex(visitante) as visitante, jornada, fecha, goleslocal, golesvisitante, estado from partido where id=unhex(?)";
    public final static String UPDATE_PARTIDO = "update partido set local=unhex(?), visitante=unhex(?), jornada=?, fecha=?, goleslocal=?, golesvisitante=?, estado=? where id=unhex(?)";
    public final static String GET_PARTIDOS = "select hex(id) as id, hex(local) as local, hex(visitante) as visitante, jornada, fecha, goleslocal, golesvisitante, estado from partido";
    public final static String DELETE_PARTIDO = "delete from partido where id=unhex(?)";

}
