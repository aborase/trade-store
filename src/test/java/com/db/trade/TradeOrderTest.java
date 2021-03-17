package com.db.trade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.db.trade.bean.Trade;
import com.db.trade.store.TradeStore;
import com.db.trade.store.exception.InvalidTradeException;

@TestMethodOrder(OrderAnnotation.class)
@DisplayName("Test class for Trade Store")
class TradeOrderTest {

	TradeOrder tradeOrder;
	SimpleDateFormat formatter;
	private static String DATE_FORMAT = "dd/MM/yyyy";

	@BeforeEach
	void init() {
		tradeOrder = new TradeOrder();
		formatter = new SimpleDateFormat(DATE_FORMAT);
	}

	@Test
	@Order(1)
	@DisplayName("Should add all valid trades")
	void testAddAllTrade() {
		List<Trade> tradeOrders = this.populateValidTrades();
		for (Trade trade : tradeOrders) {
			try {
				tradeOrder.addTrade(trade);
			} catch (InvalidTradeException e) {

				// e.printStackTrace();
			}
		}
		assertEquals(4, TradeStore.getInstance().getAllTrades().size(), "Should add all valid trades");
	}

	@Test
	@Order(2)
	@DisplayName("Should reject trade as trade version is less than existing trade version")
	void testAddTradeWithLowVersionThanExistingTrade() {
		List<Trade> tradeOrders = this.populateValidTrades();
		for (Trade trade : tradeOrders) {
			try {
				tradeOrder.addTrade(trade);
			} catch (InvalidTradeException e) {
				e.printStackTrace();
			}
		}

		Trade t1 = new Trade();
		t1.setTradeId("T1");
		// Trade version = 0
		t1.setVersion(0);
		t1.setCounterPartyid("CP-1");
		t1.setBookId("B1");
		t1.setMaturityDate("20/05/2021");
		t1.setCreatedDate(formatter.format(new Date()));
		t1.setExpired('N');
		
		InvalidTradeException ex = assertThrows(InvalidTradeException.class, () -> tradeOrder.addTrade(t1),
				"Should reject trade as trade version is less than existing trade version");
		
		assertTrue(ex.getErrorMsg().equals("Received trade version is less than current trade version"));

	}

	@Test
	@Order(3)
	@DisplayName("Should reject trade as trade maturity date less than current date")
	void testAddTradeWithBackDatedMaturityDate() {

		Trade t1 = new Trade();
		t1.setTradeId("T1");
		t1.setVersion(1);
		t1.setCounterPartyid("CP-1");
		t1.setBookId("B1");
		// Trade maturity date < current date
		t1.setMaturityDate("20/05/2014");
		t1.setCreatedDate(formatter.format(new Date()));
		t1.setExpired('N');

		InvalidTradeException ex = assertThrows(InvalidTradeException.class, () -> tradeOrder.addTrade(t1),
				"Should reject trade as trade maturity date less than current date");
		
		assertTrue(ex.getErrorMsg().equals("Maturity date can not be less than current date"));

	}

	@Test
	@Order(4)
	@DisplayName("Should update existing trade for same trade version")
	void testupdateExistingTrade() {
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		List<Trade> tradeOrders = this.populateValidTrades();
		for (Trade trade : tradeOrders) {
			try {
				tradeOrder.addTrade(trade);
			} catch (InvalidTradeException e) {
				// e.printStackTrace();
			}
		}

		Trade t1 = new Trade();
		t1.setTradeId("T1");
		t1.setVersion(1);
		t1.setCounterPartyid("CP-1");
		t1.setBookId("B3");
		t1.setMaturityDate("20/05/2022");
		t1.setCreatedDate(formatter.format(new Date()));
		t1.setExpired('N');
		try {
			tradeOrder.addTrade(t1);
			Trade existingTrade = TradeStore.getInstance().getAllTrades().get(0);
			assertEquals("B3", existingTrade.getBookId(), "Should update existing trade for same trade version");
		} catch (InvalidTradeException e) {
			// e.printStackTrace();
		}

	}

	private List<Trade> populateValidTrades() {
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		List<Trade> tradeOrders = new ArrayList<Trade>();

		Trade t1 = new Trade();
		t1.setId(1);
		t1.setTradeId("T1");
		t1.setVersion(1);
		t1.setCounterPartyid("CP-1");
		t1.setBookId("B1");
		t1.setMaturityDate("20/05/2022");
		t1.setCreatedDate(formatter.format(new Date()));
		t1.setExpired('N');
		tradeOrders.add(t1);

		Trade t2 = new Trade();
		t2.setId(2);
		t2.setTradeId("T2");
		t2.setVersion(1);
		t2.setCounterPartyid("CP-2");
		t2.setBookId("B1");
		t2.setMaturityDate("20/05/2022");
		t2.setCreatedDate(formatter.format(new Date()));
		t2.setExpired('N');
		tradeOrders.add(t2);

		Trade t3 = new Trade();
		t3.setId(3);
		t3.setTradeId("T2");
		t3.setVersion(2);
		t3.setCounterPartyid("CP-1");
		t3.setBookId("B1");
		t3.setMaturityDate("20/05/2022");
		t3.setCreatedDate("14/03/2015");
		t3.setExpired('N');
		tradeOrders.add(t3);

		Trade t4 = new Trade();
		t4.setId(4);
		t4.setTradeId("T3");
		t4.setVersion(3);
		t4.setCounterPartyid("CP-3");
		t4.setBookId("B2");
		t4.setMaturityDate("20/05/2022");
		t4.setCreatedDate(formatter.format(new Date()));
		t4.setExpired('Y');
		tradeOrders.add(t4);

		return tradeOrders;

	}

}
