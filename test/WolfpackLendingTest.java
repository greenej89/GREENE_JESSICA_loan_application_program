import org.junit.Test;
import junit.framework.TestCase;

/**
 * Program to test WolfpackLending methods
 * @author Suzanne Balik
 * @author Jessica Greene
 */
public class WolfpackLendingTest extends TestCase {
    /** Acceptable margin of error for test cases that produce monetary values */
    public static final double DELTA = .01;
    
    /**
     * Testing isValidDate with valid date (March 20)
     */
    @Test
    public void testIsValidDate1() {
        assertTrue("WolfpackLending.isValidDate(3, 20)", 
                   WolfpackLending.isValidDate(3, 20)); 
    }
    
    /**
     *Testing isValidDate with invalid month assignment 13
     */
    @Test
    public void testIsValidDate2() {
        assertFalse("WolfpackLending.isValidDate(13, 24)", 
                    WolfpackLending.isValidDate(13, 24));
    }
    
    /**
     * Testing isValidDate with invalid date in 29-day month (February 30)
     */
    @Test
    public void testIsValidDateFebruary30() {
        assertFalse("WolfpackLending.isValidDate(2, 30)", 
                    WolfpackLending.isValidDate(2, 30));
    }
    
    /**
     * Testing isValidDate with invalid date in 30-day month (June 31)
     */
    @Test
    public void testIsValidDateJune31() {
        assertFalse("WolfpackLending.isValidDate(6, 31)", 
                   WolfpackLending.isValidDate(6, 31));
    }
    
    /**
     * Testing isValidDate with valid date in 31-day month (July 31)
     */
    @Test
    public void testIsValidDateJuly31() {
        assertTrue("WolfpackLending.isValidDate(7, 31)", 
                   WolfpackLending.isValidDate(7, 31));
    }
    
    /**
     * Testing isValidDate with invalid month assignment (Month is 0)
     */
    @Test
    public void testIsValidDateMonthIsZero() {
        assertFalse("WolfpackLending.isValidDate(0, 31)", 
                   WolfpackLending.isValidDate(0, 31));
    }
    
    /**
     * Testing isValidDate with invalid day assignment (December 32)
     */
    @Test
    public void testIsValidDateMonthHas32Days() {
        assertFalse("WolfpackLending.isValidDate(12, 32)", 
                   WolfpackLending.isValidDate(12, 32));
    }

    /**
     * Testing getInterestRate for special interest rate (5.5%) with mid-range loan amount,
     * good credit (> 720), and low income (< 2 * loan amount)
     */
    @Test
    public void testGetInterestRate1() { 
        assertEquals("WolfpackLending.getInterestRate(2254, 755, 2000)", 5.5,
                     WolfpackLending.getInterestRate(2254, 755, 2000), DELTA);
    }
    
    /**
     * Testing getInterestRate for nonqualifying loan application due to insufficient income 
     * and very poor credit score (< 350)
     */
    @Test
    public void testGetInterestRate2() {
        assertEquals("WolfpackLending.getInterestRate(8967, 300, 0)", -1.0,
                     WolfpackLending.getInterestRate(8967, 300, 0), DELTA);  
    }

    /**
     * Testing getInterestRate for high interest rate (7.5%) with minimum loan amount, 
     * poor credit score boundary (350), and minimum qualifying income if credit < 720
     * (income = 2 * loan amount)
     */
    @Test
    public void testGetInterestRateHighInterestMinLoanPoorCreditMinIncome() {
        assertEquals("WolfpackLending.getInterestRate(1000, 350, 2000)", 7.5,
                     WolfpackLending.getInterestRate(1000, 350, 2000), DELTA);           
    }
    
    /**
     * Testing getInterestRate for moderate interest rate (6.5%) with maximum loan amount, 
     * fair credit score, and minimum income for interest rate (income = 3 * loan amount)
     */
    @Test
    public void testGetInterestRateMedInterestMaxLoanFairCreditMinIncome() {
        assertEquals("WolfpackLending.getInterestRate(10000, 500, 30000)", 6.5,
                     WolfpackLending.getInterestRate(10000, 500, 30000), DELTA);          
    }
    
    /**
     * Testing getInterestRate for low interest rate (6.0%) with mid-range loan amount, 
     * minimum credit score, and minimum income to qualify for a loan if credit score < 350
     * (income = 5 * loan amount)
     */
    @Test
    public void testGetInterestRateLowInterestMedLoanMinCreditHighIncome() {    
        assertEquals("WolfpackLending.getInterestRate(5000, 300, 25000)", 6.0,
                     WolfpackLending.getInterestRate(5000, 300, 25000), DELTA);                  
    }
    
    /**
     * Testing getMonthPayment with mid-range loan amount, 6.5% interest rate, 
     * and 30-month repayment period
     */
    @Test
    public void testGetMonthlyPayment1() { 
        assertEquals("WolfpackLending.getMonthlyPayment(2502, 6.5, 30)", 90.58,
                     WolfpackLending.getMonthlyPayment(2502, 6.5, 30), DELTA);
    }
    
    /**
     * Testing getMonthlyPayment for large loan amount (> $10000) with 8.3% interest rate 
     * and 5-year repayment period
     */
    @Test
    public void testGetMonthlyPayment2() {
        assertEquals("WolfpackLending.getMonthlyPayment(26890, 8.3, 60)", 549.10,
                     WolfpackLending.getMonthlyPayment(26890, 8.3, 60), DELTA); 
    }

    /**
     * Testing getMonthlyPayment for mid-range loan amount with 5.5% interest rate 
     * and 5-year repayment period
     */
    @Test
    public void testGetMonthlyPaymentMedLoanSpecialInterest60Months() {
        assertEquals("WolfpackLending.getMonthlyPayment(5000, 5.5, 60)", 95.51,
                    WolfpackLending.getMonthlyPayment(5000, 5.5, 60), DELTA);
    }
    
    /**
     * Testing getMonthlyPayment for small loan amount with 6.0% interest rate and 5-year 
     * repayment period
     */
    @Test
    public void testGetMonthlyPaymentSmallLoanLowInterest60Months() {
        assertEquals("WolfpackLending.getMonthlyPayment(1040, 6.0, 60)", 20.11,
                    WolfpackLending.getMonthlyPayment(1040, 6.0, 60), DELTA);
    }
    
    /**
     * Testing getMonthlyPayment for mid-range loan amount with 7.5% interest rate and 
     * 5-year repayment period
     */
    @Test
    public void testGetMonthlyPaymentMedLoanHighInterest60Months() {
        assertEquals("WolfpackLending.getMonthlyPayment(2025, 7.5, 60)", 40.58,
                    WolfpackLending.getMonthlyPayment(2025, 7.5, 60), DELTA);
    }

    /**
     * Testing getDisbursementDate for express processing of March application and 
     * same month disbursement
     */
    @Test
    public void testGetDisbursementDate1() {
        assertEquals("WolfpackLending.getDisbursementDate(3, 16, true)", "Thu, 3 19 2020",
                   WolfpackLending.getDisbursementDate(3, 16, true)); 
    }

    /**
     * Testing getDisbursementDate for normal processing of April application and 
     * next month disbursement
     */
    @Test
    public void testGetDisbursementDate2() {
        assertEquals("WolfpackLending.getDisbursementDate(4, 30, false)", "Wed, 5 20 2020",
                   WolfpackLending.getDisbursementDate(4, 30, false));
    }
    
    /**
     * Testing getDisbursementDate for express processing of December application 
     * and next year disbursement
     */    
    @Test
    public void testGetDisbursementDateExpressWith2021DisbursementYear() {
        assertEquals("WolfpackLending.getDisbursementDate(12, 30, true)", "Sat, 1 2 2021",
                   WolfpackLending.getDisbursementDate(12, 30, true));   
    }
    
    /**
     * Testing getDisbursementDate for normal processing of December application 
     * and next year disbursement
     */  
    @Test
    public void testGetDisbursementDateNormalWith2021DisbursementYear() {
        assertEquals("WolfpackLending.getDisbursementDate(12, 12, false)", "Fri, 1 1 2021",
                   WolfpackLending.getDisbursementDate(12, 12, false));
    }
    
    /**
     * Testing getDisbursementDate for express processing of February application 
     * and next month disbursement
     */  
    @Test
    public void testGetDisbursementDateExpressFebAppWithDifferentDisbursementMonth() { 
        assertEquals("WolfpackLending.getDisbursementDate(2, 27, true)", "Sun, 3 1 2020",
                   WolfpackLending.getDisbursementDate(2, 27, true)); 
    }
    
    /**
     * Testing getDisbursementDate for normal processing of October application 
     * and next month disbursement
     */  
    @Test
    public void testGetDisbursementDateNormalOctAppWithDifferentDisbursementMonth() {    
        assertEquals("WolfpackLending.getDisbursementDate(10, 27, false)", "Mon, 11 16 2020",
                   WolfpackLending.getDisbursementDate(10, 27, false));     
    }
    
    /**
     * Testing getDisbursementDate for express processing of January application 
     * and same month disbursement
     */  
    @Test
    public void testGetDisbursementDateExpressJanAppWithSameDisbursementMonth() { 
        assertEquals("WolfpackLending.getDisbursementDate(1, 4, true)", "Tue, 1 7 2020",
                   WolfpackLending.getDisbursementDate(1, 4, true));   
    }

    /**
     * Testing getDisbursementDate for express processing of July application 
     * and next month disbursement
     */      
    @Test
    public void testGetDisbursementDateExpressJulAppWithDifferentDisbursementMonth() {
        
        assertEquals("WolfpackLending.getDisbursementDate(7, 31, true)", "Mon, 8 3 2020",
                   WolfpackLending.getDisbursementDate(7, 31, true));    
    }

    
    /**
     * Test the WolfpackLending methods with invalid values
     */ 
    @Test
    public void testInvalidMethods() {
        // Invalid test cases are provided for you below - You do NOT
        // need to add additional invalid tests. Just make sure these
        // pass!
        try {
            WolfpackLending.getInterestRate(0, 500, 5000);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid loan amount", e.getMessage());
        }

        try {
            WolfpackLending.getInterestRate(1000, 200, 5000);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid credit score", e.getMessage());
        }

        try {
            WolfpackLending.getInterestRate(1000, 500, -20);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid income", e.getMessage());
        }

        try {
            WolfpackLending.getMonthlyPayment(-5, 5.5, 20);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid loan amount", e.getMessage());
        }

        try {
            WolfpackLending.getMonthlyPayment(3000, 0.49, 20);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid interest rate", e.getMessage());
        }

        try {
            WolfpackLending.getMonthlyPayment(4500, 5.5, 0);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid number of months", e.getMessage());
        }
        
        try {
            WolfpackLending.getDisbursementDate(13, 4, true);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid date", e.getMessage());
        }

        try {
            WolfpackLending.getDisbursementDate(4, 31, false);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid date", e.getMessage());
        }   
    }
}
