import java.io.*;
import java.util.*;

public class Test {

    public static void main(String[] args) throws IOException {

        Methods.getDataCollection();

        /* tests addWord */
        System.out.println("Tests addWord:");

        Word wrd = new Word("6200662222", "eat", "verb",
                new ArrayList<>(Arrays.asList("6206620066222", "6206620066222444", "620662006622220")),
                new ArrayList<>(Arrays.asList("620066222206", "620066222280444", "620662006622220")),
                new ArrayList<>());
        Definition def = new Definition("The Art Of 01-d", "definitions",
                2020, new ArrayList<>(List.of("2 222042 444066 28877720 77770444 44406644444480444.")));
        wrd.getDefinitions().add(def);
        def = new Definition("The Art Of 01-s", "synonyms", 2021,
                new ArrayList<>(Arrays.asList("6337778332222", "222666667777886222")));
        wrd.getDefinitions().add(def);
        System.out.println("1: " + Methods.addWord(wrd, "01"));

            /* already existing word */
        wrd = new Word("Hund", "dog", "noun",
                new ArrayList<>(List.of("Hund")), new ArrayList<>(List.of("Hunde")), new ArrayList<>());
        def = new Definition("Syno02", "synonyms", 2008, null);
        wrd.getDefinitions().add(def);
        System.out.println("2: " + Methods.addWord(wrd, "ge"));

        /* tests removeWord */
        System.out.println("\nTests removeWord:");
        System.out.println("1: " + Methods.removeWord("gehen", "ge"));
            /* non-existing word */
        System.out.println("2: " + Methods.removeWord("colind", "ro"));
            /* already removed word */
        System.out.println("3: " + Methods.removeWord("gehen", "ge"));

        /* tests addDefinitionForWord */
        System.out.println("\nTests addDefinitionForWord:");
        System.out.println("1: " + Methods.addDefinitionForWord("câine", "ro",
                new Definition("sth", "synonyms", 1964,
                        new ArrayList<>(Arrays.asList("as", "bs", "cs")))));
            /* same dict */
        System.out.println("2: " + Methods.addDefinitionForWord("câine", "ro",
                new Definition("sth", "synonyms", 1967,
                        new ArrayList<>(Arrays.asList("ds", "es", "fs")))));
            /* non-existing word */
        System.out.println("3: " + Methods.addDefinitionForWord("22222", "01",
                new Definition("The Art Of 01-s", "synonyms", 1964,
                        new ArrayList<>(Arrays.asList("20202", "299")))));

        /* tests removeDefinition */
        System.out.println("\nTests removeDefinition:");
        System.out.println("1: " + Methods.removeDefinition("câine", "ro", "sth"));
        System.out.println("2: " + Methods.removeDefinition("essen", "ge", "Antisch"));
            /* non-existing dictionary */
        System.out.println("3: " + Methods.removeDefinition("2222004446633", "01", "fake"));
        System.out.println("4: " + Methods.removeDefinition("dragon", "fr", "Dex_01"));
            /* non-existing word */
        System.out.println("5: " + Methods.removeDefinition("stehen", "ge", "Sym02"));

        /* tests translateWord */
        System.out.println("\nTests translateWord:");
        System.out.println("1: " + Methods.translateWord("pisică", "ro", "fr"));
            /* 2th person plural */
        System.out.println("2: " + Methods.translateWord("esst", "ge", "01"));
            /* non-existing word */
        System.out.println("3: " + Methods.translateWord("bere", "ro", "ge"));
            /* non-existing translation */
        System.out.println("4: " + Methods.translateWord("2222004446633", "01", "fr"));
        System.out.println("5: " + Methods.translateWord("22220044466444", "01", "ro"));

        /* tests translateSentence */
        System.out.println("\nTests translateSentence:");
        System.out.println("1: " +
                Methods.translateSentence("Hund isst.", "ge", "01"));
        System.out.println("2: " +
                Methods.translateSentence("Pisici, balauri", "ro", "fr"));
        System.out.println("3: " +
                Methods.translateSentence("Mange! mangeons!!", "fr", "ge"));
            /* one non-existing word */
        System.out.println("4: " +
                Methods.translateSentence("Chat francois!", "fr", "ro"));
            /* one non-translatable word */
        System.out.println("5: " +
                Methods.translateSentence("Chats mange????", "fr", "ro"));
            /* non-existing words */
        System.out.println("6: " +
                Methods.translateSentence("is 2022 2020 too?", "01", "ro"));
            /* non-existing toLanguage */
        System.out.println("7: " +
                Methods.translateSentence("Chat mange.", "fr", "tu"));

        /* tests translateSentences */
        System.out.println("\nTests translateSentences:");
        System.out.println("1: " +
                Methods.translateSentences("Hund isst.", "ge", "01"));
        System.out.println("2: " +
                Methods.translateSentences("Pisici, balauri!!", "ro", "fr"));
            /* just two options */
        System.out.println("3: " +
                Methods.translateSentences("Chat mange", "fr", "ge"));
            /* partial translation */
        System.out.println("4: " +
                Methods.translateSentences("Chat mange", "fr", "ro"));
            /* impossible translation */
        System.out.println("5: " +
                Methods.translateSentences("Wir essen", "ge", "01"));


        /* tests getDefinitionsForWord */
        System.out.println("\nTests getDefinitionsForWord:");
        System.out.println("1: " + Methods.getDefinitionsForWord("jeu", "fr"));
        System.out.println("2: " + Methods.getDefinitionsForWord("Hund", "ge"));
        System.out.println("3: " + Methods.getDefinitionsForWord("balaur", "ro"));
        System.out.println("4: " + Methods.getDefinitionsForWord("6200662222", "01"));
            /* non-existing word */
        System.out.println("5: " + Methods.getDefinitionsForWord("tema", "ro"));

        /* tests exportDictionary */
        System.out.println("\nTests exportDictionary:");
        System.out.println("ro: ");
        Methods.exportDictionary("ro");
        System.out.println("fr: ");
        Methods.exportDictionary("fr");
        System.out.println("ge: ");
        Methods.exportDictionary("ge");
        System.out.println("01: ");
        Methods.exportDictionary("01");
        System.out.println("tu: ");
        Methods.exportDictionary("tu");


    }
}
