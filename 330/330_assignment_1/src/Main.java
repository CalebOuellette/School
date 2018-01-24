
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        Set<String> stockSymbols = new HashSet();

        CrazyDay crazyDays = new CrazyDay();
        SplitDay splitDays = new SplitDay();

        ReportCondition[] conditions = {crazyDays, splitDays};

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

        String result = "";
        String[] symbolArray = stockSymbols.toArray(new String[stockSymbols.size()]);
        for (String symbol : symbolArray) {
            for(int i=0;i<conditions.length;i++) {
                System.out.print(conditions[i].toString(symbol));
            }
        }

    }

}

