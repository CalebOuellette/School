
import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        Set<String> stockSymbols = new HashSet();

        CrazyDay crazyDays = new CrazyDay();
        SplitDay splitDays = new SplitDay();

        ReportCondition[] conditions = {crazyDays, splitDays};

        //Read in Data
        try {
            File file = new File("StockMarket-1990-2015.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                StockDayRow day = new StockDayRow(line);

                for(int i=0;i<conditions.length;i++) {
                    conditions[i].processDay(day);
                }

                stockSymbols.add(day.symbol);
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //format results
        String result = "";
        String[] symbolArray = stockSymbols.toArray(new String[stockSymbols.size()]);
        Arrays.sort(symbolArray);
        for (String symbol : symbolArray) {
            result += "\n" +
                    "Processing " + symbol + "\n" +
                    "======================\n";
            for(int i=0;i<conditions.length;i++) {

                result += conditions[i].toString(symbol);
            }
        }
        System.out.print(result);
    }

}

