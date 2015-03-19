/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import business.PowerSupplier;
import business.Price;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.CurrencyEnum;

/**
 *
 * @author Peter
 */
public class PowerSupplierDB {
    
    public static PowerSupplier selectPowerSupplier(int id) {
  
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
        MysqlOwnDataSource ds = new MysqlOwnDataSource();
        Connection connection = ds.getOwnConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String query = "SELECT * FROM powersupplier WHERE id = ? ";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            PowerSupplier powerSupplier = null;
            while (rs.next())
            {
                String name = rs.getString("name");
                CurrencyEnum currency = CurrencyEnum.valueOf(rs.getString("currency"));
                BigDecimal amount = rs.getBigDecimal("standardRate");
                Price standardRate = new Price(currency, amount);
                amount = rs.getBigDecimal("lowRate");
                Price lowRate = new Price(currency, amount);
                amount = rs.getBigDecimal("highRate");
                Price highRate = new Price(currency, amount);
                powerSupplier = new PowerSupplier(name, standardRate, lowRate, highRate);
            }
            return powerSupplier;
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }        
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeConnection(connection);
//            pool.freeConnection(connection);
        }
    }    
}
