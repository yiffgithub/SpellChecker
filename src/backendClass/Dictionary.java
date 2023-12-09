 
package backendClass;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * The Dictionary class manages different types of dictionaries 
 * including user, temporary, and system dictionaries. 
 * It allows adding, removing, and editing words in these dictionaries.
 * @author Yingle Hu
 * @version 3.0
 */
public class Dictionary {
    private DefaultDic defaultDic = new DefaultDic();
    private static List<String> tempList = new ArrayList<>();
    private static List<String> userWordList = getUserWordList();
    private static List<String> ignoreOnceWordList = new ArrayList<>();
    private static List<String> ignoreSessionList = new ArrayList<>();
    public static List<String> getUserWordList(){
        List<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/backendClass/userDic.txt"))) {
            String word;
            while ((word = br.readLine()) != null) {
                list.add(word);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
    /**
     * Adds a word to the specified dictionary and, if the dictionary is the user dictionary,
     * writes the updated list to a file.
     *
     * @param word    the word to be added to the dictionary
     * @param dicName the name of the dictionary to which the word will be added
     */
    public static void addWord(String word, String dicName) {
        List<String> dictionary = getListByName(dicName);
        dictionary.add(word);
        if (dicName.equals("user")) {
            writeUserDictionaryToFile(dictionary);
        }
    }
    /**
     * Removes a word from the specified dictionary.
     *
     * @param word the word to remove
     * @param dicName the name of the dictionary
     */
    public static void removeWord(String word, String dicName) {
        List<String> dictionary = getListByName(dicName);
        dictionary.remove(word);
        if (dicName.equals("user")) {
            writeUserDictionaryToFile(dictionary);
        }
    }
    /**
     * Writes the contents of the user dictionary to a file, with each word on a new line.
     * If the userDictionary is null or empty, writes an empty file.
     *
     * @param userDictionary the list of words in the user dictionary
     */
    private static void writeUserDictionaryToFile(List<String> userDictionary) {
        Set<String> setWithoutDuplicates = new HashSet<>(userDictionary);
        List<String> listWithoutDuplicates = new ArrayList<>(setWithoutDuplicates);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/backendClass/userDic.txt"))) {
            if (listWithoutDuplicates != null) {
                for (String word : listWithoutDuplicates) {
                    writer.write(word);
                    writer.newLine();
                }
            }
            // If the userDictionary is null or empty, this will result in an empty file.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Replaces an old word with a new word in the user dictionary.
     *
     * @param newWord the new word
     * @param oldWord the old word to be replaced
     * @return true if the replacement was successful, false otherwise
     */
    public boolean replaceWordForUserDic(String newWord, String oldWord){
        int index = userWordList.indexOf(oldWord);
        if (index != -1) {
            userWordList.set(index, newWord);
            writeUserDictionaryToFile(userWordList);
            return true;
        }
        
        return false;
    }
    /**
     * Edits a word in the specified dictionary.
     *
     * @param oldWord the word to be replaced
     * @param newWord the new word
     * @param dicName the name of the dictionary
     * @return the new word if the replacement was successful, null otherwise
     */
    public String editWord(String oldWord, String newWord, String dicName) {
        List<String> dictionary = getListByName(dicName);
        int index = dictionary.indexOf(oldWord);
        if (index != -1) {
            dictionary.set(index, newWord);
            if (dicName == "user"){
                writeUserDictionaryToFile(dictionary);
            }
            return newWord;
        }
        return null;
    }
    /**
     * Resets all dictionaries except the system dictionary to their default state.
     */
    public void resetDictionary() {
        tempList.clear();
        userWordList.clear();
        ignoreOnceWordList.clear();
        writeUserDictionaryToFile(userWordList);
    }
    /**
     * Resets the user dictionary.
     * This method clears the current list of user-defined words and then writes
     * the empty list back to the user dictionary file. This effectively removes
     * all custom words that were added by the user during the application's usage.
     */
    public void resetUserDic(){
        userWordList.clear();
        writeUserDictionaryToFile(userWordList);
    }
    
    /**
     * Resets the ignore session dictionary.
     * This method clears the current list of ignore session
     */
    public void resetSessionDic(){
        ignoreSessionList.clear();
    }
    /**
     * Checks if a word is present in the temporary dictionary.
     *
     * @param word the word to check
     * @return true if the word is present in the temporary dictionary, false otherwise
     */
    public boolean isWordInSessionDictionary(String word) {
        return ignoreSessionList.contains(word);
    }
    /**
     * Checks if a word is present in the user dictionary.
     *
     * @param word the word to check
     * @return true if the word is present in the user dictionary, false otherwise
     */
    public boolean isWordInUserDictionary(String word) {
        return userWordList.contains(word);
    }
    /**
     * Checks if a word is present in the system dictionary.
     *
     * @param word the word to check
     * @return true if the word is present in the system dictionary, false otherwise
     */
    public boolean isWordInSystemDictionary(String word) {
        return defaultDic.contains(word);
    }
    /**
     * Retrieves a list of words from the specified dictionary.
     *
     * @param dicName the name of the dictionary
     * @return the list of words in the specified dictionary
     */
    public List<String> getDictionary(String dicName) {
        return getListByName(dicName);
    }
    /**
     * Checks if a word is marked to be ignored once.
     *
     * @param word the word to check
     * @return true if the word is in the ignoreOnce list, false otherwise
     */
    public boolean isIgnoreOnce (String word){
        return ignoreOnceWordList.contains(word);
    }
    /**
     * Retrieves the appropriate dictionary list based on its name.
     *
     * @param dicName the name of the dictionary
     * @return the corresponding dictionary list
     * @throws IllegalArgumentException if an invalid dictionary name is provided
     */
    private static List<String> getListByName(String dicName) {
        switch (dicName.toLowerCase()) {
            case "temp":
                return tempList;
            case "user":
                return userWordList;
            case "ignoreonce":
                return ignoreOnceWordList;
            case "ignoressession":
                return ignoreSessionList;
            default:
                throw new IllegalArgumentException("Invalid dictionary name: " + dicName);
        }
    }
    // Main method to run the test
//    public static void main(String[] args) {
//        Dictionary dictionary = new Dictionary();
//        // Test adding and removing words, and also ensure the file is updated accordingly
//        dictionary.addWord("TestWord", "user");
//        System.out.println("Added 'TestWord': " + dictionary.isWordInUserDictionary("TestWord"));
//
//        //Dictionary.removeWord("TestWord", "user");
//        //System.out.println("Removed 'TestWord': " + !dictionary.isWordInUserDictionary("TestWord"));
//
//        //System.out.println("Exist 'TestWord': " + dictionary.isWordInUserDictionary("TestWord"));
//        //dictionary.removeWord("TestWord", "user");
//        //System.out.println("Removed 'TestWord': " + !dictionary.isWordInUserDictionary("TestWord"));
//        dictionary.resetDictionary();
//    }
}

