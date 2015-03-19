/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import data.PriceLevelDB;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import util.CurrencyEnum;

/**
 *
 * @author Peter
 */
public class RentalRates  implements Serializable  {

    private String description;
    private List<PeriodPrice> periodPrices;

    public RentalRates() {
        this.description = "";
        this.periodPrices = new LinkedList<PeriodPrice>();
    }

    public RentalRates(String description) {
        this.description = description;
        this.periodPrices = new LinkedList<PeriodPrice>();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPeriodPrices(String priceLevelDescription, BigDecimal amount) {
        boolean found = false;
        int j = 0;
        while (j < periodPrices.size() && !found) {
            if (priceLevelDescription.equals(periodPrices.get(j).getPriceLevel().getDescription())) {
                periodPrices.get(j).getPrice().setAmount(amount);
                found = true;
            }
            j++;
        }
        if (!found) {
            PriceLevel priceLevel = PriceLevelDB.selectPriceLevel(priceLevelDescription);
            Price price = new Price(amount);
            PeriodPrice periodPrice = new PeriodPrice(priceLevel, price);
            periodPrices.add(periodPrice);
        }
    }

    public void setPeriodPrices(String priceLevelDescription, CurrencyEnum currency) {
        boolean found = false;
        int j = 0;
        while (j < periodPrices.size() && !found) {
            if (priceLevelDescription.equals(periodPrices.get(j).getPriceLevel().getDescription())) {
                periodPrices.get(j).getPrice().setCurrency(currency);
                found = true;
            }
            j++;
        }
        if (!found) {
            PriceLevel priceLevel = PriceLevelDB.selectPriceLevel(priceLevelDescription);
            Price price = new Price(currency);
            PeriodPrice periodPrice = new PeriodPrice(priceLevel, price);
            periodPrices.add(periodPrice);
        }
    }

    public void setPeriodPrices(String priceLevelDescription, Price price) {
        boolean found = false;
        int j = 0;
        while (j < periodPrices.size() && !found) {
            if (priceLevelDescription.equals(periodPrices.get(j).getPriceLevel().getDescription())) {
                periodPrices.get(j).setPrice(price);
                found = true;
            }
            j++;
        }
        if (!found) {
            PriceLevel priceLevel = PriceLevelDB.selectPriceLevel(priceLevelDescription);
            addPeriodPrices(priceLevel, price);
        }
    }

    public void addPeriodPrices(PriceLevel priceLevel, Price price) {
        PeriodPrice periodPrice = new PeriodPrice(priceLevel, price);
        this.periodPrices.add(periodPrice);
    }

    public String getDescription() {
        return description;
    }

    public List<PeriodPrice> getPeriodPrices() {
        return periodPrices;
    }

    public Price getPrice(Date startDate, Date endDate) {
        Calendar startDateCalendar = Calendar.getInstance();
        startDateCalendar.setTime(startDate);
        int startWeek = startDateCalendar.get(Calendar.WEEK_OF_YEAR);
        Calendar endDateCalendar = Calendar.getInstance();
        endDateCalendar.setTime(endDate);
        int endWeek = endDateCalendar.get(Calendar.WEEK_OF_YEAR);
        int startDateDayOfWeek = startDateCalendar.get(Calendar.DAY_OF_WEEK);
        int endDateDayOfWeek = endDateCalendar.get(Calendar.DAY_OF_WEEK);
        int numberOfDaysBookedInWeek;

        Price price = new Price();
        Map<Integer, Integer> bookedDaysInWeek = new LinkedHashMap<Integer, Integer>();
        if (startWeek <= endWeek) {
            int i = startWeek;
            while (i <= endWeek) {
                numberOfDaysBookedInWeek = 7;
                if (i == startWeek) {
                    numberOfDaysBookedInWeek = numberOfDaysBookedInWeek - startDateDayOfWeek;
                }
                if (i == endWeek) {
                    numberOfDaysBookedInWeek = endDateDayOfWeek - (7 - numberOfDaysBookedInWeek);
                }
                if (numberOfDaysBookedInWeek > 0) {
                    bookedDaysInWeek.put(i, numberOfDaysBookedInWeek);
                }
                i++;
            }
        } else {
            int i = 1;
            while (i <= endWeek) {
                numberOfDaysBookedInWeek = 7;
                if (i == endWeek) {
                    numberOfDaysBookedInWeek = endDateDayOfWeek;
                }
                bookedDaysInWeek.put(i, numberOfDaysBookedInWeek);
                i++;
            }
            i = startWeek;
            int numberOfWeeksinYear = startDateCalendar.getActualMaximum(Calendar.WEEK_OF_YEAR);
            while (i <= numberOfWeeksinYear) {
                numberOfDaysBookedInWeek = 7;
                if (i == startWeek) {
                    numberOfDaysBookedInWeek = numberOfDaysBookedInWeek - startDateDayOfWeek;
                }
                if (numberOfDaysBookedInWeek > 0) {
                    bookedDaysInWeek.put(i, numberOfDaysBookedInWeek);
                }
                i++;
            }
        }
        Iterator iterator = bookedDaysInWeek.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            int bookedWeek = (Integer) entry.getKey();
            numberOfDaysBookedInWeek = (Integer) entry.getValue();
            boolean found = false;
            int j = periodPrices.size() - 1;
//            start with last periodPrice(j) this has highest priority, highest index = length - 1 
            while (j >= 0 && !found) {
                int startCatalogWeek = periodPrices.get(j).getPriceLevel().getStartWeekNumber();
                int endCatalogWeek = periodPrices.get(j).getPriceLevel().getEndWeekNumber();
                if ((startCatalogWeek <= endCatalogWeek
                        && (startCatalogWeek <= bookedWeek
                        && endCatalogWeek >= bookedWeek))
                        || (startCatalogWeek > endCatalogWeek
                        && (startCatalogWeek <= bookedWeek
                        || endCatalogWeek >= bookedWeek))) {
                    Price weekPrice = periodPrices.get(j).getPrice(numberOfDaysBookedInWeek);
                    price.addPrice(weekPrice);
                    found = true;
                }
                j--;
            }
        }

        return price;
    }
}
