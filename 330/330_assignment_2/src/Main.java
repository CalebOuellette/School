
import java.util.Properties;
import java.util.Scanner;
import java.io.FileInputStream;
import java.sql.*;

public class Main {
    static Connection conn = null;
    static Properties connectProps = new Properties();
    public static void main(String[] args) throws Exception{


        String paramsFile = "ConnectionParameters.txt";
        if (args.length >= 1) {
            paramsFile = args[0];
        }
        connectProps.load(new FileInputStream(paramsFile));
        int run = 1;
        while(run == 1){
            run = requestInput();
        }
        System.out.print("Closing program \n");
    }

    static int requestInput(){

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a stock ticker start end: \n");
        String inputString = scanner.nextLine();

        String[] userInputs = inputString.split("\\s+");
        if(inputString.trim().isEmpty() || userInputs[0].equals("quit")){
            return 0;
        }else{
            if(userInputs.length == 1) {
                connectAndProcess(userInputs[0], null, null);
            }else if(userInputs.length == 3) {
                connectAndProcess(userInputs[0], userInputs[1], userInputs[2]);
            }
            return 1;
        }
    }

    static void connectAndProcess( String ticker,String startDate, String endDate){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String dburl = connectProps.getProperty("dburl");
            String username = connectProps.getProperty("user");
            conn = DriverManager.getConnection(dburl, connectProps);
            System.out.printf("Database connection %s %s established.%n",
                    dburl, username);
            processCompany(ticker.toUpperCase(), startDate, endDate);
        }
        catch (Exception e){
            System.out.print(e.getMessage());
        }
        finally{
            try {
                conn.close();
                System.out.print("Connection Closed \n");
            }
            catch (SQLException e){
                System.out.print(e.getMessage());
                System.out.print("Failed to close connection.");
            }
        }
    }


    static void processCompany(String companyTicker,String startDate, String endDate)throws SQLException {

        SplitDay splitDays = new SplitDay();
        InvestmentSimulator invest = new InvestmentSimulator();
        ReportCondition[] conditions = { splitDays, invest};
        PreparedStatement pstmt;
        if(startDate != null && endDate != null){
            pstmt = conn.prepareStatement("select * "
                    + " from PriceVolume "
                    + " where Ticker = ? and TransDate between ? and ?  order by TransDate");
            pstmt.setString(1, companyTicker);
            pstmt.setString(2, startDate);
            pstmt.setString(3, endDate);
        }else{
            pstmt = conn.prepareStatement("select * "
                    + " from PriceVolume "
                    + " where Ticker = ?  order by TransDate");
            pstmt.setString(1, companyTicker);
        }
        boolean hasResult = false;
        ResultSet results = pstmt.executeQuery();
        while (results.next()) {

            hasResult = true;
            for(int i=0;i<conditions.length;i++) {
                StockDayRow day = new StockDayRow(results);
                conditions[i].processDay(day);
            }
        }
        System.out.print(" \n");
        pstmt.close();
        if(hasResult){
            for(int i=0;i<conditions.length;i++) {
                System.out.print(conditions[i].toString(companyTicker));
            }
        }else{
            System.out.print(companyTicker + " not found in database. \n");
        }

    }
}

