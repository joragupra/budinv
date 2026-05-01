package com.joragupra.budinv.dto;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EventTest {

	@Test
	void setAndGetTitle() {
		Event event = new Event();
		event.setTitle("Salary");
		assertEquals("Salary", event.getTitle());
	}

	@Test
	void setAndGetUrl() {
		Event event = new Event();
		event.setUrl("/ledger/1");
		assertEquals("/ledger/1", event.getUrl());
	}
}
