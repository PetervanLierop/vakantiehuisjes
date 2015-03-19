/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import business.Accommodation;
import business.Booking;
import business.Security;
import business.User;
import business.UserRole;
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
public class UserRoleDB {

    public static UserRole selectUserRoleOfUser(String inLogName) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT userrole.* FROM user, userrole "
                + "WHERE user.logInName = ? "
                + "AND user.userrole_id = userrole.id";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, inLogName);
            rs = ps.executeQuery();
            UserRole userRole = null;
            if (rs.first()) {
                userRole = new UserRole();
                userRole.setId(rs.getInt("id"));
                userRole.setDescription(rs.getString("description"));
            }
            return userRole;
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

    public static UserRole selectUserRole(int id) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT userrole.* FROM userrole "
                + "WHERE userrole.id = ? ";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            UserRole userRole = null;
            if (rs.first()) {
                userRole = new UserRole();
                userRole.setId(rs.getInt("id"));
                userRole.setDescription(rs.getString("description"));
            }
            return userRole;
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

    public static UserRole selectUserRole(String description) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT userrole.* FROM userrole "
                + "WHERE description = ? ";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, description);
            rs = ps.executeQuery();
            UserRole userRole = null;
            if (rs.first()) {
                userRole = new UserRole();
                userRole.setId(rs.getInt("id"));
                userRole.setDescription(rs.getString("description"));
            }
            return userRole;
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

    public static List<UserRole> selectAllUserRoles() {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM userrole ";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            List<UserRole> allUserRoles = new ArrayList<UserRole>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String description = rs.getString("description");
                Date lastUpdate = rs.getDate("lastUpdate");
                UserRole userRole = new UserRole(id, description, lastUpdate);
                allUserRoles.add(userRole);
                userRole = null;
            }
            return allUserRoles;
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

    public static int deleteUserRole(int userRoleId) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "DELETE FROM userrole "
                + "WHERE id = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, userRoleId);
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

    public static UserRole insertUserRole(UserRole userRole) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query =
                "INSERT INTO userrole (description, lastUpdate) "
                + "VALUES (?, ?)";
        try {
            ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, userRole.getDescription());
            ps.setDate(2, userRole.getLastUpdate());
            ps.executeUpdate();
            int autoIncKeyFromApi = -1;
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                autoIncKeyFromApi = rs.getInt(1);
            } else {
                // throw an exception from here
            }
            userRole.setId(autoIncKeyFromApi);
            return userRole;
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

    public static Boolean userRoleExists(String description) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM userrole "
                + "WHERE userrole.description = ? ";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, description);
            rs = ps.executeQuery();
            if (rs.first()) {
                return true;
            }
            return false;
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

    public static List<UserRole> selectUserRolesWithoutStandardPrice(Accommodation accommodation) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM userrole "
                + "WHERE id NOT IN "
                + "(SELECT userRole_id FROM accommodationprice "
                + "WHERE standardPrice > 0 "
                + "AND accommodation_id = ? )";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, accommodation.getId());
            rs = ps.executeQuery();
            List<UserRole> userRoles = new ArrayList<UserRole>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String description = rs.getString("description");
                Date lastUpdate = rs.getDate("lastUpdate");
                UserRole userRole = new UserRole(id, description, lastUpdate);
                userRoles.add(userRole);
                userRole = null;
            }
            return userRoles;
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
}