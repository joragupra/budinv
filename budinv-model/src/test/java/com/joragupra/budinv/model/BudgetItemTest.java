package com.joragupra.budinv.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BudgetItemTest {

	@Test
	void setAndGetCategory() {
		BudgetItem item = new BudgetItem();
		Category category = new Category();
		item.setCategory(category);
		assertSame(category, item.getCategory());
	}

	@Test
	void setAndGetBudgetedAmount() {
		BudgetItem item = new BudgetItem();
		item.setBudgetedAmount(150.0);
		assertEquals(150.0, item.getBudgetedAmount());
	}
}
