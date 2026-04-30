package com.joragupra.budinv.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AggregationBudgetedExpenseCategoryTest {

	@Test
	void getBudgetedAmount_noExpenses_returnsZero() {
		AggregationBudgetedExpenseCategory category = new AggregationBudgetedExpenseCategory("All expenses");
		assertEquals(0.0, category.getBudgetedAmount());
	}

	@Test
	void getBudgetedAmount_singleExpense() {
		AggregationBudgetedExpenseCategory category = new AggregationBudgetedExpenseCategory("All expenses");
		ExpenseConcept food = new ExpenseConcept("Food", 300.0);
		category.addExpense(food);
		assertEquals(300.0, category.getBudgetedAmount());
	}

	@Test
	void getBudgetedAmount_sumsAllChildBudgets() {
		AggregationBudgetedExpenseCategory category = new AggregationBudgetedExpenseCategory("All expenses");
		ExpenseConcept food = new ExpenseConcept("Food", 300.0);
		ExpenseConcept transport = new ExpenseConcept("Transport", 100.0);
		ExpenseConcept leisure = new ExpenseConcept("Leisure", 50.0);
		category.addExpense(food);
		category.addExpense(transport);
		category.addExpense(leisure);
		assertEquals(450.0, category.getBudgetedAmount());
	}
}
