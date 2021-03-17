/**
 * Class to accept trade orders
 */
package com.db.trade;

import com.db.trade.bean.Trade;
import com.db.trade.controller.TradeController;
import com.db.trade.exception.InvalidTradeException;


/**
 * Class to accept trade orders
 * @auther Akshay Borase
 * 
 */
public class TradeOrder {
	
	/**
	 * Method add trade to trade store
	 * @param trade
	 * @throws InvalidTradeException
	 */
	public void addTrade(Trade trade) throws InvalidTradeException {
		TradeController controller = new TradeController();		
		controller.addTrade(trade);
	}

	/**
	 * Class to accept trade orders
	 */
	public static void main(String[] args) {
		
//		TradeController controller = new TradeController();
//		List<Trade> trades = controller.getAllTrades();

	}

}
