/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import data.BookingDB;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Peter
 */
public class Invoice {

    private int renterId;
    private Date invoiceDate;
    private Price totalRentalPrice;
    private Price electicityPrice;
    private Price totalPrice;
    private List<InvoiceLine> invoiceLines;

    public Invoice() {
        invoiceLines = new ArrayList<InvoiceLine>();
    }

    public Invoice(int renterId) {
        this.renterId = renterId;
        java.util.Date now = new java.util.Date();
        this.invoiceDate = new java.sql.Date(now.getTime());
        this.invoiceLines = BookingDB.selectAllInvoiceLines(renterId);
        this.totalRentalPrice = new Price();
        this.electicityPrice = new Price();
        this.totalPrice = new Price();
        for (InvoiceLine invoiceLine : invoiceLines){
            this.totalRentalPrice.addPrice(invoiceLine.getPriceAtBookingDate());
            this.electicityPrice.addPrice(invoiceLine.getElecticityPrice());
            this.totalPrice.addPrice(invoiceLine.getTotalPrice());
        }
    }

    public void setRenterId(int renterId) {
        this.renterId = renterId;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public void setInvoiceLines(List<InvoiceLine> invoiceLines) {
        this.invoiceLines = invoiceLines;
    }

    public void setElecticityPrice(Price electicityPrice) {
        this.electicityPrice = electicityPrice;
    }

    public void setTotalRentalPrice(Price totalRentalPrice) {
        this.totalRentalPrice = totalRentalPrice;
    }

    public Price getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Price totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getRenterId() {
        return renterId;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public List<InvoiceLine> getInvoiceLines() {
        return invoiceLines;
    }

    public Price getElecticityPrice() {
        return electicityPrice;
    }

    public Price getTotalRentalPrice() {
        return totalRentalPrice;
    }
    
}
