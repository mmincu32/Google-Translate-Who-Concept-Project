import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class Methods {

    public static Map<Word, String> dataCollection = new HashMap<>();

    public static void getDataCollection() {

        /*File Directory = new File("C:\\Users\\User\\Desktop\\An2\\An2Sem1\\POO\\" +
                "Tema2\\Dictionaries"); */
        File Directory = new File("Dictionaries");


        for (File file : Objects.requireNonNull(Directory.listFiles())) {
            if (file.getName().endsWith(".json")) {

                String language = file.getName().substring(0, 2);
                //ArrayList<Word> wordArrayList;
                //Gson gson = new Gson();

                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    /*String line;
                    String json = "";
                    while ((line = br.readLine()) != null) {
                        json = json.concat(line + "\n");
                    }
*/
                    /* import */
                    //Type type = new TypeToken<ArrayList<Word>>() {
                    //}.getType();
                    ArrayList<Word> wordArrayList = new Gson().fromJson(br, new TypeToken<ArrayList<Word>>(){}.getType());

                    /* now add in collection */
                    for (Word word : wordArrayList) {
                        dataCollection.put(word, language);
                    }


                } catch (IOException e) {
                    System.out.println("File " + file.getName() + "could not be read.");
                }
            }
        }
    }

    public static boolean addWord(Word word, String language) {

        for (Map.Entry<Word, String> entry : dataCollection.entrySet()) {
            if (entry.getKey().compareTo(word) == 0)
                /* word already exists */
                return false;
        }

        dataCollection.put(word, language);
        return true;
    }

    public static boolean removeWord(String word, String language) {
        for (Map.Entry<Word, String> entry : dataCollection.entrySet()) {
            if (entry.getKey().getWord().equals(word) && entry.getValue().equals(language)) {
                /* word exists */
                dataCollection.remove(entry.getKey(), entry.getValue());
                return true;
            }

        }
        return false;   /* word does not exist */

    }

    public static boolean addDefinitionForWord(String word, String language, Definition definition) {
        for (Map.Entry<Word, String> entry : dataCollection.entrySet()) {
            if (entry.getKey().getWord().equals(word) && entry.getValue().equals(language)) {
                /* word exists */
                int sizeOfDefinitionList = entry.getKey().getDefinitions().size();
                for (int index = 0; index < sizeOfDefinitionList; ++index) {
                    if (definition.compareDict(entry.getKey().getDefinitions().get(index)) == 0)
                        return false;
                }
                entry.getKey().getDefinitions().add(definition);
                return true;
            }

        }
        return false;
    }

    public static boolean removeDefinition(String word, String language, String dictionary) {
        Definition toCompareDefinition = new Definition(dictionary, "", 0, null);

        for (Map.Entry<Word, String> entry : dataCollection.entrySet()) {
            if (entry.getKey().getWord().equals(word) && entry.getValue().equals(language)) {

                int sizeOfDefinitionList = entry.getKey().getDefinitions().size();
                for (int index = 0; index < sizeOfDefinitionList; ++index) {

                    if (toCompareDefinition.compareDict(entry.getKey().getDefinitions().get(index)) == 0) {
                        /* we found the definition to be removed */
                        entry.getKey().getDefinitions().remove(index);
                        return true;
                    }

                }

            }
        }
        return false;
    }

    public static String translateWord(String word, String fromLanguage, String toLanguage) {

        boolean isSingular = false;
        boolean found = false;
        int indexinFormsArray = 0;
        String enTranslation = "";

        for (Map.Entry<Word, String> entry : dataCollection.entrySet()) {
            if (entry.getValue().equals(fromLanguage)) {
                if (entry.getKey().getType().equals("noun")) {
                    if (entry.getKey().getSingular().get(0).equalsIgnoreCase(word)) {
                        isSingular = true;
                        found = true;
                        enTranslation = entry.getKey().getWord_en();
                    }
                    if (entry.getKey().getPlural().get(0).equalsIgnoreCase(word)) {
                        found = true;
                        enTranslation = entry.getKey().getWord_en();
                    }

                }
                if (entry.getKey().getType().equals("verb")) {
                    for (int i = 0; i < 3 && !found; ++i) {
                        if (entry.getKey().getSingular().get(i).equalsIgnoreCase(word)) {
                            isSingular = true;
                            found = true;
                            indexinFormsArray = i;
                            enTranslation = entry.getKey().getWord_en();
                        }
                    }
                    for (int i = 0; i < 3 && !found; ++i) {
                        if (entry.getKey().getPlural().get(i).equalsIgnoreCase(word)) {
                            found = true;
                            indexinFormsArray = i;
                            enTranslation = entry.getKey().getWord_en();
                        }
                    }

                }
            }
        }
        if (!found)
            return null;

        for (Map.Entry<Word, String> entry : dataCollection.entrySet()) {
            if (entry.getKey().getWord_en().equals(enTranslation) &&
                    entry.getValue().equals(toLanguage)) {
                if (isSingular)
                    return entry.getKey().getSingular().get(indexinFormsArray);
                return entry.getKey().getPlural().get(indexinFormsArray);
            }
        }
        return word.toLowerCase();
    }

    public static String translateSentence(String sentence, String fromLanguage, String toLanguage) {

        String[] splitSentence = sentence.split("[.,?;:! ]+", -1);
        sentence = "";

        for (int i = 0; i < splitSentence.length &&
                !splitSentence[i].equals(""); ++i) {
            String translatedWord = translateWord(splitSentence[i], fromLanguage, toLanguage);
            if (translatedWord != null)
                sentence = sentence.concat(translatedWord + " ");
            else
                return null;
        }

        return sentence;


    }

    public static ArrayList<String> translateSentences(String sentence, String fromLanguage, String toLanguage) {

        ArrayList<String> translatedSentences = new ArrayList<>();
        String originalTranslation = translateSentence(sentence, fromLanguage, toLanguage);
        if (originalTranslation == null)
            return null;

        translatedSentences.add(originalTranslation);

        String[] translatedWords = originalTranslation.split(" ", -1);
        String secondOptionTranslation = "";
        String thirdOptionTranslation = "";
        int countWordsForSecondOption = 0;
        int countWordsForThirdOption = 0;
        int countWordsForFirstOption = 0;

        for (String tWord : translatedWords) {
            if (!tWord.equals("")) {
                String synonym1 = "";
                String synonym2 = "";
                boolean found = false;
                for (Map.Entry<Word, String> entry : dataCollection.entrySet()) {
                    if (entry.getValue().equals(toLanguage) && !found) {
                        if (entry.getKey().getType().equals("noun")) {
                            if (entry.getKey().getSingular().get(0).equals(tWord)) {
                                found = true;
                                for (Definition def : entry.getKey().getDefinitions()) {
                                    if (def.getDictType().equals("synonyms") &&
                                            (synonym1.equals("") || synonym2.equals(""))) {
                                        for (String str : def.getText()) {
                                            if (!str.equals("")) {
                                                if (synonym1.equals("")) {
                                                    synonym1 = str;
                                                } else if (synonym2.equals("")) {
                                                    synonym2 = str;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (entry.getKey().getPlural().get(0).equals(tWord)) {
                                found = true;
                                for (Definition def : entry.getKey().getDefinitions()) {
                                    if (def.getDictType().equals("synonyms") &&
                                            (synonym1.equals("") || synonym2.equals(""))) {
                                        for (String str : def.getText()) {
                                            if (!str.equals("")) {
                                                if (synonym1.equals("")) {
                                                    synonym1 = str;
                                                } else if (synonym2.equals("")) {
                                                    synonym2 = str;
                                                }
                                            }
                                        }
                                    }
                                }

                            }
                        }
                        if (entry.getKey().getType().equals("verb")) {
                            for (int i = 0; i < 3 && !found; ++i) {
                                if (entry.getKey().getSingular().get(i).equals(tWord)) {
                                    found = true;
                                    for (Definition def : entry.getKey().getDefinitions()) {
                                        if (def.getDictType().equals("synonyms") &&
                                                (synonym1.equals("") || synonym2.equals(""))) {
                                            for (String str : def.getText()) {
                                                if (!str.equals("")) {
                                                    if (synonym1.equals("")) {
                                                        synonym1 = str;
                                                    } else if (synonym2.equals("")) {
                                                        synonym2 = str;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                if (entry.getKey().getPlural().get(i).equals(tWord)) {
                                    found = true;
                                    for (Definition def : entry.getKey().getDefinitions()) {
                                        if (def.getDictType().equals("synonyms") &&
                                                (synonym1.equals("") || synonym2.equals(""))) {
                                            for (String str : def.getText()) {
                                                if (!str.equals("")) {
                                                    if (synonym1.equals("")) {
                                                        synonym1 = str;
                                                    } else if (synonym2.equals("")) {
                                                        synonym2 = str;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                            }
                        }

                    }
                    if (entry.getValue().equals(fromLanguage) && !found) {
                        if (entry.getKey().getType().equals("noun")) {
                            if (entry.getKey().getSingular().get(0).equals(tWord)) {
                                found = true;
                                for (Definition def : entry.getKey().getDefinitions()) {
                                    if (def.getDictType().equals("synonyms") &&
                                            (synonym1.equals("") || synonym2.equals(""))) {
                                        for (String str : def.getText()) {
                                            if (!str.equals("")) {
                                                if (synonym1.equals("")) {
                                                    synonym1 = str;
                                                } else if (synonym2.equals("")) {
                                                    synonym2 = str;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (entry.getKey().getPlural().get(0).equals(tWord)) {
                                found = true;
                                for (Definition def : entry.getKey().getDefinitions()) {
                                    if (def.getDictType().equals("synonyms") &&
                                            (synonym1.equals("") || synonym2.equals(""))) {
                                        for (String str : def.getText()) {
                                            if (!str.equals("")) {
                                                if (synonym1.equals("")) {
                                                    synonym1 = str;
                                                } else if (synonym2.equals("")) {
                                                    synonym2 = str;
                                                }
                                            }
                                        }
                                    }
                                }

                            }
                        }
                        if (entry.getKey().getType().equals("verb")) {
                            for (int i = 0; i < 3 && !found; ++i) {
                                if (entry.getKey().getSingular().get(i).equals(tWord)) {
                                    found = true;
                                    for (Definition def : entry.getKey().getDefinitions()) {
                                        if (def.getDictType().equals("synonyms") &&
                                                (synonym1.equals("") || synonym2.equals(""))) {
                                            for (String str : def.getText()) {
                                                if (!str.equals("")) {
                                                    if (synonym1.equals("")) {
                                                        synonym1 = str;
                                                    } else if (synonym2.equals("")) {
                                                        synonym2 = str;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                if (entry.getKey().getPlural().get(i).equals(tWord)) {
                                    found = true;
                                    for (Definition def : entry.getKey().getDefinitions()) {
                                        if (def.getDictType().equals("synonyms") &&
                                                (synonym1.equals("") || synonym2.equals(""))) {
                                            for (String str : def.getText()) {
                                                if (!str.equals("")) {
                                                    if (synonym1.equals("")) {
                                                        synonym1 = str;
                                                    } else if (synonym2.equals("")) {
                                                        synonym2 = str;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                            }
                        }

                    }
                }

                if (synonym2.equals(synonym1))
                    synonym2 = "";
                if (!synonym1.equals("")) {
                    secondOptionTranslation = secondOptionTranslation.concat(synonym1 + " ");
                    countWordsForSecondOption++;
                }
                if (!synonym2.equals("")) {
                    thirdOptionTranslation = thirdOptionTranslation.concat(synonym2 + " ");
                    countWordsForThirdOption++;
                }
                countWordsForFirstOption++;


            }

        }
        if (countWordsForFirstOption == countWordsForSecondOption) {
            translatedSentences.add(secondOptionTranslation);
            if (countWordsForSecondOption == countWordsForThirdOption)
                translatedSentences.add(thirdOptionTranslation);
        }
        return translatedSentences;
    }

    public static ArrayList<Definition> getDefinitionsForWord(String word, String language) {

        PriorityQueue<Definition> definitionCollector = new PriorityQueue<>();
        LinkedList<Definition> orderedDefinitions = new LinkedList<>();
        boolean found = false;

        for (Map.Entry<Word, String> entry : dataCollection.entrySet()) {
            if (entry.getKey().getWord().equals(word) && entry.getValue().equals(language)) {
                found = true;
                for (Definition def : entry.getKey().getDefinitions()) {
                    definitionCollector.offer(def);
                }
                while (definitionCollector.peek() != null) {
                    orderedDefinitions.addLast(definitionCollector.poll());
                }
            }
        }
        if (!found)
            return null;
        return new ArrayList<>(orderedDefinitions);


    }

    public static void exportDictionary(String language) throws IOException {

        PriorityQueue<Word> wordsFromLanguage = new PriorityQueue<>();
        LinkedList<Word> orderedWords = new LinkedList<>();

        for (Map.Entry<Word, String> entry : dataCollection.entrySet()) {
            if (entry.getValue().equals(language)) {
                entry.getKey().definitions = getDefinitionsForWord(entry.getKey().getWord(), language);
                /* to sort definitions */
                wordsFromLanguage.offer(entry.getKey());
            }
        }
        while (wordsFromLanguage.peek() != null) {
            orderedWords.addLast(wordsFromLanguage.poll());
        }

        FileWriter fw = new FileWriter("C:\\Users\\User\\Desktop\\An2\\An2Sem1\\POO\\Tema2\\Exported\\" +
                "dictionary_" + language + ".json");
        Gson gson = new Gson();
        fw.write("[" + '\n' + '\t');
        int count = 0;
        for (Word w : orderedWords) {
            if (count == 0) {
                fw.write(gson.toJson(w));
            } else {
                fw.write(",\n\t" + gson.toJson(w));
            }
            count++;
        }
        fw.write("\n]");
        fw.close();
        System.out.println("Success!");

    }

}
