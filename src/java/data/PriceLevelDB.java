/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import business.Accommodation;
import business.PriceLevel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Peter
 */
public class PriceLevelDB {

    public static PriceLevel insertPriceLevel(PriceLevel priceLevel) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query =
                "INSERT INTO pricelevel ("
                + "description, "
                + "startweeknumber, "
                + "endweeknumber, "
                + "priority) "
                + "VALUES (?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, priceLevel.getDescription());
            ps.setInt(2, priceLevel.getStartWeekNumber());
            ps.setInt(3, priceLevel.getEndWeekNumber());
            ps.setInt(4, priceLevel.getPriority());
            ps.executeUpdate();
            int autoIncKeyFromApi = -1;
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                autoIncKeyFromApi = rs.getInt(1);
            } else {
                // throw an exception from here
            }
            priceLevel.setId(autoIncKeyFromApi);
            return priceLevel;
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

    public static PriceLevel selectPriceLevel(int id) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM pricelevel WHERE id = ? ";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            PriceLevel priceLevel = null;
            if (rs.next()) {
                priceLevel = new PriceLevel();
                priceLevel.setId(id);
                priceLevel.setDescription(rs.getString("description"));
                priceLevel.setStartWeekNumber(rs.getInt("startweeknumber"));
                priceLevel.setEndWeekNumber(rs.getInt("endweeknumber"));
                priceLevel.setPriority(rs.getInt("priority"));
            }
            return priceLevel;
        } catch (SQLException e) {
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeConnection(connection);
//            pool.freeConnection(connection);
        }
    }

    public static PriceLevel selectPriceLevel(String description) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM pricelevel WHERE description = ? ";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, description);
            rs = ps.executeQuery();
            PriceLevel priceLevel = null;
            if (rs.next()) {
                priceLevel = new PriceLevel();
                priceLevel.setId(rs.getInt("id"));
                priceLevel.setDescription(rs.getString("description"));
                priceLevel.setStartWeekNumber(rs.getInt("startweeknumber"));
                priceLevel.setEndWeekNumber(rs.getInt("endweeknumber"));
                priceLevel.setPriority(rs.getInt("priority"));
            }
            return priceLevel;
        } catch (SQLException e) {
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeConnection(connection);
//            pool.freeConnection(connection);
        }
    }

    public static int deletePriceLevel(PriceLevel priceLevel) {
        PriceDB.deleteAccommodationPriceLevel(priceLevel);
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "DELETE FROM pricelevel "
                + "WHERE id = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, priceLevel.getId());
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

    public static boolean priceLevelDescriptionAlreadyExists(String priceLevelDescription) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT 1 FROM pricelevel WHERE description = ? ";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, priceLevelDescription);
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

    public static boolean priceLevelPriorityAlreadyExists(int priceLevelPriority) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT 1 FROM pricelevel WHERE priority = ? ";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, priceLevelPriority);
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

    public static int updatePriceLevelDescription(PriceLevel priceLevel) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;

        String query = "UPDATE pricelevel SET "
                + "description = ? "
                + "WHERE id = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, priceLevel.getDescription());
            ps.setInt(2, priceLevel.getId());

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

    public static int updatePriceLevelStartWeekNumber(PriceLevel priceLevel) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;

        String query = "UPDATE pricelevel SET "
                + "startWeekNumber = ? "
                + "WHERE id = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, priceLevel.getStartWeekNumber());
            ps.setInt(2, priceLevel.getId());

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

    public static int updatePriceLevelEndWeekNumber(PriceLevel priceLevel) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;

        String query = "UPDATE pricelevel SET "
                + "endWeekNumber = ? "
                + "WHERE id = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, priceLevel.getEndWeekNumber());
            ps.setInt(2, priceLevel.getId());

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

    public static int updatePriceLevelPriority(PriceLevel priceLevel) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;

        String query = "UPDATE pricelevel SET "
                + "priority = ? "
                + "WHERE id = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, priceLevel.getPriority());
            ps.setInt(2, priceLevel.getId());

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
}
