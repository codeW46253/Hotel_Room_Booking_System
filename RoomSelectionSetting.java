public class RoomSelectionSetting {
    // Room Package
    private String[] packages = {
        "Single", // RM80
        "Couple", // RM140
        "Family", // RM75/Adult, RM40/Child
        "Party"   // RM90/Person
    };

    private double[] packageAPrices = {
        80,
        70,
        75,
        90
    };

    private double[] packageCPrices = {
        0,
        0,
        40,
        0
    };

    // Room Type
    private String[] typeOffered = {
        "One bed: King size",
        "One bed: Queen Size",
        "Two bed: One Room",
        "Two bed: Two Room"
    };

    private double[] typeMultiplier = {
        1,   // "One bed: King size"
        1.5, // "One bed: Queen Size"
        2,   // "Two bed: One Room"
        4    // "Two bed: Two Room"
    };

    // Occupant Base Number
    private String[] occpntABaseNumber = {
        "1", // Single: Adult 
        "2", // Couple: Adult 
        "0", // Family: Adult 
        "2"  // Party:  Adult 
    };

    private String[] occpntCBaseNumber = {
        "0", // Single: Child
        "0", // Couple: Child
        "0", // Family: Child
        "0"  // Party:  Child
    };

    // Dine Option
    private double breakfastPrice = 50;
    private double lunchPrice     = 48;
    private double dinnerPrice    = 62;
    private double[] dinePrice = {
        breakfastPrice,
        lunchPrice,
        dinnerPrice
    };

    // Occupant Input Permission
    private boolean[] occpntAInputPermission = {
        false, // Single: Adult
        false, // Couple: Adult
         true, // Family: Adult
         true  // Party:  Adult
    };

    private boolean[] occpntCInputPermission = {
        false, // Single: Child
        false, // Couple: Child
         true, // Family: Child
        false  // Party:  Child
    };

    private String[] durationDay = {
        "1",
        "2",
        "3",
        "4",
        "5",
        "6", // 7 - 1 week
    };

    private String[] durationWeek = {
        "0",
        "1",
        "2",
        "3"  // 4 - 1 month
    };

    // Duration Selection Available [Max Duration: 27 days]
    private int[] priceDayMultiplier = {
        1,
        2,
        3,
        4,
        5,
        6, // 7 - 1 week
    };
    private int[] priceWeekMultiplier = {
        0,
        7,
        14,
        21  // 4 - 1 month
    };

    public RoomSelectionSetting() {
    }

    /*--------------------------Standard Value--------------------------------------------------------- */

    //------------------------------------------------------------------------------------------- Offered Room Package
    /** Return one Offered Package */
    public String getOfferedPackage(int index) { return packages[index]; }
    /** Return All Offered Packages */
    public String[] getOfferedPackages()       { return packages; }

    //------------------------------------------------------------------------------------------- Offered Room Type
    /** Return one Offered Type */
    public String getOfferedType(int index)    { return typeOffered[index]; }
    /** Return All Offered Type */
    public String[] getOfferedTypes()          { return typeOffered; }
    /** Return one Offered Type Multiplier */
    public double getTypeMultiplier(int index) { return typeMultiplier[index]; }
    /** Return one Offered Type Multiplier */
    public double[] getTypeMultiplier()        { return typeMultiplier; }

    //------------------------------------------------------------------------------------------- Occupant Base Number
    /** Return one adult base value */
    public String getOccpntABaseNumber(int index) { return occpntABaseNumber[index]; }
    /** Return one child base value */
    public String getOccpntCBaseNumber(int index) { return occpntCBaseNumber[index]; }
    /** Return all adult base value */
    public String[] getAllOccpntABaseNumber()     { return occpntABaseNumber; }
    /** Return all child base value */
    public String[] getAllOccpntCBaseNumber()     { return occpntCBaseNumber; }

    //------------------------------------------------------------------------------------------- Dine Option Price
    /** Return Breakfast Option price */
    public double getBreakfastPrice()     { return breakfastPrice; }
    /** Return Lunch Option price */
    public double getLunchPrice()         { return lunchPrice; }
    /** Return Dinner Option price */
    public double getDinnerPrice()        { return dinnerPrice; }
    /** Return All Dine Option price */
    public double getDinePrice(int index) { return dinePrice[index]; }
    /** Return All Dine Option price */
    public double[] getDinePrices()       { return dinePrice; }

    //------------------------------------------------------------------------------------------- Occupant Input Permission
    /** Return one Adult Input Permission */
    public boolean getOccpntAInputPermission(int index) { return occpntAInputPermission[index]; }
    /** Return one Child Input Permission */
    public boolean getOccpntCInputPermission(int index) { return occpntCInputPermission[index]; }
    /** Return all Adult Input Permission */
    public boolean[] getOccpntAInputPermissions()       { return occpntAInputPermission; }
    /** Return all Child Input Permission */
    public boolean[] getOccpntCInputPermissions()       { return occpntCInputPermission; }

    //------------------------------------------------------------------------------------------- Duration Selection
    /** Return one day duration */
    public String getDurationDay(int index)        { return durationDay[index]; }
    /** Return one week duration */
    public String getDurationWeek(int index)       { return durationWeek[index]; }
    /** Return all available day duration */
    public String[] getDurationDays()              { return durationDay; }
    /** Return all available week duration */
    public String[] getDurationWeeks()             { return durationWeek; }
    /** Return one day duration price */
    public int getDurationDayPriceMult(int index)  { return priceDayMultiplier[index]; }
    /** Return one week duration price */
    public int getDurationWeekPriceMult(int index) { return priceWeekMultiplier[index]; }

    /*--------------------------Price------------------------------------------------------------------ */

    public double getPkgAPrice(int index) { return packageAPrices[index]; }
    public double getPkgCPrice(int index) { return packageCPrices[index]; }
    public double[] getAllPkgAPrice() { return packageAPrices; }
    public double[] getAllPkgCPrice() { return packageCPrices; }

    public double getDinePrice(boolean breakfast, boolean lunch, boolean dinner) {
        double dinePrice = 0;
        if (breakfast) dinePrice += this.breakfastPrice;
        if (lunch)     dinePrice += this.lunchPrice;
        if (dinner)    dinePrice += this.dinnerPrice;
        return dinePrice;
    }

    /*--------------------------Index------------------------------------------------------------------ */
    public int getPkgIndex(String item) {
        int index = 0;
        // Search in Package List
        for (String pkg : packages) {
            if (item == pkg) return index;
            index++;
        }
        return -1;
    }
    public int getTypeIndex(String item) {
        int index = 0;
        for (String type : typeOffered) {
            if (item == type) return index;
            index++;
        }

        return -1;
    }
    public int getDurationDayPriceMultIndex(String item) {
        int index = 0;
        for (String day : durationDay) {
            if (item == day) return index;
            index++;
        }

        return -1;
    }
    public int getDurationWeekPriceMultIndex(String item) {
        int index = 0;
        for (String week : durationWeek) {
            if (item == week) return index;
            index++;
        }

        return -1;
    }
}