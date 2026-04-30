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

	@Test
	void bookEntry_insertsEntriesInChronologicalOrder() {
		LocalDate first = LocalDate.of(2024, 1, 5);
		LocalDate second = LocalDate.of(2024, 1, 10);
		LocalDate third = LocalDate.of(2024, 1, 20);
		Ledger ledger = new Ledger();
		Income laterIncome = new Income();
		laterIncome.setIncurredDate(third);
		laterIncome.setAmount(300.0);
		Income earlierIncome = new Income();
		earlierIncome.setIncurredDate(first);
		earlierIncome.setAmount(100.0);
		Income middleIncome = new Income();
		middleIncome.setIncurredDate(second);
		middleIncome.setAmount(200.0);
		ledger.bookEntry(laterIncome);
		ledger.bookEntry(earlierIncome);
		ledger.bookEntry(middleIncome);
		assertEquals(first, ledger.getEntries().get(0).getIncurredDate());
		assertEquals(second, ledger.getEntries().get(1).getIncurredDate());
		assertEquals(third, ledger.getEntries().get(2).getIncurredDate());
	}

	@Test
	void calculateIncome_emptyLedger() {
		LocalDate from = LocalDate.of(2024, 1, 1);
		LocalDate to = LocalDate.of(2024, 1, 31);
		Ledger ledger = new Ledger(from, to);
		assertEquals(0.0, ledger.calculateIncome());
	}

	@Test
	void calculateExpense_emptyLedger() {
		LocalDate from = LocalDate.of(2024, 1, 1);
		LocalDate to = LocalDate.of(2024, 1, 31);
		Ledger ledger = new Ledger(from, to);
		assertEquals(0.0, ledger.calculateExpense());
	}

	@Test
	void calculateBalance_emptyLedger() {
		LocalDate from = LocalDate.of(2024, 1, 1);
		LocalDate to = LocalDate.of(2024, 1, 31);
		Ledger ledger = new Ledger(from, to);
		assertEquals(0.0, ledger.calculateBalance());
	}
}
