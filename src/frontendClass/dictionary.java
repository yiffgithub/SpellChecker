package frontendClass;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import backendClass.*;

/**
 * The {@code dictionary} class creates a modal dialog displaying the user's dictionary.
 * It allows users to view, delete, edit, and reset their stored vocabulary.
 * This dialog is part of the frontend user interface and interacts with the backend {@code Dictionary} class.
 * @author Houyujie Lu
 */

public class dictionary extends JDialog {

    //Declare two global private variables within this class
    private static JDialog dialog;
    private static ArrayList<String> word;


    /**
     * Constructs a {@code dictionary} dialog.
     * This constructor initializes the GUI components and sets up the event handling.
     *
     * @param owner The JFrame from which this dialog is displayed.
     */
    public dictionary(JFrame owner) {
        // instantiate a Dictionary object
        Dictionary dictionary = new Dictionary();
        //assign user dictionary to word
        word = new ArrayList<String>(dictionary.getUserWordList());

        // window frame set up
        dialog = new JDialog(owner, "Your Dictionary", Dialog.ModalityType.APPLICATION_MODAL);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setTitle("Your Dictionary");

        //The gridPanel is used for formatting display of the list of dictionary
        FixedSizeGridPanel wordsPanel = new FixedSizeGridPanel(100, 50, 5);

        //read and display the words using for loop
        for (String word : word) {
            wordsPanel.displayWord(word);
        }

        //create and add a scroll pane to the display area
        JScrollPane scrollPane = new JScrollPane(wordsPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);

        /*
        * Create three action button: delete, edit, and reset
        * Delete button is used to to take input word from user that they wish
        * to delete form the user dictionary
        * Edit button is for user to replace/modify the existing word
        * Reset is to clear the user dictionary
        */
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        // create a button for deleting the word that user input
        JButton deleteButton = new JButton("Delete Words");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteDictionary delete = new deleteDictionary(dictionary);
                delete.setVisible(true);
            }
        });
        footerPanel.add(deleteButton);

        JButton editButton = new JButton("Edit Words");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editDictionary edit = new editDictionary(dictionary);
                edit.setVisible(true);
            }
        });
        footerPanel.add(editButton);

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dictionary.resetUserDic();
                wordsPanel.removeAll(); // 清空所有单词
                wordsPanel.revalidate(); // 重新验证布局
                wordsPanel.repaint(); // 重新绘制
            }
        });
        footerPanel.add(resetButton);
        add(footerPanel, BorderLayout.SOUTH);

        //dispose frame when closed
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }



    /**
     * Inner class {@code FixedSizeGridPanel} is used to format the page in a grid list.
     * It provides a clearer view of the stored vocabulary for the user.
     */
    static class FixedSizeGridPanel extends JPanel {

        private final int cellWidth;
        private final int cellHeight;
        private final int columns;

        /**
         * Constructs a {@code FixedSizeGridPanel} with specified cell dimensions and number of columns.
         *
         * @param cellWidth  The width of each cell in the grid.
         * @param cellHeight The height of each cell in the grid.
         * @param columns    The number of columns in the grid.
         */
        public FixedSizeGridPanel(int cellWidth, int cellHeight, int columns) {
            this.cellWidth = cellWidth;
            this.cellHeight = cellHeight;
            this.columns = columns;
            setLayout(new GridLayout(0, columns, 10, 10));
        }

        /**
         * Displays a word in the grid panel.
         * Also with some output formatting
         * @param word The word to be displayed in the panel.
         */
        public void displayWord(String word) {
            JLabel label = new JLabel(word, SwingConstants.CENTER);
            label.setPreferredSize(new Dimension(cellWidth, cellHeight));
            label.setMaximumSize(new Dimension(cellWidth, cellHeight));
            label.setMinimumSize(new Dimension(cellWidth, cellHeight));
            label.setOpaque(true);
            label.setBackground(new Color(204, 153, 255, 128)); // Light purple color
            label.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.WHITE),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
            add(label);
        }
    }
}