package com.joragupra.budinv.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BudgetTest {

	@Test
	void totalBudgeted_sumsBudgetLineAmounts() throws Exception {
		Budget budget = new Budget(new Date(), new Date());

		ExpenseType type = new ExpenseType("FOOD", "Food");
		BudgetLine line = new BudgetLine(type);
		BudgetItem item1 = new BudgetItem();
		item1.setBudgetedAmount(100.0);
		line.addItem(item1);
		BudgetItem item2 = new BudgetItem();
		item2.setBudgetedAmount(50.0);
		line.addItem(item2);

		Field budgetedLinesField = Budget.class.getDeclaredField("budgetedLines");
		budgetedLinesField.setAccessible(true);
		budgetedLinesField.set(budget, List.of(line));

		assertEquals(150.0, budget.totalBudgeted());
	}

	@Test
	void totalBudgeted_emptyLines_returnsZero() throws Exception {
		Budget budget = new Budget(new Date(), new Date());

		Field budgetedLinesField = Budget.class.getDeclaredField("budgetedLines");
		budgetedLinesField.setAccessible(true);
		budgetedLinesField.set(budget, new ArrayList<>());

		assertEquals(0.0, budget.totalBudgeted());
	}
}
