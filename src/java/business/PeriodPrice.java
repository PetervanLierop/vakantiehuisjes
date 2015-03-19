/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author Peter
 */
public class PeriodPrice implements Serializable {
 
    private PriceLevel priceLevel;
    private Price price;

    public PeriodPrice(PriceLevel priceLevel, Price price) {
        this.priceLevel = priceLevel;
        this.price = price;
    }

    public PeriodPrice() {
        this.priceLevel = new PriceLevel();
        this.price = new Price();
    }

    public PriceLevel getPriceLevel() {
        return priceLevel;
    }

    public Price getPrice() {
        return price;
    }

    public void setPriceLevel(PriceLevel priceLevel) {
        this.priceLevel = priceLevel;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Price getPrice(Integer numberOfDays) {
        if (numberOfDays < 7) {
            BigDecimal newAmount = price.getAmount().multiply(
                    new BigDecimal(numberOfDays)).divide(new BigDecimal(7), 2, RoundingMode.HALF_UP);
            Price newPrice = new Price(price.getCurrency(), newAmount);
            return newPrice;
        } else {
            return price;
        }
    }
}
