/**
 * 
 */
package com.db.trade.bean;

import java.util.Date;

/**
 * @author Icon
 *
 */
public class Trade {
	
	private String tradeId;
	private int version;
	private String counterPartyid;
	private String bookId;
	private String maturityDate;
	private String createdDate;
	private char expired;
	
	private int id;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	
	/**
	 * @return the tradeId
	 */
	public String getTradeId() {
		return tradeId;
	}
	/**
	 * @param tradeId the tradeId to set
	 */
	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}
	/**
	 * @return the version
	 */
	public int getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(int version) {
		this.version = version;
	}
	/**
	 * @return the counterPartyid
	 */
	public String getCounterPartyid() {
		return counterPartyid;
	}
	/**
	 * @param counterPartyid the counterPartyid to set
	 */
	public void setCounterPartyid(String counterPartyid) {
		this.counterPartyid = counterPartyid;
	}
	/**
	 * @return the bookId
	 */
	public String getBookId() {
		return bookId;
	}
	/**
	 * @param bookId the bookId to set
	 */
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	/**
	 * @return the maturityDate
	 */
	public String getDateMaturityDate() {
		return maturityDate;
	}
	
	/**
	 * @param maturityDate the maturityDate to set
	 */
	public void setMaturityDate(String maturityDate) {
		this.maturityDate = maturityDate;
	}
	/**
	 * @return the createdDate
	 */
	public String getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @return the expired
	 */
	public char getExpired() {
		return expired;
	}
	/**
	 * @param expired the expired to set
	 */
	public void setExpired(char expired) {
		this.expired = expired;
	}	
	
	@Override
	public String toString() {
		return "Trade [id=" + id + ", tradeId=" + tradeId + ", version=" + version + ", counterPartyid=" + counterPartyid + ", bookId="
				+ bookId + ", maturityDate=" + maturityDate + ", createdDate=" + createdDate + ", expired=" + expired
				+ "]";
	}

}
