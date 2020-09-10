package main.java.com.tradestore.main;

import java.time.LocalDate;

import main.java.com.tradestore.main.pojo.Trade;


public class TradeStoreStarter {
	public static void main(String[] args) {
		//If Trade id is unique then we can use TradeStore_V1 class to store the trades. 
		//TradeStore_V1 uses Map to store unique trades
		TradeStore tradeStore = new TradeStore_V1();
		
		//If there are duplicate Trade id's then we can use TradeStore_V2 class to store trade.
		//TradeStore_V2 uses List to store trades  
		//TradeStore tradeStore = new TradeStore_V2();
		
		TradeStoreStarter tradeStoreStarter = new TradeStoreStarter();
		
		tradeStoreStarter.addDataInStore(tradeStore);
		tradeStore.displayData();
	}
	
	public void addDataInStore(TradeStore tradeStore) {		
		tradeStore.addTrade(new Trade("T1", 1, "CP-1", "B1", LocalDate.parse("2021-05-20"), LocalDate.now()));
		tradeStore.addTrade(new Trade("T2", 2, "CP-2", "B1", LocalDate.parse("2021-05-20"), LocalDate.now()));
		tradeStore.addTrade(new Trade("T2", 3, "CP-1", "B1", LocalDate.parse("2021-05-20"), LocalDate.parse("2015-03-14")));
		tradeStore.addTrade(new Trade("T3", 3, "CP-1", "B1", LocalDate.parse("2014-05-20"), LocalDate.now()));
	}

}
