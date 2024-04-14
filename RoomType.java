import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RoomType extends JFrame implements ActionListener {
    RoomSelectionSetting setting = new RoomSelectionSetting(); // Implement Common Setting

    // Variables Declaration
    private String pkgSelected, typeSelected;
    private boolean[]              dineSrvce;
    private int           occpnt_a, occpnt_c;
    private double                 basePrice;
    private boolean                  testRun;

    // Specified Offer Selection
    private String[] typeOffered_Single = { setting.getOfferedType(0), setting.getOfferedType(1) };
    private String[] typeOffered_Couple = { setting.getOfferedType(1) };
    private String[] typeOffered_Family = { setting.getOfferedType(1), setting.getOfferedType(2), setting.getOfferedType(3)};
    private String[] typeOffered_Party  = { setting.getOfferedType(1), setting.getOfferedType(2), setting.getOfferedType(3)};

    // Widgets Declaration
    private JPanel         typePanel,
                         occpntPanel;
    private JComboBox  typeSelection;
    private JLabel      occpntALable,
                        occpntCLable;
    private JTextField  occpntACount, 
                        occpntCCount;
    private JButton TimeSelectButton;

    public RoomType(String pkgSellected, boolean[] dineSrvce, boolean testRun) {
        this.pkgSelected  = pkgSellected;
        this.dineSrvce    = dineSrvce;
        this.testRun      = testRun;

        // Widgets Initialization
        if (this.pkgSelected == setting.getOfferedPackage(0)) typeSelection = new JComboBox(typeOffered_Single);
        if (this.pkgSelected == setting.getOfferedPackage(1)) typeSelection = new JComboBox(typeOffered_Couple);
        if (this.pkgSelected == setting.getOfferedPackage(2)) typeSelection = new JComboBox(typeOffered_Family);
        if (this.pkgSelected == setting.getOfferedPackage(3)) typeSelection = new JComboBox(typeOffered_Party);
        
        occpntALable = new JLabel("Adult"); occpntACount = new JTextField();
        occpntCLable = new JLabel("Child"); occpntCCount = new JTextField();
        
        for (int i = 0; i < 4; i++) {
            if (this.pkgSelected == setting.getOfferedPackage(i)) {
                occpntACount.setText(setting.getOccpntABaseNumber(i)); occpntACount.setEnabled(setting.getOccpntAInputPermission(i));
                occpntCCount.setText(setting.getOccpntCBaseNumber(i)); occpntCCount.setEnabled(setting.getOccpntCInputPermission(i));
            }
        }

        TimeSelectButton = new JButton("Proceed");

        // Action Listener Declaration
        typeSelection.addActionListener(this);
        occpntACount.addActionListener(this);
        occpntCCount.addActionListener(this);
        TimeSelectButton.addActionListener(this);

        // Panel Declaration
        typePanel = new JPanel();                                                    // Type Selection  Panel
        typePanel.setLayout(new GridLayout(1,0));
        typePanel.setBorder(BorderFactory.createTitledBorder("ROOM TYPE"));

        occpntPanel = new JPanel();                                                  // Occupant Input Panel
        occpntPanel.setLayout(new GridLayout(2, 2));
        occpntPanel.setBorder(BorderFactory.createTitledBorder("NUMBER OF PEOPLE"));

        // Add Widgets to Panel
        typePanel.add(typeSelection);
        occpntPanel.add(occpntALable); occpntPanel.add(occpntACount);
        occpntPanel.add(occpntCLable); occpntPanel.add(occpntCCount);

        // Set Widgets' Bounds
        typePanel.setBounds(20, 0, 350, 50);
        occpntPanel.setBounds(20, 50, 350, 80);
        TimeSelectButton.setBounds(120, 180, 100, 30);

        // Frame Setup
        this.setSize(400, 300);
        this.setLayout(null);
        this.setTitle("Room Type Selection");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setVisible(true);

        // Add All to Frame
        this.add(typePanel);
        this.add(occpntPanel);
        this.add(TimeSelectButton);
    }

    /** Return base price for a day
     * 
     * basePrice = ((occpnt_a * occpnt_aBase) + (occpnt_c * occpnt_cBase) + dinePrice) * typeMultiplier
     */
    public double calculateBasePrice(double typeMultiplier, int occpnt_a, int occpnt_c) {
        double dinePrice       = 0;
        double occpnt_aBPrice  = 0;
        double occpnt_cBPrice  = 0;

        for (int dineIndex=0;dineIndex<3; dineIndex++) if (this.dineSrvce[dineIndex]) dinePrice += setting.getDinePrice(dineIndex);

        // Occupant Base Price Counter
        for (int occpntAIndex = 0; occpntAIndex < 4; occpntAIndex++) if (this.pkgSelected == setting.getOfferedPackage(occpntAIndex)) occpnt_aBPrice = setting.getPkgAPrice(occpntAIndex);
        for (int occpntCIndex = 0; occpntCIndex < 4; occpntCIndex++) if (this.pkgSelected == setting.getOfferedPackage(occpntCIndex)) occpnt_cBPrice = setting.getPkgCPrice(occpntCIndex);

        return 
            ((occpnt_a * occpnt_aBPrice) + // Adult Occupant Price
            (occpnt_c * occpnt_cBPrice) +  // Child Occupant Price
            dinePrice) *                   // Dining Price
            typeMultiplier;               // Price Multiplier [Room Type]
    }

    public void actionPerformed(ActionEvent e) {
        this.typeSelected = this.typeSelection.getSelectedItem().toString(); // Get the Selected Room Type
        this.occpnt_a = Integer.parseInt(occpntACount.getText());              // Get Number of Adult Occupant
        this.occpnt_c = Integer.parseInt(occpntCCount.getText());              // Get Number of Child Occupant
        this.basePrice = calculateBasePrice(setting.getTypeMultiplier(setting.getTypeIndex(this.typeSelected)), this.occpnt_a, this.occpnt_c);

        // Time Select Button Action
        if (e.getSource() == this.TimeSelectButton) { 
            int proceed = JOptionPane.showConfirmDialog(
                null,
                "Confirm to Proceed?",
                "Done",
                JOptionPane.YES_NO_OPTION
            );

            if (proceed == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(
                    null,
                    // Message
                    "Room Type Selection: " + this.typeSelected + "\n" +
                    "Number of Occupant:"                       + "\n" +
                    "Adult: "               + this.occpnt_a     + "\n" +
                    "Child: "               + this.occpnt_c     + "\n" +
                    "Base Price: "          + this.basePrice,
    
                    // Title
                    "Input Data",
                    JOptionPane.INFORMATION_MESSAGE
                );
    
                this.dispose();
                if (!this.testRun)
                    new TimeSelection(
                        this.pkgSelected,
                        this.typeSelected,
                        this.dineSrvce,
                        this.occpnt_a,
                        this.occpnt_c,
                        this.basePrice,
                        false
                    );
            }
        }
    }
}
