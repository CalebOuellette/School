/**
 * Created by Caleb on 4/5/17.
 *
 * This class will handle encoding strings and decoding string by known Ciphers
 *
 */
public class Cipher {

    public static String cipherString(String inputString, String keyWord){
        //encodes a string using a keyword

        String encodedString = "";
        int k =0;
        for(int i =0; i < inputString.length(); i++){  //for each letter in the input string...
            String newLetter = "";
            if(Alphabet.getPosition(Character.toString(inputString.charAt(i))) != -1){
                newLetter = cipherLetter(Character.toString(inputString.charAt(i)), Character.toString(keyWord.charAt(k % keyWord.length())));
                k++;
            }else{
                newLetter = Character.toString(inputString.charAt(i));
            }

            encodedString = encodedString + newLetter; //Add the encoded Letter to the out string.
        }

        return encodedString;
    }



    public static String decipherString(String inputString, String keyWord){
        //encodes a string using a keyword

        String encodedString = "";
        int k =0;
        for(int i =0; i < inputString.length(); i++){  //for each letter in the input string...
            String newLetter = "";
            if(Alphabet.getPosition(Character.toString(inputString.charAt(i))) != -1){
                newLetter = decipherLetter( Character.toString(inputString.charAt(i)),  Character.toString(keyWord.charAt(i % keyWord.length())));
                k++;
            }else{
                newLetter = Character.toString(inputString.charAt(i));
            }
            encodedString = encodedString + newLetter; //Add the encoded Letter to the out string.
        }

        return encodedString;
    }


    public static String cipherLetter(String letter, String cypher){
        //Encodes a sing letter using another letter.


        int offset = Alphabet.getPosition(cypher); //get position of our Cypher
        int letterPos = Alphabet.getPosition(letter);//get position of our letter
        if(letter.toLowerCase().equals(letter)){
            //if letter is lowerCase
            return Alphabet.getLetter(letterPos + offset); //Add the Cyhper to letter to get a new encoded Letter
        }else{
            return Alphabet.getLetter(letterPos + offset).toUpperCase();
        }

    }

    public static String decipherLetter(String letter, String cypher){
        //Encodes a sing letter using another letter.
        int offset = Alphabet.getPosition(cypher); //get position of our Cypher
        int letterPos = Alphabet.getPosition(letter);//get position of our letter
        if(letter.toLowerCase().equals(letter)){
            return Alphabet.getLetter(letterPos - offset); //Add the Cyhper to letter to get a new encoded Letter
        }else{
            return Alphabet.getLetter(letterPos - offset).toUpperCase(); //Add the Cyhper to letter to get a new encoded Letter
        }

    }


}
