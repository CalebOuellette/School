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

    public ArrayList<SplitPair> results = new ArrayList<SplitPair>();

    public static final float SPLIT_DAY_ROUNDING_THRESHOLD= 0.05f;


    StockDayRow nextDay = new StockDayRow();
    public void processDay(StockDayRow day){
        //2/14/2003
        Date myDate = new GregorianCalendar(1991, Calendar.JUNE, 26).getTime();
        if(day.symbol.equals("MSFT") && day.date.equals(myDate)){
            int i = 0;
        }
        if(day.symbol.equals(this.nextDay.symbol)){

            if(abs((day.closingPrice / this.nextDay.openingPrice) - 2.0f) < SPLIT_DAY_ROUNDING_THRESHOLD ){
                SplitPair split = new SplitPair(day, this.nextDay, SplitType.TWOONE);
                results.add(split);
            }
            else if(abs((day.closingPrice / this.nextDay.openingPrice ) - 3.0f) < SPLIT_DAY_ROUNDING_THRESHOLD ){
                SplitPair split = new SplitPair(day, this.nextDay, SplitType.THREEONE);
                results.add(split);
            }
            else if(abs((day.closingPrice / this.nextDay.openingPrice) - 1.5f) < SPLIT_DAY_ROUNDING_THRESHOLD ){
                SplitPair split = new SplitPair(day, this.nextDay, SplitType.THREETWO);
                results.add(split);
            }
        }
        this.nextDay = day;
    }

    public String toString(String stockID){
        String outString = "";
        int length = 0;
        for (SplitPair split: this.results) {
            if(split.day.symbol.equals(stockID)){
                outString = outString + split.toString() + '\n';
                length++;
            }
        }
        outString = outString + "Total number of splits: " + length + '\n';
        return outString;
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
    public SplitPair(StockDayRow day, StockDayRow nextDay, SplitType type){
        this.day = day;
        this.nextDay = nextDay;
        this.type = type;
    }

    public String toString(){
        String outString = "";
        switch(this.type){
            case THREEONE:
                outString = outString + "3:1 split on ";
                break;
            case THREETWO:
                outString = outString + "3:2 split on ";
                break;
            case TWOONE:
                outString = outString + "2:1 split on ";
                break;
        }

        SimpleDateFormat outDate = new SimpleDateFormat("MM/dd/yyyy"); //3/30/2015
        outString = outString + outDate.format(this.day.date) + "\t" + this.day.closingPrice.toString() + "  --> " + this.nextDay.openingPrice.toString();
        return outString;
    }
}