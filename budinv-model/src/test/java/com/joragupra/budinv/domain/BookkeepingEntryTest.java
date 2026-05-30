package com.joragupra.budinv.domain;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BookkeepingEntryTest {

	@Test
	void setId_and_getId() {
		Income entry = new Income();
		entry.setId(42L);
		assertEquals(42L, entry.getId());
	}

	@Test
	void setLogDate_and_getLogDate() {
		Income entry = new Income();
		LocalDate date = LocalDate.of(2024, 1, 15);
		entry.setLogDate(date);
		assertEquals(date, entry.getLogDate());
	}

	@Test
	void setComments_and_getComments() {
		Income entry = new Income();
		entry.setComments("salary");
		assertEquals("salary", entry.getComments());
	}
}
