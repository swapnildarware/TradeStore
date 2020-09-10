package main.java.com.tradestore.main;

import java.time.LocalDate;

import main.java.com.tradestore.main.pojo.Trade;

public interface TradeStore {
	public void addTrade(final Trade trade);
	public void displayData();
	public boolean validateTrade(final Trade trade);
	public boolean validateMaturityDate(LocalDate maturityDate);
	public void checkVersionAndUpdateStore(final Trade trade); 
}
