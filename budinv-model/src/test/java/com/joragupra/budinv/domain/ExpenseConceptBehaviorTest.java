package com.joragupra.budinv.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExpenseConceptBehaviorTest {

	@Test
	void changeType_whenCurrentTypeIsNull_doesNothing() {
		ExpenseConcept concept = new ExpenseConcept("Car maintenance");
		RegularExpense target = new RegularExpense();
		concept.changeType(target);
		assertNull(concept.getType());
		assertEquals(0, target.getConcepts().size());
	}

	@Test
	void constructor_withTypeRegistersConceptInType() {
		RegularExpense regularExpense = new RegularExpense();
		new ExpenseConcept("Mortgage", regularExpense, 500.0);
		assertEquals(1, regularExpense.getConcepts().size());
	}
}
