package frontendClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;


/**
 * The {@code setting_page} class creates a user interface for setting preferences in an application.
 * It allows users to select and apply different themes and settings for the main application page.
 * This class manages the creation of UI components for the settings and handles user interactions.
 * @author Fan Yang
 */
public class setting_page {
    private mainpage mainPage;

    //Declare two global private variables within this class
    private JFrame frame;
    private JDialog dialog;

    /**
     * Constructs a {@code setting_page} dialog.
     * This constructor initializes the GUI components for the settings page and configures event handling.
     *
     * @param owner    The frame from which the dialog is displayed.
     * @param mainPage The main page of the application where settings will be applied.
     */
    public setting_page(Frame owner,mainpage mainPage) {

        //instantiate the parent page - mainpage in this sub page
        this.mainPage = mainPage;

        //Setting this sub window with dialog, labels, and panels
        dialog = new JDialog(owner, "Setting", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setSize(962, 779);
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(null);

        JLabel settingLabel = new JLabel("Setting");
        settingLabel.setBounds(50, 40, 200, 40);
        settingLabel.setFont(new Font("Segoe UI", Font.BOLD, 34));
        dialog.add(settingLabel);

        JLabel userCustomLabel = new JLabel("User customizable themes");
        userCustomLabel.setBounds(50, 102, 300, 20);
        userCustomLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        userCustomLabel.setForeground(new Color(0xA5A5A5));
        dialog.add(userCustomLabel);

        JPanel theme1Panel = new JPanel();
        theme1Panel.setBounds(50, 146, 862, 275);
        theme1Panel.setBackground(new Color(0, 0, 0, 0x0A));
        dialog.add(theme1Panel);

        JLabel theme1Label = new JLabel("Theme 1");
        theme1Label.setBounds(81, 151, 130, 40);
        theme1Label.setFont(new Font("Segoe UI", Font.BOLD, 32));
        dialog.add(theme1Label);

        JLabel fontSize1Label = new JLabel("Font size: Middle");
        fontSize1Label.setBounds(158, 226, 200, 20);
        fontSize1Label.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        dialog.add(fontSize1Label);

        JLabel highlightColor1Label = new JLabel("Font name: Georgia");
        highlightColor1Label.setBounds(158, 264, 300, 30);
        highlightColor1Label.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        dialog.add(highlightColor1Label);

        JPanel theme2Panel = new JPanel();
        theme2Panel.setBounds(50, 450, 862, 275);
        theme2Panel.setBackground(new Color(0, 0, 0, 0x0A));
        dialog.add(theme2Panel);

        JLabel theme2Label = new JLabel("Theme 2");
        theme2Label.setBounds(81, 455, 130, 40);
        theme2Label.setFont(new Font("Segoe UI", Font.BOLD, 32));
        dialog.add(theme2Label);

        JLabel fontSize2Label = new JLabel("Font size: Large");
        fontSize2Label.setBounds(158, 530, 200, 30);
        fontSize2Label.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        dialog.add(fontSize2Label);

        JLabel highlightColor2Label = new JLabel("Font name: Times New Roman");
        highlightColor2Label.setBounds(158, 568, 300, 20);
        highlightColor2Label.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        dialog.add(highlightColor2Label);


        /*
        * Creating two theme button and allow some action listening to be performed
        * The action event inside the ActionListening is to change the font and size
        *   in the text area
        * */
        //button1
        JButton setThemeButton1 = new JButton("Set theme");
        setThemeButton1.setBounds(688, 341, 200, 54);
        setThemeButton1.setFont(new Font("Segoe UI", Font.PLAIN, 32));
        setThemeButton1.setBackground(new Color(0x6418C3));
        setThemeButton1.setForeground(Color.WHITE);
        setThemeButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mainPage != null) {
                    mainPage.changeTextStyle(24, "Georgia");
                }
                saveThemePreference("Theme1");
                dialog.dispose(); // 关闭对话框
            }
        });

        // button2
        JButton setThemeButton2 = new JButton("Set theme");
        setThemeButton2.setBounds(688, 646, 200, 54);
        setThemeButton2.setFont(new Font("Segoe UI", Font.PLAIN, 32));
        setThemeButton2.setBackground(new Color(0x6418C3));
        setThemeButton2.setForeground(Color.WHITE);
        setThemeButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mainPage != null) {
                    mainPage.changeTextStyle(32, "Times New Roman");
                }
                saveThemePreference("Theme2");
                dialog.dispose(); // 关闭对话框
            }
        });

        // add everything onto the this sub frame
        dialog.add(setThemeButton2);
        dialog.add(setThemeButton1);


        JPanel mainBg = new JPanel();
        mainBg.setBounds(0, 0, 962, 779);
        mainBg.setBackground(new Color(0xF6EEFF));
        dialog.add(mainBg);


        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    /**
     * Sets the visibility of the settings page dialog.
     *
     * @param b {@code true} to make the dialog visible, {@code false} otherwise.
     */
    public void setVisible(boolean b) {
        dialog.setVisible(b);
    }

    /**
     * Changes the text size and font style in the main application page.
     *
     * @param newSize  The new size of the font.
     * @param fontName The name of the font to be applied.
     */
    public void changeTextSize(int newSize, String fontName) {
        mainpage.changeTextStyle(newSize, fontName);
    }


    /**
     * Adds a window listener to the settings page dialog.
     * This is for allowing the program to perform specified actions after this window closed
     * @param listener The window listener to be added.
     */
    public void addWindowListener(WindowListener listener) {
        dialog.addWindowListener(listener);
    }

    /**
     * Saves the user's selected theme preference.
     *
     * @param theme The name of the theme to be saved.
     */
    public void saveThemePreference(String theme) {
        //instantiate properties for saving theme(font and style) into a configured file
        Properties props = new Properties();
        props.setProperty("theme", theme);
        try (FileOutputStream out = new FileOutputStream("src/frontendClass/user_preferences.properties")) {
            props.store(out, "User Preferences");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}