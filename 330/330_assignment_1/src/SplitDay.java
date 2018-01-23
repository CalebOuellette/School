import java.text.SimpleDateFormat;
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

    public ArrayList<SplitPair> resultsTwo_One = new ArrayList<SplitPair>();
    public ArrayList<SplitPair> resultsThree_One = new ArrayList<SplitPair>();
    public ArrayList<SplitPair> resultsThree_two = new ArrayList<SplitPair>();

    public static final float SPLIT_DAY_ROUNDING_THRESHOLD= 0.05f;


    StockDayRow nextDay = new StockDayRow();
    public void processDay(StockDayRow day){
        //2/14/2003
        Date myDate = new GregorianCalendar(2003, Calendar.FEBRUARY, 14).getTime();
        if(day.symbol.equals("MSFT") && day.date.equals(myDate)){
            int i = 0;
        }
        if(day.symbol.equals(this.nextDay.symbol)){
            SplitPair split = new SplitPair(day, this.nextDay);
            if(abs((day.closingPrice / this.nextDay.openingPrice) - 2.0f) < SPLIT_DAY_ROUNDING_THRESHOLD ){
                resultsTwo_One.add(split);
            }
            else if(abs((day.closingPrice / this.nextDay.openingPrice ) - 3.0f) < SPLIT_DAY_ROUNDING_THRESHOLD ){
                resultsThree_One.add(split);
            }
            else if(abs((day.closingPrice / this.nextDay.openingPrice) - 1.5f) < SPLIT_DAY_ROUNDING_THRESHOLD ){
                resultsThree_two.add(split);
            }
        }
        this.nextDay = day;
    }

    public String toString(String stockID){
        String outString = "";
        for (SplitPair split: this.resultsTwo_One) {
            if(split.day.symbol.equals(stockID)){
                System.out.println("2:1 split on " + split.toString());
            }
        }
        for (SplitPair split: this.resultsThree_One) {
            if(split.day.symbol.equals(stockID)){
                System.out.println("3:1 split on " + split.toString());
            }
        }
        for (SplitPair split: this.resultsThree_two) {
            if(split.day.symbol.equals(stockID)){
                System.out.println("3:2 split on " + split.toString());
            }
        }
        return "";
    }
}

enum SplitType{
    THREETWO,
    THREEONE,
    TWOONE

}

class SplitPair{
    StockDayRow day;
    StockDayRow nextDay;
    SplitType type;
    public SplitPair(StockDayRow day, StockDayRow nextDay){
        this.day = day;
        this.nextDay = nextDay;
    }

    public String toString(){
        String outString = "";
        SimpleDateFormat outDate = new SimpleDateFormat("MM/dd/yyyy"); //3/30/2015
        outString = outString + outDate.format(this.day.date) + "\t" + this.day.closingPrice.toString() + "  --> " + this.nextDay.openingPrice.toString();
        return outString;
    }
}