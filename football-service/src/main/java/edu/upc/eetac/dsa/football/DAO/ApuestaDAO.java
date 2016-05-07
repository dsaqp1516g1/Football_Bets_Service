package edu.upc.eetac.dsa.football.DAO;

import edu.upc.eetac.dsa.football.entity.Apuesta;
import edu.upc.eetac.dsa.football.entity.ApuestaCollection;

import java.sql.SQLException;

/**
 * Created by toni on 4/05/16.
 */
public interface ApuestaDAO {
    public Apuesta createApuesta(String partidoid, float cuota1, float cuotax, float cuota2, String ganadora, String estado) throws SQLException, ApuestaAlreadyExistsException;
    public Apuesta updateApuesta(Apuesta apuesta) throws SQLException;
    public Apuesta getApuestaById(String id) throws SQLException;
    public ApuestaCollection getApuestas() throws SQLException;
    public boolean deleteApuesta(String id) throws SQLException;

}
