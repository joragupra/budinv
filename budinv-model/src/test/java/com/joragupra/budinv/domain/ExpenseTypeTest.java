package com.joragupra.budinv.domain;

import junit.framework.TestCase;

public class ExpenseTypeTest extends TestCase {
	
	public void testGetBudgetedAmountForExpenseType(){
		Budget budget = new Budget();
		budget.setBudgetedRegularExpense(new RegularExpense());
		ExpenseConcept carManteinance = new ExpenseConcept("Car manteinance", budget.getBudgetedRegularExpense(), 400);
		ExpenseConcept christmas = new ExpenseConcept("Christmas", budget.getBudgetedRegularExpense(), 500);
		assertEquals(900.0, budget.getBudgetedRegularExpense().getBudgetedAmount());
	}
}
