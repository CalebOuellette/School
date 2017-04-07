import static java.lang.Math.abs;

/**
 * Created by Caleb on 4/5/17.
 *
 * This is my main object that holds letter Distributions. It does the work of breaking strings into there letter distributions.
 * This also provides a toolbox of operations between other distributions of letters,
 *
 */
public class LetterDistribution {
//TODO write base class. I have too many concepts in this class, which will lead to way to mean null checks, which means I should write a seprate class.



    public String text;
    public  int[] letterCounts;
    public  float[] letterPercents;

    LetterDistribution(){
        this("");
    }


    LetterDistribution(String text){
        this.text = text;
        this.letterCounts = this.getLetterDistribution(text);
        this.letterPercents = this.getLetterDistributionPercents(text);
    }

    LetterDistribution(float[] letterDist){
        //for construction the object from a know statics
        this.letterPercents = letterDist;
    }



    private int[] getLetterDistribution(String inputString){
        int[] lettersCounts = this.initLetterCountArray();



        for(int i = 0; i < inputString.length(); i++){
           String c = Character.toString(inputString.charAt(i));
           int letterPos = Alphabet.getPosition(c);
            lettersCounts[letterPos] = lettersCounts[letterPos] + 1;
        }
        return lettersCounts;
    };

    public float[] getLetterDistributionPercents(String inputString){

        int[] letters = this.getLetterDistribution(inputString);
        float[] percents = new float[Constants.Letters.length];

        for(int i = 0; i < letters.length; i++) {
            percents[i] = ((float) letters[i]) / (float)  (inputString.length());
        }
        return percents;
    }

    public int[] initLetterCountArray(){
        //creates an empty array that we will add to as we find letters.
        int[] a = new int[Constants.Letters.length];

        for(int i = 0; i < Constants.Letters.length; i++){
            a[i] = 0;
        }
        return a;
    }

    public LetterDistribution subtractDistribution(LetterDistribution subtractor){
        LetterDistribution a = new LetterDistribution("");

        for(int i = 0; i < Constants.Letters.length; i++){
            int sum = this.letterCounts[i] - subtractor.letterCounts[i];
            float percentSum = this.letterPercents[i] - subtractor.letterPercents[i];
            a.letterCounts[i] = abs(sum);
            a.letterPercents[i] = abs(percentSum);
        }

        return a;
    }

    public float proximityScore(LetterDistribution subtractor){
        LetterDistribution diff = this.subtractDistribution(subtractor);
        Float total = 0f;
        for (int i = 0; i < this.letterCounts.length; i++) {
            total = total + diff.letterPercents[i];
        }
        return total;

    }


    public LetterDistribution shift(int i ){
        return this.shift(Alphabet.getLetter(i));

    }

    public LetterDistribution shift(String letter ){
        if(letter.length() != 1){
            throw new java.lang.Error("letter input should be length 1");
        }

        LetterDistribution out = new LetterDistribution(Cipher.decipherString(this.text, letter));
        return  out;
    }



}
