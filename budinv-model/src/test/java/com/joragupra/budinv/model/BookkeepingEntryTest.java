package com.joragupra.budinv.model;

import java.util.Date;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BookkeepingEntryTest {

	@Test
	void constructor_threeArgs_setsDateAmountTitle() {
		Date date = new Date();
		Income entry = new Income(date, 500.0, "Salary");
		assertEquals(date, entry.getDate());
		assertEquals(500.0, entry.getAmount());
		assertEquals("Salary", entry.getTitle());
		assertNull(entry.getCategory());
	}

	@Test
	void constructor_fourArgs_setsCategory() {
		Category category = new Category();
		Income entry = new Income(new Date(), 200.0, "Bonus", category);
		assertSame(category, entry.getCategory());
	}

	@Test
	void setAndGetId() {
		Income entry = new Income(new Date(), 100.0, "Test");
		entry.setId(99L);
		assertEquals(99L, entry.getId());
	}

	@Test
	void setAndGetDate() {
		Income entry = new Income(new Date(), 100.0, "Test");
		Date newDate = new Date(0);
		entry.setDate(newDate);
		assertEquals(newDate, entry.getDate());
	}

	@Test
	void setAndGetAmount() {
		Income entry = new Income(new Date(), 100.0, "Test");
		entry.setAmount(250.0);
		assertEquals(250.0, entry.getAmount());
	}

	@Test
	void setAndGetTitle() {
		Income entry = new Income(new Date(), 100.0, "Original");
		entry.setTitle("Updated");
		assertEquals("Updated", entry.getTitle());
	}

	@Test
	void setAndGetComment() {
		Income entry = new Income(new Date(), 100.0, "Test");
		entry.setComment("A note");
		assertEquals("A note", entry.getComment());
	}

	@Test
	void setAndGetCategory() {
		Income entry = new Income(new Date(), 100.0, "Test");
		Category category = new Category();
		entry.setCategory(category);
		assertSame(category, entry.getCategory());
	}
}
