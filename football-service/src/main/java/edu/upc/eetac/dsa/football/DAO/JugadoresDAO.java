package edu.upc.eetac.dsa.football.DAO;

import edu.upc.eetac.dsa.football.entity.Jugadores;
import edu.upc.eetac.dsa.football.entity.JugadoresCollection;

import java.sql.SQLException;

/**
 * Created by toni on 9/06/16.
 */
public interface JugadoresDAO {
    public Jugadores createJugadores(String equipoid, String nombre, int dorsal, String nacionalidad, String valor, String nacimiento, String fincontrato) throws SQLException;
    public Jugadores getJugadoresbyNOM(String nombre) throws SQLException;
    public Jugadores getJugadoresbyEID(String equipoid) throws SQLException;
    public JugadoresCollection getJugadores() throws SQLException;
    public Jugadores updateJugadores(Jugadores jugadores) throws SQLException;
    public boolean deleteJugadores(String nombre) throws SQLException;
}
