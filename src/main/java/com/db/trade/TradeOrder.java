/**
 * Class to accept trade orders
 */
package com.db.trade;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
		
		// Automated task that will run every 5 seconds and it will update trade status if found expired
		ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);

        Runnable task2 = (() -> {        	
        	TradeController controller = new TradeController();		
    		controller.updateTradeStatus();
        });
        
        ses.scheduleAtFixedRate(task2, 5, 5, TimeUnit.SECONDS);

	}

}
