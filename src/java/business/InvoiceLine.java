/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import data.AccommodationDB;
import data.PowerSupplierDB;
import java.sql.Date;

/**
 *
 * @author Peter
 */
public class InvoiceLine {

    private Accommodation accommodation;
    private Date startDate;
    private Date endDate;
    private int startElectrReadingStandard;
    private int endElectrReadingStandard;
    private int startElectrReadingLow;
    private int endElectrReadingLow;
    private int startElectrReadingHigh;
    private int endElectrReadingHigh;
    private Date bookingDate;
    private Price priceAtBookingDate;
    private Price electicityPrice;
    private Price totalPrice;
    private boolean accountIsMet;

    public InvoiceLine() {
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setStartElectrReadingStandard(int startElectrReadingStandard) {
        this.startElectrReadingStandard = startElectrReadingStandard;
    }

    public void setEndElectrReadingStandard(int endElectrReadingStandard) {
        this.endElectrReadingStandard = endElectrReadingStandard;
    }

    public void setStartElectrReadingLow(int startElectrReadingLow) {
        this.startElectrReadingLow = startElectrReadingLow;
    }

    public void setEndElectrReadingLow(int endElectrReadingLow) {
        this.endElectrReadingLow = endElectrReadingLow;
    }

    public void setStartElectrReadingHigh(int startElectrReadingHigh) {
        this.startElectrReadingHigh = startElectrReadingHigh;
    }

    public void setEndElectrReadingHigh(int endElectrReadingHigh) {
        this.endElectrReadingHigh = endElectrReadingHigh;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public void setPriceAtBookingDate(Price priceAtBookingDate) {
        this.priceAtBookingDate = priceAtBookingDate;
    }

    public void setAccountIsMet(boolean accountIsMet) {
        this.accountIsMet = accountIsMet;
    }

    public void setElecticityPrice() {

        int standardConsumption = endElectrReadingStandard - startElectrReadingStandard;
        int lowConsumption = endElectrReadingLow - startElectrReadingLow;
        int highConsumption = endElectrReadingHigh - startElectrReadingHigh;
        PowerSupplier powerSupplier = PowerSupplierDB.selectPowerSupplier(accommodation.getPowerSupplierId());
        Price costs = new Price();
        if (standardConsumption > 0) {
            Price standardRate = powerSupplier.getStandardRate();
            costs = standardRate.multiplyWithConsumption(standardConsumption);
        }
        if (lowConsumption > 0) {
            Price lowRate = powerSupplier.getLowRate();
            Price lowCosts = lowRate.multiplyWithConsumption(lowConsumption);
            costs.addPrice(lowCosts);

        }
        if (highConsumption > 0) {
            Price highRate = powerSupplier.getHighRate();
            Price highCosts = highRate.multiplyWithConsumption(highConsumption);
            costs.addPrice(highCosts);
        }
        this.electicityPrice = costs;
    }

    public void setElecticityPrice(Price electicityPrice) {
        this.electicityPrice = electicityPrice;
    }

    public Price getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice() {
        this.totalPrice = new Price();
        this.totalPrice.addPrice(this.priceAtBookingDate);
        this.totalPrice.addPrice(this.electicityPrice);
    }

    public void setTotalPrice(Price totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public int getStartElectrReadingStandard() {
        return startElectrReadingStandard;
    }

    public int getEndElectrReadingStandard() {
        return endElectrReadingStandard;
    }

    public int getStartElectrReadingLow() {
        return startElectrReadingLow;
    }

    public int getEndElectrReadingLow() {
        return endElectrReadingLow;
    }

    public int getStartElectrReadingHigh() {
        return startElectrReadingHigh;
    }

    public int getEndElectrReadingHigh() {
        return endElectrReadingHigh;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public Price getPriceAtBookingDate() {
        return priceAtBookingDate;
    }

    public Price getElecticityPrice() {
        return electicityPrice;
    }

    public boolean isAccountIsMet() {
        return accountIsMet;
    }
}
