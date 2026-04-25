package com.joragupra.budinv.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * A group of bookkeeping entries which occurred between two dates.
 * It should have an associated budget.
 */
public class Ledger {

	private Budget budget;
	private List<BookkeepingEntry> entries;
	private LocalDate from;
	private LocalDate to;

	public Ledger() {
		this(null, null);
	}

	public Ledger(LocalDate from, LocalDate to) {
		this.entries = new ArrayList<>();
		this.from = from;
		this.to = to;
	}

	public Budget getBudget() { return budget; }
	public void setBudget(Budget budget) { this.budget = budget; }

	public List<BookkeepingEntry> getEntries() { return entries; }
	public void setEntries(List<BookkeepingEntry> entries) { this.entries = entries; }

	public LocalDate getFrom() { return from; }
	public LocalDate getTo() { return to; }

	public void bookEntry(BookkeepingEntry entry) {
		int i = 0;
		for (BookkeepingEntry e : entries) {
			if (e.getIncurredDate().isAfter(entry.getIncurredDate())) {
				break;
			}
			i++;
		}
		entries.add(i, entry);
	}

	public List<BookkeepingEntry> getEntriesBetweenDates(LocalDate from, LocalDate to) {
		List<BookkeepingEntry> result = new ArrayList<>();
		for (BookkeepingEntry entry : entries) {
			if (entry.getIncurredDate().isAfter(to)) break;
			if (!entry.getIncurredDate().isBefore(from)) {
				result.add(entry);
			}
		}
		return result;
	}

	public double calculateBalance() {
		return calculateBalance(this.from, this.to);
	}

	public double calculateBalance(LocalDate from, LocalDate to) {
		return calculateIncome(from, to) - calculateExpense(from, to);
	}

	public double calculateIncome() {
		return calculateIncome(this.from, this.to);
	}

	public double calculateIncome(LocalDate from, LocalDate to) {
		double income = 0.0;
		for (BookkeepingEntry entry : entries) {
			if (entry.getIncurredDate().isAfter(to)) break;
			if (!entry.getIncurredDate().isBefore(from)) {
				income += switch (entry) {
					case Income i -> i.getAmount();
					case IncurredExpense e -> 0.0;
				};
			}
		}
		return income;
	}

	public double calculateExpense() {
		return calculateExpense(this.from, this.to);
	}

	public double calculateExpense(LocalDate from, LocalDate to) {
		double expense = 0.0;
		for (BookkeepingEntry entry : entries) {
			if (entry.getIncurredDate().isAfter(to)) break;
			if (!entry.getIncurredDate().isBefore(from)) {
				expense += switch (entry) {
					case Income i -> 0.0;
					case IncurredExpense e -> e.getAmount();
				};
			}
		}
		return expense;
	}
}
