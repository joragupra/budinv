package com.joragupra.budinv.domain;

import junit.framework.TestCase;

public class ExpenseTypeTest extends TestCase {
	
	public void testGetBudgetedAmount(){
		RegularExpense regulars = new RegularExpense();
		ExpenseConcept carManteinance = new ExpenseConcept("Car manteinance", regulars, 400);
		ExpenseConcept christmas = new ExpenseConcept("Christmas", regulars, 500);
		assertEquals(900.0, regulars.getBudgetedAmount());
	}
}
