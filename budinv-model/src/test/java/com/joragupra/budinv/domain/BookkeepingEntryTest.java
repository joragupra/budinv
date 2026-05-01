package com.joragupra.budinv.domain;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BookkeepingEntryTest {

	@Test
	void setAndGetId() {
		Income entry = new Income();
		entry.setId(42L);
		assertEquals(42L, entry.getId());
	}

	@Test
	void setAndGetLogDate() {
		Income entry = new Income();
		LocalDate logDate = LocalDate.of(2024, 3, 15);
		entry.setLogDate(logDate);
		assertEquals(logDate, entry.getLogDate());
	}

	@Test
	void setAndGetComments() {
		Income entry = new Income();
		entry.setComments("Monthly salary");
		assertEquals("Monthly salary", entry.getComments());
	}
}
