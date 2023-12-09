package frontendClass;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import backendClass.Dictionary;

/**
 * The {@code deleteDictionary} class provides a graphical user interface to remove words from a dictionary.
 * It displays a window where users can enter a word to be deleted from the dictionary.
 * The class is responsible for handling the user interaction and updating the dictionary accordingly.
 * @author Houyujie Lu
 */

public class deleteDictionary {

    //Declare two global private variables within this class
    private static JFrame frame;
    private static String word;

    /**
     * Constructs a {@code deleteDictionary} window.
     * This constructor initializes the GUI components and sets up the event handling for the delete operation.
     *
     * @param dictionary The dictionary from which words will be deleted.
     **/
    public deleteDictionary(Dictionary dictionary) {

        // Below are the window set up by using JFrame, JtextLabel and JTextField
        frame = new JFrame("Dictionary Edit");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(805, 554);
        frame.setLayout(null);

        // This is the main panel that is used to group and formatting the test area and buttons
        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(0, 0, 805, 554);
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.decode("#F9F9F9"));

        JTextField searchTextField = new JTextField();
        searchTextField.setBounds(219, 86, 491, 68);
        searchTextField.setForeground(Color.decode("#AAAAAA"));
        searchTextField.setFont(new Font("Cairo", Font.BOLD, 16));
        searchTextField.setBorder(BorderFactory.createEmptyBorder());
        

        JLabel removeLabel = new JLabel("Remove");
        removeLabel.setFont(new Font("Cairo", Font.BOLD, 32));
        removeLabel.setForeground(Color.decode("#6418C3"));
        removeLabel.setBounds(84, 88, 140, 49);


        //Create JButton with ActionListening to close this page
        //Action event is to store the user input word and then add to user dictionary
        JButton doneButton = new JButton("Done Edit");
        doneButton.setBounds(331, 407, 186, 54);
        doneButton.setBackground(Color.decode("#6418C3"));
        doneButton.setForeground(Color.WHITE);
        doneButton.setFont(new Font("Cairo", Font.BOLD, 18));
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                word = searchTextField.getText();
                dictionary.removeWord(word, "user");
                frame.dispose();
                JOptionPane.showMessageDialog(frame, "The word is deleted!\nClose and Open the Dictionary " +
                        "to view new new version!");

            }
        });

        //Add everything to the panel and window frame
        mainPanel.add(searchTextField);
        mainPanel.add(removeLabel);
        mainPanel.add(doneButton);

        frame.add(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    /**
     * Sets the visibility of the delete dictionary window.
     *
     * @param b {@code true} to make the window visible, {@code false} otherwise.
     */
    public void setVisible(boolean b) {
        frame.setVisible(b);
    }

/*
    Additional commenting to the main method in this class, only for temp testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new deleteDictionary();
            }
        });
    }
*/

}