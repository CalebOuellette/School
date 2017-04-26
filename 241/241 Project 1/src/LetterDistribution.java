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
    public  int totalLetterCount = 0;

    LetterDistribution(){
        this("");
    }


    LetterDistribution(String text){
        this.text = text;
        this.setTotalLetterCount();
        this.letterCounts = this.getLetterDistribution(text);
        this.letterPercents = this.getLetterDistributionPercents(text);
    }

    LetterDistribution(float[] letterDist){
        //for construction the object from a know statics
        this.letterPercents = letterDist;
    }


    private void setTotalLetterCount(){
        this.totalLetterCount = 0;
        for(int i = 0; i <  this.text.length(); i++){
            String c = Character.toString(this.text.charAt(i));
            int letterPos = Alphabet.getPosition(c);
            if(letterPos != -1){
                totalLetterCount = totalLetterCount + 1;
            }
        }
    }

    private int[] getLetterDistribution(String inputString){
        //count the letters in a given string

        int[] lettersCounts = this.initLetterCountArray();

        for(int i = 0; i < inputString.length(); i++){
           String c = Character.toString(inputString.charAt(i));
           int letterPos = Alphabet.getPosition(c);
           if(letterPos != -1){
               lettersCounts[letterPos] = lettersCounts[letterPos] + 1;
           }

        }
        return lettersCounts;
    };

    public float[] getLetterDistributionPercents(String inputString){
        //count the letters in a given string, divide by length to get averages.

        int[] letters = this.getLetterDistribution(inputString);
        float[] percents = new float[Constants.Letters.length];

        for(int i = 0; i < letters.length; i++) {
            percents[i] = (((float) letters[i]) / (float)  (totalLetterCount)) * 100;
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
        //subtracts one LetterDistribution from another
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
        //subtracts one Letter Distribution from another and sums the percents
        LetterDistribution diff = this.subtractDistribution(subtractor);
        Float total = 0f;
        for (int i = 0; i < this.letterCounts.length; i++) {
            total = total + diff.letterPercents[i];
        }
        return total;

    }


    public LetterDistribution shift(int i ){
        //creates a new distribution moved over
        return this.shift(Alphabet.getLetter(i));
    }

    public LetterDistribution shift(String letter ){
        //creates a new distribution moved over by the letter
        if(letter.length() != 1){
            throw new java.lang.Error("letter input should be length 1");
        }

        LetterDistribution out = new LetterDistribution(Cipher.decipherString(this.text, letter));
        return  out;
    }



}
