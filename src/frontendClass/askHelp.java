package frontendClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowListener;

public class askHelp {

    private JDialog dialog;
    private JTextArea textArea;

    public askHelp(Frame owner) {
        dialog = new JDialog(owner, "Ask Help", Dialog.ModalityType.APPLICATION_MODAL);
        textArea = new JTextArea(getHelpText());
        textArea.setEditable(false);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setBackground(new Color(0xF6EEFF));
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog.setSize(962, 779);

        setupUI();
    }

    private void setupUI() {
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(0xF6EEFF));

        setupTitleLabel(contentPanel);
        /*       setupImageLabel(contentPanel);*/
        setupTextArea(contentPanel);
        setupCloseButton(contentPanel);

        dialog.add(contentPanel);
        dialog.setLocationRelativeTo(null);
    }

    private void setupTitleLabel(Container container) {
        JLabel titleLabel = new JLabel("How to use it?");
        titleLabel.setFont(new Font("Cairo", Font.BOLD, 34));
        container.add(titleLabel, BorderLayout.NORTH);
    }

    private void setupCloseButton(Container container) {
        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Open Sans", Font.PLAIN, 32));
        closeButton.setBackground(new Color(0x6418C3));
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);

        closeButton.addActionListener(e -> dialog.dispose());

        container.add(closeButton, BorderLayout.SOUTH);
    }

    /* private void setupImageLabel(Container container) {
         String imagePath = "G:/javaswing-study/src/cs2212/com/demo-pic.png";
         ImageIcon imageIcon = new ImageIcon(imagePath);
         JLabel imageLabel = new JLabel(imageIcon);
         container.add(imageLabel, BorderLayout.CENTER);
     }
 */
    private void setupTextArea(Container container) {
        JScrollPane scrollPane = new JScrollPane(textArea);

        container.add(scrollPane, BorderLayout.CENTER);
    }

    public void setVisible(boolean b) {
        dialog.setVisible(b);
    }

    public void addWindowListener(WindowListener listener) {
        dialog.addWindowListener(listener);
    }

    private String getHelpText() {
        return "Q1: What is the purpose of the Spell Checker application?\n"
                + "A1: The Spell Checker application is a specialized tool designed for checking the spelling of text documents. It scans each word against a dictionary and offers correction options for any detected spelling mistakes. Its primary goal is to produce documents free of errors, thereby enhancing the professionalism and accuracy of written content.\n\n"
                + "Q2: How do I get started with the Spell Checker application?\n"
                + "A2: To start using the application, follow these steps:\n"
                + "1. Launch Application: Open the Spell Checker on your device. It is highly recommended for use on Windows systems.\n"
                + "2. Open File: Click the 'Open File' button on the main page to load a text file (.txt) for spell checking.\n"
                + "3. Filter Option: Choose whether to filter out HTML tags when loading the document. Selecting \"yes\" will exclude HTML tags from the spell check.\n\n"
                + "Q3: Can you describe the spell checking process in the application?\n"
                + "A3: The spell checking is a linear process:\n"
                + "1. Correction Start: Initiate spell checking by clicking the 'Correction Start' button.\n"
                + "2. Error Highlighting: The application highlights each spelling error in red and bold, focusing on one word at a time.\n"
                + "3. Error Types: Errors are categorized into misspellings, miscapitalization, and duplicate words, the types of errors will be displayed on the top right corner.\n"
                + "4. Correction Options: Options include ignoring errors(ignore once and ignore session), adding to the dictionary, manual modification, deleting words, and suggested corrections.\n"
                + "5. Correction Complete: Finalize the corrections with this button. Note that this action is irreversible.\n\n"
                + "Q4: What information does the Summary panel provide after spell checking?\n"
                + "A4: The Summary panel provides:\n"
                + "1. Document Data: Counts of characters, lines(number of returned lines), and words in the document.\n"
                + "2. Errors Detected: Number of each type of error found.\n"
                + "3. Corrections Made: Number of each type of correction applied.\n\n"
                + "Q5: What are the options in the Summary Process?\n"
                + "A5: They include:\n"
                + "1. Save: Saves corrections to the original file.\n"
                + "2. Save As: Saves the corrections as a new file, leaving the original unchanged.\n"
                + "3. Discard Changes: Discards changes with a warning. Confirming returns to the main interface; cancelling allows for reconsideration.\n"
                + "4. Click on the close button on the summary panel to go back to the main page.\n\n"
                + "Q6: What features does the Dictionary in the Spell Checker offer?\n"
                + "A6: The Dictionary feature allows users to personalize their vocabulary list. It includes:\n"
                + "1. Reset: Clears all existing words in the dictionary.\n"
                + "2. Edit Words: Enables modification of an existing word. Enter the original word and its replacement in the provided text panels.\n"
                + "3. Delete: Allows for the removal of a specific word from the dictionary.\n"
                + "4. Dictionary Updates: Changes made will be visible after closing and reopening the dictionary page.\n\n"
                + "Q7: What additional features does the application have?\n"
                + "A7: Additional features are:\n"
                + "1. Settings: Customization of the font size and font style.\n"
                + "2. Ask Help: Access to instructions for using the application.\n"
                + "3. Compatibility: Optimal for Windows operating systems.\n"
                + "4. Data Security: Reminder to save or discard documents before exiting to prevent data loss.\n\n"
                + "Q8: What if the drop down menu bar is empty?\n"
                + "A8: If there are no suggestions for an error, the drop down menu bar of the \"suggested words\" will be blank.\n";
    }
}

