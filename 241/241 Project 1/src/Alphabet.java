/**
 * Created by Caleb on 4/5/17.
 *
 * New to java and not sure what the best implmentation is for converting letters to numbers and number to letters.
 * I thought it would be best to encapslate this away and change it later if needed.
 */
public class Alphabet {

    public static String getLetter(int position){
        //gets letter by Position
        int find;
        if(position > -1){
             find = position % Constants.Letters.length;
        }else{
             find = position % Constants.Letters.length;
             find = Constants.Letters.length + find;
        }

            return Constants.Letters[find];
    }

    public static int getPosition(String letter){
        //return position of given letter
        letter = letter.toLowerCase();
        if(Constants.hLetters.containsKey(letter)){
            int test = Constants.hLetters.get(letter);
            return test;
        }else{
            return -1;
        }
    }

}
