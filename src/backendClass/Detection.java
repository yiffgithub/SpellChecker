package backendClass;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class file is part of a text processing and error detection module.
 * It provides functionality for detecting various types of errors in text documents,
 * such as spelling errors, capitalization errors, and occurrences of double words.
 *
 * @author Xinyuan Wang
 * @version 15.0
 */
public class Detection {
    private Dictionary dictionary;
    
    /**
     * Default constructor for the Detection class.
     */
    public Detection() {}

    /**
     * Constructs a {@code Detection} instance with a specified dictionary object.
     *
     * @param dictionary The user's custom dictionary.
     */
    public Detection(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    /**
     * Analyzes the given document and identifies any spelling or capitalization errors,
     * or double words. It can optionally filter out HTML tags during analysis.
     *
     * @param indices Indices to ignore during the check.
     * @param words   List of words in the document.
     * @param filter  If {@code true}, HTML tags will be ignored during analysis.
     * @return A list of integers representing errors found in the format [index, type].
     */
    // misspell=0, notcapitalize=1, miscap=2, double word=3
    public List<Integer> checkDocument(ArrayList<Integer> indices, List<String> words, boolean filter) {
        boolean isNewSentence = true;
        boolean insideHtmlTag = false;
        boolean afterHtmlTagAtNewSentence = false;
        List<Integer> errors = new ArrayList<>();
        Set<Integer> ignoreIndices = new HashSet<>(indices);
        boolean spelling = true;

        for (int i = 0; i < words.size(); i++) {
            // Skip the indices to ignore and continue with the next iteration
            String word = words.get(i);
            String trimmedWord = word.trim();
            
            if (ignoreIndices.contains(i)) {
                isNewSentence = trimmedWord.endsWith(".") || trimmedWord.endsWith("?") || trimmedWord.endsWith("!") || trimmedWord.endsWith("\n");
                continue;
            }

            // Handle HTML tags if filter is true
            if (filter) {
                if (trimmedWord.startsWith("<")) {
                    insideHtmlTag = true;
                    afterHtmlTagAtNewSentence = isNewSentence;
                    continue;
                }
                if (insideHtmlTag) {
                    if (trimmedWord.endsWith(">")) {
                        insideHtmlTag = false;
                    }
                    continue;
                }
                if (insideHtmlTag || word.matches("\\p{Punct}")) {
                    isNewSentence = trimmedWord.endsWith(".") || trimmedWord.endsWith("?") || trimmedWord.endsWith("!") || trimmedWord.endsWith("\n");
                    continue;
                }
            }
            
            if (afterHtmlTagAtNewSentence) {
                isNewSentence = true;
                afterHtmlTagAtNewSentence = false;
            }

            // Skip punctuation, purely numeric words, or empty strings
            if (trimmedWord.isEmpty() || trimmedWord.matches("\\p{Punct}") || trimmedWord.matches("\\d+")) {
            	if (trimmedWord.endsWith(".") || trimmedWord.endsWith("?") || trimmedWord.endsWith("!") || trimmedWord.contains("\n")) {
                    isNewSentence = true;
                } else if(trimmedWord.matches("\\d+")|| trimmedWord.matches("\\p{Punct}")){
                    isNewSentence = false;
                }
                continue;
            }

            // Check for misspellings (type 0)
            if (!isWordValid(trimmedWord)) {
                errors.add(i);
                errors.add(0);
                spelling = false;
                return errors;
            }
            
            if(!dictionary.isWordInUserDictionary(word)&&!dictionary.isWordInSessionDictionary(word)) {
            // Check for capitalization errors (type 1 for first word of a sentence, type 2 within a word)
            if (isNewSentence && Character.isLowerCase(trimmedWord.charAt(0)) && spelling) {
                errors.add(i);
                errors.add(1);
                return errors;
            } else if (!trimmedWord.toUpperCase().equals(trimmedWord) && !trimmedWord.toLowerCase().equals(trimmedWord) && spelling && !isNewSentence) {
                        errors.add(i);
                        errors.add(2);
                        isNewSentence = trimmedWord.endsWith(".") || trimmedWord.endsWith("?") || trimmedWord.endsWith("!") || trimmedWord.endsWith("\n");
                        return errors;
            }
            }
            
            // Check for double words (type 3)
            
            if(i>=2) {
            if (i > 0 && !words.get(i - 2).trim().matches("\\d+|\\p{Punct}") && trimmedWord.equalsIgnoreCase(words.get(i - 2).trim())) {
                errors.add(i);
                errors.add(3);
                return errors;
            }
            }
            

            // Update isNewSentence for the next word
            isNewSentence = trimmedWord.endsWith(".") || trimmedWord.endsWith("?") || trimmedWord.endsWith("!") || trimmedWord.endsWith("\n");


            
        }
        
        
        
        return errors.isEmpty() ? null : errors; // Return null if no errors were found
    }

    /**
     * Checks if a word is valid by verifying its presence in the system or user dictionary.
     *
     * @param word the word to be checked.
     * @return {@code true} if the word is found in the dictionaries, otherwise {@code false}.
     */
    public boolean isWordValid(String word) {
        // Normalize the word to lowercase before checking
        // word = word.toLowerCase();
        boolean result = true;
        if(!dictionary.isWordInSystemDictionary(word)) {
        	if(!dictionary.isWordInUserDictionary(word)) {
        		if(!dictionary.isWordInSessionDictionary(word)) {
        			result = false;
        		}
        	}
        }
        // Check if the word is in the system dictionary or the user dictionary
        return result;
    }
    
    /**
     * Main method for testing the Detection class.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
    	
    	//new objects
        Dictionary dictionary = new Dictionary();
        Detection detection = new Detection(dictionary);

        // Mock list of words for testing, including some HTML tags and deliberate errors
        List<String> words = new ArrayList<>();
//        words.add("this");
//        words.add("dOg");
//        words.add("<");a
//        words.add("html");
//        words.add(">");
//        words.add("This");
//        words.add("is");
//        words.add("a");
//        words.add("test");
        words.add("700");// Double word
        words.add("years");
        words.add(".");
//        words.add("<");
//        words.add("/");
//        words.add("html");
//        words.add(">");
        words.add(" ");
        words.add("it"); // Not capitalization
        words.add("contains");
        words.add("Speling"); // Misspelling
        words.add("errors");
        words.add("and");
        words.add("capitalization"); 
        words.add("Errors"); 

        // Call the checkDocument method and filter out the tags
        List<Integer> ignoreindices = new ArrayList<>();
        ignoreindices.add(7);
        List<Integer> errors = detection.checkDocument((ArrayList<Integer>) ignoreindices, words, true);

        // print
        if (errors != null) {
            System.out.println("Errors found:");
            for (int i = 0; i < errors.size(); i += 2) {
                int index = errors.get(i);
                int errorType = errors.get(i + 1);
                String errorTypeName;
                switch (errorType) {
                    case 0:
                        errorTypeName = "Misspelling";
                        break;
                    case 1:
                        errorTypeName = "Not capitalized";
                        break;
                    case 2:
                        errorTypeName = "Miscapitalization within a word";
                        break;
                    case 3:
                        errorTypeName = "Double word";
                        break;
                    default:
                        errorTypeName = "Unknown";
                        break;
                }
                System.out.printf("Error type: %s at index: %d, Word: '%s'%n", errorTypeName, index, words.get(index));
            }
        } else {
            System.out.println("No errors found.");
        }
    }

}
