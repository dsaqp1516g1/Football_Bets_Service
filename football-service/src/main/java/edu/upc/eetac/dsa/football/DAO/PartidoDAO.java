package edu.upc.eetac.dsa.football.DAO;

import edu.upc.eetac.dsa.football.entity.Partido;

import java.sql.SQLException;

/**
 * Created by toni on 12/05/16.
 */
public interface PartidoDAO {
    public Partido createPartido(String local, String visitante, int jornada,String fecha, int goleslocal, int golesvisitante, String estado) throws SQLException, PartidoAlreadyExistsException;

    public Partido updatePartido(Partido partido) throws SQLException;

    public Partido getPartidoById(String id) throws SQLException;

    public boolean deletePartido(String id) throws SQLException;


}
