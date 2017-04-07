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


}
