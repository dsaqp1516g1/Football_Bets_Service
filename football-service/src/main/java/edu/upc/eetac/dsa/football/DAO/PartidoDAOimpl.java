package edu.upc.eetac.dsa.football.DAO;

import edu.upc.eetac.dsa.football.Database.Database;
import edu.upc.eetac.dsa.football.entity.Partido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by toni on 12/05/16.
 */
public class PartidoDAOimpl implements PartidoDAO{
    @Override
    public Partido createPartido(String local, String visitante, int jornada, String fecha, int goleslocal, int golesvisitante, String estado) throws SQLException, PartidoAlreadyExistsException {
        Connection connection = null;
        PreparedStatement stmt = null;
        String id = null;
        try {

            connection = Database.getConnection();

            stmt = connection.prepareStatement(PartidoDAOQuery.UUID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                id = rs.getString(1);
            else
                throw new SQLException();

            connection.setAutoCommit(false);

            stmt.close();
            stmt = connection.prepareStatement(PartidoDAOQuery.CREATE_PARTIDO);
            stmt.setString(1, id);
            stmt.setString(2, local);
            stmt.setString(3, visitante);
            stmt.setInt(4, jornada);
            stmt.setString(5, fecha);
            stmt.setInt(6, goleslocal);
            stmt.setInt(7, golesvisitante);
            stmt.setString(8, estado);
            stmt.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
        return getPartidoById(id);
    }

    @Override
    public Partido updatePartido(Partido partido) throws SQLException {
        Partido partidon = null;
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(PartidoDAOQuery.UPDATE_PARTIDO);
            stmt.setString(1, partido.getLocal());
            stmt.setString(2, partido.getVisitante());
            stmt.setInt(3, partido.getJornada());
            stmt.setString(4, partido.getFecha());
            stmt.setInt(5, partido.getGoleslocal());
            stmt.setInt(6, partido.getGolesvisitante());
            stmt.setString(7, partido.getEstado());
            stmt.setString(8, partido.getId());

            int rows = stmt.executeUpdate();
            if (rows == 1){
                partidon = getPartidoById(partido.getId());
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return partidon;
    }


    @Override
    public Partido getPartidoById(String id) throws SQLException {
        // Modelo a devolver
        Partido partido = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            // Obtiene la conexión del DataSource
            connection = Database.getConnection();

            // Prepara la consulta
            stmt = connection.prepareStatement(PartidoDAOQuery.GET_PARTIDOBYID);
            // Da valor a los parámetros de la consulta
            stmt.setString(1, id);

            // Ejecuta la consulta
            ResultSet rs = stmt.executeQuery();
            // Procesa los resultados
            if (rs.next()) {
                partido = new Partido();
                partido.setId(rs.getString("id"));
                partido.setLocal(rs.getString("local"));
                partido.setVisitante(rs.getString("visitante"));
                partido.setJornada(rs.getInt("jornada"));
                partido.setFecha(rs.getString("fecha"));
                partido.setGoleslocal(rs.getInt("goleslocal"));
                partido.setGolesvisitante(rs.getInt("golesvisitante"));
                partido.setEstado(rs.getString("estado"));
            }
        } catch (SQLException e) {
            // Relanza la excepción
            throw e;
        } finally {
            // Libera la conexión
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        // Devuelve el modelo
        return partido;
    }


    @Override
    public boolean deletePartido(String id) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(PartidoDAOQuery.DELETE_PARTIDO);
            stmt.setString(1, id);

            int rows = stmt.executeUpdate();
            return (rows == 1);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
    }
}
