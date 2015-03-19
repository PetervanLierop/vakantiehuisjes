/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import business.InvoiceLine;
import java.util.Comparator;

/**
 *
 * @author Peter
 */
public class InvoiceLineComparator implements Comparator<InvoiceLine>   {

    @Override
    public int compare(InvoiceLine o1, InvoiceLine o2) {
        return o1.getStartDate().compareTo(o2.getStartDate());
    }
    
}
