import java.util.ArrayList;

public class Word implements Comparable<Word> {
    String word;
    String word_en;
    String type;
    ArrayList<String> singular;
    ArrayList<String> plural;
    ArrayList<Definition> definitions;

    public String getWord() {
        return word;
    }

    public String getWord_en() {
        return word_en;
    }

    public String getType() {
        return type;
    }

    public ArrayList<String> getSingular() {
        return singular;
    }

    public ArrayList<String> getPlural() {
        return plural;
    }

    public ArrayList<Definition> getDefinitions() {
        return definitions;
    }

    public Word(String word, String word_en, String type, ArrayList<String> singular, ArrayList<String> plural,
                ArrayList<Definition> definitions) {
        this.word = word;
        this.word_en = word_en;
        this.type = type;
        this.singular = singular;
        this.plural = plural;
        this.definitions = definitions;
    }

    @Override
    public String toString() {
        return "Word{" +
                "word='" + word + '\'' +
                ", word_en='" + word_en + '\'' +
                ", type='" + type + '\'' +
                ", singular=" + singular +
                ", plural=" + plural +
                ", definitions=" + definitions +
                '}';
    }

    @Override
    public int compareTo(Word o) {
        return Integer.compare(this.word.compareToIgnoreCase(o.word), 0);

    }
}


