package backendClass;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * The DefaultDic class represents a dictionary loaded from a file.
 * It contains a set of words and provides functionality to check if a word exists in the dictionary.
 * @author Yingle Hu
 * @version 2.0
 */
public class DefaultDic {

    private Set<String> dictionary;

    /**
     * Constructs a new DefaultDic object and loads words into the dictionary from a file.
     */
    public DefaultDic() {
        dictionary = new HashSet<>();
        loadWords();
    }

    /**
     * Loads words from a file into the dictionary.
     * Each word is trimmed and converted to lower case before being added.
     */
    private void loadWords() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/backendClass/words.txt"))) {
            String word;
            while ((word = br.readLine()) != null) {
                dictionary.add(word.trim().toLowerCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if a word is contained in the dictionary.
     *
     * @param word the word to check
     * @return true if the word is in the dictionary, false otherwise
     */
    public boolean contains(String word) {
        return dictionary.contains(word.toLowerCase());
    }

    // Main method to run the test
//    public static void main(String[] args) {
//        DefaultDic defaultDic = new DefaultDic();
//        // An array of words to test
//        String[] wordsToTest = {"Apple", "Banana", "orange", "kiwi", "grapefruit","aksdjf"};
//        for (String word : wordsToTest) {
//            System.out.println("The dictionary " + (defaultDic.contains(word) ? "contains " : "does not contain ") + word);
//        }
//    }
}