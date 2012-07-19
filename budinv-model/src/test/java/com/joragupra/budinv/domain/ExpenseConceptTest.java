package com.joragupra.budinv.domain;


import junit.framework.TestCase;

public class ExpenseConceptTest extends TestCase {
	
	public void testGetDifference_Positive(){
		ExpenseConcept carManteinance = new ExpenseConcept("Car manteinance", 800);
		Expense e1 = new Expense(carManteinance);
		e1.setAmount(200);
		Expense e2 = new Expense(carManteinance);
		e2.setAmount(400);
		assertEquals(200d, carManteinance.getDifference());
	}
	
	public void testGetDifference_Negative(){
		ExpenseConcept carManteinance = new ExpenseConcept("Car manteinance", 400);
		Expense e1 = new Expense(carManteinance);
		e1.setAmount(200);
		Expense e2 = new Expense(carManteinance);
		e2.setAmount(400);
		assertEquals(-200d, carManteinance.getDifference());
	}
	
	public void testGetActualExpending(){
		ExpenseConcept carManteinance = new ExpenseConcept("Car manteinance");
		Expense e1 = new Expense(carManteinance);
		e1.setAmount(200);
		Expense e2 = new Expense(carManteinance);
		e2.setAmount(400);
		assertEquals(600d, carManteinance.getActualSpending());
	}
}