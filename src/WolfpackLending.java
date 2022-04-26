import java.util.*;

/**
 * Determines whether a 5-year loan is approved and the loan interest rate based on user   
 * input for the loan amount, the borrower's credit score, the borrower's 2019 income, and  
 * whether the borrower would like express (3-day) or normal (20-day) processing. If the
 * loan is approved, outputs the loan amount, interest rate, monthly payment, and the 
 * loan disbursement date.
 * The program uses a monthly payment calculation (Source: http://en.wikipedia.org/wiki/Loan)
 * The program uses Zeller's algorithm (Source: Sedgewick R & Wayne K. Introduction to 
 * Programming in Java) to determine the day of the week for a given month, day, and year. 
 *
 * @author Jessica Greene
 */
public class WolfpackLending {
    //Loan Application Constants
    
    /** Year in which user applied for loan */
    public static final int APPLICATION_YEAR = 2020;
    
    /** Minimum loan amount */
    public static final int MIN_LOAN = 1000;
    
    /** Maximum loan amount */
    public static final int MAX_LOAN = 10000;
    
    /** Maximum credit score */
    public static final int MAX_CREDIT_SCORE = 850;
    
    /** Minimum credit score for automatic approval at 5.5% interest rate */
    public static final int GOOD_CREDIT_SCORE = 720;
    
    /** Minimum credit score for approval at 6.5% interest rate */
    public static final int FAIR_CREDIT_SCORE = 500;
    
    /** Minimum credit score for approval at 7.5% interest rate */
    public static final int POOR_CREDIT_SCORE = 350;
    
    /** Minimum credit score */
    public static final int MIN_CREDIT_SCORE = 300;
    
    /** Income to debt ratio to qualify for low interest loan if credit score < 720 */
    public static final int HIGH_INCOME_TO_DEBT_RATIO = 5;
    
    /** Income to debt ratio to qualify for moderate interest loan if credit score < 720 */
    public static final int MODERATE_INCOME_TO_DEBT_RATIO = 3;
    
    /** Income to debt ratio to qualify for high interest loan if credit score < 720 */
    public static final int LOW_INCOME_TO_DEBT_RATIO = 2;
    
    
    //Approved Loan Interest Rate and Fee Constants
    
    /** Special interest rate (5.5%) expressed as a percentage */
    public static final double SPECIAL_INTEREST_RATE = 5.5;
    
    /** Low interest rate (6.0%) expressed as a percentage */
    public static final double LOW_INTEREST_RATE = 6.0;
    
    /** Moderate interest rate (6.5%) expressed as a percentage */
    public static final double MODERATE_INTEREST_RATE = 6.5;
    
    /** High interest rate (7.5%) expressed as a percentage */
    public static final double HIGH_INTEREST_RATE = 7.5;
    
    /** Annual interest rate minimum */
    public static final double MIN_INTEREST_RATE = 0.5;
    
    /** Fee for express processing */
    public static final int EXPRESS_PROCESSING_FEE = 25;
    
    
    //Loan Disbursement and Repayment Constants
    
    /** Number of days until disbursement for express processing */
    public static final int DAYS_FOR_EXPRESS_PROCESSING = 3;
    
    /** Number of days until disbursement for normal processing */
    public static final int DAYS_FOR_NORMAL_PROCESSING = 20;
    
    /** Number of months until loan repayment in 5 years */
    public static final int NUMBER_OF_MONTHS = 60;
    
    
    //Time Constants
    
    /** Number of years in a century */
    public static final int YEARS_IN_CENTURY = 100;
    
    /** Number of years between leap years */
    public static final int LEAP_YEAR_FREQUENCY = 4;
    
    /** Number of days in a week */
    public static final int DAYS_IN_WEEK = 7;
    
    /** Number of days in Feb in a leap year */
    public static final int DAYS_IN_FEB_OF_LEAP_YEAR = 29;
    
    /** Number of days in Apr, Jun, Sep, and Nov */
    public static final int DAYS_IN_SHORT_MONTH = 30;
    
    /** Number of days in Jan, Mar, May, Jul, Aug, Oct, and Dec */
    public static final int DAYS_IN_LONG_MONTH = 31;
    
    /** Number of months in a year */
    public static final int MONTHS_IN_YEAR = 12;
    
    
    // Month Number Constants
    
    /** Month number of January */
    public static final int JAN = 1;
    
    /** Month number of February */
    public static final int FEB = 2;
    
    /** Month number of April */
    public static final int APR = 4;
    
    /** Month number of June */
    public static final int JUN = 6;
    
    /** Month number of September */
    public static final int SEP = 9;
    
    /** Month number of November */
    public static final int NOV = 11;
    
    /** Month number of December */
    public static final int DEC = 12;
    
    
    // Day of the Week Constants
    /** Sunday day of the week */
    public static final int SUN = 0;

    /** Monday day of the week */
    public static final int MON = 1;    

    /** Tuesday day of the week */
    public static final int TUES = 2;
    
    /** Wednesday day of the week */
    public static final int WED = 3;
    
    /** Thursday day of the week */
    public static final int THU = 4;
    
    /** Friday day of the week */
    public static final int FRI = 5;
    
    /** Saturday day of the week */
    public static final int SAT = 6;
    
    /**
     * Starts program, displays program header, and gives instructions to user
     * Prints error message and exits program if user enters invalid input
     * @param args command line arguments (not used)
     * 
     */
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        
        System.out.println();
        System.out.println("                  Welcome to Wolfpack Lending!\n" +
                        "Applications for loans from $1000 to $10000 will be accepted from\n" +
                        "January 1 to December 31, 2020. All loans will be paid back over a\n" +
                        "5 year period. When prompted, please enter today's date, your credit\n" +
                        "score, your 2019 income, and the loan amount. Loans are normally paid\n" +
                        "out 20 days after the application date, but for a fee of $25, which\n" +
                        "will be added to the loan amount, you may request Express Processing\n" +
                        "in which case the loan will be paid out 3 days after the application\n" +
                        "date. If your loan is approved, the loan amount, interest rate,\n" +
                        "disbursement date, and monthly payment amount will be output.\n");
            
        String inputValidation = userInterface(console);
        if (!inputValidation.equals("valid input")) {
            System.out.println(inputValidation);
        }
    }
    
    /**
     * Gets and validates the user input for credit score, income, loan amount, and 
     * processing type. Returns an error message causing program to quit if the user enters 
     * an invalid date, an invalid credit score, a negative income, or an invalid loan amount.
     * Prints loan amount, interest rate, monthly payment, and disbursement date to console
     * if loan is approved. If loan is not approved, returns an error message of "Loan denied"
     * which will cause the program to quit. 
     * @param console Scanner that reads user input from the console
     * @return String verifying valid user input or displaying error message for invalid input
     */
    public static String userInterface (Scanner console) {
        //get and validate application date
        System.out.print("Today's Date - Month Day (e.g., 3 15): ");
        int month = console.nextInt();
        int monthDay = console.nextInt();
        if (!isValidDate(month, monthDay)) {
            return "Invalid date";
        }
        
        int creditScore = 0;
        int income = 0;
        int loanAmount = 0;
        String processing = "";
        
        //get and validate credit score between 300 and 850
        System.out.print("Credit Score (300-850): ");
        creditScore = console.nextInt();
        if (creditScore < MIN_CREDIT_SCORE || creditScore > MAX_CREDIT_SCORE) {
            return "Invalid credit score";
        } 
        
        //get and validate income > 0
        System.out.print("2019 Income: ");
        income = console.nextInt();
        if (income < 0) {
            return "Invalid income";
        }
        
        //get and validate loan amount between 1,000 - 10,000
        System.out.print("Loan Amount (1000-10000): ");
        loanAmount = console.nextInt();
        if (loanAmount < MIN_LOAN || loanAmount > MAX_LOAN) {
            return "Invalid loan amount";
        } 
        
        //get processing    
        System.out.print("Express Processing (y, n)): ");
        processing = console.next();
        
        //get disbursement date and adds fee for express processing
        String disbursementDate = "";
        if (processing.startsWith("y") || processing.startsWith("Y")) {
            loanAmount += EXPRESS_PROCESSING_FEE; //adds express processing fee and parameter
            disbursementDate = getDisbursementDate(month, monthDay, true); 
        } else {    //calculates based on normal processing parameters
            disbursementDate = getDisbursementDate(month, monthDay, false);
        } 
        
        //get loan approval status annual interest rate
        double annualInterestRate = getInterestRate(loanAmount, creditScore, income);
        if (annualInterestRate == -1) {
            return "\nLoan denied";
        }
        
        // get the monthly payment
        double monthlyPayment = getMonthlyPayment(loanAmount, 
                                                  annualInterestRate, NUMBER_OF_MONTHS);
        
        double loan = loanAmount; //Change loanAmount to a double for floating point output 
        
        //Program output                                        
        System.out.printf("\nLoan Amount: $%.2f", loan);
        System.out.println("\nInterest Rate: " + annualInterestRate + "%"); 
        System.out.printf("Monthly Payment: $%.2f", monthlyPayment);
        System.out.println("\nDisbursement Date: " + disbursementDate);
        
        return "valid input";
    }
    
    /**
     * Validates the application month and application day
     * @param month Application month input by user
     * @param day Application day input by user
     * @return Boolean indicating if date is a valid date between Jan 1 and Dec 31, 2020
     */
    public static boolean isValidDate (int month, int day) {
        if (month >= 1 && month <= MONTHS_IN_YEAR && day >= 1 && day <= DAYS_IN_LONG_MONTH) {
            //Validate dates in months with 30 days
            if ((month == APR || month == JUN || month == SEP || month == NOV) 
                 && day > DAYS_IN_SHORT_MONTH) { 
                return false;
            }
            //Validate dates in February 2020 (a leap year)
            if (month == FEB && day > DAYS_IN_FEB_OF_LEAP_YEAR) {  
                return false;     
            }
        } else {
            return false;
        }
        return true;               
    }
    
    /**
     * Determines whether a loan is approved as well as the interest rate based on the loan 
     * amount, the borrower's credit score and the borrower's 2019 income.
     * @param loanAmount Total loan amount (including express processing fee)
     * @param creditScore Loan applicant credit score
     * @param income Loan applicant income for 2019
     * @return Interest rate if the loan is approved or -1.0 if the loan is not approved
     * @throws IllegalArgumentException if the loan amount is less than or equal to 0
     * @throws IllegalArgumentException if the credit score is less than 300 or greater than 850
     * @throws IllegalArgumentException if the income amount is less than 0
     */ 
    public static double getInterestRate(int loanAmount, int creditScore, int income) {
        //"Invalid loan amount" exception
        if (loanAmount <= 0) {
            throw new IllegalArgumentException ("Invalid loan amount");
        }
        
        //"Invalid credit score" exception
        if (creditScore < MIN_CREDIT_SCORE || creditScore > MAX_CREDIT_SCORE) {
            throw new IllegalArgumentException ("Invalid credit score");
        }   
            
        //"Invalid income" exception
        if (income < 0) {
            throw new IllegalArgumentException ("Invalid income");
        }  
        
        //Returns the interest rate if loan is approved, return -1.0 if loan not approved
        if (creditScore >= GOOD_CREDIT_SCORE) {
            return SPECIAL_INTEREST_RATE;
        } else if (income >= (HIGH_INCOME_TO_DEBT_RATIO * loanAmount)) {
            return LOW_INTEREST_RATE;
        } else if (creditScore >= FAIR_CREDIT_SCORE && 
                   income >= (MODERATE_INCOME_TO_DEBT_RATIO * loanAmount)) {
            return MODERATE_INTEREST_RATE;
        } else if (creditScore >= POOR_CREDIT_SCORE 
                   && income >= (LOW_INCOME_TO_DEBT_RATIO * loanAmount)) {
            return HIGH_INTEREST_RATE;
        } else {
            return -1.0;
        }
    }
    
    /**
     * Calculates the monthly payment amount for a loan based on the loan amount, the annual 
     * interest rate, and the number of months over which the loan is being repaid.
     * @param loanAmount Total loan amount (including express processing fee)
     * @param annualInterestRate Annual interest rate for approved loan
     * @param numberOfMonths Number of months in loan repayment period
     * @return Monthly payment
     * @throws IllegalArgumentException if the loan amount is less than or equal to 0
     * @throws IllegalArgumentException if the annual interest rate is less than 0.5
     * @throws IllegalArgumentException if the number of months is less than or equal to 0 
     */
    public static double getMonthlyPayment(double loanAmount, double annualInterestRate,
                                           int numberOfMonths) {
        //"Invalid loan amount" exception
        if (loanAmount <= 0) {
            throw new IllegalArgumentException ("Invalid loan amount");
        }    
            
        //"Invalid interest rate" exception
        if (annualInterestRate < MIN_INTEREST_RATE) {
            throw new IllegalArgumentException ("Invalid interest rate");
        }     
            
        //"Invalid number of months" exception
        if (numberOfMonths <= 0) {
            throw new IllegalArgumentException ("Invalid number of months");
        }  
        
        // Calculate monthly payment
        double monthlyInterestRate = annualInterestRate / (MONTHS_IN_YEAR * 100.0);
        double formulaPowerCalc = Math.pow((1 + monthlyInterestRate), numberOfMonths);
        double monthlyPayment = loanAmount * 
                                ((monthlyInterestRate * formulaPowerCalc) / 
                                (formulaPowerCalc - 1));                                 
            
        return monthlyPayment;                        
    }
    
    /**
     * Calculates disbursement date based on the the application month, application day, 
     * a year of 2020, and whether the loan will receive express processing.
     * The disbursement date is 3 days after the application date for loans receiving express
     * processing.  The disbursement date is 20 days after the application date otherwise.
     * Method uses a monthly payment calculation (Source: http://en.wikipedia.org/wiki/Loan)
     * Method uses Zeller's algorithm (Source: Sedgewick R & Wayne K. Introduction to 
     * programming in Java) to determine the day of the week for a given month, day, and year. 
     * Method returns disbursement date in the format "Day, m d year" (e.g., "Tue, 4 21 2020")
     * @param applicationMonth Month in which the user applied for loan
     * @param applicationDay Day of the month on which the user applied for loan
     * @param expressProcessing True if user would like express processing, otherwise false
     * @return String containing loan disbursement date in the format "dayOfWeek, mon day year"
     * @throws IllegalArgumentException if application date not valid date from 1/1 to 12/31/20
     *
     */
    public static String getDisbursementDate(int applicationMonth, int applicationDay, 
                                             boolean expressProcessing) {
        //"Invalid date" exception
        if (!isValidDate (applicationMonth, applicationDay)) {
            throw new IllegalArgumentException ("Invalid date");
        }  
        
        int disbursementDay = applicationDay;   
        int disbursementMonth = applicationMonth;
        int disbursementYear = APPLICATION_YEAR;
        
        if (expressProcessing) {
            disbursementDay += DAYS_FOR_EXPRESS_PROCESSING;
        } else {
            disbursementDay += DAYS_FOR_NORMAL_PROCESSING;
        }
        
        // Validate disbursement date in months with 30 days
        if ((disbursementMonth == APR || disbursementMonth == JUN || 
             disbursementMonth == SEP || disbursementMonth == NOV) 
             && disbursementDay > DAYS_IN_SHORT_MONTH) { 
            disbursementMonth++;
            disbursementDay -= DAYS_IN_SHORT_MONTH;
        //Validate disbursement date in February
        } else if (disbursementMonth == FEB && disbursementDay > DAYS_IN_FEB_OF_LEAP_YEAR) {  
            disbursementMonth++;
            disbursementDay -= DAYS_IN_FEB_OF_LEAP_YEAR; 
        //Validate disbursement date in December    
        } else if (disbursementMonth == DEC && disbursementDay > DAYS_IN_LONG_MONTH) {
            disbursementMonth = JAN;
            disbursementDay -= DAYS_IN_LONG_MONTH;
            disbursementYear++;
        //Validate disbursement date in months with 31 days
        } else if (disbursementDay > DAYS_IN_LONG_MONTH) {
            disbursementMonth++;
            disbursementDay -= DAYS_IN_LONG_MONTH;
        }
        //Zeller's algorithm     
        int w = disbursementYear - (2 + MONTHS_IN_YEAR - disbursementMonth) / MONTHS_IN_YEAR;
        int x = w + w / LEAP_YEAR_FREQUENCY - w / YEARS_IN_CENTURY + 
                w / (LEAP_YEAR_FREQUENCY * YEARS_IN_CENTURY);
        int z = disbursementMonth + MONTHS_IN_YEAR * 
                ((2 + MONTHS_IN_YEAR - disbursementMonth) / MONTHS_IN_YEAR) - 2;
        int dayOfWeek = (disbursementDay + x + (DAYS_IN_LONG_MONTH * z) / 
                         MONTHS_IN_YEAR) % DAYS_IN_WEEK;
        
        //Convert numerical day of week to text String day of week
        String day = "";
        if (dayOfWeek == SUN) {
            day = "Sun";
        }
        if (dayOfWeek == MON) {
            day = "Mon";
        }
        if (dayOfWeek == TUES) {
            day = "Tue";
        }
        if (dayOfWeek == WED) {
            day = "Wed";
        }
        if (dayOfWeek == THU) {
            day = "Thu";
        }
        if (dayOfWeek == FRI) {
            day = "Fri";
        }
        if (dayOfWeek == SAT) {
            day = "Sat";
        }
        //disbursement date must be in the format "Tue, 4 21 2020"
        return (day + ", " + disbursementMonth + " " + disbursementDay + " " + 
                disbursementYear);                            
    }
}