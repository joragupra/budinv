package com.joragupra.budinv.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A group of bookkeeping entries which occurred between to dates.
 * It should have an associated budget.
 */
public class Ledger {
	
	private Budget budget;
	
	private List<BookkeepingEntry> entries;
	
	private Date from;
	
	private Date to;
	
	public Ledger(){
		this(null, null);
	}
	
	public Ledger(Date from, Date to){
		super();
		this.entries = new ArrayList<BookkeepingEntry>();
		this.from = from;
		this.to = to;
	}
	
	public Budget getBudget() {
		return budget;
	}

	public void setBudget(Budget budget) {
		this.budget = budget;
	}

	public List<BookkeepingEntry> getEntries() {
		return entries;
	}

	public void setEntries(List<BookkeepingEntry> entries) {
		this.entries = entries;
	}
	
	public void bookEntry(BookkeepingEntry entry){
		int i = 0;
		for(BookkeepingEntry e : entries){
			if(entry.getIncurredDate().before(entry.getIncurredDate())){
				break;
			}
			i++;
		}
		entries.add(i, entry);
	}
	
	public List<BookkeepingEntry> getEntriesBetweenDates(Date from, Date to){
		List<BookkeepingEntry> result = new ArrayList<BookkeepingEntry>();
		Date maxDate = new Date(to.getTime());
		maxDate.setDate(to.getDate()+1);
		maxDate.setHours(0);
		maxDate.setMinutes(0);
		maxDate.setSeconds(0);
		Date minDate = new Date(from.getTime());
		minDate.setHours(0);
		minDate.setMinutes(0);
		minDate.setSeconds(0);
		for(BookkeepingEntry entry : entries){
			if(entry.getIncurredDate().after(maxDate)){
				break;
			}
			if(entry.getIncurredDate().after(minDate)){
				result.add(entry);
			}
		}
		return result;
	}
	
	public double calculateBalance(){
		return this.calculateBalance(this.from, this.to);
	}
	
	public double calculateBalance(Date from, Date to){
		return calculateIncome(from, to) - calculateExpense(from, to);
	}
	
	public double calculateIncome(){
		return this.calculateIncome(this.from, this.to);
	}
	
	public double calculateIncome(Date from, Date to){
		double income = 0.0;
		Date maxDate = new Date(to.getTime());
		maxDate.setDate(to.getDate()+1);
		maxDate.setHours(0);
		maxDate.setMinutes(0);
		maxDate.setSeconds(0);
		Date minDate = new Date(from.getTime());
		minDate.setHours(0);
		minDate.setMinutes(0);
		minDate.setSeconds(0);
		for(BookkeepingEntry entry : entries){
			if(entry.getIncurredDate().after(maxDate)){
				break;
			}
			if(entry.getIncurredDate().after(minDate) && entry instanceof Income){
				income += entry.getAmount();
			}
		}
		return income;
	}
	
	public double calculateExpense(){
		return this.calculateExpense(this.from, this.to);
	}
	
	public double calculateExpense(Date from, Date to){
		double expense = 0.0;
		Date maxDate = new Date(to.getTime());
		maxDate.setDate(to.getDate()+1);
		maxDate.setHours(0);
		maxDate.setMinutes(0);
		maxDate.setSeconds(0);
		Date minDate = new Date(from.getTime());
		minDate.setHours(0);
		minDate.setMinutes(0);
		minDate.setSeconds(0);
		for(BookkeepingEntry entry : entries){
			if(entry.getIncurredDate().after(maxDate)){
				break;
			}
			if(entry.getIncurredDate().after(minDate) && entry instanceof IncurredExpense){
				expense += entry.getAmount();
			}
		}
		return expense;
	}
}
