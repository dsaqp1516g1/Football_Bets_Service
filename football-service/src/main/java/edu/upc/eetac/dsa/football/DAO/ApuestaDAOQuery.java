package edu.upc.eetac.dsa.football.DAO;

/**
 * Created by toni on 4/05/16.
 */
public interface ApuestaDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_APUESTA = "insert into apuesta (id, cuota1, cuotax, cuota2, ganadora, estado) values (UNHEX(?), ?, ?, ?, ?, ?)";
    public final static String GET_APUESTABYID = "select hex(id) as id, cuota1, cuotax, cuota2, ganadora, estado from apuesta where id=unhex(?)";
    public final static String GET_APUESTAS = "select hex(id) as id, cuota1, cuotax, cuota2, ganadora, estado from apuesta";
    public final static String UPDATE_APUESTA = "update apuesta set cuota1=?, cuotax=?,cuota2=?, ganadora=?, estado=? where id=unhex(?)";
    public final static String DELETE_APUESTA = "delete from apuesta where id=unhex(?)";
    public final static String FINAL_APUESTA = "update apuesta set ganadora=?, estado=? where id=unhex(?)";
    public final static String GET_APUESTAS_BY_APUESTAID = "select hex(id) as id, hex(usuarioid) as usuarioid, hex(apuestaid) as apuestaid, resultado, valor, resolucion, balance from apuesta_usuario where apuestaid=unhex(?)";
    //Cuotas
    public final static String UPDATE_CUOTAS = "update apuesta set cuota1=?, cuotax=?, cuota2=? where id=unhex(?)";
    public final static String SUM_CUOTA_1 = "select sum(valor) as suma1 from apuesta_usuario where apuestaid=unhex(?) and resultado ='1'";
    public final static String SUM_CUOTA_X = "select sum(valor) as sumax from apuesta_usuario where apuestaid=unhex(?) and resultado ='x'";
    public final static String SUM_CUOTA_2 = "select sum(valor) as suma2 from apuesta_usuario where apuestaid=unhex(?) and resultado ='2'";

    //ApuestasUsuarios
    public final static String CREATE_APUESTA_USUARIO = "insert into apuesta_usuario (id, usuarioid, apuestaid, resultado, valor, balance) values (UNHEX(?), UNHEX(?), UNHEX(?), ?, ?, ?)";
    public final static String GET_APUESTABYID_USUARIO = "select hex(id) as id, hex(usuarioid) as usuarioid, hex(apuestaid) as apuestaid, resultado, valor, resolucion, balance from apuesta_usuario where id=unhex(?)";
    public final static String GET_APUESTAS_USUARIO = "select hex(id) as id, hex(usuarioid) as usuarioid, hex(apuestaid) as apuestaid, resultado, valor, resolucion, balance from apuesta_usuario where usuarioid=unhex(?)";
    public final static String UPDATE_APUESTA_USUARIO = "update apuesta_usuario set usuarioid=unhex(?), apuestaid=unhex(?), resultado=?, valor=?, resolucion=?, balance=? where id=unhex(?)";
}
