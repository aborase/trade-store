/**
 * 
 */
package com.db.trade.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import com.db.trade.bean.Trade;
import com.db.trade.constants.ErrorMessage;
import com.db.trade.exception.InvalidTradeException;
import com.db.trade.store.TradeStore;


/**
 * Controller class to manage trades. This class contain all business logic to
 * handle trade
 * 
 * @auther Akshay Borase
 * 
 */
public class TradeController {

	private static String DATE_FORMAT = "dd/MM/yyyy";

	/**
	 * Method to add trade to trade store
	 * 
	 * @param trade
	 * @throws InvalidTradeException
	 */
	public void addTrade(Trade trade) throws InvalidTradeException {

		this.validateMaturityDate(trade);
		this.process(trade);
	}

	/**
	 * Validate trade for invlaid maturity date
	 * 
	 * @param trade
	 * @throws InvalidTradeException
	 */
	private void validateMaturityDate(Trade trade) throws InvalidTradeException {
		try {
			Date maturityDate = new SimpleDateFormat(DATE_FORMAT).parse(trade.getDateMaturityDate());
			if (maturityDate.before(new Date())) {
				throw new InvalidTradeException(ErrorMessage.INVALID_MATURITY_DATE);
			}

		} catch (ParseException e) {
			// e.printStackTrace();
		}
	}

	/**
	 * Method to process trade
	 * 
	 * @param inTrade
	 * @throws InvalidTradeException
	 */
	private void process(Trade inTrade) throws InvalidTradeException {

		List<Trade> trades = TradeStore.getInstance().getAllTrades();

		// get all trades with matching trade id
		List<Trade> filteredTrades = trades.parallelStream().filter(res -> inTrade.getTradeId() == res.getTradeId())
				.collect(Collectors.toList());
		
		// if new Trade
		int listSize = filteredTrades.size();

		switch (listSize) {
		// if filteredTrades list size is zero then add new trade to store
		case 0:
			this.addNewTrade(inTrade, trades.size());
			break;
		// if filteredTrades list size is 1 then process single trade
		case 1:
			this.processSingleTrade(filteredTrades, inTrade, trades.size());
			break;
		// if filteredTrades list size is > 1 then process all trades
		default:
			this.processTrades(filteredTrades, inTrade, trades.size());
			break;

		}

	}

	/**
	 * 
	 * @param filteredTrades
	 * @param inTrade
	 * @param listSize
	 * @throws InvalidTradeException
	 */
	private void processTrades(List<Trade> filteredTrades, Trade inTrade, int listSize) throws InvalidTradeException {

		// get min and max version of Trade

		Trade minVersion = filteredTrades
							.stream()
							.min(Comparator.comparing(Trade::getVersion))
							.orElseThrow(NoSuchElementException::new);

		Trade maxVersion = filteredTrades
							.stream()
							.max(Comparator.comparing(Trade::getVersion))
							.orElseThrow(NoSuchElementException::new);

		//If received trade version less than min trade version
		if (inTrade.getVersion() < minVersion.getVersion()) {
			
			throw new InvalidTradeException(ErrorMessage.INVALID_TRADE_VERSION);
			
		} else if (inTrade.getVersion() > maxVersion.getVersion()) {
			
			//if received trade version greater than max trade version then add new trade
			this.addNewTrade(inTrade, listSize);
			
		} else {
			// else update existing trade with matching trade id
			Trade oldTrade = null;
			
			for (Trade t : TradeStore.getInstance().getAllTrades()) {
				if (t.getTradeId() == inTrade.getTradeId() && t.getVersion() == inTrade.getVersion()) {
					oldTrade = t;
					break;
				}
			}

			this.updateTrade(oldTrade, inTrade);
		}

	}

	/**
	 * Method to add new trade to store
	 * @param inTrade
	 * @param listSize
	 */
	private void addNewTrade(Trade inTrade, int listSize) {

		Trade trade = new Trade();
		trade.setId(++listSize);
		trade.setTradeId(inTrade.getTradeId());
		trade.setVersion(inTrade.getVersion());
		trade.setCounterPartyid(inTrade.getCounterPartyid());
		trade.setBookId(inTrade.getBookId());
		trade.setMaturityDate(inTrade.getDateMaturityDate());
		trade.setCreatedDate(inTrade.getCreatedDate());
		trade.setExpired(inTrade.getExpired());

		TradeStore.getInstance().addTrade(trade);
		
	}

	/**
	 * Method to process single matching trade
	 * @param trade
	 * @throws InvalidTradeException
	 */
	private void processSingleTrade(List<Trade> trades, Trade inTrade, int listSize) throws InvalidTradeException {
		Trade trade = trades.get(0);
		
		//If input trade version < existing trade version
		if (inTrade.getVersion() < trade.getVersion()) {
			
			throw new InvalidTradeException(ErrorMessage.INVALID_TRADE_VERSION);

		} else if (inTrade.getVersion() == trade.getVersion()) {	
			
			//If input trade version = existing trade version then update existing trade
			this.updateTrade(trade, inTrade);

		} else {		
			
			//Add new trade
			this.addNewTrade(inTrade, listSize);
			
		}
	}

	/**
	 * Method to update trade
	 * @param trade
	 * @param inTrade
	 */
	private void updateTrade(Trade trade, Trade inTrade) {
		for (Trade t : TradeStore.getInstance().getAllTrades()) {
			if (t.getId() == trade.getId()) {
				t.setTradeId(inTrade.getTradeId());
				t.setVersion(inTrade.getVersion());
				t.setCounterPartyid(inTrade.getCounterPartyid());
				t.setBookId(inTrade.getBookId());
				t.setMaturityDate(inTrade.getDateMaturityDate());
				t.setCreatedDate(inTrade.getCreatedDate());
				t.setExpired(inTrade.getExpired());
				break;
			}
		}
	}	

	/**
	 * Method to get all trades from store
	 * @return
	 */
	public List<Trade> getAllTrades() {
		TradeStore store = TradeStore.getInstance();
		// store.populateTrades(this.populateTrades());
		return store.getAllTrades();
	}

}
