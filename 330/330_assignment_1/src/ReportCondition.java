import java.util.ArrayList;

/**
 * Created by Caleb on 1/19/18.
 */
public abstract class ReportCondition {

    public abstract void processDay(StockDayRow newDay);
    public ArrayList<StockDayRow> results = new ArrayList<StockDayRow>();
    public abstract String resultsToString();
}
