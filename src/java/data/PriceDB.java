/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import business.Accommodation;
import business.Price;
import business.PriceLevel;
import business.RentalRates;
import business.UserRole;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import util.CurrencyEnum;

/**
 *
 * @author Peter
 */
public class PriceDB {

    /**
     *
     * @param rentalRates
     */
    public static int updateRentalStandardRate(Price price, UserRole userRole, Accommodation accommodation) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;

        String query = "UPDATE accommodationprice SET "
                + "currency = ?, "
                + "standardPrice = ? "
                + "WHERE userRole_id = ? "
                + "AND accommodation_id = ? ";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, price.getCurrency().name());
            ps.setBigDecimal(2, price.getAmount());
            ps.setInt(3, userRole.getId());
            ps.setInt(4, accommodation.getId());

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

    public static int insertRentalRate(Price price, UserRole userRole, PriceLevel priceLevel, Accommodation accommodation) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query =
                "INSERT INTO accommodationprice ("
                + "currency, "
                + "price, "
                + "userRole_id, "
                + "priceLevel_id, "
                + "accommodation_id) "
                + "VALUES (?, ?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, price.getCurrency().name());
            ps.setBigDecimal(2, price.getAmount());
            ps.setInt(3, userRole.getId());
            ps.setInt(4, priceLevel.getId());
            ps.setInt(5, accommodation.getId());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeConnection(connection);
//            pool.freeConnection(connection);
        }
    }

    public static int deleteAccommodationPrice(UserRole userRole) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "DELETE FROM accommodationprice "
                + "WHERE userRole_id = ? ";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, userRole.getId());
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

    public static int deleteAccommodationPriceLevel(PriceLevel priceLevel) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "DELETE FROM accommodationprice "
                + "WHERE priceLevel_id = ? ";
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

    public static Map<String, RentalRates> selectPriceLevels(int accomodationId) {
        RentalRates rentalRates = new RentalRates();
        Map<String, RentalRates> priceLevels = new LinkedHashMap<String, RentalRates>();

//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM accommodationprice, "
                + "userrole, "
                + "pricelevel "
                + "WHERE accommodationprice.accommodation_id = ? "
                + "AND accommodationprice.userRole_id = userrole.id "
                + "AND accommodationprice.priceLevel_id = pricelevel.id "
                + "ORDER BY accommodationprice.userRole_id, "
                + "pricelevel.id ";
        try {
            int saveUserRoleId = 1;
            String userRoleDescription = "";
            ps = connection.prepareStatement(query);
            ps.setInt(1, accomodationId);
            rs = ps.executeQuery();
            while (rs.next()) {
                int userRoleId = rs.getInt("userrole.id");
                if (userRoleId != saveUserRoleId) {
                    rentalRates = new RentalRates();
                    saveUserRoleId = userRoleId;
                }
                CurrencyEnum currency = CurrencyEnum.valueOf(rs.getString("currency"));
                BigDecimal accommodationAmount = rs.getBigDecimal("price");
                Price accommodationRate = new Price(currency, accommodationAmount);
                userRoleDescription = rs.getString("userrole.description");
                
                int priceLevelId = rs.getInt("pricelevel.id");
                String priceLevelDescription  = rs.getString("pricelevel.description");
                int priceLevelStartWeekNumber  = rs.getInt("pricelevel.startweeknumber");
                int priceLevelEndWeekNumber = rs.getInt("pricelevel.endweeknumber");
                int priceLevelPriority = rs.getInt("pricelevel.priority");
                PriceLevel priceLevel = new PriceLevel(
                        priceLevelId,
                        priceLevelDescription,
                        priceLevelStartWeekNumber,
                        priceLevelEndWeekNumber,
                        priceLevelPriority);
                
                rentalRates.addPeriodPrices(priceLevel, accommodationRate);
                priceLevels.put(userRoleDescription, rentalRates);

            }
            return priceLevels;
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

    public static List<String> selectAllPriceLevelDescriptions() {
        List<String> priceLevelDescriptions = new LinkedList<String>();

//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT DISTINCT (description), id "
                + "FROM pricelevel "
                + "ORDER BY id ";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                String priceLevelDescription = rs.getString("description");
                priceLevelDescriptions.add(priceLevelDescription);
            }
            return priceLevelDescriptions;
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

    public static List<PriceLevel> selectAllPriceLevels(int accomodationId) {
        List<PriceLevel> priceLevels = new LinkedList<PriceLevel>();

//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT DISTINCT (pricelevel.id), "
                + "pricelevel.description, "
                + "pricelevel.startWeekNumber, "
                + "pricelevel.endWeekNumber, "
                + "pricelevel.priority "
                + "FROM accommodationprice, "
                + "pricelevel "
                + "WHERE accommodationprice.accommodation_id = ? "
                + "AND accommodationprice.priceLevel_id = pricelevel.id "
                + "ORDER BY pricelevel.priority ";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, accomodationId);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String priceLevelDescription = rs.getString("pricelevel.description");
                int startWeekNumber = rs.getInt("startWeekNumber");
                int endWeekNumber = rs.getInt("endWeekNumber");
                int priority = rs.getInt("priority");
                PriceLevel priceLevel = new PriceLevel(
                        id,
                        priceLevelDescription,
                        startWeekNumber,
                        endWeekNumber,
                        priority);
                priceLevels.add(priceLevel);
            }
            return priceLevels;
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

    public static List<PriceLevel> selectAllPriceLevels() {
        List<PriceLevel> priceLevels = new LinkedList<PriceLevel>();

//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM pricelevel "
                + "ORDER BY pricelevel.priority ";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String priceLevelDescription = rs.getString("pricelevel.description");
                int startWeekNumber = rs.getInt("startWeekNumber");
                int endWeekNumber = rs.getInt("endWeekNumber");
                int priority = rs.getInt("priority");
                PriceLevel priceLevel = new PriceLevel(
                        id,
                        priceLevelDescription,
                        startWeekNumber,
                        endWeekNumber,
                        priority);
                priceLevels.add(priceLevel);
            }
            return priceLevels;
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

    public static PriceLevel selectPriceLevel(String priceDescription) {
        PriceLevel priceLevel = new PriceLevel();

//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM pricelevel "
                + "WHERE description = ? ";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, priceDescription);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String priceLevelDescription = rs.getString("pricelevel.description");
                int startWeekNumber = rs.getInt("startWeekNumber");
                int endWeekNumber = rs.getInt("endWeekNumber");
                int priority = rs.getInt("priority");
                priceLevel = new PriceLevel(
                        id,
                        priceLevelDescription,
                        startWeekNumber,
                        endWeekNumber,
                        priority);
            }
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

    public static boolean rentalRatePriceExists(UserRole userRole, PriceLevel priceLevel, Accommodation accommodation) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT 1 FROM accommodationprice "
                + "WHERE userRole_id = ? "
                + "AND priceLevel_id = ? "
                + "AND accommodation_id = ? ";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, userRole.getId());
            ps.setInt(2, priceLevel.getId());
            ps.setInt(3, accommodation.getId());
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

    public static int updateRentalRatePriceCurrency(CurrencyEnum priceCurrency, UserRole userRole, PriceLevel priceLevel, Accommodation accommodation) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;

        String query = "UPDATE accommodationprice SET "
                + "currency = ? "
                + "WHERE userRole_id = ? "
                + "AND priceLevel_id = ? "
                + "AND accommodation_id = ? ";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, priceCurrency.name());
            ps.setInt(2, userRole.getId());
            ps.setInt(3, priceLevel.getId());
            ps.setInt(4, accommodation.getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 99;
        } finally {
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeConnection(connection);
//            pool.freeConnection(connection);
        }
    }

    public static int insertRentalRatePriceCurrency(CurrencyEnum priceCurrency, UserRole userRole, PriceLevel priceLevel, Accommodation accommodation) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query =
                "INSERT INTO accommodationprice ("
                + "currency, "
                + "userRole_id, "
                + "priceLevel_id, "
                + "accommodation_id) "
                + "VALUES (?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, priceCurrency.name());
            ps.setInt(2, userRole.getId());
            ps.setInt(3, priceLevel.getId());
            ps.setInt(4, accommodation.getId());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeConnection(connection);
//            pool.freeConnection(connection);
        }
    }

    public static int updateRentalRatePriceAmount(BigDecimal amount, UserRole userRole, PriceLevel priceLevel, Accommodation accommodation) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;

        String query = "UPDATE accommodationprice SET "
                + "price = ? "
                + "WHERE userRole_id = ? "
                + "AND priceLevel_id = ? "
                + "AND accommodation_id = ? ";
        try {
            ps = connection.prepareStatement(query);
            ps.setBigDecimal(1, amount);
            ps.setInt(2, userRole.getId());
            ps.setInt(3, priceLevel.getId());
            ps.setInt(4, accommodation.getId());
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

    public static int insertRentalRatePriceAmount(BigDecimal amount, UserRole userRole, PriceLevel priceLevel, Accommodation accommodation) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query =
                "INSERT INTO accommodationprice ("
                + "price, "
                + "userRole_id, "
                + "priceLevel_id, "
                + "accommodation_id) "
                + "VALUES (?, ?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setBigDecimal(1, amount);
            ps.setInt(2, userRole.getId());
            ps.setInt(3, priceLevel.getId());
            ps.setInt(4, accommodation.getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeConnection(connection);
//            pool.freeConnection(connection);
        }
    }

    public static int deleteAccommodationPriceLevel(PriceLevel priceLevel, Accommodation accommodation) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "DELETE FROM accommodationprice "
                + "WHERE priceLevel_id = ? "
                + "AND accommodation_id = ? ";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, priceLevel.getId());
            ps.setInt(2, accommodation.getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeConnection(connection);
//            pool.freeConnection(connection);
        }
    }
}
