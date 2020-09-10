package main.java.com.tradestore.main;

import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import main.java.com.tradestore.main.pojo.Trade;

/**
 * @author Swapnil
 * This TradeStore uses Map to store unique trades. 
 *
 */
public class TradeStore_V1 implements TradeStore{
	private final Map<String, Trade> tradeStore;
	
	public  TradeStore_V1() {
		this.tradeStore = new ConcurrentHashMap<String, Trade>();
	}
	
	@Override
	public void addTrade(final Trade trade) {
		if(validateTrade(trade))
			checkVersionAndUpdateStore(trade);
	}
	
	@Override
	public void displayData(){
		this.tradeStore.forEach((k,v) -> System.out.println(v.toString()));
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
		String key = trade.getTradeId();
		if(this.tradeStore.containsKey(key)){
			Integer existingVersion = this.tradeStore.get(key).getVersion();
			if(version < existingVersion)
				throw new RuntimeException(trade.getTradeId() + " trade version "+trade.getVersion()+" is lower than existing trade version "+existingVersion);
			else 
				if(validateTrade(trade))			
					this.tradeStore.put(trade.getTradeId(), trade);			
		}
		else
			this.tradeStore.put(trade.getTradeId(), trade);
	}

	public Map<String, Trade> getTradeStore() {
		return this.tradeStore;
	}
	
	
}
