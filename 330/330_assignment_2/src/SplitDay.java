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


    StockDayRow lastDay = new StockDayRow();
    public void processDay(StockDayRow day){
        this.processDayWithResult(day);
    }

    public float processDayWithResult(StockDayRow newDay){
        float splitRatio = 1;
        if(this.lastDay.symbol.equals(newDay.symbol)){

            if(abs((this.lastDay.closingPrice / newDay.openingPrice) - 2.0f) <= 0.20 ){
                SplitPair split = new SplitPair(this.lastDay, newDay, SplitType.TWOONE);
                results.add(split);
                splitRatio = 2.0f;
            }
            else if(abs((this.lastDay.closingPrice / newDay.openingPrice ) - 3.0f) <= 0.30 ){
                SplitPair split = new SplitPair(this.lastDay, newDay, SplitType.THREEONE);
                results.add(split);
                splitRatio = 3.0f;
            }
            else if(abs((this.lastDay.closingPrice / newDay.openingPrice) - 1.5f) <= 0.15 ){
                SplitPair split = new SplitPair(this.lastDay, newDay, SplitType.THREETWO);
                results.add(split);
                splitRatio = 1.5f;
            }
        }
        this.lastDay = newDay;
        return splitRatio;
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
