package com.joragupra.budinv.dto;

import java.time.LocalDate;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public abstract sealed class BookkeepingEntry permits Income, IncurredExpense {

	private Long id;
	private LocalDate logDate;
	private LocalDate incurredDate;
	private double amount;
	private String comments;

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public LocalDate getLogDate() { return logDate; }
	public void setLogDate(LocalDate logDate) { this.logDate = logDate; }

	public LocalDate getIncurredDate() { return incurredDate; }
	public void setIncurredDate(LocalDate incurredDate) { this.incurredDate = incurredDate; }

	public double getAmount() { return amount; }
	public void setAmount(double amount) { this.amount = amount; }

	public String getComments() { return comments; }
	public void setComments(String comments) { this.comments = comments; }

	@XmlElement(name = "entryType")
	public abstract String getEntryType();
}
