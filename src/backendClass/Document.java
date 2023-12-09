package backendClass;

import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a document with text content and provides methods for document manipulation.
 *
 * @author Sophia Feng
 * @version 3.0
 */
public class Document {
    private String text;

    /**
     * Loads the document from the specified file path.
     *
     * @param filePath The path to the file to load.
     */
    public void loadDocument(String filePath) {
        // Code to load the document from filePath and remove words inside HTML tags if removeTags is true
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();

            text = content.toString();

        } catch (IOException e) {
            System.out.println("Error loading the document: " + e.getMessage());
        }
    }

    /**
     * Gets the current text content of the document.
     *
     * @return The text content of the document.
     */
    public String getText() {
        return text;
    }

    /**
     * Gets the text content of the document as a list of words.
     *
     * @return A list containing the words of the document.
     */
    public List<String> getTextAsList() {
        // Split the text into tokens while preserving punctuation, spaces, tabs, hyphens, and newlines
        return Arrays.asList(text.split("(?<=\\p{Punct}|\\s|-|\n)|(?=\\p{Punct}|\\s|-|\n)"));
    }

    /**
     * Replaces the word at the specified index with a new word.
     *
     * @param index            The index of the word to replace.
     * @param replacementWord  The word to replace the existing word with.
     */
    public void replaceWordAtIndex(int index, String replacementWord) {
        List<String> words = getTextAsList();

        if (index >= 0 && index < words.size()) {
            // Check if the replacement is an empty string
            if (replacementWord.isEmpty()) {
                // If the replacement is empty, check the index right before it
                if (index > 0 && words.get(index - 1).trim().isEmpty()) {
                    // If the index before is only a space, replace it as well
                    words.set(index - 1, replacementWord);
                }
            }

            words.set(index, replacementWord);

            // Update the text with the modified list of words
            StringBuilder newText = new StringBuilder();
            for (String word : words) {
                newText.append(word);
            }
            text = newText.toString();

            // System.out.println("Word at index " + index + " replaced with: " + replacementWord);
        } else {
            System.out.println("Index out of bounds.");
        }
    }

    /**
     * Saves the document to the specified file path.
     *
     * @param filePath The path to save the document.
     */
    public void saveDocument(String filePath) {
        // Code to save the document to the given filePath
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            // Print the list of words
            List<String> words = getTextAsList();
            // System.out.println("List of words:");
            for (String word : words) {
                writer.write(word);
            }
            writer.close();
            // System.out.println("\nDocument saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving the document: " + e.getMessage());
        }
    }
}