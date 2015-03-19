/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import data.PriceLevelDB;
import java.io.Serializable;

/**
 *
 * @author Peter
 */
public class PriceLevel implements Serializable {

    int id;
    String description;
    int startWeekNumber;
    int endWeekNumber;
    int priority;

    public PriceLevel() {
    }

    public PriceLevel(String description, int startWeekNumber, int endWeekNumber, int priority) {
        this.description = description;
        this.startWeekNumber = startWeekNumber;
        this.endWeekNumber = endWeekNumber;
        this.priority = priority;
    }

    public PriceLevel(int id, String description, int startWeekNumber, int endWeekNumber, int priority) {
        this.id = id;
        this.description = description;
        this.startWeekNumber = startWeekNumber;
        this.endWeekNumber = endWeekNumber;
        this.priority = priority;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String Description) {
        this.description = Description;
        PriceLevelDB.updatePriceLevelDescription(this);
    }

    public void setStartWeekNumber(int startWeekNumber) {
        this.startWeekNumber = startWeekNumber;
        PriceLevelDB.updatePriceLevelStartWeekNumber(this);
    }

    public void setEndWeekNumber(int endWeekNumber) {
        this.endWeekNumber = endWeekNumber;
        PriceLevelDB.updatePriceLevelEndWeekNumber(this);
    }

    public void setPriority(int priority) {
        this.priority = priority;
        PriceLevelDB.updatePriceLevelPriority(this);
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getStartWeekNumber() {
        return startWeekNumber;
    }

    public int getEndWeekNumber() {
        return endWeekNumber;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.id;
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
        final PriceLevel other = (PriceLevel) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
}
