/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import security.CheckUserId;
import security.PasswordEncryptionService;

/**
 *
 * @author Peter
 */
public class Security {

    private int id;
    private byte[] salt;
    private byte[] encryptedPassword;
    private boolean administrator;
    private Date expirationDate;
    private Timestamp lastUpdate;

    public Security() {
        this.id = 0;
        this.salt = new byte[0];
        this.encryptedPassword = new byte[0];
        this.administrator = false;
        this.expirationDate = new Date(0);
        this.lastUpdate = new Timestamp(0);
    }

    public Security(int id, byte[] salt, byte[] encryptedPassword) {
        this.id = id;
        this.salt = salt;
        this.encryptedPassword = encryptedPassword;
        this.administrator = false;
    }

    public Security(int id, byte[] salt, byte[] encryptedPassword, boolean administrator, Date expirationDate, Timestamp lastUpdate) {
        this.id = id;
        this.salt = salt;
        this.encryptedPassword = encryptedPassword;
        this.administrator = administrator;
        this.expirationDate = expirationDate;
        this.lastUpdate = lastUpdate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public void setEncryptedPassword(byte[] encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public int getId() {
        return id;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setAdministrator(boolean administrator) {
        this.administrator = administrator;
    }

    public boolean isAdministrator() {
        return administrator;
    }

    public byte[] getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setExpirationDate(boolean isTemporary) {
        Calendar date = Calendar.getInstance();
        if (isTemporary) {
            date.add(Calendar.DATE, 5);
        } else {
            date.add(Calendar.YEAR, 5);
        }
        java.util.Date utilDate = date.getTime();
        Date sqlDate = new Date((utilDate).getTime());
        this.setExpirationDate(sqlDate);
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void setLastUpdate() {
        java.util.Date date = new java.util.Date();
        this.setLastUpdate(new Timestamp(date.getTime()));
    }

    public Date getExpirationDate() {
        return this.expirationDate;
    }

    public boolean checkPassword(String passwordToCheck) {
        byte[] encryptedPasswordToCheck = null;
        PasswordEncryptionService pes = new PasswordEncryptionService();
        try {
            encryptedPasswordToCheck = pes.getEncryptedPassword(passwordToCheck, salt);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(CheckUserId.class.getName()).log(
                    Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(CheckUserId.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        if (Arrays.equals(encryptedPassword, encryptedPasswordToCheck)) {
            return (true);
        }
        return (false);
    }

    public boolean checkExpirationDate() {
        Calendar currentDate = Calendar.getInstance();
        Date now = new Date((currentDate.getTime()).getTime());
        if (now.before(expirationDate)) {
            return (true);
        }
        return (false);
    }

    public void setPassword(String newPassword) {
        PasswordEncryptionService pes = new PasswordEncryptionService();
        byte[] newEncryptedPassword = null;
        try {
            salt = pes.generateSalt();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(CheckUserId.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            newEncryptedPassword = pes.getEncryptedPassword(newPassword, salt);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(CheckUserId.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(CheckUserId.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setEncryptedPassword(newEncryptedPassword);
        this.setLastUpdate();
    }

    public String setPassword() {
        String defaultPassword = "temporary";
        String alphaNum = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
//        this will be used in future instead "temporary"
        String autoGeneratedPassword = generateRandomString(alphaNum, 8);
        setPassword(defaultPassword);
        return (defaultPassword);
    }

    public static String generateRandomString(String characters, int length) {
        char[] text = new char[length];
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(random.nextInt(characters.length()));
        }
        return new String(text);
    }
}
