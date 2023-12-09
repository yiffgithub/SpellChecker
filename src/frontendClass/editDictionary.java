package frontendClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import backendClass.Dictionary;

/**
 * The {@code editDictionary} class provides a graphical user interface to edit words in a dictionary.
 * It allows users to replace a word with a new word in the dictionary.
 * This class manages the UI components for searching and replacing words.
 * @author Fan Yang
 */
public class editDictionary{

    //Declare two global private variables within this class
    private static String oldWord= "";
    private static String newWord ="";
    private JFrame frame;

    /**
     * Constructs an {@code editDictionary} window.
     * This constructor initializes the UI components for editing words in the dictionary.
     *
     * @param dictionary The dictionary instance to be edited.
     */
    public editDictionary(Dictionary dictionary) {

        // Initialize the main application frame
        this.frame = new JFrame("Dictionary edit");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(805, 554);
        frame.setLayout(null);

        // Set up the main panel with a custom rounded panel class
        JPanel mainPanel = new RoundedPanel(14);
        mainPanel.setBounds(0, 0, 805, 554);
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.decode("#F9F9F9"));

        // Configure the search panel with rounded corners
        JPanel searchPanel = new RoundedPanel(46);
        searchPanel.setBounds(219, 86, 491, 68);
        searchPanel.setLayout(null);
        searchPanel.setBackground(Color.WHITE);

        // Configure the "with" panel for word replacement
        JPanel withPanel = new RoundedPanel(46);
        withPanel.setBounds(219, 253, 491, 68);
        withPanel.setLayout(null);
        withPanel.setBackground(Color.WHITE);

        // Define the search text field with a placeholder
        JTextField searchTextField = new PlaceholderTextField("Search for replacement");
        searchTextField.setBounds(86, 19, 391, 30);
        searchTextField.setForeground(Color.decode("#AAAAAA"));
        searchTextField.setFont(new Font("Cairo", Font.BOLD, 16));
        searchTextField.setOpaque(false);
        searchTextField.setBorder(BorderFactory.createEmptyBorder());

        // Define the "with" text field with a placeholder
        JTextField withTextField = new PlaceholderTextField("New word");
        withTextField.setBounds(86, 19, 391, 30);
        withTextField.setForeground(Color.decode("#AAAAAA"));
        withTextField.setFont(new Font("Cairo", Font.BOLD, 16));
        withTextField.setOpaque(false);
        withTextField.setBorder(BorderFactory.createEmptyBorder());

        // Add the text fields to their respective panels
        searchPanel.add(searchTextField);
        withPanel.add(withTextField);

        // Create and style the "Replace" label
        JLabel replaceLabel = new JLabel("Replace");
        replaceLabel.setFont(new Font("Cairo", Font.BOLD, 32));
        replaceLabel.setForeground(Color.decode("#6418C3"));
        replaceLabel.setBounds(84, 88, 140, 49);

        // Create and style the "With" label
        JLabel withLabel = new JLabel("With");
        withLabel.setFont(new Font("Cairo", Font.BOLD, 32));
        withLabel.setForeground(Color.decode("#6418C3"));
        withLabel.setBounds(108, 253, 122, 49);

        // Initialize the "Done Edit" button with action to close the frame
        //Action event is to store the user input word and then add to user dictionary
        JButton doneButton = new RoundedButton("Done Edit");
        doneButton.setBounds(331, 407, 186, 54);
        doneButton.setBackground(Color.decode("#6418C3"));
        doneButton.setForeground(Color.WHITE);
        doneButton.setFont(new Font("Cairo", Font.BOLD, 18));
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                oldWord = searchTextField.getText();
                newWord = withTextField.getText();
                
             // Check if newWord is null or empty
                if (newWord == null || newWord.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Can't replace with an empty word!", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    // Existing logic to replace the word and close the frame
                    dictionary.replaceWordForUserDic(newWord, oldWord);
                    frame.dispose();
                    JOptionPane.showMessageDialog(frame, "The word is replaced!\nClose and Open the Dictionary to view new version!");
                }
            }
        });

        // Add UI components to the main panel
        mainPanel.add(searchPanel);
        mainPanel.add(withPanel);
        mainPanel.add(replaceLabel);
        mainPanel.add(withLabel);
        mainPanel.add(doneButton);

        // Place the main panel inside the frame
        frame.add(mainPanel);
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setVisible(true);
    }

    /**
     * Sets the visibility of the edit dictionary window.
     *
     * @param b {@code true} to make the window visible, {@code false} otherwise.
     */
    public void setVisible(boolean b) {
        frame.setVisible(b);
    }


    /**
     * Inner class {@code RoundedPanel} is a custom JPanel with rounded corners.
     * It supports optional shadow and high-quality rendering.
     */
    static class RoundedPanel extends JPanel {
        protected int strokeSize = 1;
        protected Color shadowColor = Color.black;
        protected boolean shady = true;
        protected boolean highQuality = true;
        protected Dimension arcs;
        protected int shadowGap = 5;
        protected int shadowOffset = 4;
        protected int shadowAlpha = 150;

        RoundedPanel(int radius) {
            super();
            setOpaque(false);
            arcs = new Dimension(radius, radius);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int width = getWidth();
            int height = getHeight();
            Graphics2D graphics = (Graphics2D) g;

            // Apply antialiasing if highQuality is true
            if (highQuality) {
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            }

            // Paint shadow beneath the rounded rectangle if shady is true
            if (shady) {
                graphics.setColor(shadowColor);
                graphics.fillRoundRect(
                        shadowOffset, shadowOffset,
                        width - strokeSize - shadowOffset,
                        height - strokeSize - shadowOffset,
                        arcs.width, arcs.height);
            } else {
                shadowGap = 1;
            }

            // Paint the background of the rounded rectangle
            graphics.setColor(getBackground());
            graphics.fillRoundRect(0, 0, width - shadowGap,
                    height - shadowGap, arcs.width, arcs.height);

            // Draw the border of the rounded rectangle
            graphics.setColor(getForeground());
            graphics.setStroke(new BasicStroke(strokeSize));
            graphics.drawRoundRect(0, 0, width - shadowGap,
                    height - shadowGap, arcs.width, arcs.height);
            graphics.setStroke(new BasicStroke());
        }
    }

    /**
     * Inner class {@code RoundedButton} is a custom JButton with rounded corners.
     */
    static class RoundedButton extends JButton {
        RoundedButton(String label) {
            super(label);
            setContentAreaFilled(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            // Change color when button is pressed
            if (getModel().isArmed()) {
                g.setColor(Color.lightGray);
            } else {
                g.setColor(getBackground());
            }
            g.fillRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 14, 14);
            super.paintComponent(g);
        }

        @Override
        protected void paintBorder(Graphics g) {
            // Paint border of the button
            g.setColor(getForeground());
            g.drawRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 14, 14);
        }
    }

    /**
     * Inner class {@code PlaceholderTextField} is a custom JTextField with placeholder functionality.
     */
    static class PlaceholderTextField extends JTextField implements FocusListener {
        private final String placeholder;
        private boolean showingPlaceholder;

        public PlaceholderTextField(final String pPlaceholder) {
            super(pPlaceholder);
            placeholder = pPlaceholder;
            showingPlaceholder = true;
            super.addFocusListener(this);
            this.setForeground(Color.decode("#AAAAAA"));
        }

        @Override
        public void focusGained(FocusEvent e) {
            // Clear the placeholder text when the field gains focus
            if (showingPlaceholder) {
                super.setText("");
                showingPlaceholder = false;
                this.setForeground(Color.BLACK);
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            // Set the placeholder text if the field is empty when it loses focus
            if (super.getText().isEmpty()) {
                super.setText(placeholder);
                showingPlaceholder = true;
                this.setForeground(Color.decode("#AAAAAA"));
            }
        }

        @Override
        public String getText() {
            // Return an empty string if the placeholder is showing
            return showingPlaceholder ? "" : super.getText();
        }
    }
}