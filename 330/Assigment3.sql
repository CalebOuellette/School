

/* IMPORTANT NOTES
My DB seems to be locked up. I can't drop table/ create table / or select from tables without a timeout.
I have placed my resutls in ouellec_chinook.

I did not use java for this assigment because I wanted to improve my SQL skills. I understand that I deviated from the assigment, and understand if you take off points.
I did plan to write a java wrapper for this but ran into a lot of issues and so I am just going to hand in this.

Thanks,

Caleb Ouellette

*/
use ouellec_chinook;


# 1 find all companies for given industry that have at least 150 days of trading. 
CREATE TEMPORARY TABLE IF NOT EXISTS tickerIntervals AS  (
select Ticker,  industry, min(TransDate) as startdate, max(TransDate) as enddate,
 count(distinct TransDate) as TradingDays
 from johnson330.Company natural join johnson330.PriceVolume
 group by Ticker, Industry
 having TradingDays >= 150
 order by industry, Ticker
);

# 2 
# Based off the data from step 1 we can select all the priceVolume data for the first (or min) alphabetical company.
# Also adding a rowNumber to this table for later use.
CREATE TEMPORARY TABLE IF NOT EXISTS priceVolumeOfFirstStock AS  (
select   t.ticker, @_sequence:=@_sequence+1 as rowNumber, p.TransDate, t.Industry
from johnson330.PriceVolume p
join(
 select min(ticker) as ticker, max(startDate) startDate, min(endDate) endDate, industry  from tickerIntervals
 group by industry) t on p.ticker = t.ticker,
 (SELECT @_sequence:=1)s
 where p.TransDate between t.startDate and t.endDate
 order by t.ticker, p.TransDate);
 
 # 3 
 # Temp tables can't be called twice in the same query so I am making another table to hold this data in preperation for
 # Step 4
 CREATE TEMPORARY TABLE IF NOT EXISTS minRow AS  (
 select ticker, min(rowNumber) as minRow from priceVolumeOfFirstStock group by ticker);
 
 
 # 4 
 # Using row number and min row number we can select the 60th record for each category
 # we also select every 59th record as an end date and mark the records as such.
 # the rowNumber does not reset, so we need to subtract the first records row number 
 # for each ticker to make the math line up.
 
 # 4.5 the outer query flattens down the date ranges into on record and verifies that each start has an end.
 CREATE TEMPORARY TABLE IF NOT EXISTS industryIntervals AS  (
 
select TradeInterval, industry, min(transDate) startDate, max(transDate) endDate from 
(
 select
 floor((rowNumber - minRow) /60) + 1 as TradeInterval, 
case 
	when mod((rowNumber - minRow), 60) = 0 
	then 's' 
    else 'e' 
    end dayType ,
    pv.industry,
    pv.transDate
from priceVolumeOfFirstStock pv
join minRow mt on pv.ticker = mt.ticker
where 
mod((rowNumber - minRow), 60) = 0 
or mod((rowNumber - minRow), 60) = 59
 ) x
group by TradeInterval, industry
having count(1) = 2
order by industry, TradeInterval
 );
 
 
#5 Now to find the splits. This was tricker then expected but i think this is a pretty cool method. 
# First thing is to select relevant data. I used my intervals to only select the records I needed.
# Then Order then by ticker, and transdate
CREATE TEMPORARY TABLE IF NOT EXISTS pricetable1 AS  (
select p.ticker, p.openPrice, p.closeprice, transdate, @_sequence:=@_sequence+1 as rowNumber   from johnson330.PriceVolume p
join  johnson330.Company c on p.ticker = c.ticker
join(
 select min(ticker) as ticker, max(startDate) startDate, min(endDate) endDate, industry  from tickerIntervals
 group by industry) t on c.industry = t.industry,
 (SELECT @_sequence:=1) s
 where transdate between t.startdate and t.enddate
 order by p.ticker, p.transdate
);
# 5.1 Ran into some perforance issues, but adding these indexes really helped
create index aRow on pricetable1 (rowNumber);

#5.2 Temp table don't let you join to them self, so i copy my first table here.
CREATE TEMPORARY TABLE IF NOT EXISTS pricetable2 AS  (select * from pricetable1);

# 5.3 same as 5.1
create index aRow on pricetable2 (rowNumber);

# 5.4 Next I calculate the pricediff ration between the two days.
CREATE TEMPORARY TABLE IF NOT EXISTS priceRatio AS  (
select ABS(p1.closeprice / p2.openprice) as priceDiffRatio, p1.transdate, p1.ticker 
from pricetable1 p1
join pricetable2 p2 on (p1.rowNumber + 1) = (p2.rowNumber) and p1.ticker = p2.ticker
);

#5.5 based off the ratio we can derive if there is a split.
CREATE TEMPORARY TABLE IF NOT EXISTS priceSplits AS(
 select transdate,
	case 
    when ABS(priceDiffRatio - 2) < .20 then 2 
    when ABS(priceDiffRatio - 3) < .30 then 3  
    when ABS(priceDiffRatio - 1.5) < .15 then 1.5 
    else 1 end as split,
    ticker
 from priceRatio
where 
ABS(priceDiffRatio - 2) < .20 or 
ABS(priceDiffRatio - 3) < .30 or 
ABS(priceDiffRatio - 1.5) < .15);
 
# 6
 # now that we have splits and time intervals we can start building out the results table. 
 # one limitaion i found was a lack of multiplcation, which I need in order to combine splits if 2 occur on the same intervale. Apperantly 
 CREATE TEMPORARY TABLE IF NOT EXISTS  TickerPerformance as (
 select ti.industry, ii.startDate, ii.endDate, ti.ticker, pvs.openPrice, pve.closeprice * IFNULL(round(exp(sum(ln(split))), 1), 1) , ((pve.closeprice  / (IFNULL(round(exp(sum(ln(split))), 1) , 1) *pvs.openprice)) - 1.0)  tickerReturn from industryIntervals ii
 join tickerIntervals ti on ii.industry = ti.industry
 join johnson330.PriceVolume pvs on pvs.transDate = ii.startDate and ti.ticker = pvs.ticker
 join johnson330.PriceVolume pve on pve.transDate = ii.endDate and ti.ticker = pve.ticker
 left join priceSplits ps on (ps.transdate between ii.startDate and ii.endDate) and ps.ticker = ti.ticker
 group by ii.startDate, ii.endDate, ti.ticker, pvs.openPrice, pve.closeprice, ti.industry
 );
 
 #7 Last step is to calculate industry return first grab the average
CREATE TEMPORARY TABLE IF NOT EXISTS  IndustryAverage as (
select industry, startDate, endDate, avg(tickerReturn) iAvg, count(1) tickerCount  from TickerPerformance 
group by industry,  startDate, endDate);

# 7.1 for each ticker pull out it's value from that average.
CREATE TEMPORARY TABLE IF NOT EXISTS  PerformanceT as (
select tp.Industry, tp.Ticker, tp.StartDate, tp.EndDate, tp.TickerReturn,  ((iAvg * tickerCount) - tp.tickerReturn) / (tickerCount -1) IndustryReturn  
from TickerPerformance tp
join IndustryAverage ia  on ia.industry =tp.industry and ia.enddate = tp.endDate and ia.startdate = tp.startdate);

# 8 Create table
drop table if exists Performance;
create table Performance (Industry char(30), Ticker char(6), StartDate char(10), EndDate char(10), TickerReturn char(12), IndustryReturn char(12));

#9 insert our findings
insert into Performance (Industry , Ticker , StartDate , EndDate ,TickerReturn , IndustryReturn)
select * from PerformanceT;

