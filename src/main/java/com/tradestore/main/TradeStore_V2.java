package main.java.com.tradestore.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import main.java.com.tradestore.main.pojo.Trade;

/**
 * @author Swapnil
 * This TradeStore uses List to store trades. 
 *
 */
public class TradeStore_V2 implements TradeStore{

	private final List<Trade> tradeStore;

	public TradeStore_V2() {
		tradeStore = new ArrayList<Trade>();
	}
	
	@Override
	public void addTrade(final Trade trade) {
		if (validateTrade(trade)) {
			checkVersionAndUpdateStore(trade);
		}
	}
	
	@Override
	public void displayData() {
		this.tradeStore.forEach(trade -> System.out.println(trade.toString()));
	}
	
	@Override
	public boolean validateTrade(final Trade trade) {
		if(trade == null)
			return false;
		if(trade.getTradeId() == null ||  trade.getTradeId().isEmpty())
			return false;		
		if(trade.getVersion() == null)
			return false;
		if(trade.getCounterPartyId() == null || trade.getCounterPartyId().isEmpty())
			return false;
		if(trade.getBookId() == null || trade.getBookId().isEmpty())
			return false;
		if(trade.getMaturityDate() == null)
			return false;
		if(trade.getCreatedDate() == null)
			return false;
		
		return validateMaturityDate(trade.getMaturityDate());
	}
	
	@Override
	public boolean validateMaturityDate(LocalDate maturityDate) {
		LocalDate currentDate = LocalDate.now();
		if (maturityDate.isBefore(currentDate)) {
			System.out.println("");
			return false;
		}

		return true;
	}
		
	@Override
	public void checkVersionAndUpdateStore(final Trade trade) {
		Integer version = trade.getVersion();
		int index = 0;
		boolean isNewTrade = true;
		synchronized (tradeStore) {
			for (Trade t : this.tradeStore) {
				Integer existingVersion = t.getVersion();
				if (t.getTradeId().equals(trade.getTradeId())) {
					if (version < existingVersion)
						throw new RuntimeException(trade.getTradeId() + " trade version " + trade.getVersion()
								+ " is lower than existing trade version");
					else if (version > existingVersion) {
						this.tradeStore.add(trade);
						isNewTrade = false;
						break;
					}
					else if (version.equals(existingVersion)) {
						this.tradeStore.set(index, trade);
						isNewTrade = false;
						break;
					}
				}
							
			}
			if(isNewTrade)
				this.tradeStore.add(trade);
		}
		

	}
	
	public List<Trade> getTradeStore() {
		return this.tradeStore;
	}
}
