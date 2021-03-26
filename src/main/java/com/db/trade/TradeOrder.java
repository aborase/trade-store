/**
 * Interface to accept trade orders
 */
package com.db.trade;

import com.db.trade.bean.Trade;
import com.db.trade.exception.InvalidTradeException;


/**
 * Class to accept trade orders
 * @auther Akshay Borase
 * 
 */
public interface TradeOrder {
	
	/**
	 * Method add trade to trade store
	 * @param trade
	 * @throws InvalidTradeException
	 */
	public void addTrade(Trade trade) throws InvalidTradeException;
	

}
