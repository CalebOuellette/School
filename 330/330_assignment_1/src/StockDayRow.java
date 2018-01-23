import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
/**
 * Created by Caleb on 1/18/18.
 * 1. ticker symbol (MSFT),
 * 2. date (8/18/2004),
 * 3. opening price (26.93),
 * 4. high price (27.50),
 * 5. low price (26.89),
 * 6. closing price (27.46),
 * 7. volume or number of shares traded on that day (58844000 shares),
 * 8. adjusted closing price (19.46).
 */
public class StockDayRow {

    public StockDayRow() {
        this.symbol = "";
    }

    public StockDayRow(String row){

        String[] elements = row.split("\t");
        this.symbol = elements[0];

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy"); //3/30/2015
        try {
            this.date = formatter.parse(elements[1]);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.openingPrice = Float.parseFloat(elements[2]);
        this.highPrice = Float.parseFloat(elements[3]);
        this.lowPrice = Float.parseFloat(elements[4]);
        this.closingPrice = Float.parseFloat(elements[5]);
        this.volume = Long.parseLong(elements[6]);
        this.adjClosingPrice = Float.parseFloat(elements[7]);
    }
    String symbol;
    Date date;
    Float openingPrice;
    Float highPrice;
    Float lowPrice;
    Float closingPrice;
    Long volume;
    Float adjClosingPrice;
}

