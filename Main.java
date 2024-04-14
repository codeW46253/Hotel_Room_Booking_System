class Main {
    public Main() {

    }

    public static void main(String[] args) {
        RoomSelectionSetting setting = new RoomSelectionSetting();
        // Main Application Window
        new App();
        
        /** ---------------------------------------------- For Testing Purposes Only ---------------------------------------------- */
        // Test Value
        String  testPkgSellected = "Single"; //
        boolean[] testDineOption = {
            true,
            true,
            true
        };

        /** Test: RoomType.java */
        //new RoomType(testPkgSellected, testDineOption, true);
        
        String testTypeSellected = "One bed: Queen Size";
        int          testOccpntA = 0;
        int          testOccpntC = 0;
        double     testBasePrice = (
            (testOccpntA * setting.getPkgAPrice(setting.getPkgIndex(testPkgSellected))) + // Adult Occupant Price
            (testOccpntC * setting.getPkgCPrice(setting.getPkgIndex(testPkgSellected))) + // Child Occupant Price
            setting.getDinePrice(true, true, true)) *              // Dining Price
            setting.getTypeMultiplier(setting.getTypeIndex(testTypeSellected)             // Price Multiplier [Room Type]
        );



        /** Test: TimeSelection.java */
        //new TimeSelection(testPkgSellected, testTypeSellected, testDineOption, testOccpntA, testOccpntC, testBasePrice, true);
    }
}