/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import business.Renter;
import business.Security;
import business.User;
import business.UserRole;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Peter
 */
public class UserDB {

    public static int updateUserRole(User user) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;

        String query = "UPDATE user SET "
                + "userRole_id = ? "
                + "WHERE id = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, user.getUserRole().getId());
            ps.setInt(2, user.getId());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeConnection(connection);
//            pool.freeConnection(connection);
        }
    }

        public static int updateAdministrator(User user) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;

        String query = "UPDATE user SET "
                + "isAdministrator = ? "
                + "WHERE id = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setBoolean(1, user.isAdministrator());
            ps.setInt(2, user.getId());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeConnection(connection);
//            pool.freeConnection(connection);
        }
    }
    
    public static User insertUser(User user) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query =
                "INSERT INTO user ("
                + "logInName, "
                + "firstName, "
                + "lastName, "
                + "emailAddress, "
                + "userRole_id, "
                + "isAdministrator, "
                + "salt, "
                + "encryptedPassword, "
                + "expirationDate, "
                + "lastUpdate) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getInlogName());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getEmailAddress());
            ps.setInt(5, user.getUserRole().getId());
            ps.setBoolean(6, user.isAdministrator());
            Blob blob = connection.createBlob();
            Security security = user.getSecurity();
            blob.setBytes(1, security.getSalt());
            ps.setBlob(7, blob);
            blob.setBytes(1, security.getEncryptedPassword());
            ps.setBlob(8, blob);
            ps.setDate(9, security.getExpirationDate());
//            ps.setTimestamp(9, security.getLastUpdate());
            ps.setDate(10, user.getLastUpdate());
            ps.executeUpdate();
            int autoIncKeyFromApi = -1;
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                autoIncKeyFromApi = rs.getInt(1);
            } else {
                // throw an exception from here
            }
            user.setId(autoIncKeyFromApi);

            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeConnection(connection);
//            pool.freeConnection(connection);
        }
    }

    public static int updateUser(User user) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;

        String query = "UPDATE user SET "
                + "LogInName = ?, "
                + "FirstName = ?, "
                + "LastName = ?, "
                + "EmailAddress = ? "
                + "WHERE id = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, user.getInlogName());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getEmailAddress());
            ps.setInt(5, user.getId());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeConnection(connection);
//            pool.freeConnection(connection);
        }
    }

    public static User selectUser(String logInName) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM user WHERE logInName = ? ";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, logInName);
            rs = ps.executeQuery();
            User user = null;
            if (rs.first()) {
                user = getUser(rs);
            }
            return user;
        } catch (SQLException e) {
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeConnection(connection);
//            pool.freeConnection(connection);
        }
    }

    public static User selectUser(int id) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM user WHERE id = ? ";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            User user = null;
            if (rs.next()) {
                user = getUser(rs);
            }
            return user;
        } catch (SQLException e) {
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeConnection(connection);
//            pool.freeConnection(connection);
        }
    }

    public static Renter selectRenter(int id) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM user WHERE id = ? ";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            Renter renter = null;
            if (rs.next()) {
                renter = new Renter();
                renter.setInlogName(rs.getString("logInName"));
                renter.setId(id);
                renter.setFirstName(rs.getString("firstName"));
                renter.setLastName(rs.getString("lastName"));
                renter.setEmailAddress(rs.getString("emailAddress"));
                Security security =
                        UserDB.selectSecurity(id);
                renter.setSecurity(security);
                UserRole userRole =
                        UserRoleDB.selectUserRoleOfUser(renter.getInlogName());
                renter.setUserRole(userRole);
            }
            return renter;
        } catch (SQLException e) {
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeConnection(connection);
//            pool.freeConnection(connection);
        }
    }

    public static List<User> selectAllUsers() {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM user ";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            List<User> allUsers = new ArrayList<User>();
            while (rs.next()) {
                User user = getUser(rs);
                allUsers.add(user);
                user = null;
            }
            return allUsers;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeConnection(connection);
//            pool.freeConnection(connection);
        }
    }

    public static boolean inlogNameExists(int id, String inlogName) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT 1 FROM user WHERE id != ? AND logInName = ? ";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ps.setString(2, inlogName);
            rs = ps.executeQuery();
            return rs.first();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeConnection(connection);
//            pool.freeConnection(connection);
        }
    }

    public static boolean inlogNameExists(String inlogName) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT 1 FROM user WHERE logInName = ? ";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, inlogName);
            rs = ps.executeQuery();
            return rs.first();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeConnection(connection);
//            pool.freeConnection(connection);
        }
    }

    public static int updateUserRoleAllUsers(int oldUserRole, int newUserRole) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;

        String query = "UPDATE user SET "
                + "userRole_id = ? "
                + "WHERE userRole_id = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, newUserRole);
            ps.setInt(2, oldUserRole);

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeConnection(connection);
//            pool.freeConnection(connection);
        }
    }

    public static int deleteUser(User user) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "DELETE FROM user "
                + "WHERE id = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, user.getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 99;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeConnection(connection);
//            pool.freeConnection(connection);
        }
    }

    public static int updateSecurity(Security security) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;

        String query = "UPDATE user SET "
                + "salt = ? , "
                + "encryptedPassword = ? , "
                + "expirationDate = ? , "
                + "isAdministrator = ?, "
                + "lastUpdate = ? "
                + "WHERE id = ? ";
        try {
            ps = connection.prepareStatement(query);
            Blob blob = connection.createBlob();
            blob.setBytes(1, security.getSalt());
            ps.setBlob(1, blob);
            blob.setBytes(1, security.getEncryptedPassword());
            ps.setBlob(2, blob);
            ps.setDate(3, security.getExpirationDate());
            ps.setBoolean(4, security.isAdministrator());
            ps.setTimestamp(5, security.getLastUpdate());
            ps.setInt(6, security.getId());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeConnection(connection);
//            pool.freeConnection(connection);
        }
    }

    public static Security selectSecurity(int id) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM user WHERE id = ? ";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            Security security = null;
            if (rs.next()) {
                security = new Security();
                security.setId(rs.getInt("id"));
                security.setExpirationDate(rs.getDate("expirationDate"));
                security.setAdministrator(rs.getBoolean("isAdministrator"));
                security.setLastUpdate(rs.getTimestamp("lastUpdate"));
                Blob blob;
                int blobLength;
                blob = rs.getBlob("salt");
                if (blob != null) {
                    blobLength = (int) blob.length();
                    security.setSalt(blob.getBytes(1, blobLength));
                    blob = rs.getBlob("encryptedPassword");
                    blobLength = (int) blob.length();
                    security.setEncryptedPassword(blob.getBytes(1, blobLength));
                    blob.free();
                }
            }
            return security;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeConnection(connection);
//            pool.freeConnection(connection);
        }
    }

    private static User getUser(ResultSet rs) throws SQLException  {
                User user = new User();
                user.setInlogName(rs.getString("logInName"));
                int userId = rs.getInt("id");
                user.setId(userId);
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                user.setEmailAddress(rs.getString("emailAddress"));
                Security security =
                        UserDB.selectSecurity(userId);
                user.setSecurity(security);
                UserRole userRole =
                        UserRoleDB.selectUserRoleOfUser(user.getInlogName());
                user.setUserRole(userRole);
                user.setAdministrator(rs.getBoolean("isAdministrator"));
                user.setLastUpdate(rs.getDate("lastUpdate"));
                return user;
    }
}
