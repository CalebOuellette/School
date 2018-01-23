import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static java.lang.Math.abs;

/**
 * Created by Caleb on 1/22/18.
 *
 */
public class SplitDay extends ReportCondition  {

    public ArrayList<StockDayRow> resultsTwo_One = new ArrayList<StockDayRow>();
    public ArrayList<StockDayRow> resultsThree_One = new ArrayList<StockDayRow>();
    public ArrayList<StockDayRow> resultsThree_two = new ArrayList<StockDayRow>();

    public static final float SPLIT_DAY_ROUNDING_THRESHOLD= 0.05f;


    StockDayRow nextDay = new StockDayRow();
    public void processDay(StockDayRow day){
        //2/14/2003
        Date myDate = new GregorianCalendar(2003, Calendar.FEBRUARY, 14).getTime();
        if(day.symbol.equals("MSFT") && day.date.equals(myDate)){
            int i = 0;
        }

        if(day.symbol.equals(this.nextDay.symbol)){
            if(abs((day.closingPrice / this.nextDay.openingPrice) - 2.0f) < SPLIT_DAY_ROUNDING_THRESHOLD ){
                resultsTwo_One.add(this.nextDay);
            }
            else if(abs((day.closingPrice / this.nextDay.openingPrice ) - 3.0f) < SPLIT_DAY_ROUNDING_THRESHOLD ){
                resultsThree_One.add(this.nextDay);
            }
            else if(abs((day.closingPrice / this.nextDay.openingPrice) - 1.5f) < SPLIT_DAY_ROUNDING_THRESHOLD ){
                resultsThree_two.add(this.nextDay);
            }
        }
        this.nextDay = day;
    }

    public String resultsToString(String StockID){
        return "";
    }
}
