import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Caleb on 1/19/18.
 */
public class CrazyDay extends ReportCondition {

    public static final double CRAZY_DAY_THRESHOLD=.15f;
    public ArrayList<StockDayRow> results = new ArrayList<StockDayRow>();

    public void processDay(StockDayRow day){
        if(this.dayFluctuation(day) >= CRAZY_DAY_THRESHOLD){
            this.results.add(day);
        }
    }

    public String toString(String stockID){
        String outString = "";
        SimpleDateFormat outDate = new SimpleDateFormat("MM/dd/yyyy");
        StockDayRow highestDay = null;
        int length = 0;
        for (StockDayRow day: this.results) {
            if(day.symbol.equals(stockID)){
                outString += "Crazy day: " + outDate.format(day.date) + " " + this.dayFluctuationToString(day) + '\n';
                if(highestDay == null || this.dayFluctuation(day) > this.dayFluctuation(highestDay)){
                    highestDay = day;
                }
                length++;
            }
        }
        outString += "Total crazy days  = " + length + '\n';
        if(highestDay != null){
            outString += "The craziest day: " + this.dayFluctuationToString(highestDay) + "\n";
        }
        outString += "\n";
        return outString;
    }

    private double dayFluctuation(StockDayRow day){
        return ((day.highPrice - day.lowPrice) / day.highPrice);
    }
    private String dayFluctuationToString(StockDayRow day){
        double value =  ((day.highPrice - day.lowPrice) / day.highPrice) * 100;
        return String.format("%.2f", value);
    }

}