package com.joragupra.budinv.domain;

import java.util.Date;

/**
 * This class represents any kind of entry we can annotate
 * in our accountability system.
 */
public class BookkeepingEntry {
	
	private Long id;

	private Date logDate;

	private Date incurredDate;

	private double amount;

	private String comments;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	public Date getIncurredDate() {
		return incurredDate;
	}

	public void setIncurredDate(Date incurredDate) {
		this.incurredDate = incurredDate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}
