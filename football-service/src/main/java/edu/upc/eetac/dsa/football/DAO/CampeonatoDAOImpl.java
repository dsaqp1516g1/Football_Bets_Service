package edu.upc.eetac.dsa.football.DAO;

import edu.upc.eetac.dsa.football.Database.Database;
import edu.upc.eetac.dsa.football.entity.Campeonato;
import edu.upc.eetac.dsa.football.entity.CampeonatoCollection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by mbmarkus on 18/05/16.
 */
public class CampeonatoDAOImpl implements CampeonatoDAO {
    @Override
    public Campeonato createEquipo(String equipoid, String nombreequipo, int puntos, int golesfavor, int golescontra,
                                   int diferencia) throws SQLException, CampeonatoAlreadyExistsException{
        Connection connection = null;
        PreparedStatement stmt = null;
        int ranking_pre = 0;
        try {
            connection = Database.getConnection();

            Campeonato campeonato = getEquipobyID(equipoid);
            if (campeonato != null)
                throw new CampeonatoAlreadyExistsException();

            //Sacar el número de ranking preeliminar
            stmt = connection.prepareStatement(CampeonatoDAOQuery.GET_NUMERO_EQUIPOS);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ranking_pre = rs.getInt("numero");
                //En caso que haya 0 equipos le sumamos 1, EX: 2 equipos, assignamos el ranking a la cola rank=3
                ranking_pre++;
            }
            else
                throw new SQLException();

            connection.setAutoCommit(false);

            stmt = connection.prepareStatement(CampeonatoDAOQuery.CREATE_EQUIPO);
            stmt.setString(1, equipoid);
            stmt.setString(2, nombreequipo);
            stmt.setInt(3, ranking_pre);
            stmt.setInt(4, puntos);
            stmt.setInt(5, golesfavor);
            stmt.setInt(6, golescontra);
            stmt.setInt(7, diferencia);
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

            //Refresca la tabla según los puntos
            actualizarTabla();
        }
        return getEquipobyID(equipoid);
    }
    @Override
    public Campeonato getEquipobyID(String equipoid) throws SQLException
    {
        // Modelo a devolver
        Campeonato campeonato = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            // Obtiene la conexión del DataSource
            connection = Database.getConnection();

            // Prepara la consulta
            stmt = connection.prepareStatement(CampeonatoDAOQuery.GET_EQUIPOBYID);
            // Da valor a los parámetros de la consulta
            stmt.setString(1, equipoid);

            // Ejecuta la consulta
            ResultSet rs = stmt.executeQuery();
            // Procesa los resultados
            if (rs.next()) {
                Campeonato campeonato1 = new Campeonato();
                campeonato1.setEquipoid(rs.getString("equipoid"));
                campeonato1.setNombreequipo(rs.getString("nombreequipo"));
                campeonato1.setRanking(rs.getInt("ranking"));
                campeonato1.setPuntos(rs.getInt("puntos"));
                campeonato1.setGolesfavor(rs.getInt("golesfavor"));
                campeonato1.setGolescontra(rs.getInt("golescontra"));
                campeonato1.setDiferencia(rs.getInt("diferencia"));

                campeonato = campeonato1;
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
        return campeonato;
    }
    @Override
    public CampeonatoCollection getEquipos() throws SQLException
    {
        CampeonatoCollection campeonatoCollection = new CampeonatoCollection();
        // Modelo a devolver
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            // Obtiene la conexión del DataSource
            connection = Database.getConnection();

            // Prepara la consulta
            stmt = connection.prepareStatement(CampeonatoDAOQuery.GET_EQUIPOS);
            // Da valor a los parámetros de la consulta

            // Ejecuta la consulta
            ResultSet rs = stmt.executeQuery();
            // Procesa los resultados
            while (rs.next()) {
                Campeonato campeonato = new Campeonato();
                campeonato.setEquipoid(rs.getString("equipoid"));
                campeonato.setNombreequipo(rs.getString("nombreequipo"));
                campeonato.setRanking(rs.getInt("ranking"));
                campeonato.setPuntos(rs.getInt("puntos"));
                campeonato.setGolesfavor(rs.getInt("golesfavor"));
                campeonato.setGolescontra(rs.getInt("golescontra"));
                campeonato.setDiferencia(rs.getInt("diferencia"));

                campeonatoCollection.getListado_campeonato().add(campeonato);
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
        return campeonatoCollection;
    }
    @Override
    public Campeonato updateEquipo(Campeonato campeonato) throws SQLException
    {
        Campeonato campeonato1 = new Campeonato();
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            //nombreequipo=?, ranking=?, puntos=?, golesfavor=?, golescontra=?, diferencia=?
            stmt = connection.prepareStatement(CampeonatoDAOQuery.UPDATE_EQUIPO);
            stmt.setString(1, campeonato.getNombreequipo());
            stmt.setInt(2, campeonato.getRanking());
            stmt.setInt(3, campeonato.getPuntos());
            stmt.setInt(4, campeonato.getGolesfavor());
            stmt.setInt(5, campeonato.getGolescontra());
            stmt.setInt(6, campeonato.getDiferencia());
            stmt.setString(7, campeonato.getEquipoid());

            int rows = stmt.executeUpdate();
            if (rows == 1){
                campeonato1 = getEquipobyID(campeonato.getEquipoid());
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();

            //Refresca la tabla según los puntos
            actualizarTabla();
        }

        return campeonato1;
    }
    @Override
    public boolean deleteEquipo(String equipoid) throws SQLException
    {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(CampeonatoDAOQuery.DELETE_EQUIPO);
            stmt.setString(1, equipoid);

            int rows = stmt.executeUpdate();
            return (rows == 1);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();

            //Refresca la tabla según los puntos
            actualizarTabla();
        }
    }
    @Override
    public CampeonatoCollection actualizarTabla() throws SQLException
    {
        // Modelo a devolver
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            // Obtiene la conexión del DataSource
            connection = Database.getConnection();

            // Prepara la consulta
            stmt = connection.prepareStatement(CampeonatoDAOQuery.GET_RANKS);

            // Ejecuta la consulta
            ResultSet rs = stmt.executeQuery();
            // Procesa los resultados

            int indice = 1;
            while (rs.next()) {
                actualizarRankingEquipo(rs.getString("equipoid"), indice);
                indice++;
            }
        } catch (SQLException e) {
            // Relanza la excepción
            throw e;
        } finally {
            // Libera la conexión
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return getEquipos();
    }
    @Override
    public void actualizarRankingEquipo(String equipoid, int ranking) throws SQLException
    {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            //"update campeonato set ranking=? where equipoid=unhex(?)"
            stmt = connection.prepareStatement(CampeonatoDAOQuery.UPDATE_RANK);
            stmt.setInt(1, ranking);
            stmt.setString(2, equipoid);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
    }
}
