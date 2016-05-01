package edu.upc.eetac.dsa.football.DAO;

/**
 * Created by mbmarkus on 1/05/16.
 */
public interface EquipoDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_EQUIPO = "insert into equipo (id, nombre, nomenclatura, valor) values (UNHEX(?), ?, ?, ?);";
    public final static String GET_EQUIPOBYID = "select hex(id) as id, nombre, nomenclatura, valor from equipo where id=unhex(?)";
    public final static String GET_EQUIPOBYNOM = "select hex(id) as id, nombre, nomenclatura, valor from equipo where nomenclatura=?";
    public final static String GET_EQUIPOS = "select hex(id) as id, nombre, nomenclatura, valor from equipo ORDER BY nomenclatura ASC";
    public final static String UPDATE_EQUIPO = "update equipo set nombre=?, nomenclatura=?, valor=? where id=unhex(?)";
    public final static String DELETE_EQUIPO = "delete from equipo where id=unhex(?)";


}
