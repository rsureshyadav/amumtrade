package com.amumtrade.bean;

public class StockBean {

	 	String ticker;
	    String price;
	    float change;
	    String chartUrlSmall;
	    String chartUrlLarge;
	    long lastUpdated;
	    String yahooRating;
	    String cnnRating;
	    String theStreetRating;
	    String marketWatchRating;
	    //Newly Added
	    String ask;
	    String AverageDailyVolume;
	    String AskSize;  
	    String Bid;
	    String AskRealTime;  
	    String BidRealTime;  
	    String BookValue ;
	    String BidSize;
	    String ChangePercentChange; 
	    String Change;
	    String Commission;  
	    String ChangeRealTime;  
	    String AfterHoursChangeRealTimeDividendShare;  
	    String LastTradeDate;  
	    String TradeDate;
	    String EarningsShare; 
	    String ErrorIndication;   
	    String EstimateCurrentYear;  
	    String EstimateNextYear;  
	    String EstimateNextQuarter;  
	    String FloatShares;  
	    String DayLow;  
	    String DayHigh;  
	    String FiftyTwoWeekLow;  
	    String FiftyTwoWeekHigh;  
	    String HoldingsGainPercent;  
	    String AnnualizedGain;  
	    String HoldingsGain;  
	    String HoldingsGainPercentRealTime; 
	    String HoldingsGainRealTime;
	    String MoreInfo;
	    String OrderBookRealTime; 
	    String MarketCapitalization;
	    String MarketCapRealTime; 
	    String EBITDA;  
	    String ChangeFromFiftyTwoWeekLow;
	    String PercentChangeFromFiftyTwoWeekLow; 
	    String LastTradeRealTime; 
	    String ChangePercentRealTime;
	    String LastTradeSize;
	    String ChangeFromFiftyTwoWeekHigh;  
	    String PercentChangeFromFiftyTwoWeekHigh; 
	    String LastTradeWithTime; 
	    String LastTradePriceOnly;
	    String HighLimit;  
	    String LowLimit; 
	    String DayRange; 
	    String DayRangeRealTime;  
	    String FiftyDayMovingAverage;  
	    String TwoHundredDayMovingAverage; 
	    String ChangeFromTwoHundredDayMovingAverage;
	    String PercentChangeFromTwoHundredDayMovingAverage;  
	    String ChangeFromFiftyDayMovingAverage;  
	    String PercentChangeFromFiftyDayMovingAverage; 
	    String Name;  
	    String Notes;  
	    String Open;  
	    String PreviousClose;  
	    String PricePaid;  
	    String ChangeInPercent; 
	    String PriceOrSales;  
	    String PriceOrBook;  
	    String ExDividentDate;  
	    String PERatio; 
	    String DividendPayDate;  
	    String PERatioRealTime;  
	    String PEGRatio;  
	    String PriceOrEstimateCurrentYear;  
	    String PriceOrEstimateNextYear;  
	    String Symbol;  
	    String SharesOwned;  
	    String ShortRatio;  
	    String LastTradeTime;  
	    String TradeLinks;  
	    String TickerTrend;  
	    String OneYearTargetPrice;  
	    String Volume;  
	    String HoldingsValue;  
	    String HoldingsValueRealTime;  
	    String FiftyTwoWeekRange;  
	    String DaysValueChange;
	    String DaysValueChangeRealTime;  
	    String StockExchange;  
	    String DividendYield;     
	    String AMUMTradeWeight;



		public String getAMUMTradeWeight() {
			return AMUMTradeWeight;
		}
		public void setAMUMTradeWeight(String tradeWeight) {
			AMUMTradeWeight = tradeWeight;
		}
		public String getTicker() {
			return ticker;
		}
		public void setTicker(String ticker) {
			this.ticker = ticker;
		}
		public String getPrice() {
			return price;
		}
		public void setPrice(String price) {
			this.price = price;
		}
		public float getChange() {
			return change;
		}
		public void setChange(float change) {
			this.change = change;
		}
		public String getChartUrlSmall() {
			return chartUrlSmall;
		}
		public void setChartUrlSmall(String chartUrlSmall) {
			this.chartUrlSmall = chartUrlSmall;
		}
		public String getChartUrlLarge() {
			return chartUrlLarge;
		}
		public void setChartUrlLarge(String chartUrlLarge) {
			this.chartUrlLarge = chartUrlLarge;
		}
		public long getLastUpdated() {
			return lastUpdated;
		}
		public void setLastUpdated(long lastUpdated) {
			this.lastUpdated = lastUpdated;
		}
	    //Newly Added
		public String getAsk() {
			return ask;
		}
		public void setAsk(String ask) {
			this.ask = ask;
		}
		public String getAverageDailyVolume() {
			return AverageDailyVolume;
		}
		public void setAverageDailyVolume(String averageDailyVolume) {
			AverageDailyVolume = averageDailyVolume;
		}
		public String getAskSize() {
			return AskSize;
		}
		public void setAskSize(String askSize) {
			AskSize = askSize;
		}
		public String getBid() {
			return Bid;
		}
		public void setBid(String bid) {
			Bid = bid;
		}
		public String getAskRealTime() {
			return AskRealTime;
		}
		public void setAskRealTime(String askRealTime) {
			AskRealTime = askRealTime;
		}
		public String getBidRealTime() {
			return BidRealTime;
		}
		public void setBidRealTime(String bidRealTime) {
			BidRealTime = bidRealTime;
		}
		public String getBookValue() {
			return BookValue;
		}
		public void setBookValue(String bookValue) {
			BookValue = bookValue;
		}
		public String getBidSize() {
			return BidSize;
		}
		public void setBidSize(String bidSize) {
			BidSize = bidSize;
		}
		public String getChangePercentChange() {
			return ChangePercentChange;
		}
		public void setChangePercentChange(String changePercentChange) {
			ChangePercentChange = changePercentChange;
		}
		public String getCommission() {
			return Commission;
		}
		public void setCommission(String commission) {
			Commission = commission;
		}
		public String getChangeRealTime() {
			return ChangeRealTime;
		}
		public void setChangeRealTime(String changeRealTime) {
			ChangeRealTime = changeRealTime;
		}
		public String getAfterHoursChangeRealTimeDividendShare() {
			return AfterHoursChangeRealTimeDividendShare;
		}
		public void setAfterHoursChangeRealTimeDividendShare(
				String afterHoursChangeRealTimeDividendShare) {
			AfterHoursChangeRealTimeDividendShare = afterHoursChangeRealTimeDividendShare;
		}
		public String getLastTradeDate() {
			return LastTradeDate;
		}
		public void setLastTradeDate(String lastTradeDate) {
			LastTradeDate = lastTradeDate;
		}
		public String getTradeDate() {
			return TradeDate;
		}
		public void setTradeDate(String tradeDate) {
			TradeDate = tradeDate;
		}
		public String getEarningsShare() {
			return EarningsShare;
		}
		public void setEarningsShare(String earningsShare) {
			EarningsShare = earningsShare;
		}
		public String getErrorIndication() {
			return ErrorIndication;
		}
		public void setErrorIndication(String errorIndication) {
			ErrorIndication = errorIndication;
		}
		public String getEstimateCurrentYear() {
			return EstimateCurrentYear;
		}
		public void setEstimateCurrentYear(String estimateCurrentYear) {
			EstimateCurrentYear = estimateCurrentYear;
		}
		public String getEstimateNextYear() {
			return EstimateNextYear;
		}
		public void setEstimateNextYear(String estimateNextYear) {
			EstimateNextYear = estimateNextYear;
		}
		public String getEstimateNextQuarter() {
			return EstimateNextQuarter;
		}
		public void setEstimateNextQuarter(String estimateNextQuarter) {
			EstimateNextQuarter = estimateNextQuarter;
		}
		public String getFloatShares() {
			return FloatShares;
		}
		public void setFloatShares(String floatShares) {
			FloatShares = floatShares;
		}
		public String getDayLow() {
			return DayLow;
		}
		public void setDayLow(String dayLow) {
			DayLow = dayLow;
		}
		public String getDayHigh() {
			return DayHigh;
		}
		public void setDayHigh(String dayHigh) {
			DayHigh = dayHigh;
		}
		public String getFiftyTwoWeekLow() {
			return FiftyTwoWeekLow;
		}
		public void setFiftyTwoWeekLow(String fiftyTwoWeekLow) {
			FiftyTwoWeekLow = fiftyTwoWeekLow;
		}
		public String getFiftyTwoWeekHigh() {
			return FiftyTwoWeekHigh;
		}
		public void setFiftyTwoWeekHigh(String fiftyTwoWeekHigh) {
			FiftyTwoWeekHigh = fiftyTwoWeekHigh;
		}
		public String getHoldingsGainPercent() {
			return HoldingsGainPercent;
		}
		public void setHoldingsGainPercent(String holdingsGainPercent) {
			HoldingsGainPercent = holdingsGainPercent;
		}
		public String getAnnualizedGain() {
			return AnnualizedGain;
		}
		public void setAnnualizedGain(String annualizedGain) {
			AnnualizedGain = annualizedGain;
		}
		public String getHoldingsGain() {
			return HoldingsGain;
		}
		public void setHoldingsGain(String holdingsGain) {
			HoldingsGain = holdingsGain;
		}
		public String getHoldingsGainPercentRealTime() {
			return HoldingsGainPercentRealTime;
		}
		public void setHoldingsGainPercentRealTime(String holdingsGainPercentRealTime) {
			HoldingsGainPercentRealTime = holdingsGainPercentRealTime;
		}
		public String getHoldingsGainRealTime() {
			return HoldingsGainRealTime;
		}
		public void setHoldingsGainRealTime(String holdingsGainRealTime) {
			HoldingsGainRealTime = holdingsGainRealTime;
		}
		public String getMoreInfo() {
			return MoreInfo;
		}
		public void setMoreInfo(String moreInfo) {
			MoreInfo = moreInfo;
		}
		public String getOrderBookRealTime() {
			return OrderBookRealTime;
		}
		public void setOrderBookRealTime(String orderBookRealTime) {
			OrderBookRealTime = orderBookRealTime;
		}
		public String getMarketCapitalization() {
			return MarketCapitalization;
		}
		public void setMarketCapitalization(String marketCapitalization) {
			MarketCapitalization = marketCapitalization;
		}
		public String getMarketCapRealTime() {
			return MarketCapRealTime;
		}
		public void setMarketCapRealTime(String marketCapRealTime) {
			MarketCapRealTime = marketCapRealTime;
		}
		public String getEBITDA() {
			return EBITDA;
		}
		public void setEBITDA(String ebitda) {
			EBITDA = ebitda;
		}
		public String getChangeFromFiftyTwoWeekLow() {
			return ChangeFromFiftyTwoWeekLow;
		}
		public void setChangeFromFiftyTwoWeekLow(String changeFromFiftyTwoWeekLow) {
			ChangeFromFiftyTwoWeekLow = changeFromFiftyTwoWeekLow;
		}
		public String getPercentChangeFromFiftyTwoWeekLow() {
			return PercentChangeFromFiftyTwoWeekLow;
		}
		public void setPercentChangeFromFiftyTwoWeekLow(
				String percentChangeFromFiftyTwoWeekLow) {
			PercentChangeFromFiftyTwoWeekLow = percentChangeFromFiftyTwoWeekLow;
		}
		public String getLastTradeRealTime() {
			return LastTradeRealTime;
		}
		public void setLastTradeRealTime(String lastTradeRealTime) {
			LastTradeRealTime = lastTradeRealTime;
		}
		public String getChangePercentRealTime() {
			return ChangePercentRealTime;
		}
		public void setChangePercentRealTime(String changePercentRealTime) {
			ChangePercentRealTime = changePercentRealTime;
		}
		public String getLastTradeSize() {
			return LastTradeSize;
		}
		public void setLastTradeSize(String lastTradeSize) {
			LastTradeSize = lastTradeSize;
		}
		public String getChangeFromFiftyTwoWeekHigh() {
			return ChangeFromFiftyTwoWeekHigh;
		}
		public void setChangeFromFiftyTwoWeekHigh(String changeFromFiftyTwoWeekHigh) {
			ChangeFromFiftyTwoWeekHigh = changeFromFiftyTwoWeekHigh;
		}
		public String getPercentChangeFromFiftyTwoWeekHigh() {
			return PercentChangeFromFiftyTwoWeekHigh;
		}
		public void setPercentChangeFromFiftyTwoWeekHigh(
				String percentChangeFromFiftyTwoWeekHigh) {
			PercentChangeFromFiftyTwoWeekHigh = percentChangeFromFiftyTwoWeekHigh;
		}
		public String getLastTradeWithTime() {
			return LastTradeWithTime;
		}
		public void setLastTradeWithTime(String lastTradeWithTime) {
			LastTradeWithTime = lastTradeWithTime;
		}
		public String getLastTradePriceOnly() {
			return LastTradePriceOnly;
		}
		public void setLastTradePriceOnly(String lastTradePriceOnly) {
			LastTradePriceOnly = lastTradePriceOnly;
		}
		public String getHighLimit() {
			return HighLimit;
		}
		public void setHighLimit(String highLimit) {
			HighLimit = highLimit;
		}
		public String getLowLimit() {
			return LowLimit;
		}
		public void setLowLimit(String lowLimit) {
			LowLimit = lowLimit;
		}
		public String getDayRange() {
			return DayRange;
		}
		public void setDayRange(String dayRange) {
			DayRange = dayRange;
		}
		public String getDayRangeRealTime() {
			return DayRangeRealTime;
		}
		public void setDayRangeRealTime(String dayRangeRealTime) {
			DayRangeRealTime = dayRangeRealTime;
		}
		public String getFiftyDayMovingAverage() {
			return FiftyDayMovingAverage;
		}
		public void setFiftyDayMovingAverage(String fiftyDayMovingAverage) {
			FiftyDayMovingAverage = fiftyDayMovingAverage;
		}
		public String getTwoHundredDayMovingAverage() {
			return TwoHundredDayMovingAverage;
		}
		public void setTwoHundredDayMovingAverage(String twoHundredDayMovingAverage) {
			TwoHundredDayMovingAverage = twoHundredDayMovingAverage;
		}
		public String getChangeFromTwoHundredDayMovingAverage() {
			return ChangeFromTwoHundredDayMovingAverage;
		}
		public void setChangeFromTwoHundredDayMovingAverage(
				String changeFromTwoHundredDayMovingAverage) {
			ChangeFromTwoHundredDayMovingAverage = changeFromTwoHundredDayMovingAverage;
		}
		public String getPercentChangeFromTwoHundredDayMovingAverage() {
			return PercentChangeFromTwoHundredDayMovingAverage;
		}
		public void setPercentChangeFromTwoHundredDayMovingAverage(
				String percentChangeFromTwoHundredDayMovingAverage) {
			PercentChangeFromTwoHundredDayMovingAverage = percentChangeFromTwoHundredDayMovingAverage;
		}
		public String getChangeFromFiftyDayMovingAverage() {
			return ChangeFromFiftyDayMovingAverage;
		}
		public void setChangeFromFiftyDayMovingAverage(
				String changeFromFiftyDayMovingAverage) {
			ChangeFromFiftyDayMovingAverage = changeFromFiftyDayMovingAverage;
		}
		public String getPercentChangeFromFiftyDayMovingAverage() {
			return PercentChangeFromFiftyDayMovingAverage;
		}
		public void setPercentChangeFromFiftyDayMovingAverage(
				String percentChangeFromFiftyDayMovingAverage) {
			PercentChangeFromFiftyDayMovingAverage = percentChangeFromFiftyDayMovingAverage;
		}
		public String getName() {
			return Name;
		}
		public void setName(String name) {
			Name = name;
		}
		public String getNotes() {
			return Notes;
		}
		public void setNotes(String notes) {
			Notes = notes;
		}
		public String getOpen() {
			return Open;
		}
		public void setOpen(String open) {
			Open = open;
		}
		public String getPreviousClose() {
			return PreviousClose;
		}
		public void setPreviousClose(String previousClose) {
			PreviousClose = previousClose;
		}
		public String getPricePaid() {
			return PricePaid;
		}
		public void setPricePaid(String pricePaid) {
			PricePaid = pricePaid;
		}
		public String getChangeInPercent() {
			return ChangeInPercent;
		}
		public void setChangeInPercent(String changeInPercent) {
			ChangeInPercent = changeInPercent;
		}
		public String getPriceOrSales() {
			return PriceOrSales;
		}
		public void setPriceOrSales(String priceOrSales) {
			PriceOrSales = priceOrSales;
		}
		public String getPriceOrBook() {
			return PriceOrBook;
		}
		public void setPriceOrBook(String priceOrBook) {
			PriceOrBook = priceOrBook;
		}
		public String getExDividentDate() {
			return ExDividentDate;
		}
		public void setExDividentDate(String exDividentDate) {
			ExDividentDate = exDividentDate;
		}
		public String getPERatio() {
			return PERatio;
		}
		public void setPERatio(String ratio) {
			PERatio = ratio;
		}
		public String getDividendPayDate() {
			return DividendPayDate;
		}
		public void setDividendPayDate(String dividendPayDate) {
			DividendPayDate = dividendPayDate;
		}
		public String getPERatioRealTime() {
			return PERatioRealTime;
		}
		public void setPERatioRealTime(String ratioRealTime) {
			PERatioRealTime = ratioRealTime;
		}
		public String getPEGRatio() {
			return PEGRatio;
		}
		public void setPEGRatio(String ratio) {
			PEGRatio = ratio;
		}
		public String getPriceOrEstimateCurrentYear() {
			return PriceOrEstimateCurrentYear;
		}
		public void setPriceOrEstimateCurrentYear(String priceOrEstimateCurrentYear) {
			PriceOrEstimateCurrentYear = priceOrEstimateCurrentYear;
		}
		public String getPriceOrEstimateNextYear() {
			return PriceOrEstimateNextYear;
		}
		public void setPriceOrEstimateNextYear(String priceOrEstimateNextYear) {
			PriceOrEstimateNextYear = priceOrEstimateNextYear;
		}
		public String getSymbol() {
			return Symbol;
		}
		public void setSymbol(String symbol) {
			Symbol = symbol;
		}
		public String getSharesOwned() {
			return SharesOwned;
		}
		public void setSharesOwned(String sharesOwned) {
			SharesOwned = sharesOwned;
		}
		public String getShortRatio() {
			return ShortRatio;
		}
		public void setShortRatio(String shortRatio) {
			ShortRatio = shortRatio;
		}
		public String getLastTradeTime() {
			return LastTradeTime;
		}
		public void setLastTradeTime(String lastTradeTime) {
			LastTradeTime = lastTradeTime;
		}
		public String getTradeLinks() {
			return TradeLinks;
		}
		public void setTradeLinks(String tradeLinks) {
			TradeLinks = tradeLinks;
		}
		public String getTickerTrend() {
			return TickerTrend;
		}
		public void setTickerTrend(String tickerTrend) {
			TickerTrend = tickerTrend;
		}
		public String getOneYearTargetPrice() {
			return OneYearTargetPrice;
		}
		public void setOneYearTargetPrice(String oneYearTargetPrice) {
			OneYearTargetPrice = oneYearTargetPrice;
		}
		public String getVolume() {
			return Volume;
		}
		public void setVolume(String volume) {
			Volume = volume;
		}
		public String getHoldingsValue() {
			return HoldingsValue;
		}
		public void setHoldingsValue(String holdingsValue) {
			HoldingsValue = holdingsValue;
		}
		public String getHoldingsValueRealTime() {
			return HoldingsValueRealTime;
		}
		public void setHoldingsValueRealTime(String holdingsValueRealTime) {
			HoldingsValueRealTime = holdingsValueRealTime;
		}
		public String getFiftyTwoWeekRange() {
			return FiftyTwoWeekRange;
		}
		public void setFiftyTwoWeekRange(String fiftyTwoWeekRange) {
			FiftyTwoWeekRange = fiftyTwoWeekRange;
		}
		public String getDaysValueChange() {
			return DaysValueChange;
		}
		public void setDaysValueChange(String daysValueChange) {
			DaysValueChange = daysValueChange;
		}
		public String getDaysValueChangeRealTime() {
			return DaysValueChangeRealTime;
		}
		public void setDaysValueChangeRealTime(String daysValueChangeRealTime) {
			DaysValueChangeRealTime = daysValueChangeRealTime;
		}
		public String getStockExchange() {
			return StockExchange;
		}
		public void setStockExchange(String stockExchange) {
			StockExchange = stockExchange;
		}
		public String getDividendYield() {
			return DividendYield;
		}
		public void setDividendYield(String dividendYield) {
			DividendYield = dividendYield;
		}
		public void setChange(String change) {
			Change = change;
		}
		public String getYahooRating() {
			return yahooRating;
		}
		public void setYahooRating(String yahooRating) {
			this.yahooRating = yahooRating;
		}
		public String getCnnRating() {
			return cnnRating;
		}
		public void setCnnRating(String cnnRating) {
			this.cnnRating = cnnRating;
		}
		public String getTheStreetRating() {
			return theStreetRating;
		}
		public void setTheStreetRating(String theStreetRating) {
			this.theStreetRating = theStreetRating;
		}
		public String getMarketWatchRating() {
			return marketWatchRating;
		}
		public void setMarketWatchRating(String marketWatchRating) {
			this.marketWatchRating = marketWatchRating;
		}

			    
}
