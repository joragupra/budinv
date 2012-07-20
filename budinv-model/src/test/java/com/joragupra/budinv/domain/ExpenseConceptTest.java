package com.joragupra.budinv.domain;


import junit.framework.TestCase;

public class ExpenseConceptTest extends TestCase {
	
	public void testGetDifference_Positive(){
		ExpenseConcept carManteinance = new ExpenseConcept("Car manteinance", 800);
		ExpenseFactory.createExpense(carManteinance, 200);
		ExpenseFactory.createExpense(carManteinance, 400);
		assertEquals(200d, carManteinance.getDifference());
	}
	
	public void testGetDifference_Negative(){
		ExpenseConcept carManteinance = new ExpenseConcept("Car manteinance", 400);
		ExpenseFactory.createExpense(carManteinance, 200);
		ExpenseFactory.createExpense(carManteinance, 400);
		assertEquals(-200d, carManteinance.getDifference());
	}
	
	public void testGetActualExpending(){
		ExpenseConcept carManteinance = new ExpenseConcept("Car manteinance");
		ExpenseFactory.createExpense(carManteinance, 200);
		ExpenseFactory.createExpense(carManteinance, 400);
		assertEquals(600d, carManteinance.getActualSpending());
	}
}