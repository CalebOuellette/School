import java.util.HashMap;

/**
 * Created by Caleb on 2/15/18.
 */
public class InvestmentSimulator extends ReportCondition{

    final float TRADING_FEE = 8;

    public HashMap<String, InvestCompanyData> results = new HashMap<>();


    RollingAverage averages = new RollingAverage();
    SplitDay splitDays = new SplitDay();

    public  void processDay(StockDayRow newDay){

        results.putIfAbsent(newDay.symbol, new InvestCompanyData(newDay.symbol));

        InvestCompanyData company = results.get(newDay.symbol);

        float adjustment = splitDays.processDayWithResult(newDay);
        if(adjustment != 1){
            averages.adjustCompanyData(newDay.symbol, adjustment);
            company.adjust(adjustment);
        }

        if(company.shouldBuy){
            company.shares += 100;
            company.cash -= (100 * newDay.openingPrice);
            company.cash =- TRADING_FEE;
            company.tradeCount++;
            company.shouldBuy = false;
        }

        averages.processDay(newDay);
        float rAvg = averages.getAverage(newDay.symbol);
        if(rAvg != 0){
            if(newDay.closingPrice < rAvg && (newDay.closingPrice / newDay.openingPrice <= 0.97)){
             //Set buy trigger
                company.shouldBuy = true;
            }
            else if(company.shares > 100 && newDay.openingPrice > rAvg && newDay.openingPrice / company.previousDay.closingPrice >= 1.01){
                //sell 100 shares at price (open(d) + close(d))/2
                company.shares -= 100;
                company.cash += 100 * ((newDay.openingPrice + newDay.closingPrice) / 2);
                company.cash =- TRADING_FEE;
                company.tradeCount++;
            }
        }
        company.previousDay = newDay;
    }
    public String toString(String stockID){
        InvestCompanyData company = results.get(stockID);
        Float outCash = company.cash + (company.shares * company.previousDay.openingPrice);

        String out =  "Transactions executed:" + company.tradeCount;
        out += "\nTicker: " + stockID + " \nCash: " + outCash.toString() + '\n';
        return out;
    }

    private class InvestCompanyData{
        InvestCompanyData(String ticker){
            this.ticker = ticker;
        }
        String ticker;
        float shares = 0;
        float cash = 0;
        int tradeCount = 0;
        boolean shouldBuy = false;
        StockDayRow previousDay;

        public void adjust(Float adjustment){
            this.shares = this.shares * adjustment;
        }
    }



}
