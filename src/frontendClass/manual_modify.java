package frontendClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import backendClass.Document;

/**
 * The {@code manual_modify} class provides a graphical user interface for manually modifying a word in a document.
 * It displays a dialog where users can enter the new word to replace an existing one at a specified index.
 * This class is responsible for creating the UI components and handling the replacement action.
 * @author Houyujie Lu
 */

public class manual_modify extends JDialog {

    //Declare two global private variables within this class
    private static JDialog dialog;
    private static String modify = "";

    /**
     * Constructs a {@code manual_modify} dialog.
     * This constructor initializes the GUI components for manually modifying a word in the document.
     *
     * @param owner    The JFrame from which this dialog is displayed.
     * @param document The document where the word will be modified.
     * @param index    The index of the word to be replaced in the document.
     */
    public manual_modify(JFrame owner, Document document, int index) {
        // Window frame set up
        dialog = new JDialog(owner, "Manual modify", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setTitle("Manual modify");
        dialog.setSize(280, 345);
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(null);
        dialog.getContentPane().setBackground(Color.decode("#FDFDFD"));


        JPanel mainPanel = createPanel(0, 0, 280, 345, new Color(253, 253, 253));
        dialog.add(mainPanel);
        JPanel topTextPanel = createPanel(30, 20, 197, 69, Color.WHITE);
        mainPanel.add(topTextPanel);
        JLabel topTextLabel = createLabel("Manual Modify", new Font("Cairo", Font.BOLD, 24), Color.decode("#6418C3"));
        topTextLabel.setBounds(30, 12, 165, 43);
        topTextPanel.add(topTextLabel);
        dialog.add(topTextPanel);

        JPanel inputTextPanel = createPanel(30, 115, 197, 69, Color.decode("#FFFFFF"));
        mainPanel.add(inputTextPanel);

        JTextField inputTextField = new JTextField("Change to");
        inputTextField.setForeground(Color.GRAY);
        inputTextField.setBounds(30, 12, 165, 43);
        inputTextPanel.add(inputTextField);

        // Focus listener to clear the placeholder text when the text field gains focus
        inputTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (inputTextField.getText().equals("Change to")) {
                    inputTextField.setText("");
                    inputTextField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (inputTextField.getText().isEmpty()) {
                    inputTextField.setForeground(Color.GRAY);
                    inputTextField.setText("Change to");
                }
            }
        });

        // Confirm button
        // store user input and past to document to replace the word at current index
        JButton confirmbutton = new JButton("Confirm");
        confirmbutton.setBounds(60, 250, 165, 50);
        confirmbutton.setFont(new Font("Cairo", Font.BOLD, 18));
        confirmbutton.setForeground(Color.WHITE);
        confirmbutton.setBackground(new Color(0x6418C3));
        confirmbutton.addActionListener(new ActionListener() {
            //open modify window as the action event
            @Override
            public void actionPerformed(ActionEvent e) {
                modify = inputTextField.getText();
                document.replaceWordAtIndex(index, modify);
                dialog.dispose(); // Close the window when the confirm button is clicked
            }
        });
        mainPanel.add(confirmbutton);

        // Set all components bounds
        topTextPanel.setBounds(30, 20, 197, 69);
        inputTextPanel.setBounds(30, 115, 197, 69);

    }

    /**
     * Creates and returns a new JPanel with specified dimensions and background color.
     *
     * @param x         The x position of the panel.
     * @param y         The y position of the panel.
     * @param width     The width of the panel.
     * @param height    The height of the panel.
     * @param background The background color of the panel.
     * @return A JPanel with the specified properties.
     */
    private JPanel createPanel(int x, int y, int width, int height, Color background) {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(x, y, width, height);
        panel.setBackground(background);
        return panel;
    }

    /**
     * Creates and returns a new JLabel with specified text, font, and color.
     *
     * @param text  The text to be displayed on the label.
     * @param font  The font of the label.
     * @param color The color of the label's text.
     * @return A JLabel with the specified properties.
     */
    private JLabel createLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(font);
        label.setForeground(color);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setBounds(0, 0, 280, 80); // Make sure to set the bounds to match the parent panel size
        return label;
    }

    /**
     * Sets the visibility of the manual modify dialog.
     *
     * @param b {@code true} to make the dialog visible, {@code false} otherwise.
     */
    public void setVisible(boolean b) {
        dialog.setVisible(b);
    }

    /**
     * Returns the modified word after the dialog is closed.
     *
     * @return The modified word.
     */
    public String returnModify(){
        return modify;
    }

    /*
    main method for test only
    public static void main(String[] args) {
        // Run the application
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new manual_modify();
            }
        });
    }
    */
}