/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.math.BigDecimal;

/**
 *
 * @author Peter
 */
public class PowerSupplier {
    String name;
    Price standardRate;
    Price lowRate;
    Price highRate;

    public PowerSupplier() {
        this.name = "";
        this.standardRate =  new Price();
        this.lowRate =  new Price();
        this.highRate =  new Price();
    }

    public PowerSupplier(String name, Price standardRate, Price lowRate, Price highRate) {
        this.name = name;
        this.standardRate = standardRate;
        this.lowRate = lowRate;
        this.highRate = highRate;
    }

    public String getName() {
        return name;
    }

    public Price getStandardRate() {
        return standardRate;
    }

    public Price getLowRate() {
        return lowRate;
    }

    public Price getHighRate() {
        return highRate;
    }

}
