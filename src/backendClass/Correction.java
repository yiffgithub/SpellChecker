package backendClass;

import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

/**
 * The Correction class is responsible for providing word correction suggestions.
 * It integrates with a dictionary to add words and to generate possible corrections
 * for given words based on various algorithms.
 * @author Yingle Hu
 * @version 2.0
 */
public class Correction {
    private DefaultDic defaultDic = new DefaultDic();

    /**
     * Adds a word to a specified dictionary.
     *
     * @param word the word to be added
     * @param dicName the name of the dictionary to which the word is to be added
     */
    public void addToDic(String word, String dicName){
        Dictionary.addWord(word, dicName);
    }
    
    /**
     * Adds a word to the ignore session list in the dictionary.
     *
     * @param word the word to be added to the ignore session list
     */
    public void ignoreSeccion(String word){
        Dictionary.addWord(word,"ignoreSessionList");
    }

    /**
     * Corrects capitalization errors in a word according to a specified error type.
     * 
     * @param errorType The type of capitalization error.
     *                  1: Corrects the word by making only the first character uppercase.
     *                  2: Corrects the word by making all characters lowercase.
     *                  Other: Unidentified error type; no correction is applied.
     * @param word The word to correct.
     * @return A list of corrected word(s). The list is empty for unidentified error types.
     */
    public List<String> correctCap(int errorType, String word){
        List<String> ans = new ArrayList<>();
        //Make the first character in upper case, then add to the ans list
        if(errorType == 1){
            String corrected = word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
            ans.add(corrected);
        }
        //Make the whole word in lower case, then add to the ans list
        else if(errorType == 2){
            ans.add(word.toLowerCase());
        }
        //For other case it will return an empty array list
        return ans;
    }

    /**
     * Generates a list of suggested corrections for a given word.
     * The suggestions are based on removing, adding, or altering characters in the word,
     * and checking if the resulting words are in the dictionary.
     *
     * @param word the word for which suggestions are to be generated
     * @return a list of suggested corrections
     */
    public List<String> generateSuggestions(String word) {
        List<String> suggestedWordList = new ArrayList<>();

        // Remove each letter
        for (int i = 0; i < word.length(); i++) {
            String test = word.substring(0, i) + word.substring(i + 1);
            if (defaultDic.contains(test)) {
                suggestedWordList.add(test);
            }
        }

        // Add each letter at each position
        for (int i = 0; i <= word.length(); i++) {
            for (char ch = 'a'; ch <= 'z'; ch++) {
                String test = word.substring(0, i) + ch + word.substring(i);
                if (defaultDic.contains(test)) {
                    suggestedWordList.add(test);
                }
            }
        }

        // Swap adjacent characters
        for (int i = 0; i < word.length() - 1; i++) {
            char[] wordArray = word.toCharArray();
            char temp = wordArray[i];
            wordArray[i] = wordArray[i + 1];
            wordArray[i + 1] = temp;
            String test = new String(wordArray);
            if (defaultDic.contains(test)) {
                suggestedWordList.add(test);
            }
        }

        // Insert a space or hyphen at each interior position
        for (int i = 1; i < word.length(); i++) {
            // Insert a space
            String leftPart = word.substring(0, i);
            String rightPart = word.substring(i);
            if (defaultDic.contains(leftPart) && defaultDic.contains(rightPart)) {
                suggestedWordList.add(leftPart + " " + rightPart);
            }

            // Insert a hyphen
            if (defaultDic.contains(leftPart) && defaultDic.contains(rightPart)) {
                suggestedWordList.add(leftPart + "-" + rightPart);
            }
        }

        Set<String> setWithoutDuplicates = new HashSet<>(suggestedWordList);
        List<String> listWithoutDuplicates = new ArrayList<>(setWithoutDuplicates);

        return listWithoutDuplicates;
    }

//    public static void main(String[] args) {
//        Correction corr = new Correction();
//        // An array of words to test
//        String[] wordsToTest = {"Applei", "Banna", "ornge", "kiiwi", "graepfruit","adj","dji","Dylan", "coworker", "cowworker", "co"};
//        for (String word : wordsToTest) {
//            System.out.println("The correction " + corr.generateSuggestions(word));
//        }
//    }
}