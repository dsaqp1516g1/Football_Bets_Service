package edu.upc.eetac.dsa.football.DAO;

import edu.upc.eetac.dsa.football.entity.Campeonato;
import edu.upc.eetac.dsa.football.entity.Equipo;
import edu.upc.eetac.dsa.football.entity.CampeonatoCollection;

import java.sql.SQLException;

/**
 * Created by mbmarkus on 18/05/16.
 */
public interface CampeonatoDAO {
    public Campeonato createEquipo(String equipoid, String nombreequipo, int puntos, int golesfavor, int golescontra,
                                   int diferencia) throws SQLException, CampeonatoAlreadyExistsException;
    public Campeonato getEquipobyID(String equipoid) throws SQLException;
    public CampeonatoCollection getEquipos() throws SQLException;
    public Campeonato updateEquipo(Campeonato campeonato) throws SQLException;
    public boolean deleteEquipo(String id) throws SQLException;
    public CampeonatoCollection actualizarTabla() throws SQLException;
    public void actualizarRankingEquipo(String equipoid, int ranking) throws SQLException;
}
