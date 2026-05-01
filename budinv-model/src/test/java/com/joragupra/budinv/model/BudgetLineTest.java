package com.joragupra.budinv.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BudgetLineTest {

	private BudgetLine lineWithType(String typeName) {
		return new BudgetLine(new ExpenseType(typeName, typeName));
	}

	private BudgetItem itemWithAmount(Category category, double amount) {
		BudgetItem item = new BudgetItem();
		item.setCategory(category);
		item.setBudgetedAmount(amount);
		return item;
	}

	@Test
	void total_emptyItems_returnsZero() {
		BudgetLine line = lineWithType("REG");
		assertEquals(0.0, line.total());
	}

	@Test
	void addItem_and_total_sumsBudgetedAmounts() {
		BudgetLine line = lineWithType("REG");
		Category cat1 = new Category();
		Category cat2 = new Category();
		line.addItem(itemWithAmount(cat1, 400.0));
		line.addItem(itemWithAmount(cat2, 200.0));
		assertEquals(600.0, line.total());
	}

	@Test
	void lineTitle_returnsExpenseTypeName() {
		BudgetLine line = lineWithType("Regular");
		assertEquals("Regular", line.lineTitle());
	}

	@Test
	void cotainsCategory_returnsTrueWhenPresent() {
		BudgetLine line = lineWithType("REG");
		Category category = new Category();
		line.addItem(itemWithAmount(category, 100.0));
		assertTrue(line.cotainsCategory(category));
	}

	@Test
	void cotainsCategory_returnsFalseWhenAbsent() {
		BudgetLine line = lineWithType("REG");
		line.addItem(itemWithAmount(new Category(), 100.0));
		assertFalse(line.cotainsCategory(new Category()));
	}

	@Test
	void removeItem_removesMatchingCategory() {
		BudgetLine line = lineWithType("REG");
		Category category = new Category();
		line.addItem(itemWithAmount(category, 100.0));
		line.removeItem(category);
		assertFalse(line.cotainsCategory(category));
	}

	@Test
	void removeItem_whenCategoryNotFound_doesNotThrow() {
		BudgetLine line = lineWithType("REG");
		line.addItem(itemWithAmount(new Category(), 100.0));
		assertDoesNotThrow(() -> line.removeItem(new Category()));
	}
}
