import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class App implements ActionListener {
    RoomSelectionSetting setting = new RoomSelectionSetting(); // Setting
    
    // Widgets Declaration
    private JFrame       mainFrame;
    private JPanel     packageType,
                    dineSellection;

    private JComboBox PkgSelection;
    private JCheckBox   DineSrvceB,
                        DineSrvceL,
                        DineSrvceD;
    private JButton ContinueButton;


    public App() {
        // Widget Initialization
        PkgSelection = new JComboBox(setting.getOfferedPackages());

        DineSrvceB = new JCheckBox("BREAKFAST"); // RM50/Day
        DineSrvceL = new JCheckBox("LUNCH");     // RM48/Day
        DineSrvceD = new JCheckBox("DINNER");    // RM62/Day

        ContinueButton = new JButton("Proceed");
        
        // Action Listener Declaration
        PkgSelection.addActionListener(this);
        DineSrvceB.addActionListener(this);
        DineSrvceL.addActionListener(this);
        DineSrvceD.addActionListener(this);
        ContinueButton.addActionListener(this);

        // Panel Declaration
        packageType    = new JPanel();
        packageType.setLayout(new GridLayout(1, 0));
        packageType.setBorder(BorderFactory.createTitledBorder("PACKAGE"));
        
        dineSellection = new JPanel();
        dineSellection.setLayout(new GridLayout(3, 0));
        dineSellection.setBorder(BorderFactory.createTitledBorder("DINE SERVICE"));
        
        // Add Widgets' to Panel
        packageType.add(PkgSelection);
        dineSellection.add(DineSrvceB);
        dineSellection.add(DineSrvceL);
        dineSellection.add(DineSrvceD);
        
        // Set Widgets' Bounds
        packageType.setBounds(20, 0, 350, 50);
        dineSellection.setBounds(20, 50, 350, 120);
        ContinueButton.setBounds(120,180,100,30);

        // Frame Setup
        mainFrame = new JFrame();
        mainFrame.setSize(400, 300);
        mainFrame.setLayout(null);
        mainFrame.setTitle("Room Package Selection");
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);

        mainFrame.add(packageType);
        mainFrame.add(dineSellection);
        mainFrame.add(ContinueButton);
    }

    public void actionPerformed(ActionEvent e) {
        // ContinueButton
        if (e.getSource() == ContinueButton) {
            int proceed = JOptionPane.showConfirmDialog(
                null,
                "Confirm to proceed?",
                "Proceed",
                JOptionPane.YES_NO_OPTION
            );
                
            String finalMsg = "Package Selected: \n" + "- " + PkgSelection.getSelectedItem().toString() + "\n";
            if (DineSrvceB.isSelected()) finalMsg += " - " + "Breakfast" + "\n";
            if (DineSrvceL.isSelected()) finalMsg += " - " + "Lunch"     + "\n";
            if (DineSrvceD.isSelected()) finalMsg += " - " + "Dinner"    + "\n";
                
            boolean[] dineSrvce = {
                DineSrvceB.isSelected(),
                DineSrvceL.isSelected(),
                DineSrvceD.isSelected()
            };
            if (proceed == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(
                    null,
                    finalMsg,
                    "Input Data",
                    JOptionPane.INFORMATION_MESSAGE
                );

                mainFrame.dispose();

                new RoomType(
                    PkgSelection.getSelectedItem().toString(),
                    dineSrvce,
                    false
                );
            }
        }

    }
    
}