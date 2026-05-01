package com.joragupra.budinv.model;

import java.util.Date;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExpenseTest {

	@Test
	void constructor_threeArgs() {
		Date date = new Date();
		Expense expense = new Expense(date, 200.0, "Groceries");
		assertEquals(200.0, expense.getAmount());
		assertEquals("Groceries", expense.getTitle());
		assertNull(expense.getCategory());
	}

	@Test
	void constructor_fourArgs() {
		Category category = new Category();
		Expense expense = new Expense(new Date(), 50.0, "Coffee", category);
		assertSame(category, expense.getCategory());
	}
}
