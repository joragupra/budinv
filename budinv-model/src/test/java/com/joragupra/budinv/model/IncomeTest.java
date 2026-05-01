package com.joragupra.budinv.model;

import java.util.Date;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IncomeTest {

	@Test
	void constructor_threeArgs() {
		Date date = new Date();
		Income income = new Income(date, 1000.0, "Salary");
		assertEquals(1000.0, income.getAmount());
		assertEquals("Salary", income.getTitle());
	}

	@Test
	void constructor_fourArgs() {
		Category category = new Category();
		Income income = new Income(new Date(), 500.0, "Bonus", category);
		assertSame(category, income.getCategory());
	}
}
