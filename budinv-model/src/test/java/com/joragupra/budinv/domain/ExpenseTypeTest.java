package com.joragupra.budinv.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExpenseTypeTest {

	@Test
	void budgetedAmount_regularExpense() {
		Budget budget = new Budget();
		budget.setBudgetedRegularExpense(new RegularExpense());
		new ExpenseConcept("Personal loan", budget.getBudgetedRegularExpense(), 400);
		new ExpenseConcept("Mortgage", budget.getBudgetedRegularExpense(), 500);
		assertEquals(900.0, budget.getBudgetedRegularExpense().getBudgetedAmount());
	}

	@Test
	void budgetedAmount_irregularExpense() {
		Budget budget = new Budget();
		budget.setBudgetedIrregularExpense(new IrregularExpense());
		new ExpenseConcept("Car maintenance", budget.getBudgetedIrregularExpense(), 200);
		new ExpenseConcept("Christmas", budget.getBudgetedIrregularExpense(), 350);
		assertEquals(550.0, budget.getBudgetedIrregularExpense().getBudgetedAmount());
	}

	@Test
	void budgetedAmount_variableExpense() {
		Budget budget = new Budget();
		budget.setBudgetedVariableExpense(new VariableExpense());
		new ExpenseConcept("Going out", budget.getBudgetedVariableExpense(), 180);
		new ExpenseConcept("Vacation", budget.getBudgetedVariableExpense(), 500);
		assertEquals(680.0, budget.getBudgetedVariableExpense().getBudgetedAmount());
	}
}
