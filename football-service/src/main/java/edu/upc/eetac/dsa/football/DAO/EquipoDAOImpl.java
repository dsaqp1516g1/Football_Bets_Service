package edu.upc.eetac.dsa.football.DAO;

import edu.upc.eetac.dsa.football.Database.Database;
import edu.upc.eetac.dsa.football.entity.Equipo;
import edu.upc.eetac.dsa.football.entity.EquipoCollection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by mbmarkus on 1/05/16.
 */
public class EquipoDAOImpl implements EquipoDAO {
    @Override
    public Equipo createEquipo(String nombre, String nomnenclatura, int valor) throws SQLException, EquipoAlreadyExistsException
    {
        Connection connection = null;
        PreparedStatement stmt = null;
        String id = null;
        try {
            Equipo equipo = getEquipobyNomen(nomnenclatura);
            if (equipo != null)
                throw new EquipoAlreadyExistsException();

            connection = Database.getConnection();

            stmt = connection.prepareStatement(EquipoDAOQuery.UUID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                id = rs.getString(1);
            else
                throw new SQLException();

            connection.setAutoCommit(false);

            stmt.close();
            stmt = connection.prepareStatement(EquipoDAOQuery.CREATE_EQUIPO);
            stmt.setString(1, id);
            stmt.setString(2, nombre);
            stmt.setString(3, nomnenclatura);
            stmt.setInt(4, valor);
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
        return getEquipobyID(id);
    }
    @Override
    public Equipo getEquipobyID(String id) throws SQLException
    {
        // Modelo a devolver
        Equipo equipo = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            // Obtiene la conexión del DataSource
            connection = Database.getConnection();

            // Prepara la consulta
            stmt = connection.prepareStatement(EquipoDAOQuery.GET_EQUIPOBYID);
            // Da valor a los parámetros de la consulta
            stmt.setString(1, id);

            // Ejecuta la consulta
            ResultSet rs = stmt.executeQuery();
            // Procesa los resultados
            if (rs.next()) {
                equipo = new Equipo();
                equipo.setId(rs.getString("id"));
                equipo.setNombre(rs.getString("nombre"));
                equipo.setNomenclatura(rs.getString("nomenclatura"));
                equipo.setValor(rs.getInt("valor"));
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
        return equipo;
    }
    @Override
    public Equipo getEquipobyNomen(String nomenclatura) throws SQLException
    {
        Equipo equipo = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(EquipoDAOQuery.GET_EQUIPOBYNOM);
            stmt.setString(1, nomenclatura);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                equipo = new Equipo();
                equipo = getEquipobyID(rs.getString("id"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return equipo;
    }
    @Override
    public EquipoCollection getEquipos() throws SQLException
    {
        EquipoCollection equipoCollection = new EquipoCollection();
        // Modelo a devolver
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            // Obtiene la conexión del DataSource
            connection = Database.getConnection();

            // Prepara la consulta
            stmt = connection.prepareStatement(EquipoDAOQuery.GET_EQUIPOS);
            // Da valor a los parámetros de la consulta

            // Ejecuta la consulta
            ResultSet rs = stmt.executeQuery();
            // Procesa los resultados
            while (rs.next()) {
                Equipo equipo = new Equipo();
                equipo.setId(rs.getString("id"));
                equipo.setNombre(rs.getString("nombre"));
                equipo.setNomenclatura(rs.getString("nomenclatura"));
                equipo.setValor(rs.getInt("valor"));

                equipoCollection.getEquipos().add(equipo);
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
        return equipoCollection;
    }
    @Override
    public Equipo updateEquipo(Equipo equipo) throws SQLException
    {
        Equipo equipon = null;
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(EquipoDAOQuery.UPDATE_EQUIPO);
            stmt.setString(1, equipo.getNombre());
            stmt.setString(2, equipo.getNomenclatura());
            stmt.setInt(3, equipo.getValor());
            stmt.setString(4, equipo.getId());

            int rows = stmt.executeUpdate();
            if (rows == 1){
                equipon = new Equipo();
                equipon = getEquipobyID(equipo.getId());
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return equipon;
    }
    @Override
    public boolean deleteEquipo(String id) throws SQLException
    {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(EquipoDAOQuery.DELETE_EQUIPO);
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
