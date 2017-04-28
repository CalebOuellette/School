import java.io.File;

public class Main {

    public static void main(String[] args) {
        Tests t = new Tests();
        t.runAllTests();


        FileManager fm = new FileManager();
        String fileText = fm.readfile(Constants.inputFileName);

        VigenereDecoder vd = new VigenereDecoder(fileText);
        String decodeWord = vd.getDecodeWord();

        fm.decodeFileTwo( Constants.inputFileName, Constants.outPutFile, decodeWord);
    }
}
