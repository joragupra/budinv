package com.joragupra.budinv.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public abstract class BookkeepingEntry {

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
	
	@XmlElement(name = "entryType")
	public abstract String getEntryType();
}
