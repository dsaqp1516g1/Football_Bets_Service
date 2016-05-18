package edu.upc.eetac.dsa.football.DAO;

/**
 * Created by mbmarkus on 18/05/16.
 */
public interface CampeonatoDAOQuery {
    public final static String CREATE_EQUIPO = "insert into campeonato (equipoid, nombreequipo, ranking, puntos, golesfavor, golescontra, diferencia) values (UNHEX(?), ?, ?, ?, ?, ?, ?);";
    public final static String GET_EQUIPOBYID = "select hex(equipoid) as equipoid, nombreequipo, ranking, puntos, golesfavor, golescontra, diferencia from campeonato where equipoid=unhex(?)";
    public final static String GET_EQUIPOS = "select hex(equipoid) as equipoid, nombreequipo, ranking, puntos, golesfavor, golescontra, diferencia from campeonato ORDER BY ranking ASC";
    public final static String UPDATE_EQUIPO = "update campeonato set nombreequipo=?, ranking=?, puntos=?, golesfavor=?, golescontra=?, diferencia=? where equipoid=unhex(?)";
    public final static String DELETE_EQUIPO = "delete from campeonato where equipoid=unhex(?)";
    public final static String GET_NUMERO_EQUIPOS = "select count(*) as numero from campeonato";
    public final static String UPDATE_RANK = "update campeonato set ranking=? where equipoid=unhex(?)";
    public final static String GET_RANKS = "select hex(equipoid) as equipoid, puntos from campeonato ORDER BY puntos ASC";
}
