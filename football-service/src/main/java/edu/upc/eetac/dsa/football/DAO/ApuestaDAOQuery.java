package edu.upc.eetac.dsa.football.DAO;

/**
 * Created by toni on 4/05/16.
 */
public interface ApuestaDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_APUESTA = "insert into apuesta (id, cuota1, cuotax, cuota2, ganadora, estado) values (UNHEX(?), ?, ?, ?, ?, ?);";
    public final static String GET_APUESTABYID = "select hex(id) as id, cuota1, cuotax, cuota2, ganadora, estado from apuesta where id=unhex(?)";
    public final static String GET_APUESTAS = "select hex(id) as id, cuota1, cuotax, cuota2, ganadora, estado from apuesta";
    public final static String UPDATE_APUESTA = "update apuesta set cuota1=?, cuotax=?,cuota2=?, ganadora=?, estado=? where id=unhex(?)";
    public final static String DELETE_APUESTA = "delete from apuesta where id=unhex(?)";
    public final static String UPDATE_CUOTAS = "update apuesta set cuota1=?, cuotax=?,cuota2=? where id=unhex(?)";
    public final static String SUM_CUOTA_1 = "select sum(cuota1) as sumacuota1 from apuesta_usuario where apuestaid=unhex(?)";
    public final static String SUM_CUOTA_X = "select sum(cuotax) as sumacuotax from apuesta_usuario where apuestaid=unhex(?)";
    public final static String SUM_CUOTA_2 = "select sum(cuota2) as sumacuota2 from apuesta_usuario where apuestaid=unhex(?)";
}
