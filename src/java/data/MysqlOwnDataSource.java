/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Peter
 */
public class MysqlOwnDataSource extends MysqlDataSource {

    public MysqlOwnDataSource() {
        this.setServerName("localhost");
        this.setPort(3306);
//        this.setDatabaseName("web482_vakantiehuisje");
//        this.setUser("web482_root");
//        this.setPassword("September13#");
        this.setDatabaseName("vakantiehuisje");
        this.setUser("root");
        this.setPassword("");
    }

    public Connection getOwnConnection() {
        Connection connection = null;
        try {
            connection = this.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(MysqlOwnDataSource.class.getName()).log(Level.SEVERE, null, ex);
        }

        return connection;
    }
}
