/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.sql.Date;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Peter
 */
public class AccommodationTest {
    
    public AccommodationTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }


    /**
     * Test of checkAvailability method, of class Accommodation.
     */
    @Test
    public void testCheckAvailability() {
        System.out.println("checkAvailability");
        Date startDate = null;
        Date endDate = null;
        List<Booking> bookings = null;
        Booking booking = null;
        booking = new Booking(1, startDate, endDate);
        Accommodation instance = new Accommodation();
        boolean expResult = false;
        boolean result = instance.checkAvailability(startDate, endDate, bookings);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }




}
