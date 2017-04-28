import java.io.File;

public class Main {

    public static void main(String[] args) {
        Tests t = new Tests();
        t.runAllTests();


        FileManager x = new FileManager();
        String filePath = new File("").getAbsolutePath();
        filePath = filePath.concat("\\src\\huckfinn-balderdash.txt");
        String fileText = x.readfile(filePath);

        VigenereDecoder vd = new VigenereDecoder(fileText);
        String decodeWord = vd.getDecodeWord();

        x.decodeFileTwo(filePath, Constants.outPutFile, decodeWord);

    }
}
