import java.text.SimpleDateFormat;
import java.util.ArrayList;
import static java.lang.Math.abs;

/**
 * Created by Caleb on 1/22/18.
 *
 */


public class SplitDay extends ReportCondition  {

    public ArrayList<SplitPair> results = new ArrayList<>();

    public static final float SPLIT_DAY_ROUNDING_THRESHOLD= 0.05f;


    StockDayRow nextDay = new StockDayRow();
    public void processDay(StockDayRow day){
        if(day.symbol.equals(this.nextDay.symbol)){

            if(abs((day.closingPrice / this.nextDay.openingPrice) - 2.0f) <= SPLIT_DAY_ROUNDING_THRESHOLD ){
                SplitPair split = new SplitPair(day, this.nextDay, SplitType.TWOONE);
                results.add(split);
            }
            else if(abs((day.closingPrice / this.nextDay.openingPrice ) - 3.0f) <= SPLIT_DAY_ROUNDING_THRESHOLD ){
                SplitPair split = new SplitPair(day, this.nextDay, SplitType.THREEONE);
                results.add(split);
            }
            else if(abs((day.closingPrice / this.nextDay.openingPrice) - 1.5f) <= SPLIT_DAY_ROUNDING_THRESHOLD ){
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


    private enum SplitType{
        THREETWO,
        THREEONE,
        TWOONE

    }

    private class SplitPair{
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
            outString = outString + outDate.format(this.day.date) + "\t" +
                    String.format("%.2f", this.day.closingPrice)+ "  --> "
                    + String.format("%.2f", this.nextDay.openingPrice);
            return outString;
        }
    }
}
