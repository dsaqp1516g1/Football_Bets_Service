package edu.upc.eetac.dsa.football.DAO;


import edu.upc.eetac.dsa.football.entity.User;

import java.sql.SQLException;

/**
 * Created by marc on 2/03/16.
 */
public interface UserDAO {
        public User createUser(String loginid, String password, String email, float balance) throws SQLException, UserAlreadyExistsException;

        public User updateProfile(String id, String email, float balance) throws SQLException;

        public User getUserById(String id) throws SQLException;

        public User getUserByLoginid(String loginid) throws SQLException;

        public boolean deleteUser(String id) throws SQLException;

        public boolean checkPassword(String id, String password) throws SQLException;
}
