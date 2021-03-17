/**
 * 
 */
package com.db.trade.constants;

/**
 * Enum to store error messages
 * @author Akshay
 *
 */
public enum ErrorMessage {
	INVALID_MATURITY_DATE(0, "Maturity date can not be less than current date"),
	INVALID_TRADE_VERSION(1, "Received trade version is less than current trade version");
	
	private final int id;
	
	private final String msg;
	
	ErrorMessage(int id, String msg) {
	    this.id = id;
	    this.msg = msg;
	}

	public int getId() {
		return this.id;
	}
	
	public String getMsg() {
	    return this.msg;
	}

}
