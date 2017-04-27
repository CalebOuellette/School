/**
 * Created by Caleb on 4/7/17.
 *
 * Where the brut force magic happens.
 */
public class Decoder {

    public float[] scores;
    public float average;
    public float standardDeviation;
    public float bestScore = 1000000;
    public int best;
    public String bestLetter;
    public double zScore;


    public Decoder(String inputString, LetterDistribution statisticalNorm){
        LetterDistribution input = new LetterDistribution(inputString);

        this.scores = new float[Constants.Letters.length];
        LetterDistribution[] possibleCiphers = new LetterDistribution[Constants.Letters.length];

        for(int i = 0; i < Constants.Letters.length - 1; i++){
            possibleCiphers[i] = input.shift(i);

            scores[i] = statisticalNorm.proximityScore(possibleCiphers[i]);



            if(scores[i] < bestScore){
                best = i;
                bestScore = scores[i];
            }
        }

        this.average = findAverage(this.scores);
        this.setStandardDeviation();
        this.zScore = this.zScoreProb(this.bestScore);

        this.bestLetter =  Alphabet.getLetter(best);
    }



    public float calculateZValue(float score){
        return (score - this.average) / this.standardDeviation;
    }

    public  double zScoreProb(float zScore){
        double out = Math.pow(zScore, 1);
        out = out * -1.2;
        out = 1 - (.5 * Math.pow(Math.E, out));

        return out;
    }


    public float findAverage(float[] scores){
        float average = 0;
        float sum = 0;


        for (int i = 0; i < scores.length; i++) {
            sum = sum + scores[i];
        }
        average = sum / scores.length;
        return average;
    }


    public void setStandardDeviation(){
        float[] deviation = new float[Constants.Letters.length];

        for (int i = 0; i < scores.length; i++) {
            float v = scores[i] - this.average;
            float sq = v * v;
            deviation[i] = sq;
        }

        this.standardDeviation =  this.findAverage(deviation);
    }

}
