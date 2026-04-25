package com.joragupra.budinv.domain;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LedgerTest {

	@Test
	void calculateIncome() {
		LocalDate today = LocalDate.now();
		LocalDate tomorrow = today.plusDays(1);
		Ledger ledger = new Ledger();
		Income todayIncome = new Income();
		todayIncome.setAmount(1000.00);
		todayIncome.setIncurredDate(today);
		ledger.bookEntry(todayIncome);
		Income tomorrowIncome = new Income();
		tomorrowIncome.setAmount(1000.00);
		tomorrowIncome.setIncurredDate(tomorrow);
		ledger.bookEntry(tomorrowIncome);
		assertEquals(2000.00, ledger.calculateIncome(today, tomorrow));
	}

	@Test
	void calculateExpense() {
		LocalDate today = LocalDate.now();
		LocalDate tomorrow = today.plusDays(1);
		Ledger ledger = new Ledger();
		IncurredExpense todayExpense = new IncurredExpense(new ExpenseConcept("CAR"));
		todayExpense.setAmount(1000.00);
		todayExpense.setIncurredDate(today);
		ledger.bookEntry(todayExpense);
		IncurredExpense tomorrowExpense = new IncurredExpense(new ExpenseConcept("FOOD"));
		tomorrowExpense.setAmount(1000.00);
		tomorrowExpense.setIncurredDate(tomorrow);
		ledger.bookEntry(tomorrowExpense);
		assertEquals(2000.00, ledger.calculateExpense(today, tomorrow));
	}

	@Test
	void calculateBalance() {
		LocalDate today = LocalDate.now();
		LocalDate tomorrow = today.plusDays(1);
		Ledger ledger = new Ledger();
		Income todayIncome = new Income();
		todayIncome.setAmount(1000.00);
		todayIncome.setIncurredDate(today);
		ledger.bookEntry(todayIncome);
		IncurredExpense tomorrowExpense = new IncurredExpense(new ExpenseConcept("FOOD"));
		tomorrowExpense.setAmount(600.00);
		tomorrowExpense.setIncurredDate(tomorrow);
		ledger.bookEntry(tomorrowExpense);
		assertEquals(400.00, ledger.calculateBalance(today, tomorrow));
	}

	@Test
	void getEntriesBetweenDates() {
		LocalDate yesterday = LocalDate.now().minusDays(1);
		LocalDate today = LocalDate.now();
		LocalDate tomorrow = today.plusDays(1);
		Ledger ledger = new Ledger();
		Income yesterdayIncome = new Income();
		yesterdayIncome.setAmount(500.00);
		yesterdayIncome.setIncurredDate(yesterday);
		ledger.bookEntry(yesterdayIncome);
		Income todayIncome = new Income();
		todayIncome.setAmount(1000.00);
		todayIncome.setIncurredDate(today);
		ledger.bookEntry(todayIncome);
		IncurredExpense tomorrowExpense = new IncurredExpense(new ExpenseConcept("FOOD"));
		tomorrowExpense.setAmount(600.00);
		tomorrowExpense.setIncurredDate(tomorrow);
		ledger.bookEntry(tomorrowExpense);
		assertEquals(2, ledger.getEntriesBetweenDates(yesterday, today).size());
	}
}
