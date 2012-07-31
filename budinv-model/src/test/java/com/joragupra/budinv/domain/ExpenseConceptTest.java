package com.joragupra.budinv.domain;


import junit.framework.TestCase;

public class ExpenseConceptTest extends TestCase {
	
	public void testGetDifference_Positive(){
		Ledger ledger = new Ledger();
		ExpenseConcept carManteinance = new ExpenseConcept("Car manteinance", 800);
		ledger.bookEntry(ExpenseFactory.createExpense(carManteinance, 200));
		ledger.bookEntry(ExpenseFactory.createExpense(carManteinance, 400));
		assertEquals(200d, carManteinance.calculateDeviation(ledger));
	}
	
	public void testGetDifference_Negative(){
		Ledger ledger = new Ledger();
		ExpenseConcept carManteinance = new ExpenseConcept("Car manteinance", 400);
		ledger.bookEntry(ExpenseFactory.createExpense(carManteinance, 200));
		ledger.bookEntry(ExpenseFactory.createExpense(carManteinance, 400));
		assertEquals(-200d, carManteinance.calculateDeviation(ledger));
	}
	
	public void testGetActualExpending(){
		Ledger ledger = new Ledger();
		ExpenseConcept carManteinance = new ExpenseConcept("Car manteinance");
		ledger.bookEntry(ExpenseFactory.createExpense(carManteinance, 200));
		ledger.bookEntry(ExpenseFactory.createExpense(carManteinance, 400));
		assertEquals(600d, carManteinance.getActualSpending(ledger));
	}
	
	public void testChangeType(){
		ExpenseConcept carManteinance = new ExpenseConcept("Car manteinance");
		VariableExpense variableExpense = new VariableExpense();
		carManteinance.setType(variableExpense);
		IrregularExpense irregularExpense = new IrregularExpense();
		carManteinance.changeType(irregularExpense);
		assertEquals(irregularExpense, carManteinance.getType());
		assertEquals(0, variableExpense.getConcepts().size());
		assertEquals(1, irregularExpense.getConcepts().size());
	}
}