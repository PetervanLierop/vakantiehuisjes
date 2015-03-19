/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import business.Accommodation;
import business.Price;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.CurrencyEnum;

/**
 *
 * @author Peter
 */
public class AccommodationDB {

    public static List<Accommodation> selectAllAccommodations() {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM accommodation ";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            List<Accommodation> accomodations = new ArrayList<Accommodation>();
            while (rs.next()) {
                Accommodation accommodation = getAccommodation(rs);
                accomodations.add(accommodation);
            }
            return accomodations;
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

    public static Accommodation selectAccommodation(String name) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM accommodation WHERE name = ? ";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, name);
            rs = ps.executeQuery();
            Accommodation accommodation = null;
            while (rs.next()) {
                accommodation = getAccommodation(rs);
            }
            return accommodation;
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

    public static Accommodation selectAccomodation(int id) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        System.out.println("test inbetween1");
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM accommodation WHERE id = ? ";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            Accommodation accommodation = null;
            while (rs.next()) {
                accommodation = getAccommodation(rs);
            }
            return accommodation;
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

    public static Accommodation getAccommodation(ResultSet rs) throws SQLException {
        Accommodation accommodation = new Accommodation();
        int accommodationId;
        Price cleaningCosts = new Price();
        accommodationId = rs.getInt("id");
        accommodation.setId(accommodationId);
        accommodation.setName(rs.getString("name"));
        accommodation.setGpsLongitude(rs.getDouble("gpsLongitude"));
        accommodation.setGpsLatitude(rs.getDouble("gpsLatitude"));
        cleaningCosts.setCurrency(CurrencyEnum.valueOf(rs.getString("currency")));
        cleaningCosts.setAmount(rs.getBigDecimal("cleaningCosts"));
        accommodation.setDayAndNightElectricityRates(rs.getBoolean("hasDay/NightElectricity"));
        accommodation.setPowerSupplierId(rs.getInt("powerSupplier_id"));
        accommodation.setCleaningCosts(cleaningCosts);
        accommodation.setPriceLevels(PriceDB.selectPriceLevels(accommodationId));
        return accommodation;
    }

    public static int updateAccommodation(Accommodation accommodation) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;

        String query = "UPDATE accommodation SET "
                + "name = ?, "
                + "street = ?, "
                + "houseNumber = ?, "
                + "zip = ? "
                + "city = ?, "
                + "country = ?, "
                + "gpsLatitude = ?, "
                + "gpsLongitude = ? "
                + "gpsAltitude = ?, "
                + "currency = ?, "
                + "cleaningCosts = ?, "
                + "powerSupplier_id = ? "
                + "hasDay/NightElectricity = ?, "
                + "WHERE id = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, accommodation.getName());
            ps.setString(2, accommodation.getAddress().getStreet());
            ps.setInt(3, accommodation.getAddress().getHouseNumber());
            ps.setString(4, accommodation.getAddress().getZip());
            ps.setString(5, accommodation.getAddress().getCity());
            ps.setString(6, accommodation.getAddress().getCountry());
            ps.setDouble(7, accommodation.getGpsLatitude());
            ps.setDouble(8, accommodation.getGpsLongitude());
            ps.setDouble(9, accommodation.getGpsAltitude());
            ps.setString(10, accommodation.getCleaningCosts().getCurrency().name());
            ps.setBigDecimal(11, accommodation.getCleaningCosts().getAmount());
            ps.setInt(12, accommodation.getPowerSupplierId());
            ps.setBoolean(13, accommodation.hasDayAndNightElectricityRates());
            ps.setInt(14, accommodation.getId());
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