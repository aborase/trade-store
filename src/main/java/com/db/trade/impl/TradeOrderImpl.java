package com.db.trade.impl;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.db.trade.TradeOrder;
import com.db.trade.bean.Trade;
import com.db.trade.controller.TradeController;
import com.db.trade.exception.InvalidTradeException;

public class TradeOrderImpl implements TradeOrder{
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
		
        Runnable task = (() -> {     
        	System.out.println("Running schedular task ...");
        	TradeController controller = new TradeController();		
    		controller.updateTradeStatus();
        });
        
        ses.scheduleAtFixedRate(task, 5, 5, TimeUnit.SECONDS);

	}


}