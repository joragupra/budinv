package com.joragupra.budinv.domain;

import junit.framework.TestCase;

public class ExpenseTypeTest extends TestCase {
	
	public void testGetBudgetedAmountForRegularExpense(){
		Budget budget = new Budget();
		budget.setBudgetedRegularExpense(new RegularExpense());
		ExpenseConcept personalLoan = new ExpenseConcept("Personal loan", budget.getBudgetedRegularExpense(), 400);
		ExpenseConcept mortgage = new ExpenseConcept("Mortgage", budget.getBudgetedRegularExpense(), 500);
		assertEquals(900.0, budget.getBudgetedRegularExpense().getBudgetedAmount());
	}
	
	public void testGetBudgetedAmountForIrregularExpense(){
		Budget budget = new Budget();
		budget.setBudgetedIrregularExpense(new IrregularExpense());
		ExpenseConcept carManteinance = new ExpenseConcept("Car manteinance", budget.getBudgetedIrregularExpense(), 200);
		ExpenseConcept christmas = new ExpenseConcept("Christmas", budget.getBudgetedIrregularExpense(), 350);
		assertEquals(550.0, budget.getBudgetedIrregularExpense().getBudgetedAmount());
	}
	
	public void testGetBudgetedAmountForVariablerExpense(){
		Budget budget = new Budget();
		budget.setBudgetedVariableExpense(new VariableExpense());
		ExpenseConcept goingOut = new ExpenseConcept("Going out", budget.getBudgetedVariableExpense(), 180);
		ExpenseConcept vacation = new ExpenseConcept("Vacation", budget.getBudgetedVariableExpense(), 500);
		assertEquals(680.0, budget.getBudgetedVariableExpense().getBudgetedAmount());
	}
}
