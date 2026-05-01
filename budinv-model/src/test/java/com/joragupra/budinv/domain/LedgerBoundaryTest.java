package com.joragupra.budinv.domain;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LedgerBoundaryTest {

	@Test
	void calculateIncome_excludesEntriesBeforeFromDate() {
		LocalDate yesterday = LocalDate.now().minusDays(1);
		LocalDate today = LocalDate.now();
		Ledger ledger = new Ledger();
		Income oldEntry = new Income();
		oldEntry.setAmount(500.0);
		oldEntry.setIncurredDate(yesterday);
		ledger.bookEntry(oldEntry);
		Income currentEntry = new Income();
		currentEntry.setAmount(1000.0);
		currentEntry.setIncurredDate(today);
		ledger.bookEntry(currentEntry);
		assertEquals(1000.0, ledger.calculateIncome(today, today));
	}

	@Test
	void calculateIncome_stopsAtEntriesAfterToDate() {
		LocalDate today = LocalDate.now();
		LocalDate tomorrow = today.plusDays(1);
		Ledger ledger = new Ledger();
		Income currentEntry = new Income();
		currentEntry.setAmount(1000.0);
		currentEntry.setIncurredDate(today);
		ledger.bookEntry(currentEntry);
		Income futureEntry = new Income();
		futureEntry.setAmount(500.0);
		futureEntry.setIncurredDate(tomorrow);
		ledger.bookEntry(futureEntry);
		assertEquals(1000.0, ledger.calculateIncome(today, today));
	}

	@Test
	void calculateExpense_excludesEntriesOutsideDateRange() {
		LocalDate yesterday = LocalDate.now().minusDays(1);
		LocalDate today = LocalDate.now();
		LocalDate tomorrow = today.plusDays(1);
		Ledger ledger = new Ledger();
		IncurredExpense pastExpense = new IncurredExpense(new ExpenseConcept("Old"));
		pastExpense.setAmount(300.0);
		pastExpense.setIncurredDate(yesterday);
		ledger.bookEntry(pastExpense);
		IncurredExpense currentExpense = new IncurredExpense(new ExpenseConcept("Now"));
		currentExpense.setAmount(200.0);
		currentExpense.setIncurredDate(today);
		ledger.bookEntry(currentExpense);
		IncurredExpense futureExpense = new IncurredExpense(new ExpenseConcept("Future"));
		futureExpense.setAmount(100.0);
		futureExpense.setIncurredDate(tomorrow);
		ledger.bookEntry(futureExpense);
		assertEquals(200.0, ledger.calculateExpense(today, today));
	}

	@Test
	void bookEntry_insertsEarlyEntryBeforeLaterOnes() {
		LocalDate today = LocalDate.now();
		LocalDate yesterday = today.minusDays(1);
		Ledger ledger = new Ledger();
		Income laterEntry = new Income();
		laterEntry.setAmount(200.0);
		laterEntry.setIncurredDate(today);
		ledger.bookEntry(laterEntry);
		Income earlierEntry = new Income();
		earlierEntry.setAmount(100.0);
		earlierEntry.setIncurredDate(yesterday);
		ledger.bookEntry(earlierEntry);
		assertEquals(yesterday, ledger.getEntries().get(0).getIncurredDate());
		assertEquals(today, ledger.getEntries().get(1).getIncurredDate());
	}

	@Test
	void calculateIncome_usesLedgerDateRange() {
		LocalDate from = LocalDate.now().withDayOfMonth(1);
		LocalDate to = LocalDate.now();
		Ledger ledger = new Ledger(from, to);
		Income entry = new Income();
		entry.setAmount(800.0);
		entry.setIncurredDate(from);
		ledger.bookEntry(entry);
		assertEquals(800.0, ledger.calculateIncome());
	}

	@Test
	void calculateExpense_usesLedgerDateRange() {
		LocalDate from = LocalDate.now().withDayOfMonth(1);
		LocalDate to = LocalDate.now();
		Ledger ledger = new Ledger(from, to);
		IncurredExpense entry = new IncurredExpense(new ExpenseConcept("Food"));
		entry.setAmount(250.0);
		entry.setIncurredDate(from);
		ledger.bookEntry(entry);
		assertEquals(250.0, ledger.calculateExpense());
	}

	@Test
	void calculateBalance_usesLedgerDateRange() {
		LocalDate from = LocalDate.now().withDayOfMonth(1);
		LocalDate to = LocalDate.now();
		Ledger ledger = new Ledger(from, to);
		Income income = new Income();
		income.setAmount(1000.0);
		income.setIncurredDate(from);
		ledger.bookEntry(income);
		IncurredExpense expense = new IncurredExpense(new ExpenseConcept("Rent"));
		expense.setAmount(400.0);
		expense.setIncurredDate(from);
		ledger.bookEntry(expense);
		assertEquals(600.0, ledger.calculateBalance());
	}

	@Test
	void getBudget_returnsSetBudget() {
		Ledger ledger = new Ledger();
		Budget budget = new Budget();
		ledger.setBudget(budget);
		assertSame(budget, ledger.getBudget());
	}

	@Test
	void getFrom_andGetTo_returnConstructorValues() {
		LocalDate from = LocalDate.of(2024, 1, 1);
		LocalDate to = LocalDate.of(2024, 1, 31);
		Ledger ledger = new Ledger(from, to);
		assertEquals(from, ledger.getFrom());
		assertEquals(to, ledger.getTo());
	}

	@Test
	void setEntries_replacesExistingList() {
		Ledger ledger = new Ledger();
		Income income = new Income();
		income.setAmount(100.0);
		income.setIncurredDate(LocalDate.now());
		var entries = new ArrayList<BookkeepingEntry>();
		entries.add(income);
		ledger.setEntries(entries);
		assertEquals(1, ledger.getEntries().size());
	}
}
