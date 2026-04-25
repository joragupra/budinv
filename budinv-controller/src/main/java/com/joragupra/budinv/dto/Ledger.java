package com.joragupra.budinv.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Ledger {

	@XmlElementWrapper(name = "entries")
	@XmlElement(name = "entry")
	private List<BookkeepingEntry> entries;

	private LocalDate from;
	private LocalDate to;
	private double totalIncome;
	private double totalExpense;
	private double balance;

	public List<BookkeepingEntry> getEntries() { return entries; }
	public void setEntries(List<BookkeepingEntry> entries) { this.entries = entries; }

	public double getTotalIncome() { return totalIncome; }
	public void setTotalIncome(double totalIncome) { this.totalIncome = totalIncome; }

	public double getTotalExpense() { return totalExpense; }
	public void setTotalExpense(double totalExpense) { this.totalExpense = totalExpense; }

	public double getBalance() { return balance; }
	public void setBalance(double balance) { this.balance = balance; }

	public LocalDate getFrom() { return from; }
	public void setFrom(LocalDate from) { this.from = from; }

	public LocalDate getTo() { return to; }
	public void setTo(LocalDate to) { this.to = to; }
}
