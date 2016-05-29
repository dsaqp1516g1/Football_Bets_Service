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
public class ApuestaDAOImpl implements ApuestaDAO{
    @Override
    public Apuesta createApuesta(String id, float cuota1, float cuotax, float cuota2, String ganadora, String estado) throws SQLException, ApuestaAlreadyExistsException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            Apuesta apuesta = getApuestaById(id);
            if (apuesta != null)
                throw new ApuestaAlreadyExistsException();

            connection = Database.getConnection();

            stmt = connection.prepareStatement(ApuestaDAOQuery.CREATE_APUESTA);
            stmt.setString(1, id);
            stmt.setFloat(2, cuota1);
            stmt.setFloat(3, cuotax);
            stmt.setFloat(4, cuota2);
            stmt.setString(5, ganadora);
            stmt.setString(6, estado);
            stmt.executeUpdate();

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
            stmt.setString(6, apuesta.getId());

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
                apuesta = new Apuesta();
                apuesta.setId(rs.getString("id"));
                apuesta.setCuota1(rs.getFloat("cuota1"));
                apuesta.setCuotax(rs.getFloat("cuotax"));
                apuesta.setCuota2(rs.getFloat("cuota2"));
                apuesta.setGanadora(rs.getString("ganadora"));
                apuesta.setEstado(rs.getString("estado"));
            }
        } catch (SQLException e) {
            // Relanza la excepción
            //throw e;
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

    @Override
    public Apuesta actuCuotas(String id, float cuota1, float cuotax, float cuota2) throws SQLException
    {
        Apuesta apuestan = null;
        Connection connection = null;
        PreparedStatement stmt = null;
        float c1=0;
        float cx=0;
        float c2=0;
        float suma=0;

        try {
            connection = Database.getConnection();

            //Obtenemos las sumas de las cuotas del partido

            stmt = connection.prepareStatement(ApuestaDAOQuery.SUM_CUOTA_1);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                c1 = rs.getFloat("cuota1");
                c1 = c1 + cuota1;
            }
            else
                throw new SQLException();

            stmt = connection.prepareStatement(ApuestaDAOQuery.SUM_CUOTA_X);
            stmt.setString(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                cx = rs.getFloat("cuotax");
                cx = cx + cuotax;
            }
            else
                throw new SQLException();

            stmt = connection.prepareStatement(ApuestaDAOQuery.SUM_CUOTA_2);
            stmt.setString(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                c2 = rs.getFloat("cuota2");
                c2 = c2 + cuota2;
            }
            else
                throw new SQLException();

            //Suma total del bote
            suma = c1 + c2 + cx;

            //Aplico directamente los & para cada apuesta
            stmt = connection.prepareStatement(ApuestaDAOQuery.UPDATE_CUOTAS);
            stmt.setFloat(1, (suma/c1));
            stmt.setFloat(2, (suma/cx));
            stmt.setFloat(3, (suma/c2));
            stmt.setString(4, id);

            int rows = stmt.executeUpdate();
            if (rows == 1){
                apuestan = new Apuesta();
                apuestan = getApuestaById(id);
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return apuestan;
    }
}
