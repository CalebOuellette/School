import java.text.SimpleDateFormat;
import java.util.HashMap;

/**
 * Created by Caleb on 2/15/18.
 */
public class InvestmentSimulator extends ReportCondition{

    final double TRADING_FEE = 8;

    public HashMap<String, InvestCompanyData> results = new HashMap<>();
    SimpleDateFormat outDate = new SimpleDateFormat("yyyy.MM.dd");

    RollingAverage averages = new RollingAverage();
    SplitDay splitDays = new SplitDay();

    int errorCount = 0;

    public  void processDay(StockDayRow newDay){
        results.putIfAbsent(newDay.symbol, new InvestCompanyData(newDay.symbol));

        InvestCompanyData company = results.get(newDay.symbol);

        double adjustment = splitDays.processDayWithResult(newDay);
        if(adjustment != 1){
            averages.adjustCompanyData(newDay.symbol, adjustment);
            company.adjust(adjustment);
        }

        if(company.shouldBuy){
            company.shares += 100;
            company.cash -= (100 * newDay.openingPrice);
          // company.cash = company.cash - TRADING_FEE;
            company.tradeCount++;
            company.shouldBuy = false;

            System.out.print("Buy Day " + outDate.format(newDay.date)+ '\n');
            System.out.print("Cost " + (100 * newDay.openingPrice) + '\n');
            System.out.print("Cost " +  newDay.openingPrice + '\n');
            System.out.print(company.getString());

        }


        double rAvg = averages.getAverage(newDay.symbol);
        if(rAvg != 0){
            if(newDay.closingPrice < rAvg && ((newDay.closingPrice / newDay.openingPrice) <= 0.97000001)){
             //Set buy trigger
                company.shouldBuy = true;
            }
            else if(company.shares >= 100 && newDay.openingPrice > rAvg && newDay.openingPrice / company.previousDay.closingPrice >= 1.00999999){
                company.shares = company.shares - 100;
                company.cash += 100 * ((newDay.openingPrice + newDay.closingPrice) / 2);
               // company.cash = company.cash - TRADING_FEE;
                company.tradeCount++;
                SimpleDateFormat outDate = new SimpleDateFormat("yyyy.MM.dd");
                System.out.print("Sell Day " + outDate.format(newDay.date)+ '\n');
                System.out.print(company.getString());

            }
        }
        averages.processDay(newDay);
        company.previousDay = newDay;
        company.dayCount++;
    }
    public String toString(String stockID){
        InvestCompanyData company = results.get(stockID);
        Double outCash = company.cash + (company.shares * company.previousDay.openingPrice) - (company.tradeCount * TRADING_FEE);
        //Double outCash = company.cash + (company.shares * company.previousDay.openingPrice);

        String out =  "Transactions executed: " + company.tradeCount + '\n';
        out +=  "Days processed executed: " + company.dayCount + '\n';
        out +=  "Error Days: " + this.errorCount + '\n';
        out += "\nTicker: " + stockID + " \nCash: " + outCash.toString() + '\n';
        return out;
    }

    private class InvestCompanyData{
        InvestCompanyData(String ticker){
            this.ticker = ticker;
        }
        String ticker;
        double shares = 0;
        double cash = 0;
        int tradeCount = 0;
        int dayCount = 0;
        boolean shouldBuy = false;
        StockDayRow previousDay;

        public void adjust(Double adjustment){
            //this.shares = this.shares * adjustment;
            this.cash = cash / adjustment;
        }

        public String getString(){
            String out = "";
             out += "Cash: " + Double.toString(this.cash) + '\n';
             out += "Shares: " + Double.toString(this.shares) + '\n';

            return out;
        }
    }



}
