/**
 * 
 */
package com.db.trade.store;

import java.util.ArrayList;
import java.util.List;

import com.db.trade.bean.Trade;

/**
 * Data access class to handle CRUD operations. 
 * The class is made singleton to ensure only one class object created for CRUD operation on store (List)
 * @author Akshay
 *
 */
public class TradeStore {

	private List<Trade> tradeOrders = null;

	private static TradeStore STORE;

	private TradeStore() {
		tradeOrders = new ArrayList<Trade>();
	}

	public static TradeStore getInstance() {
		if (STORE == null) { // if there is no instance available... create new one
			STORE = new TradeStore();
		}

		return STORE;
	}

	
	/**
	 * Method to get all trades
	 * @return
	 */
	public List<Trade> getAllTrades() {
		return this.tradeOrders;
	}

	/**
	 * Method to update trade
	 * @param trade
	 */
	public void addTrade(Trade trade) {
		this.tradeOrders.add(trade);
	}

	/**
	 * Method to update trade.
	 * @param trade
	 */
	public void updateTrade(List<Trade> trades) {		
		this.tradeOrders = trades;
	}

}
