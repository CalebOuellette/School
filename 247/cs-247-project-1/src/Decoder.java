/**
 * Created by Caleb on 4/7/17.
 *
 * Where the brut force magic happens.
 */
public class Decoder {

    public String findBestCipher(String inputString, LetterDistribution statisticalNorm){
        LetterDistribution input = new LetterDistribution(inputString);


        int best = 100;
        float bestScore = 100;
        float[] scores = new float[Constants.Letters.length];
        LetterDistribution[] possibleCiphers = new LetterDistribution[Constants.Letters.length];

        for(int i = 0; i < Constants.Letters.length; i++){
            possibleCiphers[i] = input.shift(i);
            scores[i] = statisticalNorm.proximityScore(possibleCiphers[i]);
            System.out.println(Alphabet.getLetter(i)+ ": " + scores[i]);
            if(scores[i] < bestScore){
                best = i;
                bestScore = scores[i];
            }
        }



        return Alphabet.getLetter(best);
    }

    public String cleanText(String inputString){
        String cleanString = "";

        for (int i = 0; i < inputString.length(); i++) {
            if(Alphabet.getPosition(Character.toString(inputString.charAt(i))) != -1){
                cleanString = cleanString + Character.toString(inputString.charAt(i));
            }
        }



        return cleanString;

    }


    public Float calculateZValue(Float[] Scores){
        float average = 0;


        for (int i = 0; i < Scores.length; i++) {

        }

        return 0f;
    }

    public Float average(Float[] scores){
        float average = 0;
        float sum = 0;


        for (int i = 0; i < scores.length; i++) {
            sum = sum + scores[i];
        }
        average = sum / scores.length;
        return average;
    }


}
