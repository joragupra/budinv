package com.joragupra.budinv.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AggregationBudgetedExpenseCategoryTest {

	@Test
	void getBudgetedAmount_noExpenses_returnsZero() {
		AggregationBudgetedExpenseCategory category = new AggregationBudgetedExpenseCategory("All");
		assertEquals(0.0, category.getBudgetedAmount());
	}

	@Test
	void getBudgetedAmount_singleConcept() {
		AggregationBudgetedExpenseCategory category = new AggregationBudgetedExpenseCategory("All");
		category.addExpense(new ExpenseConcept("Food", 300.0));
		assertEquals(300.0, category.getBudgetedAmount());
	}

	@Test
	void getBudgetedAmount_sumsManyConceptBudgets() {
		AggregationBudgetedExpenseCategory category = new AggregationBudgetedExpenseCategory("All");
		category.addExpense(new ExpenseConcept("Food", 300.0));
		category.addExpense(new ExpenseConcept("Transport", 100.0));
		category.addExpense(new ExpenseConcept("Leisure", 50.0));
		assertEquals(450.0, category.getBudgetedAmount());
	}
}
