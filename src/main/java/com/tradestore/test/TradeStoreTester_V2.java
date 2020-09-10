package main.java.com.tradestore.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import main.java.com.tradestore.main.TradeStore_V2;
import main.java.com.tradestore.main.pojo.Trade;


/**
 * @author swapnil
 * Below JUNIT test cases written for TradeStore_V2 store.
 * TradeStore_V2 uses list to store trades.
 */
public class TradeStoreTester_V2 {
	TradeStore_V2 tradeStore;
	
	TradeStoreTester_V2(){
		tradeStore = new TradeStore_V2();
	}
	
	@BeforeAll
	static void beforeAllTestExecution() {
		System.out.println("Trade Store - V2 Execution begin...");	
	}
	
	@AfterAll
	static void afterAllTestExecution() {
		System.out.println("Trade Store - V2 Execution Completed...");
	}
		
	@Test
	void testNewTrade() {
		tradeStore.addTrade(new Trade("T1", 1, "CP-1", "B1", LocalDate.parse("2020-10-21"), LocalDate.now()));
		
		assertNotNull(tradeStore.getTradeStore());
		assertEquals(1, tradeStore.getTradeStore().size());
		assertNotNull(tradeStore.getTradeStore().get(0).getTradeId());
		assertNotNull(tradeStore.getTradeStore().get(0).getVersion());
		assertNotNull(tradeStore.getTradeStore().get(0).getCounterPartyId());
		assertNotNull(tradeStore.getTradeStore().get(0).getBookId());
		assertNotNull(tradeStore.getTradeStore().get(0).getMaturityDate());
		assertNotNull(tradeStore.getTradeStore().get(0).getCreatedDate());
		assertNotNull(tradeStore.getTradeStore().get(0).isExpired());
		
	}
	
	@Test
	void testNewTradeData() {
		tradeStore.addTrade(new Trade("T1", 1, "CP-1", "B1", LocalDate.parse("2020-10-21"), LocalDate.now()));
		
		assertEquals("T1", tradeStore.getTradeStore().get(0).getTradeId());
		assertEquals(Integer.valueOf(1), tradeStore.getTradeStore().get(0).getVersion());
		assertEquals("CP-1", tradeStore.getTradeStore().get(0).getCounterPartyId());
		assertEquals("B1", tradeStore.getTradeStore().get(0).getBookId());
		assertEquals(LocalDate.parse("2020-10-21"), tradeStore.getTradeStore().get(0).getMaturityDate());
		assertEquals(LocalDate.now(), tradeStore.getTradeStore().get(0).getCreatedDate());
		assertFalse(tradeStore.getTradeStore().get(0).isExpired());
	}
	
	@Test
	void testMaturityDate() {
		assertFalse(tradeStore.validateMaturityDate(LocalDate.parse("2020-05-20")));
		assertTrue(tradeStore.validateMaturityDate(LocalDate.parse("2021-05-20")));
	}
	
	@Test
	void testTrade() {
		Trade trade1 = new Trade(null, 1, "CP-1", "B1", LocalDate.parse("2021-05-20"), LocalDate.now());
		Trade trade2 = new Trade("T1", 1, "CP-1", "B1", LocalDate.parse("2021-05-20"), LocalDate.now());
		assertFalse(tradeStore.validateTrade(trade1));
		assertTrue(tradeStore.validateTrade(trade2));
	}
	
	@Test
	void testExpiredFlagValue() {
		Trade trade1 = new Trade("T1", 1, "CP-1", "B1", LocalDate.parse("2020-05-20"), LocalDate.now());
		Trade trade2 = new Trade("T2", 1, "CP-1", "B1", LocalDate.parse("2021-05-20"), LocalDate.now());
		
		assertTrue(trade1.isExpired());
		assertFalse(trade2.isExpired());
	}
	
	@Test
	void testLowerVersionTrade() {
		tradeStore = new TradeStore_V2();
		Trade trade1 = new Trade("T1", 2, "CP-1", "B1", LocalDate.parse("2021-05-20"), LocalDate.now());
		Trade trade2 = new Trade("T1", 1, "CP-1", "B1", LocalDate.parse("2021-05-20"), LocalDate.now());
		
		tradeStore.checkVersionAndUpdateStore(trade1);
		assertThrows(RuntimeException.class, () -> {
			tradeStore.checkVersionAndUpdateStore(trade2);
		});
		assertNotNull(tradeStore.getTradeStore());
		assertEquals(1, tradeStore.getTradeStore().size());
		assertEquals("T1", tradeStore.getTradeStore().get(0).getTradeId());
		assertEquals(Integer.valueOf(2), tradeStore.getTradeStore().get(0).getVersion());
	}
	
	@Test
	void testHigherVersionTrade() {
		tradeStore = new TradeStore_V2();
		Trade trade1 = new Trade("T1", 2, "CP-1", "B1", LocalDate.parse("2021-05-20"), LocalDate.now());
		Trade trade2 = new Trade("T1", 3, "CP-1", "B1", LocalDate.parse("2021-05-20"), LocalDate.now());
		
		tradeStore.checkVersionAndUpdateStore(trade1);
		tradeStore.checkVersionAndUpdateStore(trade2);
		
		assertNotNull(tradeStore.getTradeStore());
		assertEquals(2, tradeStore.getTradeStore().size());
		assertEquals("T1", tradeStore.getTradeStore().get(0).getTradeId());
		assertEquals("T1", tradeStore.getTradeStore().get(1).getTradeId());
		assertEquals(Integer.valueOf(2), tradeStore.getTradeStore().get(0).getVersion());
		assertEquals(Integer.valueOf(3), tradeStore.getTradeStore().get(1).getVersion());
	}
	
	@Test
	void testSameVersionTrade() {
		tradeStore = new TradeStore_V2();
		Trade trade1 = new Trade("T1", 2, "CP-1", "B1", LocalDate.parse("2021-05-20"), LocalDate.now());
		Trade trade2 = new Trade("T1", 2, "CP-2", "B2", LocalDate.parse("2021-05-20"), LocalDate.now());
		
		tradeStore.checkVersionAndUpdateStore(trade1);
		tradeStore.checkVersionAndUpdateStore(trade2);
		
		assertNotNull(tradeStore.getTradeStore());
		assertEquals(1, tradeStore.getTradeStore().size());
		assertEquals("T1", tradeStore.getTradeStore().get(0).getTradeId());
		assertEquals("CP-2", tradeStore.getTradeStore().get(0).getCounterPartyId());
		assertEquals("B2", tradeStore.getTradeStore().get(0).getBookId());
		assertEquals(Integer.valueOf(2), tradeStore.getTradeStore().get(0).getVersion());
	}
	
	@Test
	void testFinal() {
		tradeStore = new TradeStore_V2();
		tradeStore.addTrade(new Trade("T1", 1, "CP-1", "B1", LocalDate.parse("2020-10-20"), LocalDate.now()));
		tradeStore.addTrade(new Trade("T2", 2, "CP-2", "B1", LocalDate.parse("2021-10-20"), LocalDate.now()));
		tradeStore.addTrade(new Trade("T2", 3, "CP-1", "B1", LocalDate.parse("2021-10-20"), LocalDate.parse("2015-03-14")));
		tradeStore.addTrade(new Trade("T3", 3, "CP-1", "B1", LocalDate.parse("2014-10-20"), LocalDate.now()));
		
		assertEquals(3, tradeStore.getTradeStore().size());
	}
}
