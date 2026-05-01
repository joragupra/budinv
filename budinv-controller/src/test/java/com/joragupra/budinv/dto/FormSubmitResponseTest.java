package com.joragupra.budinv.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FormSubmitResponseTest {

	@Test
	void defaultConstructor_setsOkTrueAndNullMessage() {
		FormSubmitResponse response = new FormSubmitResponse();
		assertTrue(response.ok());
		assertNull(response.message());
	}

	@Test
	void canonicalConstructor_setsFields() {
		FormSubmitResponse response = new FormSubmitResponse(false, "error");
		assertFalse(response.ok());
		assertEquals("error", response.message());
	}
}
