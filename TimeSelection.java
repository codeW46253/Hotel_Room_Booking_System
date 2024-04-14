import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class TimeSelection extends JFrame implements ActionListener {
    RoomSelectionSetting setting = new RoomSelectionSetting(); // Common Setting

    // Variable
    private String      pkgSellected;
    private String      typeSelected; private double    typeMultiplier;
    private boolean[]      dineSrvce; private double         dinePrice;
    private int             occpnt_a; private int             occpnt_c;
    private int  durationSelectedDay; private int durationSelectedWeek;
    private int        totalDuration; private double  totalOccpntPrice;
    private String[]  receiptContent;
    private boolean          testRun;

    // Widgets Declaration
    private JComboBox durationSelectionDay, durationSelectionWeek;
    private JButton   receiptButton, CompleteButton;
    private JLabel    dayLable, weekLabel;
    private JPanel    timeSelectionPanel;

    public TimeSelection(
        String pkgSellected,
        String typeSelected, 
        boolean[] dineSrvce,
        int        occpnt_a,
        int        occpnt_c,
        double    basePrice,
        boolean     testRun) {

        this.pkgSellected = pkgSellected;
        this.typeSelected = typeSelected; this.typeMultiplier = setting.getTypeMultiplier(setting.getTypeIndex(this.typeSelected));
        this.dineSrvce    = dineSrvce;
        this.occpnt_a     = occpnt_a;
        this.occpnt_c     = occpnt_c;
        this.dinePrice    = setting.getDinePrice( this.dineSrvce[0], this.dineSrvce[1], this.dineSrvce[2] );

        // Widgets Initialization
        this.dayLable  = new JLabel("Day: ");  this.durationSelectionDay  = new JComboBox<>(setting.getDurationDays());
        this.weekLabel = new JLabel("Week: "); this.durationSelectionWeek = new JComboBox<>(setting.getDurationWeeks());

        this.durationSelectionDay.setSelectedItem(setting.getDurationDay(0));
        this.durationSelectionWeek.setSelectedItem(setting.getDurationWeek(0));

        this.receiptButton  = new JButton("Receipt"); 
        this.CompleteButton = new JButton("Done");

        // Action ListenerDeclaration
        this.durationSelectionDay.addActionListener(this);
        this.durationSelectionWeek.addActionListener(this);
        this.receiptButton.addActionListener(this);
        this.CompleteButton.addActionListener(this);

        // Panel Declaration
        this.timeSelectionPanel = new JPanel();
        this.timeSelectionPanel.setLayout(new GridLayout(2,2));
        this.timeSelectionPanel.setBorder(BorderFactory.createTitledBorder("Stay Duration"));

        // Add Widgets to Panel
        this.timeSelectionPanel.add(dayLable); timeSelectionPanel.add(durationSelectionDay);
        this.timeSelectionPanel.add(weekLabel); timeSelectionPanel.add(durationSelectionWeek);

        // Set Widgets' Bound
        this.timeSelectionPanel.setBounds(20, 0, 350, 80);
        this.receiptButton.setBounds(20, 90, 100,50);
        this.CompleteButton.setBounds(265, 90, 100, 50);

        // Frame Setup
        this.setSize(400, 300);
        this.setLayout(null);
        this.setTitle("Stay Duration");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setVisible(true);

        // Add All to Frame
        this.add(timeSelectionPanel);
        this.add(receiptButton);
        this.add(CompleteButton);
    }

    public void actionPerformed(ActionEvent e) {
        this.durationSelectedDay  = Integer.parseInt(setting.getDurationDay(durationSelectionDay.getSelectedIndex()) );
        this.durationSelectedWeek = Integer.parseInt(setting.getDurationWeek(durationSelectionWeek.getSelectedIndex()));

        this.totalDuration    = setting.getDurationDayPriceMult(durationSelectionDay.getSelectedIndex()) +
                                setting.getDurationWeekPriceMult(durationSelectionWeek.getSelectedIndex());

        double occpntABaseNumber = setting.getPkgAPrice(setting.getPkgIndex(this.pkgSellected));
        double occpntCBaseNumber = setting.getPkgCPrice(setting.getPkgIndex(this.pkgSellected));
        this.totalOccpntPrice = (occpntABaseNumber * this.occpnt_a) +
                                (occpntCBaseNumber * this.occpnt_c);

        double finalPrice = (this.totalOccpntPrice + this.dinePrice) * this.typeMultiplier * this.totalDuration;


        // Receipt Button
        if (this.receiptButton == e.getSource()) {
            String[] receiptContent = {
                "---------------------------------------------",                      // 0
                "               MetroSouth Hotel",                                    // 1
                "=============================================",                      // 2
                "  Package: " + this.pkgSellected,                                    // 3
                "  Type:    " + this.typeSelected,                                    // 4
                "  Number of Occupant:",                                              // 5
                "     - Adult [" + occpntABaseNumber + "] :" + this.occpnt_a,         // 6  - Occupant Number: Adult
                "     - Child [" + occpntCBaseNumber + "] :" + this.occpnt_c,         // 7  - Occupant Number: Child
                "  Stay Duration:",                                                   // 8
                "     - Day  : " + this.durationSelectedDay,                          // 9 - Start Duration: Day
                "     - Week : " + this.durationSelectedWeek,                         // 10 - Start Duration: Week
                "  Dine Option:",                                                     // 11
                "",                                                                   // 12 - Dine option: Breakfast
                "",                                                                   // 13 - Dine option: Lunch
                "",                                                                   // 14 - Dine option: Dinner
                "  Price:",                                                           // 15
                "     - Dining              : [RM]" + this.dinePrice,                 // 16
                "     - Occupant            : [RM]" + totalOccpntPrice,               // 17
                "     - Room Type Multiplier: x"    + this.typeMultiplier,            // 18
                "     - Stay Duration       :  "    + this.totalDuration + " Day(s)", // 19
                "  Total                    : [RM]" + finalPrice,                     // 20
                "---------------------------------------------"                       // 21
            };

            receiptContent[12] = (this.dineSrvce[0]) ?
                "     - Breakfast" :
                "     - No Breakfast";

            receiptContent[13] = (this.dineSrvce[1]) ?
                "     - Lunch" :
                "     - No Lunch";

            receiptContent[14] = (this.dineSrvce[2]) ?
                "     - Dinner":
                "     - No Dinner";

            try { writeReceipt(receiptContent); this.receiptContent = receiptContent; }
            catch (IOException ioe) {
                JOptionPane.showMessageDialog(null, "ERROR");
            }
        }

        // Complete Button
        try {
            if (this.CompleteButton == e.getSource()) {
                if (checkReceipt(this.receiptContent)) {
                    int proceed = JOptionPane.showConfirmDialog(
                        null,
                        "Confirm purchase?",
                        "Proceed",
                        JOptionPane.YES_NO_OPTION
                    );

                    if (proceed == JOptionPane.YES_OPTION) {
                        this.dispose();
                        JOptionPane.showMessageDialog(
                            null,
                            "Thank you for choosing our hotel! \n" +
                            "May your stay be the most comfortable.",
                            "Thank you",
                            JOptionPane.INFORMATION_MESSAGE
                        );
                    }
                }
            }
        }
        catch (IOException ioe) {} 
        catch (NullPointerException npe) {}
    }

    /** Write Receipt into the file
     *  Return:
     *  0 - pass, 1 - error
     */
    public int writeReceipt(String[] contents) throws IOException {
        try {
            FileWriter receiptWriter = new FileWriter("receipt.txt");
            for (String content: contents) {
                receiptWriter.write(content + "\n");
            }
            receiptWriter.close();
            JOptionPane.showMessageDialog(
                null,
                "Receipt: Done Write.",
                "Done",
                JOptionPane.INFORMATION_MESSAGE
            );
            return 0;

        } catch (IOException ioe) { return 1; }
    }

    /** Checking the content of the receipt */
    public boolean checkReceipt(String[] contents) throws IOException {
        boolean similar = true;
        try {
            File            inputFile = new File("receipt.txt");
            FileReader  receiptReader = new FileReader(inputFile);
            BufferedReader lineReader = new BufferedReader(receiptReader);
            for (String content : contents) {
                String  readLine = lineReader.readLine();
                if (content != "\n") similar &= (content.compareToIgnoreCase(readLine) == 0);
            }
            lineReader.close();
            return similar;

        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(null, ioe, "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}