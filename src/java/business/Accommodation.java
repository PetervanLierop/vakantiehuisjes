/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import data.BookingDB;
import data.PriceDB;
import data.PriceLevelDB;
import data.UserRoleDB;
import java.math.BigDecimal;
import java.sql.Date;
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
public class Accommodation {

    private int id;
    private String name;
    private Address address;
    private double gpsLongitude;
    private double gpsLatitude;
    private double gpsAltitude;
    private Price cleaningCosts;
    private boolean dayAndNightElectricityRates;
    private int powerSupplierId;
    private Map<String, RentalRates> priceLevels;

    public Accommodation() {
        id = 0;
        name = "";
        gpsLongitude = 0;
        gpsLatitude = 0;
        cleaningCosts = new Price();
        priceLevels = new LinkedHashMap<String, RentalRates>();
    }

    public boolean hasDayAndNightElectricityRates() {
        return dayAndNightElectricityRates;
    }

    public void setDayAndNightElectricityRates(boolean dayAndNightElectricityRates) {
        this.dayAndNightElectricityRates = dayAndNightElectricityRates;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Accommodation(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGpsLongitude(double gpsLongitude) {
        this.gpsLongitude = gpsLongitude;
    }

    public void setGpsLatitude(double gpsLatitude) {
        this.gpsLatitude = gpsLatitude;
    }

    public void setCleaningCosts(Price cleaningCosts) {
        this.cleaningCosts = cleaningCosts;
    }

    public void setPowerSupplierId(int powerSupplierId) {
        this.powerSupplierId = powerSupplierId;
    }

    public Map<String, RentalRates> getPriceLevels() {
        return priceLevels;
    }

    public void setPriceLevels() {
        this.priceLevels = PriceDB.selectPriceLevels(id);
    }

    public void setPriceLevels(Map<String, RentalRates> priceLevels) {
        this.priceLevels = priceLevels;
    }

    public void setPriceLevels(String userRoleDescription, RentalRates rentalRates) {
        this.priceLevels.put(userRoleDescription, rentalRates);
    }

    public int getId() {
        return id;
    }

    public double getGpsAltitude() {
        return gpsAltitude;
    }

    public void setGpsAltitude(double gpsAltitude) {
        this.gpsAltitude = gpsAltitude;
    }

    public String getName() {
        return name;
    }

    public double getGpsLongitude() {
        return gpsLongitude;
    }

    public double getGpsLatitude() {
        return gpsLatitude;
    }

    public Price getCleaningCosts() {
        return cleaningCosts;
    }

    public boolean isDayAndNightElectricityRates() {
        return dayAndNightElectricityRates;
    }

    public int getPowerSupplierId() {
        return powerSupplierId;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + (this.name != null ? this.name.hashCode() : 0);
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
        final Accommodation other = (Accommodation) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    public boolean isAvailable(Date startDate, Date endDate) {
        List<Booking> bookings = BookingDB.selectAllBookings(this.id);
        return (checkAvailability(startDate, endDate, bookings));
    }

    /**
     *
     * @param startDate
     * @param endDate
     * @param bookings
     * @return
     */
    public boolean checkAvailability(
            Date startDate,
            Date endDate,
            List<Booking> bookings) {
        Iterator<Booking> iterator = bookings.iterator();
        while (iterator.hasNext()) {
            Booking booking = iterator.next();
            if (startDate.compareTo(booking.getEndDate()) < 0
                    && endDate.compareTo(booking.getStartDate()) > 0) {
                return (false);
            }
        }
        return (true);
    }

    public void removePriceLevel(PriceLevel priceLevel) {
        for (String userRoleDescription : priceLevels.keySet()) {
            List<PeriodPrice> periodPrices = priceLevels.get(userRoleDescription).getPeriodPrices();
            Iterator<PeriodPrice> iterator = periodPrices.iterator();
            boolean found = false;
            while (!found && iterator.hasNext()) {
                PeriodPrice periodPrice = iterator.next();
                if (priceLevel.equals(periodPrice.getPriceLevel())) {
                    iterator.remove();
                    found = true;
                }
            }
        }
        PriceDB.deleteAccommodationPriceLevel(priceLevel, this);
    }

    public void removePriceLevel(String priceLevelDescription) {
        PriceLevel priceLevel = PriceLevelDB.selectPriceLevel(priceLevelDescription);
        removePriceLevel(priceLevel);
    }

    public List<String> selectNotUsedPriceLevelDescriptions() {
        List<String> toSelectPriceLevelDescriptions = PriceDB.selectAllPriceLevelDescriptions();
        List<String> usedPriceLevelDescriptions = getAllPriceLevelDescriptions();
        for (String usedPriceLevelDescription : usedPriceLevelDescriptions) {
            toSelectPriceLevelDescriptions.remove(usedPriceLevelDescription);
        }
        return toSelectPriceLevelDescriptions;
    }

    public void updateRentalRateCurrency(String userRoleDescription, String priceLevelDescription, CurrencyEnum priceCurrency) {
        priceLevels.get(userRoleDescription).setPeriodPrices(priceLevelDescription, priceCurrency);
        PriceLevel priceLevel = PriceLevelDB.selectPriceLevel(priceLevelDescription);
        UserRole userRole = UserRoleDB.selectUserRole(userRoleDescription);
        if (PriceDB.rentalRatePriceExists(
                userRole,
                priceLevel,
                this)) {
            PriceDB.updateRentalRatePriceCurrency(
                    priceCurrency,
                    userRole,
                    priceLevel,
                    this);
        } else {
            PriceDB.insertRentalRatePriceCurrency(
                    priceCurrency,
                    userRole,
                    priceLevel,
                    this);
        }
    }

    public void updateRentalRateAmount(String userRoleDescription, String priceLevelDescription, BigDecimal priceAmount) {
        priceLevels.get(userRoleDescription).setPeriodPrices(priceLevelDescription, priceAmount);
        PriceLevel priceLevel = PriceLevelDB.selectPriceLevel(priceLevelDescription);
        UserRole userRole = UserRoleDB.selectUserRole(userRoleDescription);
        if (PriceDB.rentalRatePriceExists(
                userRole,
                priceLevel,
                this)) {
            PriceDB.updateRentalRatePriceAmount(
                    priceAmount,
                    userRole,
                    priceLevel,
                    this);
        } else {
            PriceDB.insertRentalRatePriceAmount(
                    priceAmount,
                    userRole,
                    priceLevel,
                    this);
        }
    }

    public boolean addBooking(Renter renter, Date startDate, Date endDate) {
        boolean bookingIsOK;
        UserRole userRole = renter.getUserRole();
        RentalRates rentalRates = priceLevels.get(userRole.getDescription());
        Price price = rentalRates.getPrice(startDate, endDate);
        Booking booking = new Booking(renter, this,
                startDate, endDate, price);
        int insertBooking = BookingDB.insertBooking(booking);
        if (insertBooking == 1) {
            bookingIsOK = true;
        } else {
            bookingIsOK = false;
        }
        return bookingIsOK;
    }

    public List<String> getAllPriceLevelDescriptions() {
//        all rental rates have same pricelevels, so first one is OK 
        Map.Entry<String, RentalRates> entry = priceLevels.entrySet().iterator().next();
        RentalRates rentalRates = entry.getValue();
        List<PeriodPrice> periodPrices = rentalRates.getPeriodPrices();
        List<String> allPriceLevelDescriptions = new LinkedList<String>();
        for (PeriodPrice periodPrice : periodPrices) {
            allPriceLevelDescriptions.add(periodPrice.getPriceLevel().getDescription());
        }
        return allPriceLevelDescriptions;
    }

    public void setPriceLevel(PriceLevel priceLevel) {
        for (String userRoleDescription : priceLevels.keySet()) {
            List<PeriodPrice> periodPrices = priceLevels.get(userRoleDescription).getPeriodPrices();
            Iterator<PeriodPrice> iterator = periodPrices.iterator();
            boolean found = false;
            while (!found && iterator.hasNext()) {
                PeriodPrice periodPrice = iterator.next();
                if (priceLevel.equals(periodPrice.getPriceLevel())) {
                    periodPrice.setPriceLevel(priceLevel);
                }
            }
        }
    }
}
