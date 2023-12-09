package frontendClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The {@code Warning_Panel} class provides a user interface to display a warning dialog.
 * It is used to show warnings and confirmations to the user, particularly in situations
 * where user confirmation is required before proceeding with an action.
 * @author Fan Yang
 */

public class Warning_Panel {

    //Declare two global private variables within this class
    private static WarningPanelListener listener;
    private static JDialog dialog = null;


    /**
     * Interface for handling the closure of the warning panel.
     */
    public interface WarningPanelListener {
        void onPanelClosed();
    }

    /**
     * Displays a warning dialog to the user.
     *
     * @param owner    The parent dialog for this warning dialog.
     * @param listener The listener to be notified when the dialog is closed.
     */
    public static void showWarning(JDialog owner, WarningPanelListener listener) {
        Warning_Panel.listener = listener;
        if (dialog == null) {
            createAndShowGUI(owner);
        } else {
            dialog.setVisible(true);
        }
    }

    /**
     * Creates and displays the warning dialog GUI.
     *
     * @param parentDialog The parent dialog for this warning dialog.
     */
    private static void createAndShowGUI(JDialog parentDialog) {

        //Window set up
        dialog = new JDialog(parentDialog, "Warning", Dialog.ModalityType.APPLICATION_MODAL); // 使用父对话框
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(600, 800);
        JPanel panel = new JPanel(null);
        panel.setPreferredSize(new Dimension(552, 720));
        panel.setBackground(new Color(0x1C1A33));

        // Cancel button, allow user to go back summary window and perform save/save as
        JButton btnCancel = new JButton("Cancel");
        btnCancel.setBounds(50, 541, 452, 67);
        btnCancel.setBackground(new Color(0xE328AF));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFont(new Font("Cairo", Font.BOLD, 36));
        btnCancel.setFocusPainted(false);
        btnCancel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                listener.onPanelClosed();
                //System.out.println("Calling onPanelClosed from btnCancel");//only a test code
                dialog.dispose(); // Close the window


            }
        });
        //add the button to the main panel onto this sub frame
        panel.add(btnCancel);


        // Confirm button, user choose to discard all corrections made
        JButton btnConfirm = new JButton("Confirm");
        btnConfirm.setBounds(55, 631, 452, 67);
        btnConfirm.setBackground(new Color(0x6316C1)); // Assuming violet-800 is close to #6316C1
        btnConfirm.setForeground(Color.WHITE);
        btnConfirm.setFont(new Font("Cairo", Font.BOLD, 36));
        btnConfirm.setFocusPainted(false);
        btnConfirm.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        btnConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //close windows
                dialog.dispose();
            }
        });

        //add the button to the main panel onto this sub frame
        panel.add(btnConfirm);

        // Warning label
        JLabel lblWarning = new JLabel("Warning!", SwingConstants.CENTER);
        lblWarning.setBounds((600 - 192) / 2 - 24, 50, 192, 50); // Centered horizontally
        lblWarning.setForeground(Color.WHITE);
        lblWarning.setFont(new Font("Cairo", Font.BOLD, 38));
        panel.add(lblWarning);


        JPanel messagePanel1 = new JPanel(null);
        messagePanel1.setBounds(50, 192, 452, 60);
        messagePanel1.setOpaque(false);


        JLabel lblMessage1 = new JLabel("You'll give up all your corrections !");
        lblMessage1.setBounds(40, 6, 392, 48);
        lblMessage1.setForeground(Color.WHITE);
        lblMessage1.setFont(new Font("Cairo", Font.BOLD, 22));
        messagePanel1.add(lblMessage1);

        panel.add(messagePanel1);

        JPanel backgroundPanel1 = new JPanel();
        backgroundPanel1.setBounds(0, 0, 452, 60);
        backgroundPanel1.setBackground(new Color(0x1C1A33));
        backgroundPanel1.setBorder(BorderFactory.createLineBorder(new Color(0x6418C3), 2));
        messagePanel1.add(backgroundPanel1);


        //Second warning message
        JPanel messagePanel2 = new JPanel(null);
        messagePanel2.setBounds(50, 292, 452, 60);
        messagePanel2.setOpaque(false);
        // Text label for message 2, using HTML to allow line breaks
        JLabel lblMessage2 = new JLabel("<html><div style='text-align: center;'>Are you sure you want to quit modifying and<br/>go back to the main page?</div></html>", SwingConstants.CENTER);
        lblMessage2.setBounds(40, 6, 392, 48);
        lblMessage2.setForeground(Color.WHITE);
        lblMessage2.setFont(new Font("Cairo", Font.BOLD, 18));
        messagePanel2.add(lblMessage2);

        panel.add(messagePanel2);

        JPanel backgroundPanel2 = new JPanel();
        backgroundPanel2.setBounds(0, 0, 452, 60);
        backgroundPanel2.setBackground(new Color(0x1C1A33));
        backgroundPanel2.setBorder(BorderFactory.createLineBorder(new Color(0x6418C3), 2));
        messagePanel2.add(backgroundPanel2);

        //add every components above onto this subframe
        panel.add(messagePanel2);

        dialog.add(panel);

        //set this dialog location
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
        dialog.toFront();

    }


    /**
     * Sets the dialog to active. This is a placeholder method and currently returns true.
     *
     * @return Always returns true.
     */
    public boolean setActive(){
        return true;
    }
}