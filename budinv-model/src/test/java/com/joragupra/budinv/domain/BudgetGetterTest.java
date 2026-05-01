package com.joragupra.budinv.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BudgetGetterTest {

	@Test
	void setAndGetBudgetedRevenue() {
		Budget budget = new Budget();
		budget.setBudgetedRevenue(3500.0);
		assertEquals(3500.0, budget.getBudgetedRevenue());
	}
}
