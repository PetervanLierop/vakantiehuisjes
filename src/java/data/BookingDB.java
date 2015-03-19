/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import business.Accommodation;
import business.Booking;
import business.Invoice;
import business.InvoiceLine;
import business.Price;
import business.User;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import util.BookingComparator;
import util.CurrencyEnum;
import util.InvoiceLineComparator;

/**
 *
 * @author Peter
 */
public class BookingDB {

    public static List<Booking> selectAllBookings(int accommodationId) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM booking WHERE accommodation_id = ?  ";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, accommodationId);
            rs = ps.executeQuery();
            Booking booking = new Booking();
            List<Booking> bookings = new ArrayList<Booking>();
            while (rs.next()) {
                booking.setId(rs.getInt("id"));
                booking.setStartDate(rs.getDate("startDate"));
                booking.setEndDate(rs.getDate("endDate"));
                booking.setRenter(UserDB.selectRenter(rs.getInt("user_id")));
                booking.setAccommodation(AccommodationDB.selectAccomodation(rs.getInt("accommodation_id")));
                bookings.add(booking);
                booking = new Booking();
            }
            Collections.sort(bookings, new BookingComparator());
            return bookings;
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

    public static List<InvoiceLine> selectAllInvoiceLines(int userId) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM booking WHERE user_id = ?  ";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            InvoiceLine invoiceLine = new InvoiceLine();
            List<InvoiceLine> invoiceLines = new ArrayList<InvoiceLine>();
            while (rs.next()) {
                invoiceLine.setStartDate(rs.getDate("startDate"));
                invoiceLine.setEndDate(rs.getDate("endDate"));
                CurrencyEnum currency = CurrencyEnum.valueOf(rs.getString("currency"));
                BigDecimal rateAtBookingDate = rs.getBigDecimal("rateAtBookingDate");
                Price bookingPrice = new Price(currency, rateAtBookingDate);
                invoiceLine.setPriceAtBookingDate(bookingPrice);
                int accommodation_id = rs.getInt("accommodation_id");
                Accommodation accommodation = AccommodationDB.selectAccomodation(accommodation_id);
                invoiceLine.setAccommodation(accommodation);
                invoiceLine.setStartElectrReadingStandard(rs.getInt("startElectrReadingStandard"));
                invoiceLine.setEndElectrReadingStandard(rs.getInt("endElectrReadingStandard"));
                invoiceLine.setStartElectrReadingLow(rs.getInt("startElectrReadingLow"));
                invoiceLine.setEndElectrReadingLow(rs.getInt("endElectrReadingLow"));
                invoiceLine.setStartElectrReadingHigh(rs.getInt("startElectrReadingHigh"));
                invoiceLine.setEndElectrReadingHigh(rs.getInt("endElectrReadingHigh"));
                invoiceLine.setAccountIsMet(rs.getBoolean("accountIsMet"));
                invoiceLine.setElecticityPrice();
                invoiceLine.setTotalPrice();
                invoiceLines.add(invoiceLine);
                invoiceLine = new InvoiceLine();
            }
            Collections.sort(invoiceLines, new InvoiceLineComparator());
            return invoiceLines;
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

    public static int insertBooking(Booking booking) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query =
                "INSERT INTO booking (user_id, "
                + "accommodation_id, "
                + "startDate, "
                + "endDate, "
                + "bookingDate, "
                + "currency, "
                + "rateAtBookingDate) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, booking.getRenter().getId());
            ps.setInt(2, booking.getAccommodation().getId());
            ps.setDate(3, booking.getStartDate());
            ps.setDate(4, booking.getEndDate());
            ps.setDate(5, booking.getBookingDate());
            ps.setString(6, booking.getRateAtBookingDate().getCurrency().name());
            ps.setBigDecimal(7, booking.getRateAtBookingDate().getAmount());
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

    public static int deleteBooking(int bookingId) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "DELETE FROM booking WHERE id = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, bookingId);
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

    public static Booking selectBooking(int bookingId) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM booking WHERE id = ?  ";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, bookingId);
            rs = ps.executeQuery();
            Booking booking = new Booking();
            if (rs.next()) {
                booking.setId(rs.getInt("id"));
                booking.setStartDate(rs.getDate("startDate"));
                booking.setEndDate(rs.getDate("endDate"));
                booking.setStartElectrReadingLow(rs.getInt("startElectrReadingLow"));
                booking.setEndElectrReadingLow(rs.getInt("endElectrReadingLow"));
                booking.setStartElectrReadingHigh(rs.getInt("startElectrReadingHigh"));
                booking.setEndElectrReadingHigh(rs.getInt("endElectrReadingHigh"));
                booking.setRenter(UserDB.selectRenter(rs.getInt("user_id")));
                booking.setAccommodation(AccommodationDB.selectAccomodation(rs.getInt("accommodation_id")));
            }
            return booking;
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

    public static Boolean hasBooking(User user) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM booking WHERE user_id = ?  ";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, user.getId());
            rs = ps.executeQuery();
            if (rs.next()) {
                return (true);
            }
            return (false);
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

    public static int updateBookingDates(Booking booking) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;

        String query = "UPDATE booking SET "
                + "startDate = ?, "
                + "endDate = ? "
                + "WHERE Id = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setDate(1, booking.getStartDate());
            ps.setDate(2, booking.getEndDate());
            ps.setInt(3, booking.getId());
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

    public static int updateBookingElectrReading(Booking booking) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;

        String query = "UPDATE booking SET "
                + "startElectrReadingLow = ?, "
                + "endElectrReadingLow = ?, "
                + "startElectrReadingHigh = ?, "
                + "endElectrReadingHigh = ? "
                + "WHERE Id = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, booking.getStartElectrReadingLow());
            ps.setInt(2, booking.getEndElectrReadingLow());
            ps.setInt(3, booking.getStartElectrReadingHigh());
            ps.setInt(4, booking.getEndElectrReadingHigh());
            ps.setInt(5, booking.getId());
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

    public static int updateStartElectrReadingLow(Booking booking) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;

        String query = "UPDATE booking SET "
                + "startElectrReadingLow = ? "
                + "WHERE Id = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, booking.getStartElectrReadingLow());
            ps.setInt(2, booking.getId());
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

    public static int updateEndElectrReadingLow(Booking booking) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;

        String query = "UPDATE booking SET "
                + "endElectrReadingLow = ? "
                + "WHERE Id = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, booking.getEndElectrReadingLow());
            ps.setInt(2, booking.getId());
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

    public static int updateStartElectrReadingHigh(Booking booking) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;

        String query = "UPDATE booking SET "
                + "startElectrReadingHigh = ? "
                + "WHERE Id = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, booking.getStartElectrReadingHigh());
            ps.setInt(2, booking.getId());
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

    public static int updateEndElectrReadingHigh(Booking booking) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;

        String query = "UPDATE booking SET "
                + "endElectrReadingHigh = ? "
                + "WHERE Id = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, booking.getEndElectrReadingHigh());
            ps.setInt(2, booking.getId());
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
}
