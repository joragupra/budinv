package com.joragupra.budinv.domain;

import java.util.Date;

public class BookkeepingEntry {

	private Date logDate;

	private Date incurredDate;

	private double amount;

	private String comments;

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
