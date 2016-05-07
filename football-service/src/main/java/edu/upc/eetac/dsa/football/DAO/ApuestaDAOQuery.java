package edu.upc.eetac.dsa.football.DAO;

/**
 * Created by toni on 4/05/16.
 */
public interface ApuestaDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_APUESTA = "insert into apuesta (id, partidoid, cuota1, cuotax, cuota2, ganadora, estado) values (UNHEX(?), UNHEX(?), ?, ?, ?, ?, ?);";
    public final static String GET_APUESTABYID = "select hex(id) as id, hex(partidoid) as partidoid, cuota1, cuotax, cuota2, ganadora, estado, valor from apuesta where id=unhex(?)";
    public final static String GET_APUESTAS = "select hex(id) as id, hex(partidoid) as partidoid, cuota1, cuotax, cuota2, ganadora, estado, valor from apuesta";
    public final static String UPDATE_APUESTA = "update apuesta set cuota1=?, cuota2=?, ganadora=?, estado=? where id=unhex(?)";
    public final static String DELETE_APUESTA = "delete from apuesta where id=unhex(?)";
}
