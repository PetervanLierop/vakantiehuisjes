/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import data.UserDB;

/**
 *
 * @author Peter
 */
public class Renter extends User {

    public String getRenterName(User currentUser) {
        if (currentUser.getSecurity().isAdministrator() || this.equals(currentUser)) {
            return (this.getFirstName() + " " + this.getLastName());
        }
        return ("********");
    }
}
