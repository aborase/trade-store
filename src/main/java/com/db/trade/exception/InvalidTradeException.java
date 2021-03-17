package com.db.trade.exception;

import com.db.trade.constants.ErrorMessage;

/**
 * Exception class to handle invalid trades.
 * @author Akhsay
 *
 */
public class InvalidTradeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5480044660781327125L;

	private int errorCode;

	private String errorMsg;

	public InvalidTradeException(ErrorMessage code) {
		this.errorMsg = code.getMsg();
		this.errorCode = code.getId();
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

}
