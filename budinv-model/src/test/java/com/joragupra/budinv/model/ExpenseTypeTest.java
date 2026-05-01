package com.joragupra.budinv.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExpenseTypeTest {

	@Test
	void constructor_twoArgs_setsCodeAndName() {
		ExpenseType type = new ExpenseType("REG", "Regular");
		assertEquals("REG", type.getCode());
		assertEquals("Regular", type.getName());
		assertNull(type.getComments());
		assertNull(type.getId());
	}

	@Test
	void constructor_threeArgs_setsComments() {
		ExpenseType type = new ExpenseType("VAR", "Variable", "Variable monthly expenses");
		assertEquals("Variable monthly expenses", type.getComments());
	}
}
