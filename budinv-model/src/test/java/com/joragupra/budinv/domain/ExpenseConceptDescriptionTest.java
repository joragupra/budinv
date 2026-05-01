package com.joragupra.budinv.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExpenseConceptDescriptionTest {

	@Test
	void setAndGetDescription() {
		ExpenseConcept concept = new ExpenseConcept("Food");
		concept.setDescription("Monthly grocery spending");
		assertEquals("Monthly grocery spending", concept.getDescription());
	}
}
