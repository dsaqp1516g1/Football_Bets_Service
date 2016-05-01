package edu.upc.eetac.dsa.football.DAO;


import edu.upc.eetac.dsa.football.entity.Equipo;
import edu.upc.eetac.dsa.football.entity.EquipoCollection;

import java.sql.SQLException;

public interface EquipoDAO {
    public Equipo createEquipo(String nombre, String nomenclatura, int valor) throws SQLException, EquipoAlreadyExistsException;
    public Equipo getEquipobyID(String id) throws SQLException;
    public Equipo getEquipobyNomen(String nomenclatura) throws SQLException;
    public EquipoCollection getEquipos() throws SQLException;
    public Equipo updateEquipo(Equipo equipo) throws SQLException;
    public boolean deleteEquipo(String id) throws SQLException;

}
