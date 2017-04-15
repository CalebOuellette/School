import java.util.HashMap;
import java.util.Map;

/**
 * Created by Caleb on 4/5/17.
 * <p>
 * This class is used for all the things that won't change at run time. Like the alphabet.
 */
public final class Constants {

    private Constants() {
    }


    public static final String[] Letters = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

    public static final Map<String, Integer> hLetters;

    static {
        hLetters = new HashMap<String, Integer>();
        hLetters.put("a", 0);
        hLetters.put("b", 1);
        hLetters.put("c", 2);
        hLetters.put("d", 3);
        hLetters.put("e", 4);
        hLetters.put("f", 5);
        hLetters.put("g", 6);
        hLetters.put("h", 7);
        hLetters.put("i", 8);
        hLetters.put("j", 9);
        hLetters.put("k", 10);
        hLetters.put("l", 11);
        hLetters.put("m", 12);
        hLetters.put("n", 13);
        hLetters.put("o", 14);
        hLetters.put("p", 15);
        hLetters.put("q", 16);
        hLetters.put("r", 17);
        hLetters.put("s", 18);
        hLetters.put("t", 19);
        hLetters.put("u", 20);
        hLetters.put("v", 21);
        hLetters.put("w", 22);
        hLetters.put("x", 23);
        hLetters.put("y", 24);
        hLetters.put("z", 25);
    }



//https://en.wikipedia.org/wiki/Letter_frequency
    public static final float[] EnglishLetterDist = {
            8.167f,
            1.492f,
            2.782f,
            4.253f,
            12.702f,
            2.228f,
            2.015f,
            6.094f,
            6.966f,
            0.153f,
            0.772f,
            4.025f,
            2.406f,
            6.749f,
            7.507f,
            1.929f,
            0.095f,
            5.987f,
            6.327f,
            9.056f,
            2.758f,
            0.978f,
            2.360f,
            0.150f,
            1.974f,
            0.074f
    };


}
