/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.sql.Date;

/**
 *
 * @author Peter
 */
public class UserRole {
    private int id;
    private String description;
    private Date lastUpdate;

    public UserRole() {
    }
    
    public UserRole(String description) {
        this.description = description;
        java.util.Date today = new java.util.Date();
        java.sql.Date sqlToday = new java.sql.Date(today.getTime());
        this.lastUpdate = sqlToday;
    }

    public UserRole(int id, String description, Date lastUpdate) {
        this.id = id;
        this.description = description;
        this.lastUpdate = lastUpdate;
    }

    public UserRole(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }
    
}
