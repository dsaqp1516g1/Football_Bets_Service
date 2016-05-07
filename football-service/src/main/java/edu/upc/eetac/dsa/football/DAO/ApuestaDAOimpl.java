package edu.upc.eetac.dsa.football.DAO;

import edu.upc.eetac.dsa.football.Database.Database;
import edu.upc.eetac.dsa.football.entity.Apuesta;
import edu.upc.eetac.dsa.football.entity.ApuestaCollection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by toni on 4/05/16.
 */
public class ApuestaDAOimpl implements ApuestaDAO{
    @Override
    public Apuesta createApuesta(String partidoid, float cuota1, float cuotax, float cuota2, String ganadora, String estado) throws SQLException, ApuestaAlreadyExistsException {
        Connection connection = null;
        PreparedStatement stmt = null;
        String id = null;
        try {
            Apuesta apuesta = getApuestaById(id);
            if (apuesta != null)
                throw new ApuestaAlreadyExistsException();

            connection = Database.getConnection();

            stmt = connection.prepareStatement(EquipoDAOQuery.UUID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                id = rs.getString(1);
            else
                throw new SQLException();

            connection.setAutoCommit(false);

            stmt.close();
            stmt = connection.prepareStatement(ApuestaDAOQuery.CREATE_APUESTA);
            stmt.setString(1, id);
            stmt.setString(2, partidoid);
            stmt.setFloat(3, cuota1);
            stmt.setFloat(4, cuotax);
            stmt.setFloat(5, cuota2);
            stmt.setString(6, ganadora);
            stmt.setString(7, estado);
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
        return getApuestaById(id);
    }


    @Override
    public Apuesta updateApuesta(Apuesta apuesta) throws SQLException {
        Apuesta apuestan = null;
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(ApuestaDAOQuery.UPDATE_APUESTA);
            stmt.setFloat(1, apuesta.getCuota1());
            stmt.setFloat(2, apuesta.getCuotax());
            stmt.setFloat(3, apuesta.getCuota2());
            stmt.setString(4, apuesta.getGanadora());
            stmt.setString(5, apuesta.getEstado());

            int rows = stmt.executeUpdate();
            if (rows == 1){
                apuestan = new Apuesta();
                apuestan = getApuestaById(apuesta.getId());
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return apuestan;
    }

    @Override
    public Apuesta getApuestaById(String id) throws SQLException {
        // Modelo a devolver
        Apuesta apuesta = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            // Obtiene la conexión del DataSource
            connection = Database.getConnection();

            // Prepara la consulta
            stmt = connection.prepareStatement(ApuestaDAOQuery.GET_APUESTABYID);
            // Da valor a los parámetros de la consulta
            stmt.setString(1, id);

            // Ejecuta la consulta
            ResultSet rs = stmt.executeQuery();
            // Procesa los resultados
            if (rs.next()) {
                apuesta.setId(rs.getString("id"));
                apuesta.setPartidoid(rs.getString("partidoid"));
                apuesta.setCuota1(rs.getFloat("cuota1"));
                apuesta.setCuotax(rs.getFloat("cuotax"));
                apuesta.setCuota2(rs.getFloat("cuota2"));
                apuesta.setGanadora(rs.getString("ganadora"));
                apuesta.setEstado(rs.getString("estado"));
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
        return apuesta;
    }

    @Override
    public ApuestaCollection getApuestas() throws SQLException {
        ApuestaCollection apuestaCollection = new ApuestaCollection();
        // Modelo a devolver
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            // Obtiene la conexión del DataSource
            connection = Database.getConnection();

            // Prepara la consulta
            stmt = connection.prepareStatement(ApuestaDAOQuery.GET_APUESTAS);
            // Da valor a los parámetros de la consulta

            // Ejecuta la consulta
            ResultSet rs = stmt.executeQuery();
            // Procesa los resultados
            while (rs.next()) {
                Apuesta apuesta = new Apuesta();
                apuesta.setId(rs.getString("id"));
                apuesta.setPartidoid(rs.getString("partidoid"));
                apuesta.setCuota1(rs.getFloat("cuota1"));
                apuesta.setCuotax(rs.getFloat("cuotax"));
                apuesta.setCuota2(rs.getFloat("cuota2"));
                apuesta.setGanadora(rs.getString("ganadora"));
                apuesta.setEstado(rs.getString("estado"));

                apuestaCollection.getApuestas().add(apuesta);
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
        return apuestaCollection;
    }

    @Override
    public boolean deleteApuesta(String id) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(ApuestaDAOQuery.DELETE_APUESTA);
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
