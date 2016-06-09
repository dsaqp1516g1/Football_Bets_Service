package edu.upc.eetac.dsa.football.DAO;

import edu.upc.eetac.dsa.football.Database.Database;
import edu.upc.eetac.dsa.football.entity.Jugadores;
import edu.upc.eetac.dsa.football.entity.JugadoresCollection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by toni on 9/06/16.
 */
public class JugadoresDAOimpl implements JugadoresDAO {
    @Override
    public Jugadores createJugadores(String equipoid, String nombre, int dorsal, String nacionalidad, String valor, String nacimiento, String fincontrato) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {

            connection = Database.getConnection();

            stmt = connection.prepareStatement(JugadoresDAOQuery.CREATE_JUGADORES);
            stmt.setString(1, equipoid);
            stmt.setString(2, nombre);
            stmt.setInt(3, dorsal);
            stmt.setString(4, nacionalidad);
            stmt.setString(5, valor);
            stmt.setString(6, nacimiento);
            stmt.setString(7, fincontrato);
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
        return getJugadoresbyNOM(nombre);
    }

    @Override
    public Jugadores getJugadoresbyNOM(String nombre) throws SQLException {

        Jugadores jugadores = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(JugadoresDAOQuery.GET_JUGADORESBYNOM);
            stmt.setString(1, nombre);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                jugadores = new Jugadores();
                jugadores.setEquipoid(rs.getString("equipoid"));
                jugadores.setNombre(rs.getString("nombre"));
                jugadores.setDorsal(rs.getInt("dorsal"));
                jugadores.setNacionalidad(rs.getString("nacionalidad"));
                jugadores.setValor(rs.getString("valor"));
                jugadores.setNacimiento(rs.getString("nacimiento"));
                jugadores.setFincontrato(rs.getString("fincontrato"));

            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return jugadores;
    }

    @Override
    public Jugadores getJugadoresbyEID(String equipoid) throws SQLException {
        Jugadores jugadores = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(JugadoresDAOQuery.GET_JUGADORESBYEID);
            stmt.setString(1, equipoid);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                jugadores = new Jugadores();
                jugadores.setEquipoid(rs.getString("equipoid"));
                jugadores.setNombre(rs.getString("nombre"));
                jugadores.setDorsal(rs.getInt("dorsal"));
                jugadores.setNacionalidad(rs.getString("nacionalidad"));
                jugadores.setValor(rs.getString("valor"));
                jugadores.setNacimiento(rs.getString("nacimiento"));
                jugadores.setFincontrato(rs.getString("fincontrato"));

            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return jugadores;
    }

    @Override
    public JugadoresCollection getJugadores() throws SQLException {
        return null;
    }

    @Override
    public Jugadores updateJugadores(Jugadores jugadores) throws SQLException {
        Jugadores jugadoresn = null;
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(JugadoresDAOQuery.UPDATE_JUGADORES);
            stmt.setString(1, jugadores.getEquipoid());
            stmt.setInt(2, jugadores.getDorsal());
            stmt.setString(3, jugadores.getNacionalidad());
            stmt.setString(4, jugadores.getValor());
            stmt.setString(5, jugadores.getNacimiento());
            stmt.setString(6, jugadores.getFincontrato());
            stmt.setString(7, jugadores.getNombre());

            int rows = stmt.executeUpdate();
            if (rows == 1){
                jugadoresn = new Jugadores();
                jugadoresn = getJugadoresbyNOM(jugadores.getNombre());
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return jugadoresn;
    }

    @Override
    public boolean deleteJugadores(String nombre) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(JugadoresDAOQuery.DELETE_JUGADORES);
            stmt.setString(1, nombre);

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
