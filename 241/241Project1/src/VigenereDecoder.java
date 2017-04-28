/**
 * Created by Caleb on 4/27/2017.
 */
public class VigenereDecoder {

    public String plainText = "";
    public int Nmax;
    public int Nmin;





    public VigenereDecoder(String text){
        this(text, Constants.defaultHigh, Constants.defaultLow);
    }


    public VigenereDecoder(String text, int highRange, int lowRange){
        this.plainText = text;
        this.Nmax = highRange;
        this.Nmin = lowRange;
    }


    public String getDecodeWord(){
        String word = "";
        int i = this.Nmin;
        while ( i < this.Nmax) {
            String[] dividedStrings = sliceText(i);
            Decoder decodeObj = new Decoder(dividedStrings[0], Constants.EnglishDistObj); //only testing the first String if we get a good result we will test the rest;


                System.out.println( "length is '" + i + "' Score is: " + decodeObj.zScore + "letter is: " + decodeObj.bestLetter);

                if(decodeObj.zScore > .99){
                    word = word +  decodeObj.bestLetter;
                    for (int j = 1; j < i; j++) {
                        Decoder decodeLetter = new Decoder(dividedStrings[j], Constants.EnglishDistObj);
                        word = word + decodeLetter.bestLetter;
                    }
                    break;
                }
            i++;
        }

        return word;
    }


    private String[] sliceText(int pieces) {
        String[] outStrings = new String[pieces];
        for (int i = 0; i < pieces; i++) {
            outStrings[i] = "";
        }

        int k = 0;
        for (int i = 0; i < this.plainText.length() && i < (1000 * pieces); i++) {
            if (Alphabet.getPosition(Character.toString(this.plainText.charAt(i))) != -1) {
                outStrings[k % pieces] = "" + outStrings[k % pieces] + this.plainText.charAt(i);
                k++;
            }
        }

    return outStrings;
    }

}
