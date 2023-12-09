package frontendClass;

//import files include the backend class
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import backendClass.*;
import backendClass.Document;

/**
 * The {@code mainpage} class is the main interface for a spell checking application.
 * It provides functionalities for opening and editing documents, checking for spelling errors,
 * and applying corrections. The class also includes UI elements for user interaction,
 * such as buttons for various actions and a text pane for displaying document content.
 * @author  Fan Yang
 */

public class mainpage {

    //Declare a few global variable for this main page

    private static mainpage instance = new mainpage();

    //create static frame
    private static JFrame frame;

    //create static  menu button
    private static JButton dashboardButton;
    private static JButton editDictionaryButton;
    private static JButton askHelpButton;
    private static JButton settingsButton;

    //create static correction bar button
    private static JButton ignoreOnceButton;
    private static JButton ignoreAllButton;
    private static JButton addDictButton;
    private static JButton manualModButton;
    private static JButton deleteButton;
    private static JButton suggestionButton;

    //additional features using JavaSwing
    private static JPanel purpleIndicator;
    private static JTextPane textPane;
    private static JComboBox suggestWord;

    //some variables for storing parameters
    private static String fileContent = "";
    private static String filePath = " ";
    private static String  errorWord = "";
    private static boolean remove = false;
    private static int type = -1;
    private static int index = -1;
    private static List<String> suggest;
    private static String[] toArraySuggest ;
    private static List<Integer> errors ;
    private static ArrayList<Integer> ignoreOnce;
    private static String typeToString = "";
    private static boolean isFinish =false;

    //text formatng obejcts
    private static StyledDocument doc;
    private static SimpleAttributeSet normal;
    private static SimpleAttributeSet boldRed;

    //create some objects that will be used
    private static Dictionary dictionary= new Dictionary();
    private static Detection detection = new Detection(dictionary);
    private static Document document = new Document();
    private static Correction correction = new Correction();
    private static Metrics metrics = new Metrics();




    /**
     * This is the UI mainpage of this Spelling check program
     * This program is written by:
     *
     * The UI is written by:
     *
     * This is a huge program that includes many functions
     *
     */
    public static void main(String[] args) {

        //set up the basic part for this Main page window frame
        JFrame frame = new JFrame("Main page"); // new Fframe，set the title as Main Page
        frame.pack();
        frame.setLocationRelativeTo(null);

        JButton CorrectionButton = new JButton("Start Correction"); // create a new button，set text"Correction Start"。
        CorrectionButton.setEnabled(false);
        CorrectionButton.setVisible(false);

        JButton CompleteButton = new JButton("Complete Corrections"); // create a new button，set text"Complete Correction"。
        CompleteButton.setEnabled(false);
        CompleteButton.setVisible(false);

        JButton OpenButton = new JButton("Open File"); // create a new button，set text"Open File"。
        OpenButton.setEnabled(true);
        OpenButton.setVisible(true);

        /*
         * The code below is for creating a JButton that opens the file when
         * user click it and the initial input file will be first displayed with
         * no warning yet. this is because we want to allow user to open another
         * file if they opened the wrong one
         */
        // detatils setting and formatting the open file Button
        OpenButton.setBounds(1024 - 250 - 250, 768 - 100, 200, 40);
        OpenButton.setBackground(Color.decode("#E8DFF2"));
        OpenButton.setForeground(Color.decode("#6418C3"));
        OpenButton.setFont(new Font("Cairo", Font.BOLD, 18));
        OpenButton.setBorderPainted(true);
        // add action listening
        OpenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // file chooser
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Choose a Text File");
                fileChooser.setAcceptAllFileFilterUsed(false); // not accepting all type of file
                // set only accept .txt file
                fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
                fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("HTML Files", "html", "htm"));

                // check if user selected a file
                int result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    //do a remove html tag check
                    int removeTag = JOptionPane.showConfirmDialog(null, "Do you want to ignore all HTML tags?", "Remove HTML tags", JOptionPane.YES_NO_OPTION);
                    if (removeTag == JOptionPane.YES_OPTION) {
                        remove = true;
                    }
                    File selectedFile = fileChooser.getSelectedFile(); // get file path
                    filePath = selectedFile.toString();// get file path as string
                    try {
                    	
                        fileContent = readFile(selectedFile); // read content from the file
                        createAndShowGUI();
                        document.loadDocument(filePath);
                        for (String word : document.getTextAsList()) {
                            try {
                                doc.insertString(doc.getLength(), word, normal);
                            } catch (BadLocationException ex) {
                                ex.printStackTrace();
                            }
                        }
//                        textPane.setText(fileContent);
                        applyThemePreference();
                    } catch (IOException ex) {
                        ex.printStackTrace(); // throw and catch exception
                        JOptionPane.showMessageDialog(frame, "Error reading file", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                }

                //check if file content is empty, if not set visible and enable correctionButton
                if (fileContent != "") {
                    CorrectionButton.setEnabled(true);
                    CorrectionButton.setVisible(true);
                }
                //do a metrics count clear
                metrics.setClear();
            }
        });
        frame.add(OpenButton); // add to frame

        // create an error type title label
        JLabel errorLabel = new JLabel("Error Label"); //create a new label to show the error type
        errorLabel.setBounds(1200, 100, 150, 20);
        errorLabel.setFont(new Font("Open Sans", Font.BOLD, 10));
        errorLabel.setForeground(Color.decode("#555555"));
        frame.add(errorLabel);


        /*
         * The code below is for creating a JButton that starts the correction process
         * The correction process start immediately after user clicked the button
         */
        CorrectionButton.setBounds(1024 - 275, 768 - 100, 250, 40);
        CorrectionButton.setBackground(Color.decode("#E8DFF2"));
        CorrectionButton.setForeground(Color.decode("#6418C3"));
        CorrectionButton.setFont(new Font("Cairo", Font.BOLD, 18));
        CorrectionButton.setBorderPainted(true);
        //create ActionListening button to start correction
        CorrectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //load document and call display function to display text
                document.loadDocument(filePath);
                ignoreOnce = new ArrayList<Integer>();
                display();
                errorLabel.setText(typeToString);
                //set open file button and correction button to null and disappear
                OpenButton.setEnabled(false);
                OpenButton.setVisible(false);
                CorrectionButton.setEnabled(false);
                CorrectionButton.setVisible(false);
                CompleteButton.setEnabled(true);
                CompleteButton.setVisible(true);
            }
        });
        frame.add(CorrectionButton);


        /*
         * This button invokes that to the end of the session and user wish to end
         * set the page to initialized state
         */
        CompleteButton.setBounds(1024 - 275, 768 - 100, 250, 40);
        CompleteButton.setBackground(Color.decode("#E8DFF2"));
        CompleteButton.setForeground(Color.decode("#6418C3"));
        CompleteButton.setFont(new Font("Cairo", Font.BOLD, 18));
        CompleteButton.setBorderPainted(true);

        //create ActionListening button to start correction
        CompleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //pop a warning message for user to select proceed to summary or not
                int proceed = JOptionPane.showConfirmDialog(null, "Do you wish to complete correction?\nWarning! you cannot go back corrections if select yes.", "Session End Warning!", JOptionPane.YES_NO_OPTION);
                if (proceed == JOptionPane.YES_OPTION) {
                    metrics.calculateMetrics(document);
                    mainpage mainPage = new mainpage();
                    String filepath = mainPage.getFilePath();
                    new summary(frame,metrics,document,mainPage);
                    OpenButton.setEnabled(true);
                    OpenButton.setVisible(true);
                    CompleteButton.setEnabled(false);
                    CompleteButton.setVisible(false);
                    //set type and index count back to -1
                    type = -1;
                    index = -1;
                    errorLabel.setText("Error Label");
                    textPane.setText("");
                }
            }
        });
        frame.add(CompleteButton);


        //Create New TextFile and to display the text with error word
        textPane = new JTextPane();
        textPane.setEditable(false);

        /*
         * This is the part of the UI design, not the main functions
         * set scroll pane in the text area
         */
        JScrollPane scrollPane = new JScrollPane(textPane);


        scrollPane.setBounds(340, 120, 1024 - 330 - 50, 768 - 80 - 50 - 100);
        frame.add(scrollPane);

        frame.setSize(1324, 768);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.decode("#F3F3F3"));

        /*
         * This is the part of the UI design, not the main functions
         * set some text labels of section title or menu indication
         */
        //spell checker label
        JLabel titleLabel = new JLabel("Spell Checker");
        titleLabel.setBounds(50, 10, 200, 30);
        titleLabel.setFont(new Font("Cairo", Font.BOLD, 24));
        frame.add(titleLabel);


        //main menu label
        JLabel mainMenuLabel = new JLabel("Main Menu");
        mainMenuLabel.setBounds(50, 100, 150, 20);
        mainMenuLabel.setFont(new Font("Open Sans", Font.BOLD, 16));
        mainMenuLabel.setForeground(Color.decode("#C7C7C7"));
        frame.add(mainMenuLabel);


        //correction option label
        JLabel correctionBarLabel = new JLabel("Correction Options");
        correctionBarLabel.setBounds(1050, 100, 150, 20);
        correctionBarLabel.setFont(new Font("Open Sans", Font.BOLD, 14));
        correctionBarLabel.setForeground(Color.decode("#C7C7C7"));
        frame.add(correctionBarLabel);



        //the purple indicator on the menu section
        purpleIndicator = new JPanel();
        purpleIndicator.setBackground(Color.decode("#6418C3")); // set background as purple
        purpleIndicator.setBounds(50, 140, 5, 40);
        frame.add(purpleIndicator);


        // create an Action listener for window open event
        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // when button clicked，call setActiveButton() function
                setActiveButton((JButton) e.getSource());
            }
        };


        /*
         * Set main menu function buttons with set
         * Button :dashboardButton, editDictionaryButton, askHelpButton,settingsButton
         * These buttons have similar set up, however with few differences
         * dashboardButton is only for indicating the page is in main
         * editDictionaryButton is for user can open the dictionary page
         * askHelpButton is for user can open the ask help page
         * settingsButton is for user can open the setting page
         */
        dashboardButton = createMenuItem("\uD83D\uDCBB Dashboard", "", 140, buttonListener);

        //   dashboardButton = createMenuItem("\u2699 Dashboard", "G:/javaswing-study/src/cs2212/com/dashboard(3030).png", 140, buttonListener);
        frame.add(dashboardButton); // 创建“Dashboard”按钮并添加到窗体上。

        editDictionaryButton = createMenuItem("\uD83D\uDCD6 Dictionary", null, 190, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //call function buttons
                setActiveButton(editDictionaryButton);
                openEditDictionPage();
            }
        });
        frame.add(editDictionaryButton);

        askHelpButton = createMenuItem("\u2753 Ask Help", null, 240, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setActiveButton(askHelpButton);
                openAskHelp();
            }
        });
        frame.add(askHelpButton);


        settingsButton = createMenuItem("\u2699 Settings", null, 290, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setActiveButton(settingsButton);
                openSettingsPage();
            }
        });
        frame.add(settingsButton);


        /*
         * Create a set of JButton for correction bar button These buttons are
         * different from the menu button because they have hover effects and
         * these button won't start working before the correction start button clicked
         * Button :ignoreOnceButton, ignoreAllButton, addDictButton,manualModButton, deleteButton, suggestButton
         */

        //to ignore the error word
        ignoreOnceButton = createCorrectionBar("Ignore Once", 140, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //re display the text again with updated content
                ignoreOnce.add(index);
                display();
                //set value into error label
                errorLabel.setText(typeToString);
                //call backend for counting
                metrics.ignoreOnce();
            }
        }, new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e) {
                // Mouse hover effect - change color;
                ignoreOnceButton.setForeground(Color.decode("#6418C3"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Mouse exit effect - change color back
                ignoreOnceButton.setForeground(Color.decode("#A5A5A5"));
            }

        });
        frame.add(ignoreOnceButton);

        //to ignore the error word the entire session
        ignoreAllButton = createCorrectionBar("Ignore Session", 190, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dictionary.addWord(errorWord,"ignoressession");
                //re display the text again with updated content
                display();
                //set value into error label
                errorLabel.setText(typeToString);
                //call backend for counting
                metrics.ignoreSession();
            }
        }, new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e) {
                // Mouse hover effect - change color
                ignoreAllButton.setForeground(Color.decode("#6418C3"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Mouse exit effect - change color back
                ignoreAllButton.setForeground(Color.decode("#A5A5A5"));
            }

        });
        frame.add(ignoreAllButton);

        //to add the error word to the user dictionary
        addDictButton = createCorrectionBar("Add Dictionary", 240, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //update the content in the original text by calling correction object
                correction.addToDic(errorWord, "user");
                //re display the text again with updated content
                display();
                //set value into error label
                errorLabel.setText(typeToString);
                //call backend for counting
                metrics.addToDic();
            }
        }, new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e) {
                // Mouse hover effect - change color
                addDictButton.setForeground(Color.decode("#6418C3"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Mouse exit effect - change color back
                addDictButton.setForeground(Color.decode("#A5A5A5"));

            }

        });
        frame.add(addDictButton);

        //to manually edit the error word
        manualModButton = createCorrectionBar("Manual Modify", 290, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //open manual_modify page so that user can enter for the word they want
                manual_modify modify = new manual_modify(frame, document, index);
                modify.setVisible(true);
                //re display the text again with updated content
                display();
                //set value into error label
                errorLabel.setText(typeToString);
                //call backend for counting
                metrics.manual();
            }
        }, new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e) {
                // Mouse hover effect - change color
                manualModButton.setForeground(Color.decode("#6418C3"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Mouse exit effect - change color back
                manualModButton.setForeground(Color.decode("#A5A5A5"));
            }

        });
        frame.add(manualModButton);

        //to delete error word or the double word
        deleteButton = createCorrectionBar("Delete word", 340, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                document.replaceWordAtIndex(index,"");
                //call display functions
                display();
                //set value into error label
                errorLabel.setText(typeToString);
                //call backend for counting
                metrics.deletion();
            }
        }, new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e) {
                // Mouse hover effect - change color
                deleteButton.setForeground(Color.decode("#6418C3"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Mouse exit effect - change color back
                deleteButton.setForeground(Color.decode("#A5A5A5"));
                errorLabel.setText(typeToString);
            }

        });
        frame.add(deleteButton);

        //to select from the suggested correction words
        
        suggestionButton = createCorrectionBar("Suggested Words", 390, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	//open suggestion page so that user can enter for the word they want
                suggestion s = new suggestion(frame, document, index, toArraySuggest);
                s.setVisible(true);
                //rer display bu calling display function
                display();
                //set and rene error label
                errorLabel.setText(typeToString);
                //call backend for counting
                metrics.suggestion();
                
            }
        }, new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e) {
                // Mouse hover effect - change color
                suggestionButton.setForeground(Color.decode("#6418C3"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Mouse exit effect - change color back
                suggestionButton.setForeground(Color.decode("#A5A5A5"));
            }

        });
        frame.add(suggestionButton);


        /*
         * Creating three panel using JPanel
         * Left, Top, Right panels for button background
         */
        JPanel topPanel = new JPanel();
        topPanel.setBounds(0, 0, 1920, 50);
        topPanel.setBackground(Color.WHITE);
        frame.add(topPanel);

        JPanel leftPanel = new JPanel();
        leftPanel.setBounds(0, 0, 300, 1080);
        leftPanel.setBackground(Color.WHITE);
        frame.add(leftPanel);

        JPanel rightPanel = new JPanel();
        rightPanel.setBounds(1024, 0, 300, 1080);
        rightPanel.setBackground(Color.WHITE);
        frame.add(rightPanel);

        frame.setVisible(true);

        setActiveButton(dashboardButton);
    }

    /**
     * Returns the file path of the currently loaded document.
     *
     * @return A string representing the file path.
     */
    public String getFilePath() {

        return filePath;
    }


    /**
     * Opens the settings page of the application.
     * Setting a window close listener which set the purple indicator back to dashboard
     */
    private static void openSettingsPage() {
        setting_page settings = new setting_page(frame,instance);
        settings.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                setActiveButton(dashboardButton); // set back the purple indicator back to dashboard
            }
        });
        settings.setVisible(true);
    }


    /**
     * Opens the dictionary page of the application.
     * Setting a window close listener which set the purple indicator back to dashboard
     */
    private static void openEditDictionPage() {
        dictionary dictionaryDialog = new dictionary(frame);
        dictionaryDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                setActiveButton(dashboardButton);
            }
        });
        dictionaryDialog.setVisible(true);
    }

    /**
     * Opens the ask help page of the application.
     * Setting a window close listener which set the purple indicator back to dashboard
     */
    private static void openAskHelp() {
        askHelp askHelpDialog = new askHelp(frame);
        askHelpDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                setActiveButton(dashboardButton);
            }
        });
        askHelpDialog.setVisible(true);
    }

    /**
     * Reads the content of a file and returns it as a string.
     * the function will throw exception for the file path selected
     *
     * @param file The file to be read.
     * @return The content of the file as a string.
     * @throws IOException if an I/O error occurs.
     */
    private static String readFile(File file) throws IOException {

        StringBuilder fileContent = new StringBuilder();

        // use try-with-resources create a bufferReader object
        try ( BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            // while loop the entire text file
            while ((line = reader.readLine()) != null) {
                fileContent.append(line).append("\n");
            }
        }
        // try-with-resources ends, automatically close bufferReader

        return fileContent.toString();//return file content as as string
    }


    /**
     * Creates a menu item button with specified properties.
     *
     * @param text The text of the button.
     * @param iconPath The path to the button's icon.
     * @param yPos The y-position of the button.
     * @param buttonListener The action listener for the button.
     * @return A JButton configured with the specified properties.
     */
    private static JButton createMenuItem(String text, String iconPath, int yPos, ActionListener buttonListener) {
        JButton button = new JButton(text); // text param
        //set up and formatting the button
        button.setIcon(new ImageIcon(iconPath)); // icon path
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setHorizontalTextPosition(SwingConstants.RIGHT);
        button.setIconTextGap(10); // 设置图标和文本之间的间距。
        button.setMargin(new Insets(0, 20, 0, 0));
        button.setBounds(60, yPos, 250, 40);
        button.setFont(new Font("Cairo", Font.BOLD, 18));
        button.setForeground(Color.decode("#A5A5A5"));
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.addActionListener(buttonListener); // button Action listener
        return button; // return button to return the button back to button object
    }


    /**
     * Creates a correction bar button with specified properties.
     *
     * @param text The text of the button.
     * @param yPos The y-position of the button.
     * @param buttonListener The action listener for the button.
     * @param buttonAdapter The mouse adapter for hover effects.
     * @return A JButton configured with the specified properties.
     */
    private static JButton createCorrectionBar(String text, int yPos, ActionListener buttonListener, MouseAdapter buttonAdapter) {
        JButton button = new JButton(text); // pass text as param
        //set up and formatting the button
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setHorizontalTextPosition(SwingConstants.RIGHT);
        button.setMargin(new Insets(0, 20, 0, 0));
        button.setBounds(1024, yPos, 250, 40);
        button.setFont(new Font("Cairo", Font.BOLD, 12));
        button.setForeground(Color.decode("#A5A5A5"));
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.addMouseListener(buttonAdapter); // Create action listening for this button
        button.addActionListener(buttonListener); // Create button listening for this button
        return button; // 返回创建的按钮。
    }

    /**
     * Sets the specified button as the active button in the UI.
     *
     * @param activeButton The button to be set as active.
     */
    private static void setActiveButton(JButton activeButton) {
        // 将所有按钮的前景颜色设置为默认颜色。
        dashboardButton.setForeground(Color.decode("#A5A5A5"));
        editDictionaryButton.setForeground(Color.decode("#A5A5A5"));
        askHelpButton.setForeground(Color.decode("#A5A5A5"));
        settingsButton.setForeground(Color.decode("#A5A5A5"));
        activeButton.setForeground(Color.decode("#6418C3")); // set color before activated
        purpleIndicator.setBounds(50, activeButton.getY(), 5, 40); // set color after activated
    }


    /**
     * This diplay function is the main API for connecting backend and frontend UI
     * call object from backend  class: Dictionary, Detection, Document to get errors
     * suggested word and updated file content
     *
     * Document style is implemented and used here to set error word red and bold.
     */
    private static void display(){

        //set and create new display in textPane
    	String[] temparray = {""};
        textPane.setText("");
        createAndShowGUI();
        errors = detection.checkDocument(ignoreOnce, document.getTextAsList(),remove);

        //check if the error detection is to the last one in user's text
        if(errors != null){
            index = errors.get(0);
            type = errors.get(1);
        for (int i = 0; i < document.getTextAsList().size(); i++) {
                try {
                    // Get the current word from the ArrayList
                    String word = document.getTextAsList().get(i);
                    // Insert the word into the document with the appropriate attributes
                    doc.insertString(doc.getLength(), word, word.equals("\n") ? normal : i == index ? boldRed : normal);
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
        }

            //for loop to store the error word
            for (int i = 0; i < document.getTextAsList().size(); i++){
                if (index == i){
                    errorWord = document.getTextAsList().get(i);
                }
            }

            // create error type  title label
            if (type == 0){
            	
                suggest = new ArrayList<String>(correction.generateSuggestions(errorWord));
                if (!suggest.isEmpty()) {
                	toArraySuggest =suggest.toArray(new String[suggest.size()]);
                }else {
                	toArraySuggest = temparray;
                }
                
                typeToString = "Misspellings";
                metrics.misspell();
            }else if (type == 1 || type == 2){
                suggest = new ArrayList<String> (correction.correctCap(type, errorWord));
                toArraySuggest =suggest.toArray(new String[suggest.size()]);
                typeToString = "Capitalization";
                metrics.miscap();
            }else if (type == 3){
                typeToString = "Duplicate";
                toArraySuggest = temparray;
                metrics.doubleword();
            }
        }else {
            textPane.setText("");
            for (String word : document.getTextAsList()) {
                try {
                    doc.insertString(doc.getLength(), word, normal);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }

    public static void changeTextStyle(int newSize, String fontName) {
        // new font object
        Font newFont = new Font(fontName, Font.PLAIN, newSize);

        // set textpane with new font
        textPane.setFont(newFont);

        // adjust the font style and size to selected
        textPane.setPreferredSize(new Dimension(textPane.getWidth(), textPane.getHeight()));

        //  refresh UI with new font
        textPane.revalidate();
        textPane.repaint();
    }


    /**
     * Load the user's theme preference from the configured file
     */
    public static String loadThemePreference() {
        Properties props = new Properties();
        String filePath = "src/frontendClass/user_preferences.properties";

        try (FileInputStream in = new FileInputStream(filePath)) {
            props.load(in);
            return props.getProperty("theme", "default");

        } catch (IOException e) {
            e.printStackTrace();
            return "default"; // 发生错误时返回默认主题
        }
    }

    /**
     * Creates and displays the main GUI of the spell checking application.
     */
    private static void createAndShowGUI(){
        // Get the StyledDocument from the JTextPane
        doc = textPane.getStyledDocument();

        // Define two sets of attributes: one for normal text and one for bold and red text
        normal = new SimpleAttributeSet();
        boldRed = new SimpleAttributeSet();
        StyleConstants.setBold(boldRed, true);
        StyleConstants.setForeground(boldRed, Color.RED);
    }


    /**
     * Applies theme preferences to the text pane.
     */
    private static void applyThemePreference() {
        String theme = loadThemePreference(); // add theme preference

        // name theme
        switch (theme) {
            case "Theme1":
                changeTextStyle(24, "Georgia");
                break;
            case "Theme2":
                changeTextStyle(32, "Times New Roman");
                break;
            default:
                // default theme
                changeTextStyle(20, "Arial");
                break;
        }
    }
}