/**
 * Created by Caleb on 4/5/17.
 *
 *I am using Test driven to development to slowly build up the program.
 * I started this program before the assigment was completely written, so I except to have to change things. This run of tests will  help me track down
 * what i break.
 */
public class Tests {

    public void runAllTests(){
        this.alphabetTest();
        this.cipherTest();
        this.letterDistributionTest();
        this.decoderTest();
    }



    public void alphabetTest(){
        //Test 1
        String a = Alphabet.getLetter(0);
        if(a != "a"){
            throw new java.lang.Error("UnExpected Value");
        }

        int i = Alphabet.getPosition("a");
        if(i != 0){
            throw new java.lang.Error("UnExpected Value");
        }

        int w = Alphabet.getPosition(")");
        if(w != -1){
            throw new java.lang.Error("UnExpected Value");
        }



    }


    public void cipherTest(){
        Cipher E = new Cipher();

        String aa = Cipher.cipherString("Meet me, at midnight!", "a");
        if(!aa.equals("Meet me, at midnight!")){

            throw new java.lang.Error("Not encoding String correctly a");
        }

        String bb = Cipher.cipherString("MEETMEATmidNIGHT", "FUMBle");

        String cc = Cipher.decipherString(Cipher.cipherString("badfadsfasdf", "FUMBle"), "Fumble");
        if(!cc.equals("badfadsfasdf")){
            throw new java.lang.Error("Not encoding String correctly");
        }
    }

    public void letterDistributionTest(){
       // LetterDistribution LD = new LetterDistribution();

        LetterDistribution i =  new LetterDistribution("AAABBBBCCCDDDEEFFFZZ");



        if(i.letterCounts[0] != 3){
            throw new java.lang.Error("Letter Distribution Error");
        }
        if(Math.abs(i.letterPercents[1] - 0.2) > .0000001){
            throw new java.lang.Error("Letter Distribution Error");
        }
        if(i.letterCounts[4] != 2){
            throw new java.lang.Error("Letter Distribution Error");
        }

        LetterDistribution x = i.subtractDistribution(i);

        if(x.letterCounts[4] != 0 || x.letterCounts[2] != 0 || x.letterCounts[1] != 0 ){
            throw new java.lang.Error("Letter Distribution Error");
        }




    }

    public void decoderTest(){
        Decoder d = new Decoder();


        String testText = "Meet me, at midnight!";
        String aa = Cipher.cipherString(testText, "e");
        LetterDistribution ls = new LetterDistribution(testText);




        String bestLetter = d.findBestCipher(aa, ls);




        String outText = Cipher.decipherString(aa, bestLetter);
        if(!testText.equalsIgnoreCase(outText)){
            throw new java.lang.Error("Decoder Error");
        }
    }
}
