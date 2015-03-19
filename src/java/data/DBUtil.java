/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author Peter
 */
public class DBUtil
{
    public static void closeStatement(Statement s)
    {
        try
        {
            if (s != null) {
                s.close();
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    public static void closePreparedStatement(Statement ps)
    {
        try
        {
            if (ps != null) {
                ps.close();
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void closeResultSet(ResultSet rs)
    {
        try
        {
            if (rs != null) {
                rs.close();
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    static void closeConnection(Connection connection)
    {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

