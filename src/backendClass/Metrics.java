package backendClass;
import java.util.List;

public class Metrics {
    private int characterCount;
    private int lineCount;
    private int wordCount;
    private int misspellingsCount;
    private int miscapitalizationsCount;
    private int doubleWordsCount;
    private int manualCorrectionsCount;
    private int acceptedSuggestionsCount;
    private int wordDeletionsCount;
    private int ignoreOnceCount;
    private int ignoreSessionCount;
    private int addToDicCount;

    public Metrics() {
        // Initialize counts to zero
        characterCount = 0;
        lineCount = 0;
        wordCount = 0;
        misspellingsCount = 0;
        miscapitalizationsCount = 0;
        doubleWordsCount = 0;
        manualCorrectionsCount = 0;
        acceptedSuggestionsCount = 0;
        wordDeletionsCount = 0;
        ignoreOnceCount = 0;
        ignoreSessionCount = 0;
        addToDicCount = 0;
    }

    // Method to calculate metrics from the Document
    public void calculateMetrics(Document document) {
        List<String> wordsWithPunctuation = document.getTextAsList();
        characterCount = document.getText().replaceAll("\\s+", "").length();
        lineCount = document.getText().split("\\r?\\n").length;
        for (String word : wordsWithPunctuation) {
            if (!word.trim().isEmpty() && !word.matches("\\p{Punct}")) {
                wordCount++;
            }
        }
    }

//    // Method to update error counts from Detection
//    public void updateErrorCounts(List errors) {
//        for (int error : errors) {
//            switch (error.getType()) {
//                case 0:
//                    misspellingsCount++;
//                    break;
//                case 1:
//                    miscapitalizationsCount++;
//                    break;
//                case 2:
//                    doubleWordsCount++;
//                    break;
//            }
//        }
//    }

    public void setClear(){
        characterCount = 0;
        lineCount = 0;
        wordCount = 0;
        misspellingsCount = 0;
        miscapitalizationsCount = 0;
        doubleWordsCount = 0;
        manualCorrectionsCount = 0;
        acceptedSuggestionsCount = 0;
        wordDeletionsCount = 0;
        ignoreOnceCount = 0;
        ignoreSessionCount = 0;
        addToDicCount = 0;
    }


    // Setters for correction types
    public void manual() {
        manualCorrectionsCount++;
    }

    public void misspell() {
        misspellingsCount++;
    }

    public void miscap() {
        miscapitalizationsCount++;
    }

    public void doubleword() {
        doubleWordsCount++;
    }

    public void deletion() {
        wordDeletionsCount++;
    }

    public void ignoreOnce() {
        ignoreOnceCount++;
    }

    public void ignoreSession() {
        ignoreSessionCount++;
    }

    public void addToDic() {
        addToDicCount++;
    }

    public void suggestion() {
        acceptedSuggestionsCount++;
    }

    // Getter methods
    public int getCharacterCount() {
        return characterCount;
    }

    public int getLineCount() {
        return lineCount;
    }

    public int getWordCount() {
        return wordCount;
    }

    public int getMisspellingsCount() {
        return misspellingsCount;
    }

    public int getMiscapitalizationsCount() {
        return miscapitalizationsCount;
    }

    public int getDoubleWordsCount() {
        return doubleWordsCount;
    }

    public int getManualCorrectionsCount() {
        return manualCorrectionsCount;
    }

    public int getAcceptedSuggestionsCount() {
        return acceptedSuggestionsCount;
    }

    public int getWordDeletionsCount() {
        return wordDeletionsCount;
    }

    public int getIgnoreOnceCount() {
        return ignoreOnceCount;
    }

    public int getIgnoreSessionCount() {
        return ignoreSessionCount;
    }

    public int getAddToDicCount() {
        return addToDicCount;
    }
}

