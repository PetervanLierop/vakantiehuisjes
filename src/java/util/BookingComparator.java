/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import business.Booking;
import java.util.Comparator;

/**
 *
 * @author Peter
 */
public class BookingComparator implements Comparator<Booking>  {

    @Override
    public int compare(Booking o1, Booking o2) {
        return o1.getStartDate().compareTo(o2.getStartDate());
    }
    
}
