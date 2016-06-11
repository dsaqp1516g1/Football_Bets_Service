package edu.upc.eetac.dsa.football.DAO;



import edu.upc.eetac.dsa.football.Auth.UserInfo;
import edu.upc.eetac.dsa.football.entity.AuthToken;

import java.sql.SQLException;

/**
 * Created by marc on 2/03/16.
 */
public interface AuthTokenDAO {
    public UserInfo getUserByAuthToken(String token) throws SQLException;
    public AuthToken createAuthToken(String userid) throws SQLException;
    public void deleteToken(String userid) throws  SQLException;
}
