/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import business.Price;

/**
 *
 * @author Peter
 */
public interface CurrencyConverter {
        /**
     *
     * @param newCurrency
     * @throws Exception
     */
    public Price convert(Price price, CurrencyEnum newCurrency) throws Exception;
    
}
