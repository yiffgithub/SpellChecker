package frontendClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import backendClass.Document;

/**
 * The {@code suggestion} class provides a graphical user interface for suggesting word replacements in a document.
 * It displays a dialog where users can choose from a list of suggested words to replace a specific word at a given index.
 * This class manages the UI components for the suggestion and handles the replacement action.
 * @author Houyujie Lu
 */

public class suggestion  extends JDialog {

    //Declare two global private variables within this class
    private static JDialog dialog;
    private static String chooseWord = "";

    /**
     * Constructs a {@code suggestion} dialog.
     * This constructor initializes the GUI components for selecting a suggested word replacement.
     *
     * @param owner    The JFrame from which this dialog is displayed.
     * @param document The document where the word will be replaced.
     * @param index    The index of the word to be replaced in the document.
     * @param suggest  The array of suggested words.
     */
    public suggestion(JFrame owner, Document document, int index, String[] suggest) {
        //Window frame set up, use JDialog
        dialog = new JDialog(owner, "Suggested words", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setTitle("Suggested words");
        dialog.setSize(280, 345);
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(null);
        dialog.getContentPane().setBackground(Color.decode("#FDFDFD"));


        JPanel mainPanel = createPanel(0, 0, 280, 345, new Color(253, 253, 253));
        dialog.add(mainPanel);
        JPanel topTextPanel = createPanel(30, 20, 197, 69, Color.WHITE);
        mainPanel.add(topTextPanel);
        JLabel topTextLabel = createLabel("Suggestions", new Font("Cairo", Font.BOLD, 24), Color.decode("#6418C3"));
        topTextLabel.setBounds(30, 12, 165, 43);
        topTextPanel.add(topTextLabel);
        dialog.add(topTextPanel);

        JPanel inputTextPanel = createPanel(30, 115, 197, 69, Color.decode("#FFFFFF"));
        mainPanel.add(inputTextPanel);

        //Create JComboBox object for display and select from a list of suggest words
        JComboBox<String> select = new JComboBox<String>(suggest);
        select.setBounds(30, 12, 165, 43);
        select.setSelectedItem(" ");
        inputTextPanel.add(select);


        // Confirm button
        //accept what user select as a replacing word
        JButton confirmbutton = new JButton("Confirm");
        confirmbutton.setBounds(60, 250, 165, 50);
        confirmbutton.setFont(new Font("Cairo", Font.BOLD, 18));
        confirmbutton.setForeground(Color.WHITE);
        confirmbutton.setBackground(new Color(0x6418C3));
        confirmbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                chooseWord = (String) select.getSelectedItem();
            	int proceed = 0;
            	if((String) select.getSelectedItem()=="") {
            		proceed = JOptionPane.showConfirmDialog(null, "Confirm to delete the word!","warning!", JOptionPane.YES_NO_OPTION);
            		// check if the suggestion word list is empty, allow user to select whether to leave the panel or delete the word
            		if(proceed == JOptionPane.YES_OPTION) {

                        document.replaceWordAtIndex(index, (String) select.getSelectedItem());
            			dialog.dispose(); // Close the window when the confirm button is clicked
            		}
            	}else {
                document.replaceWordAtIndex(index, (String) select.getSelectedItem());
                dialog.dispose(); // Close the window when the confirm button is clicked
            	}  
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
     * Sets the visibility of the suggestion dialog.
     *
     * @param b {@code true} to make the dialog visible, {@code false} otherwise.
     */
    public void setVisible(boolean b) {
        dialog.setVisible(b);
    }
    }
