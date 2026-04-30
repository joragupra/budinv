package com.joragupra.budinv.domain;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExpenseCategoryTest {

	@Test
	void getActualSpending_emptyLedger() {
		DirectlyBudgetedExpenseCategory category = new DirectlyBudgetedExpenseCategory("Transport");
		Ledger ledger = new Ledger();
		assertEquals(0.0, category.getActualSpending(ledger));
	}

	@Test
	void getActualSpending_sumsAllIncurredExpensesInLedger() {
		DirectlyBudgetedExpenseCategory category = new DirectlyBudgetedExpenseCategory("Transport");
		Ledger ledger = new Ledger();
		IncurredExpense e1 = new IncurredExpense(new ExpenseConcept("Car"));
		e1.setAmount(100.0);
		e1.setIncurredDate(LocalDate.now());
		IncurredExpense e2 = new IncurredExpense(new ExpenseConcept("Bus"));
		e2.setAmount(50.0);
		e2.setIncurredDate(LocalDate.now());
		ledger.bookEntry(e1);
		ledger.bookEntry(e2);
		assertEquals(150.0, category.getActualSpending(ledger));
	}

	@Test
	void getActualSpending_ignoresIncomeEntries() {
		DirectlyBudgetedExpenseCategory category = new DirectlyBudgetedExpenseCategory("Transport");
		Ledger ledger = new Ledger();
		Income income = new Income();
		income.setAmount(1000.0);
		income.setIncurredDate(LocalDate.now());
		IncurredExpense expense = new IncurredExpense(new ExpenseConcept("Car"));
		expense.setAmount(200.0);
		expense.setIncurredDate(LocalDate.now());
		ledger.bookEntry(income);
		ledger.bookEntry(expense);
		assertEquals(200.0, category.getActualSpending(ledger));
	}

	@Test
	void addExpense_addsToExpenseSet() {
		DirectlyBudgetedExpenseCategory category = new DirectlyBudgetedExpenseCategory("Transport");
		IncurredExpense expense = new IncurredExpense(new ExpenseConcept("Car"));
		category.addExpense(expense);
		assertEquals(1, category.getExpenses().size());
	}

	@Test
	void removeExpense_removesFromExpenseSet() {
		DirectlyBudgetedExpenseCategory category = new DirectlyBudgetedExpenseCategory("Transport");
		IncurredExpense expense = new IncurredExpense(new ExpenseConcept("Car"));
		category.addExpense(expense);
		category.removeExpense(expense);
		assertEquals(0, category.getExpenses().size());
	}

	@Test
	void calculateDeviation_positiveMeansUnderBudget() {
		DirectlyBudgetedExpenseCategory category = new DirectlyBudgetedExpenseCategory("Transport");
		category.setBudgetedAmount(200.0);
		Ledger ledger = new Ledger();
		IncurredExpense expense = new IncurredExpense(new ExpenseConcept("Car"));
		expense.setAmount(150.0);
		expense.setIncurredDate(LocalDate.now());
		ledger.bookEntry(expense);
		assertEquals(50.0, category.calculateDeviation(ledger));
	}

	@Test
	void calculateDeviation_negativeMeansOverBudget() {
		DirectlyBudgetedExpenseCategory category = new DirectlyBudgetedExpenseCategory("Transport");
		category.setBudgetedAmount(100.0);
		Ledger ledger = new Ledger();
		IncurredExpense expense = new IncurredExpense(new ExpenseConcept("Car"));
		expense.setAmount(150.0);
		expense.setIncurredDate(LocalDate.now());
		ledger.bookEntry(expense);
		assertEquals(-50.0, category.calculateDeviation(ledger));
	}
}
