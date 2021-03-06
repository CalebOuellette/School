import java.sql.ResultSet;
import java.sql.SQLException;
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

        this.openingPrice = Double.parseDouble(elements[2]);
        this.highPrice = Double.parseDouble(elements[3]);
        this.lowPrice = Double.parseDouble(elements[4]);
        this.closingPrice = Double.parseDouble(elements[5]);
        this.volume = Long.parseLong(elements[6]);
        this.adjClosingPrice = Double.parseDouble(elements[7]);
    }

    public StockDayRow(ResultSet r) throws SQLException {
        this.openingPrice = Double.parseDouble(r.getString("OpenPrice"));
        this.highPrice = Double.parseDouble(r.getString("HighPrice"));
        this.lowPrice = Double.parseDouble(r.getString("LowPrice"));
        this.closingPrice= r.getDouble("ClosePrice");
        this.volume= r.getLong("Volume");
        this.adjClosingPrice= Double.parseDouble(r.getString("AdjustedClose"));
        this.symbol = r.getString("Ticker");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
        try {
            this.date = formatter.parse(r.getString("TransDate"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void adjustData(double ratio){
        this.openingPrice = this.openingPrice / ratio;
        this.highPrice = this.highPrice / ratio;
        this.lowPrice = this.lowPrice / ratio;
        this.closingPrice = this.closingPrice / ratio;
        this.adjClosingPrice = this.adjClosingPrice / ratio;
    }

    String symbol;
    Date date;
    Double openingPrice;
    Double highPrice;
    Double lowPrice;
    Double closingPrice;
    Long volume;
    Double adjClosingPrice;
}

