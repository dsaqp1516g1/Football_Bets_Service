package edu.upc.eetac.dsa.football.DAO;

/**
 * Created by marc on 2/03/16.
 */
public interface UserDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_USER = "insert into users (id, loginid, password, email, balance) values (UNHEX(?), ?, UNHEX(MD5(?)), ?, ?);";
    public final static String UPDATE_USER = "update users set email=?, balance=? where id=unhex(?)";
    public final static String ASSIGN_ROLE_REGISTERED = "insert into user_roles (userid, role) values (UNHEX(?), 'registrado')";
    public final static String GET_USER_BY_ID = "select hex(u.id) as id, u.loginid, u.email, u.balance from users u where id=unhex(?)";
    public final static String GET_USER_BY_USERNAME = "select hex(u.id) as id, u.loginid, u.email, u.balance from users u where u.loginid=?";
    public final static String DELETE_USER = "delete from users where id=unhex(?)";
    public final static String GET_PASSWORD =  "select hex(password) as password from users where id=unhex(?)";
}
