import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Caleb on 1/19/18.
 */
public class CrazyDay extends ReportCondition {

    public static final float CRAZY_DAY_THRESHOLD=.15f;
    public ArrayList<StockDayRow> results = new ArrayList<StockDayRow>();

    public void processDay(StockDayRow day){
        if(this.dayFluxuation(day) >= CRAZY_DAY_THRESHOLD){
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
                outString = outString + "Crazy day: " + outDate.format(day.date) + " " + this.dayFluxuationToString(day) + '\n';
                if(highestDay == null || this.dayFluxuation(day) > this.dayFluxuation(highestDay)){
                    highestDay = day;
                }
                length++;
            }
        }
        outString = outString + "Total crazy days  = " + length + '\n';
        if(highestDay != null){
            outString = outString + "The craziest day: " + this.dayFluxuationToString(highestDay) + "\n \n";
        }

        return outString;
    }

    private float dayFluxuation(StockDayRow day){
        return ((day.highPrice - day.lowPrice) / day.highPrice);
    }
    private String dayFluxuationToString(StockDayRow day){
        float value =  ((day.highPrice - day.lowPrice) / day.highPrice) * 100;
        return String.format("%.2f", value);
    }

}
//
//          Crazy day: 1/2/1998	16.92
//        Total crazy days = 7
//        The craziest day: 1/6/1998	26.25

