/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import data.BookingDB;
import java.sql.Date;

/**
 *
 * @author Peter
 */
public class Booking {

    private int id;
    Renter renter;
    Accommodation accommodation;
    private Date startDate;
    private Date endDate;
    private int startElectrReadingStandard;
    private int endElectrReadingStandard;
    private int startElectrReadingLow;
    private int endElectrReadingLow;
    private int startElectrReadingHigh;
    private int endElectrReadingHigh;
    private Date bookingDate;
    private Price rateAtBookingDate;

    public Booking() {
        java.util.Date now = new java.util.Date();
        this.bookingDate = new java.sql.Date(now.getTime());
    }

    public Booking(Renter renter, Accommodation accommodation, Date startDate, Date endDate, Price rate) {
        this.renter = renter;
        this.accommodation = accommodation;
        this.startDate = startDate;
        this.endDate = endDate;
        java.util.Date now = new java.util.Date();
        this.bookingDate = new java.sql.Date(now.getTime());
        this.rateAtBookingDate = rate;
    }

    public Booking(int id, Date startDate, Date endDate) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRenter(Renter renter) {
        this.renter = renter;
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
        if (this.startElectrReadingLow != startElectrReadingLow) {
            this.startElectrReadingLow = startElectrReadingLow;
            int updateStartElectrReadingLow =
                    BookingDB.updateStartElectrReadingLow(this);
        }
    }

    public void setEndElectrReadingLow(int endElectrReadingLow) {
        if (this.endElectrReadingLow != endElectrReadingLow) {
            this.endElectrReadingLow = endElectrReadingLow;
            int updateEndElectrReadingLow =
                    BookingDB.updateEndElectrReadingLow(this);
        }
    }

    public void setStartElectrReadingHigh(int startElectrReadingHigh) {
        if (this.startElectrReadingHigh != startElectrReadingHigh) {
            this.startElectrReadingHigh = startElectrReadingHigh;
            int updateStartElectrReadingHigh =
                    BookingDB.updateStartElectrReadingHigh(this);
        }
    }

    public void setEndElectrReadingHigh(int endElectrReadingHigh) {
        if (this.endElectrReadingHigh != endElectrReadingHigh) {
            this.endElectrReadingHigh = endElectrReadingHigh;
            int updateEndElectrReadingHigh =
                    BookingDB.updateEndElectrReadingHigh(this);
        }
    }

    public int getId() {
        return id;
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

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Renter getRenter() {
        return renter;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public Price getRateAtBookingDate() {
        return rateAtBookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public void setRateAtBookingDate(Price rateAtTimeBookingDate) {
        this.rateAtBookingDate = rateAtTimeBookingDate;
    }
}
