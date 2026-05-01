package com.joragupra.budinv.domain;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LedgerDateRangeTest {

	@Test
	void calculateIncome_ignoresEntryBeforeFrom() {
		LocalDate from = LocalDate.of(2024, 3, 1);
		LocalDate to = LocalDate.of(2024, 3, 31);
		Ledger ledger = new Ledger(from, to);

		Income beforeFrom = new Income();
		beforeFrom.setAmount(500.0);
		beforeFrom.setIncurredDate(LocalDate.of(2024, 2, 28));
		ledger.bookEntry(beforeFrom);

		Income inRange = new Income();
		inRange.setAmount(200.0);
		inRange.setIncurredDate(LocalDate.of(2024, 3, 15));
		ledger.bookEntry(inRange);

		assertEquals(200.0, ledger.calculateIncome());
	}

	@Test
	void calculateIncome_stopsAtEntryAfterTo() {
		LocalDate from = LocalDate.of(2024, 3, 1);
		LocalDate to = LocalDate.of(2024, 3, 31);
		Ledger ledger = new Ledger(from, to);

		Income inRange = new Income();
		inRange.setAmount(300.0);
		inRange.setIncurredDate(LocalDate.of(2024, 3, 10));
		ledger.bookEntry(inRange);

		Income afterTo = new Income();
		afterTo.setAmount(999.0);
		afterTo.setIncurredDate(LocalDate.of(2024, 4, 1));
		ledger.bookEntry(afterTo);

		assertEquals(300.0, ledger.calculateIncome());
	}

	@Test
	void calculateIncome_includesEntryOnFromDate() {
		LocalDate from = LocalDate.of(2024, 3, 1);
		LocalDate to = LocalDate.of(2024, 3, 31);
		Ledger ledger = new Ledger(from, to);

		Income onFrom = new Income();
		onFrom.setAmount(300.0);
		onFrom.setIncurredDate(from);
		ledger.bookEntry(onFrom);

		assertEquals(300.0, ledger.calculateIncome());
	}

	@Test
	void calculateIncome_includesEntryOnToDate() {
		LocalDate from = LocalDate.of(2024, 3, 1);
		LocalDate to = LocalDate.of(2024, 3, 31);
		Ledger ledger = new Ledger(from, to);

		Income onTo = new Income();
		onTo.setAmount(150.0);
		onTo.setIncurredDate(to);
		ledger.bookEntry(onTo);

		assertEquals(150.0, ledger.calculateIncome());
	}

	@Test
	void calculateExpense_ignoresEntryBeforeFrom() {
		LocalDate from = LocalDate.of(2024, 3, 1);
		LocalDate to = LocalDate.of(2024, 3, 31);
		Ledger ledger = new Ledger(from, to);

		IncurredExpense beforeFrom = new IncurredExpense(new ExpenseConcept("Car"));
		beforeFrom.setAmount(400.0);
		beforeFrom.setIncurredDate(LocalDate.of(2024, 2, 15));
		ledger.bookEntry(beforeFrom);

		IncurredExpense inRange = new IncurredExpense(new ExpenseConcept("Food"));
		inRange.setAmount(100.0);
		inRange.setIncurredDate(LocalDate.of(2024, 3, 20));
		ledger.bookEntry(inRange);

		assertEquals(100.0, ledger.calculateExpense());
	}

	@Test
	void calculateExpense_includesEntryOnFromDate() {
		LocalDate from = LocalDate.of(2024, 3, 1);
		LocalDate to = LocalDate.of(2024, 3, 31);
		Ledger ledger = new Ledger(from, to);

		IncurredExpense onFrom = new IncurredExpense(new ExpenseConcept("Car"));
		onFrom.setAmount(250.0);
		onFrom.setIncurredDate(from);
		ledger.bookEntry(onFrom);

		assertEquals(250.0, ledger.calculateExpense());
	}

	@Test
	void calculateExpense_includesEntryOnToDate() {
		LocalDate from = LocalDate.of(2024, 3, 1);
		LocalDate to = LocalDate.of(2024, 3, 31);
		Ledger ledger = new Ledger(from, to);

		IncurredExpense onTo = new IncurredExpense(new ExpenseConcept("Food"));
		onTo.setAmount(75.0);
		onTo.setIncurredDate(to);
		ledger.bookEntry(onTo);

		assertEquals(75.0, ledger.calculateExpense());
	}

	@Test
	void calculateExpense_stopsAtEntryAfterTo() {
		LocalDate from = LocalDate.of(2024, 3, 1);
		LocalDate to = LocalDate.of(2024, 3, 31);
		Ledger ledger = new Ledger(from, to);

		IncurredExpense inRange = new IncurredExpense(new ExpenseConcept("Food"));
		inRange.setAmount(150.0);
		inRange.setIncurredDate(LocalDate.of(2024, 3, 5));
		ledger.bookEntry(inRange);

		IncurredExpense afterTo = new IncurredExpense(new ExpenseConcept("Vacation"));
		afterTo.setAmount(999.0);
		afterTo.setIncurredDate(LocalDate.of(2024, 4, 10));
		ledger.bookEntry(afterTo);

		assertEquals(150.0, ledger.calculateExpense());
	}
}
