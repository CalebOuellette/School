
import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Properties;
import java.util.Scanner;
import java.io.FileInputStream;
import java.sql.*;

public class Main {
    static Connection conn = null;
    public static void main(String[] args) throws Exception{

        String paramsFile = "ConnectionParameters.txt";
        if (args.length >= 1) {
            paramsFile = args[0];
        }

        Properties connectprops = new Properties();
        connectprops.load(new FileInputStream(paramsFile));
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String dburl = connectprops.getProperty("dburl");
            String username = connectprops.getProperty("user");
            conn = DriverManager.getConnection(dburl, connectprops);
            System.out.printf("Database connection %s %s established.%n",
                    dburl, username);
            showCompanies();
        }
        catch (Exception e){
            System.out.print(e.getMessage());
        }
        finally{
            conn.close();
        }

    }


    static void showCompanies() throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet results = stmt.executeQuery("select Ticker, Name from Company");
        while (results.next()) {
            System.out.printf("%5s %s%n", results.getString("Ticker"), results.getString("Name"));
        }
        stmt.close();
    }

    static void assigmentOne(){
        Set<String> stockSymbols = new HashSet<String>();

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

