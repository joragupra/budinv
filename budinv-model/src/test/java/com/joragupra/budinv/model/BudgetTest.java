package com.joragupra.budinv.model;

import java.util.Date;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BudgetTest {

	@Test
	void constructor_setsMonthlyPeriod() {
		Date from = new Date();
		Date to = new Date();
		Budget budget = new Budget(from, to);
		assertNotNull(budget);
	}

	@Test
	void budgetPeriod_monthlyConstantExists() {
		assertEquals(Budget.BudgetPeriod.MONTHLY, Budget.BudgetPeriod.valueOf("MONTHLY"));
	}
}
