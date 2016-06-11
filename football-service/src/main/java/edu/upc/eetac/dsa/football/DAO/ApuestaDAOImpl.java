package edu.upc.eetac.dsa.football.DAO;

import edu.upc.eetac.dsa.football.Database.Database;
import edu.upc.eetac.dsa.football.entity.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by toni on 4/05/16.
 */
public class ApuestaDAOImpl implements ApuestaDAO{
    /**
     *
     * Apuestas Generales
     *
    */

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
    public Apuesta actuCuotas(String id) throws SQLException
    {
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
                c1 = rs.getFloat("suma1");
                //c1 = c1 + cuota1;
            }
            else
                throw new SQLException();
            connection.setAutoCommit(false);
            stmt.close();

            stmt = connection.prepareStatement(ApuestaDAOQuery.SUM_CUOTA_X);
            stmt.setString(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                cx = rs.getFloat("sumax");
                //cx = cx + cuotax;
            }
            else
                throw new SQLException();
            stmt.close();

            stmt = connection.prepareStatement(ApuestaDAOQuery.SUM_CUOTA_2);
            stmt.setString(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                c2 = rs.getFloat("suma2");
                //c2 = c2 + cuota2;
            }
            else
                throw new SQLException();
            stmt.close();

            //Suma total del bote
            suma = c1 + c2 + cx;

            //Aplico directamente los & para cada apuesta
            stmt = connection.prepareStatement(ApuestaDAOQuery.UPDATE_CUOTAS);
            /**
             * Implementamos de manera guarra con la declaración de floats debido a que SQL no permite la
             * división entera, ya que esta da demasiados decimales y, por lo tanto, da infinito. La solución es
             * detectar el DOuble.Positive_Infinity y devolver el valor de 0.
             */

            float n1 = suma/c1;
            if (n1 == Double.POSITIVE_INFINITY)
                n1 = 0;
            float nx = suma/cx;
            if (nx == Double.POSITIVE_INFINITY)
                nx = 0;
            float n2 = suma/c2;
            if (n2 == Double.POSITIVE_INFINITY)
                n2 = 0;
            stmt.setFloat(1, n1);
            stmt.setFloat(2,nx);
            stmt.setFloat(3, n2);
            stmt.setString(4, id);

            int rows = stmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();

            if (connection != null) connection.close();
        }

        return getApuestaById(id);
    }

    /**
     *
     * Apuestas Usuarios
     *
     */

    @Override
    public ApuestaUsuario createApuestaUsuario(String idusuario, String idapuesta, String resultado, float valor) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        String idnueva_apuestauser;
        try {

            connection = Database.getConnection();

            stmt = connection.prepareStatement(UserDAOQuery.UUID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                idnueva_apuestauser = rs.getString(1);
            else
                throw new SQLException();
            stmt.close();

            stmt = connection.prepareStatement(ApuestaDAOQuery.CREATE_APUESTA_USUARIO);
            stmt.setString(1, idnueva_apuestauser);
            stmt.setString(2, idusuario);
            stmt.setString(3, idapuesta);
            stmt.setString(4, resultado);
            stmt.setFloat(5, valor);
            //Colocamos como previo, el valor de la apuesta como balance
            stmt.setFloat(6, valor);
            stmt.executeUpdate();

            UserDAO userDAO = new UserDAOImpl();
            User user = new User();
            user = userDAO.getUserById(idusuario);
            float balance = user.getBalance() - valor;
            userDAO.updateProfile(user.getId(), user.getEmail(), balance);

        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
        return getApuestaUsuarioById(idnueva_apuestauser);
    }

    @Override
    public ApuestaUsuario getApuestaUsuarioById(String id) throws SQLException {
        // Modelo a devolver
        ApuestaUsuario apuestaUsuario = null;
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            // Obtiene la conexión del DataSource
            connection = Database.getConnection();

            // Prepara la consulta
            stmt = connection.prepareStatement(ApuestaDAOQuery.GET_APUESTABYID_USUARIO);
            // Da valor a los parámetros de la consulta
            stmt.setString(1, id);

            // Ejecuta la consulta
            ResultSet rs = stmt.executeQuery();
            // Procesa los resultados
            if (rs.next())
            {
                apuestaUsuario = new ApuestaUsuario();
                apuestaUsuario.setId(rs.getString("id"));
                apuestaUsuario.setUsuarioid(rs.getString("usuarioid"));
                apuestaUsuario.setApuestaid(rs.getString("apuestaid"));
                apuestaUsuario.setResultado(rs.getString("resultado"));
                apuestaUsuario.setValor(rs.getFloat("valor"));
                apuestaUsuario.setResolucion("");
                apuestaUsuario.setBalance(rs.getFloat("balance"));
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
        return apuestaUsuario;
    }

    @Override
    public ApuestaUsuarioCollection getApuestasUsuario(String id) throws SQLException {
        ApuestaUsuarioCollection apuestaUsuarioCollection = new ApuestaUsuarioCollection();
        // Modelo a devolver
        Connection connection = null;
        PreparedStatement stmt = null;
        ApuestaUsuario apuestaUsuario = null;
        try {
            // Obtiene la conexión del DataSource
            connection = Database.getConnection();

            // Prepara la consulta
            stmt = connection.prepareStatement(ApuestaDAOQuery.GET_APUESTAS_USUARIO);
            stmt.setString(1, id);
            // Da valor a los parámetros de la consulta

            // Ejecuta la consulta
            ResultSet rs = stmt.executeQuery();
            // Procesa los resultados
            while (rs.next()) {
                apuestaUsuario = new ApuestaUsuario();
                apuestaUsuario.setId(rs.getString("id"));
                apuestaUsuario.setUsuarioid(rs.getString("usuarioid"));
                apuestaUsuario.setApuestaid(rs.getString("apuestaid"));
                apuestaUsuario.setResultado(rs.getString("resultado"));
                apuestaUsuario.setValor(rs.getFloat("valor"));
                apuestaUsuario.setResolucion("");
                apuestaUsuario.setBalance(rs.getFloat("balance"));

                apuestaUsuarioCollection.getApuestasUsuario().add(apuestaUsuario);
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
        return apuestaUsuarioCollection;
    }

}
