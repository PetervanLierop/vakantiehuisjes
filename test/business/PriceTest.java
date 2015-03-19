/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.math.BigDecimal;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import util.CurrencyEnum;

/**
 *
 * @author Peter
 */
public class PriceTest {

    public PriceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of getCurrency method, of class Price.
     */
    @Ignore("Not Ready to Run")
    @Test
    public void testGetCurrency() {
        System.out.println("getCurrency");
        Price instance = new Price();
        CurrencyEnum expResult = null;
        CurrencyEnum result = instance.getCurrency();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAmount method, of class Price.
     */
    @Ignore("Not Ready to Run")
    @Test
    public void testGetAmount() {
        System.out.println("getAmount");
        Price instance = new Price();
        BigDecimal expResult = null;
        BigDecimal result = instance.getAmount();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCurrency method, of class Price.
     */
    @Ignore("Not Ready to Run")
    @Test
    public void testSetCurrency() {
        System.out.println("setCurrency");
        CurrencyEnum currency = null;
        Price instance = new Price();
        instance.setCurrency(currency);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAmount method, of class Price.
     */
    @Ignore("Not Ready to Run")
    @Test
    public void testSetAmount() {
        System.out.println("setAmount");
        BigDecimal amount = null;
        Price instance = new Price();
        instance.setAmount(amount);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addPrice method, of class Price.
     */
    @Test
    public void testAddPrice() {
        System.out.println("addPrice");
        Price price = new Price(CurrencyEnum.EUR, new BigDecimal(10));
        Price instance = new Price(CurrencyEnum.EUR, new BigDecimal(20));
        Price result = new Price(CurrencyEnum.EUR, new BigDecimal(30));
        instance.addPrice(price);
        assertEquals(instance.getCurrency(), result.getCurrency());
    }

    /**
     * Test of convert method, of class Price.
     */
    @Test
    public void testConvert() {
        System.out.println("convert");
        CurrencyEnum currency = CurrencyEnum.EUR;
        BigDecimal value = new BigDecimal(10);
        Price price = new Price(currency, value);
        CurrencyEnum newCurrency = CurrencyEnum.CHF;
        Price instance = new Price(CurrencyEnum.USD, BigDecimal.ZERO);
        Price convertedPrice = instance.convert(price, newCurrency);
        CurrencyEnum currencyResult = convertedPrice.getCurrency();
        assertEquals(newCurrency, currencyResult);
        
        BigDecimal bigDecimalResult = convertedPrice.getAmount();
        assertFalse(value.compareTo(bigDecimalResult) == 0);
    }

    /**
     * Test of multiplyWithConsumption method, of class Price.
     */
    @Ignore("Not Ready to Run")
    @Test
    public void testMultiplyWithConsumption() {
        System.out.println("multiplyWithConsumption");
        int consumption = 0;
        Price instance = new Price();
        Price expResult = null;
        Price result = instance.multiplyWithConsumption(consumption);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
