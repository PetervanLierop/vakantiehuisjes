/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author Peter
 */
 public enum CurrencyEnum {
        EUR("EUR"), CHF("CHF"), USD("USD");
        private String currency;

    private CurrencyEnum(String currency) {
        this.currency = currency;
    }
        
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

};  


