/**
 * Created by Caleb on 1/19/18.
 */
public abstract class ReportCondition {

    public abstract void processDay(StockDayRow newDay);
    public abstract String toString(String StockID);
}
