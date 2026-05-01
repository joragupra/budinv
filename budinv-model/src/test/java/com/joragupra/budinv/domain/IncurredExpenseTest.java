package com.joragupra.budinv.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IncurredExpenseTest {

	@Test
	void setAndGetConcept() {
		ExpenseConcept original = new ExpenseConcept("Food");
		IncurredExpense expense = new IncurredExpense(original);
		assertEquals(original, expense.getConcept());
		ExpenseConcept replacement = new ExpenseConcept("Transport");
		expense.setConcept(replacement);
		assertEquals(replacement, expense.getConcept());
	}
}
