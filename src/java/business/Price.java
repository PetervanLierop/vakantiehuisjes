/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import util.CurrencyConverter;
import util.CurrencyEnum;

/**
 *
 * @author Peter
 */
public class Price implements CurrencyConverter, Serializable{

    private CurrencyEnum currency;
    private BigDecimal amount;

    public Price() {
        this.currency = CurrencyEnum.EUR;
        this.amount = new BigDecimal(0.0);
    }

    public Price(CurrencyEnum currency) {
        this.currency = currency;
    }

    public Price(BigDecimal amount) {
        this.amount = amount;
    }

    public Price(CurrencyEnum currency, BigDecimal amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public CurrencyEnum getCurrency() {
        return currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setCurrency(CurrencyEnum currency) {
        this.currency = currency;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    void addPrice(Price price) {
        if (BigDecimal.ZERO.equals(this.amount)) {
            this.currency = price.getCurrency();
            this.amount = price.getAmount();
        } else {
            Price priceToAdd;
            if (!this.currency.equals(price.getCurrency())) {
                priceToAdd = convert(price, this.currency);
            } else {
                priceToAdd = price;
            }
            this.amount = this.amount.add(priceToAdd.getAmount());
        }
    }

    @Override
    public Price convert(Price price, CurrencyEnum newCurrency) {
        Price convertedPrice = new Price();
        BigDecimal conversionRate = null;
        String result = "";
        String line;
        try {
            URL url = new URL("http://quote.yahoo.com/d/quotes.csv?s=" + price.getCurrency()
                    + newCurrency + "=X&f=l1&e=.csv");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result += line;
            }
            rd.close();
            conversionRate = new BigDecimal(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        convertedPrice.setCurrency(newCurrency);
        BigDecimal convertedAmount = price.getAmount().multiply(conversionRate);
        BigDecimal roundedAmount = convertedAmount.setScale(2, RoundingMode.HALF_UP);
        convertedPrice.setAmount(roundedAmount);
        return convertedPrice;
    }

    public Price multiplyWithConsumption(int consumption) {
        Price consumptionPrice = new Price();
        consumptionPrice.setCurrency(currency);
        consumptionPrice.setAmount(amount.multiply(new BigDecimal(consumption)));
        return consumptionPrice;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + (this.currency != null ? this.currency.hashCode() : 0);
        hash = 13 * hash + (this.amount != null ? this.amount.hashCode() : 0);
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
        final Price other = (Price) obj;
        if (this.currency != other.currency) {
            return false;
        }
        if (this.amount != other.amount && (this.amount == null || !this.amount.equals(other.amount))) {
            return false;
        }
        return true;
    }
    
    
}
