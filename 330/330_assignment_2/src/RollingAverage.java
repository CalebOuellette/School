import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Caleb on 2/15/18.
 */
public class RollingAverage extends ReportCondition{
    final int ROLLINGAVERAGEINTERVAL = 50;

    public HashMap<String, CompanyData> results = new HashMap<>();

    public  void processDay(StockDayRow newDay){
        if(results.get(newDay.symbol) == null){
            results.put(newDay.symbol, new CompanyData(newDay.symbol));
        }
        CompanyData company = results.get(newDay.symbol);
        company.days.add(0, newDay);
        if(company.days.size() > ROLLINGAVERAGEINTERVAL)
            company.days.remove(company.days.size() - 1);

    }
    public String toString(String StockID){
        String outString = "";
        for(Map.Entry<String, CompanyData> entry : results.entrySet()) {
            String key = entry.getKey();
            outString = outString + key + "Average " + this.getAverage(key) + "\n";
        }
        return outString;
    }

    public double getAverage(String ticker){
        CompanyData company = results.get(ticker);
        if(company == null){
            return 0;
        }
        if(company.days.size() != ROLLINGAVERAGEINTERVAL){
            return 0;
        }
        double avg = 0;
        for(int i = 0; i < company.days.size(); i++ ){
            avg += company.days.get(i).closingPrice;
        }
        avg = avg / ROLLINGAVERAGEINTERVAL;
        return avg;
    }

    public void adjustCompanyData(String ticker, double adjustment){
        CompanyData company = results.get(ticker);
        for(int i = 0; i < company.days.size(); i++ ){
            company.days.get(i).adjustData(adjustment);
        }
    }

    class CompanyData{
        String ticker;
        public ArrayList<StockDayRow> days = new ArrayList<>();

        CompanyData(String ticker){
            this.ticker = ticker;
        }
    }
}
