import java.util.ArrayList;
import java.util.Arrays;

public class Definition implements Comparable<Definition> {
    String dict;
    String dictType;
    int year;
    ArrayList<String> text;

    public Definition(String dict, String dictType, int year, ArrayList<String> text) {
        this.dict = dict;
        this.dictType = dictType;
        this.year = year;
        this.text = text;
    }

    public String getDictType() {
        return dictType;
    }

    public ArrayList<String> getText() {
        return text;
    }

    @Override
    public String toString() {
        return "Definition{" +
                "dict='" + dict + '\'' +
                ", dictType='" + dictType + '\'' +
                ", year=" + year +
                ", text=" + text +
                '}';
    }

    public int compareDict(Definition o) {
        return Integer.compare(this.dict.compareTo(o.dict), 0);

    }

    @Override
    public int compareTo(Definition o) {
        return Integer.compare(this.year, o.year);

    }
}
