/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import data.UserDB;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Peter
 */
public class User {

    private int id;
    private String inlogName;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private Security security;
    private UserRole userRole;
    private boolean administrator;
    private Date lastUpdate;

    public User() {
        id = 0;
        inlogName = "";
        firstName = "";
        lastName = "";
        emailAddress = "";
        security = new Security();
        userRole = new UserRole();
    }

    public User(int id, String inlogName, String firstName, String lastName,
            String emailAddress, Security security, UserRole userRole, boolean administrator, Date LastUpdate) {
        this.id = id;
        this.inlogName = inlogName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.security = security;
        this.userRole = userRole;
        this.administrator = administrator;
        this.lastUpdate = LastUpdate;
    }

    public User(String inlogName, String firstName, String lastName,
            String emailAddress, Security security, boolean administrator,UserRole userRole) {
        this.inlogName = inlogName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.security = security;
        this.userRole = userRole;
        this.administrator = administrator;
        java.util.Date today = new java.util.Date();
        java.sql.Date sqlToday = new java.sql.Date(today.getTime());
        this.lastUpdate = sqlToday;
    }

    public User(String inlogName) {
        this.inlogName = inlogName;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void setInlogName(String inlogName) {
        this.inlogName = inlogName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public int getId() {
        return id;
    }

    public String getInlogName() {
        return inlogName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public Security getSecurity() {
        return security;
    }

    public void setSecurity(Security security) {
        this.security = security;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
//        UserDB.updateUserRole(this);   probleem draaien remote server 8/5/2014
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public boolean isAdministrator() {
        return administrator;
    }

    public void setAdministrator(boolean administrator) {
        this.administrator = administrator;
//        UserDB.updateAdministrator(this);   probleem draaien remote server 8/5/2014
    }
    
    public List<User> getSelectableUsers() {
        List<User> selectableUsers = new ArrayList<User>();
        if (this.getSecurity().isAdministrator()) {
            selectableUsers = UserDB.selectAllUsers();
        } else {
            selectableUsers.add(this);
        }
        return (selectableUsers);
    }
    
    @Override
    public String toString() {
        return "User{" + "inlogName=" + inlogName + ", firstName="
                + firstName + ", lastName=" + lastName + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
}
