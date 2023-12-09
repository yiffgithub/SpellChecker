package frontendClass;

import backendClass.Document;
import backendClass.Metrics;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * The {@code summary} class provides a user interface to display a summary of document analysis.
 * It shows various metrics like character count, word count, error counts, and corrections made.
 * The class also provides options to save, discard changes, or save as a new file.
 * @author Fan Yang
 */
public class summary extends JDialog{

    //Declare some global private variables and objects within this class
    private JDialog dialog;
    private JButton discardButton;
    private JButton saveButton;
    private JButton saveAsButton;
    private Document document;
    private mainpage mainPage;

    /**
     * Constructs a {@code summary} dialog.
     * This constructor initializes the GUI components and sets up the event handling for displaying the summary.
     *
     * @param owner The JFrame from which this dialog is displayed.
     * @param metrics The Metrics object containing information about the document.
     * @param document The Document object representing the text document.
     * @param mainPage The main page of the application for interaction.
     */
    public summary(JFrame owner, Metrics metrics, Document document, mainpage mainPage){
        this.mainPage=mainPage;
        //Metrics metrics = new Metrics();
        this.document = document;
        discardButton = new JButton("\u26A0 Discard Change");
        saveButton = new JButton("\uD83D\uDCBE Save");
        saveAsButton = new JButton("\uD83D\uDCBE Save As");
        dialog = new JDialog(owner, "Summary Panel", Dialog.ModalityType.MODELESS);
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog.setSize(962, 779);
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(null);
        dialog.setTitle("Summary Panel");

        // 主背景面板
        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(0, 0, 962, 779);
        mainPanel.setBackground(new Color(0xF6EEFF));
        mainPanel.setLayout(null);

        JPanel panel1 = new JPanel();
        panel1.setBounds(0, 0, 962, 779);
        panel1.setBackground(new Color(0xF6EEFF));
        panel1.setBorder(BorderFactory.createLineBorder(Color.decode("#F5F5F5")));
        panel1.setLayout(null);


        /*
         * Below are labels and textfield used to display some word counting
         * number of characters
         * number of lines
         * number of words
         */
        JLabel Numberofcharacters = new JLabel("Number of characters: " + metrics.getCharacterCount());
        Numberofcharacters.setFont(new Font("Cairo", Font.BOLD, 24));
        Numberofcharacters.setForeground(Color.decode("#202020"));
        Numberofcharacters.setBounds(93, 30 + 20, 350, 25);
        panel1.add(Numberofcharacters);

        JLabel Numberoflines = new JLabel("Number of lines: " + metrics.getLineCount());
        Numberoflines.setFont(new Font("Cairo", Font.BOLD, 24));
        Numberoflines.setForeground(Color.decode("#202020"));
        Numberoflines.setBounds(93, 60 + 20, 350, 25);
        panel1.add(Numberoflines);

        JLabel Numberofwords = new JLabel("Number of words: " + metrics.getWordCount());
        Numberofwords.setFont(new Font("Cairo", Font.BOLD, 24));
        Numberofwords.setForeground(Color.decode("#202020"));
        Numberofwords.setBounds(93, 90 + 20, 350, 25);
        panel1.add(Numberofwords);


        /*
         * Below are labels and textfield used to display the number of different types of Error detected
         */
        JLabel ErrorsDetected = new JLabel("\u2717 Errors Detected:");
        ErrorsDetected.setFont(new Font("Cairo", Font.BOLD, 24));
        ErrorsDetected.setForeground(Color.RED);
        //ErrorsDetected.setForeground(Color.decode("#202020"));
        ErrorsDetected.setBounds(93, 190 + 20, 250, 25);
        panel1.add(ErrorsDetected);

        JLabel Misspellings = new JLabel("\u2717 Misspellings: " + metrics.getMisspellingsCount());
        Misspellings.setFont(new Font("Cairo", Font.BOLD, 20));
        Misspellings.setForeground(Color.decode("#202020"));
        Misspellings.setBounds(123, 220 + 20, 350, 25);
        panel1.add(Misspellings);
        JLabel Miscapitalizations = new JLabel("\u2717 Miscapitalizations: " + metrics.getMiscapitalizationsCount());
        Miscapitalizations.setFont(new Font("Cairo", Font.BOLD, 20));
        Miscapitalizations.setForeground(Color.decode("#202020"));
        Miscapitalizations.setBounds(123, 250 + 20, 350, 25);
        panel1.add(Miscapitalizations);
        JLabel Doublewordsencountered = new JLabel("\u2717 Double words: " + metrics.getDoubleWordsCount());
        Doublewordsencountered.setFont(new Font("Cairo", Font.BOLD, 20));
        Doublewordsencountered.setForeground(Color.decode("#202020"));
        Doublewordsencountered.setBounds(123, 280 + 20, 350, 25);
        panel1.add(Doublewordsencountered);

        /*
        * Below are labels and textfield used to display the number of different types of correction made
        */
        JLabel CorrectionsMade = new JLabel("\u2713 Corrections Made:");
        CorrectionsMade.setFont(new Font("Cairo", Font.BOLD, 24));
        CorrectionsMade.setForeground(Color.GREEN);
        // CorrectionsMade.setForeground(Color.decode("#202020"));
        CorrectionsMade.setBounds(93, 400, 250, 25);
        panel1.add(CorrectionsMade);

        JLabel Manualcorrections = new JLabel("\u2713 Manual corrections: " + metrics.getManualCorrectionsCount());
        Manualcorrections.setFont(new Font("Cairo", Font.BOLD, 20));
        Manualcorrections.setForeground(Color.decode("#202020"));
        Manualcorrections.setBounds(123, 430, 350, 25);
        panel1.add(Manualcorrections);
        JLabel Acceptedsuggestions = new JLabel("\u2713 Suggestions: " + metrics.getAcceptedSuggestionsCount());
        Acceptedsuggestions.setFont(new Font("Cairo", Font.BOLD, 20));
        Acceptedsuggestions.setForeground(Color.decode("#202020"));
        Acceptedsuggestions.setBounds(123, 460, 350, 25);
        panel1.add(Acceptedsuggestions);
        JLabel Worddeletions = new JLabel("\u2713 Word deletions: " + metrics.getWordDeletionsCount());
        Worddeletions.setFont(new Font("Cairo", Font.BOLD, 20));
        Worddeletions.setForeground(Color.decode("#202020"));
        Worddeletions.setBounds(123, 490, 350, 25);
        panel1.add(Worddeletions);
        JLabel Addtodictionary = new JLabel("\u2713 Add to dictionary: " + metrics.getAddToDicCount());
        Addtodictionary.setFont(new Font("Cairo", Font.BOLD, 20));
        Addtodictionary.setForeground(Color.decode("#202020"));
        Addtodictionary.setBounds(123, 520, 350, 25);
        panel1.add(Addtodictionary);
        JLabel ignoreOnce =  new JLabel("\u2713 Ignored Once: " + metrics.getIgnoreOnceCount());
        ignoreOnce.setFont(new Font("Cairo", Font.BOLD, 20));
        ignoreOnce.setForeground(Color.decode("#202020"));
        ignoreOnce.setBounds(123, 550, 350, 25);
        panel1.add(ignoreOnce);
        JLabel ignoreSession = new JLabel("\u2713 Ignore Session: "  + metrics.getIgnoreSessionCount());
        ignoreSession.setFont(new Font("Cairo", Font.BOLD, 20));
        ignoreSession.setForeground(Color.decode("#202020"));
        ignoreSession.setBounds(123, 580, 350, 25);
        panel1.add(ignoreSession);



        /*
         * instantiate 3 JButton obeject, each button contains an action event
         */

        //discard button when user want to give up all the editing
        //user can click cancel if they wish to save the file
        discardButton.setFont(new Font("Cairo", Font.BOLD, 18));
        discardButton.setForeground(Color.WHITE);
        discardButton.setBackground(new Color(0x6418C3));
        discardButton.setBounds(700, 580, 200, 40);
        mainPanel.add(discardButton);
        discardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                discardButton.setEnabled(false);
                saveButton.setEnabled(false);
                saveAsButton.setEnabled(false);
                Warning_Panel.showWarning(dialog, new Warning_Panel.WarningPanelListener() {
                    @Override
                    public void onPanelClosed() {

                        System.out.println("Warning panel closed, enabling buttons.");
                        enableButtons();
                    }
                });


            }
        });


        //Save Button allow user to save the corrected text into original file
        saveButton.setFont(new Font("Cairo", Font.BOLD, 18));
        saveButton.setForeground(Color.WHITE);
        saveButton.setBackground(new Color(0x6418C3));
        saveButton.setBounds(700, 580 + 50, 200, 40);
        mainPanel.add(saveButton);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                metrics.calculateMetrics(document);
                String filepath = mainPage.getFilePath();
                File file = new File(filepath);
                //  System.out.println(file.getPath());
                System.out.println(file.getAbsolutePath());

                //document.saveDocument(file.getPath());
                document.saveDocument(file.getAbsolutePath());
                discardButton.setEnabled(false);
                saveButton.setEnabled(false);
                saveAsButton.setEnabled(false);

            }
        });

        //Save as Button allow user to save the corrected text into a new file
        saveAsButton.setFont(new Font("Cairo", Font.BOLD, 18));
        saveAsButton.setForeground(Color.WHITE);
        saveAsButton.setBackground(new Color(0x6418C3));
        saveAsButton.setBounds(700, 580 + 100, 200, 40);  // 调整位置和大小
        mainPanel.add(saveAsButton);
        saveAsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showSaveDialog(dialog) == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    document.saveDocument(file.getAbsolutePath());
                    discardButton.setEnabled(false);
                    saveButton.setEnabled(false);
                    saveAsButton.setEnabled(false);
                }
            }

        });


        dialog.setLocationRelativeTo(null);
        mainPanel.add(panel1);
        dialog.add(mainPanel);

        //make this dialog visible when window opens
        dialog.setVisible(true);

    }


    /**
     * Enables the buttons on the summary panel.
     */
    public void enableButtons() {
        discardButton.setEnabled(true);
        saveButton.setEnabled(true);
        saveAsButton.setEnabled(true);
    }


  /* Main method only used for testing
    private static void saveToFile(File file) {
        try {
            FileWriter fileWriter = new FileWriter(file);

            fileWriter.write("save");
            fileWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }*/


}