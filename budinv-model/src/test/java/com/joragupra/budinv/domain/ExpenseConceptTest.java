package com.joragupra.budinv.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExpenseConceptTest {

	@Test
	void getDifference_positive() {
		Ledger ledger = new Ledger();
		ExpenseConcept carMaintenance = new ExpenseConcept("Car maintenance", 800);
		ledger.bookEntry(ExpenseFactory.createExpense(carMaintenance, 200));
		ledger.bookEntry(ExpenseFactory.createExpense(carMaintenance, 400));
		assertEquals(200d, carMaintenance.calculateDeviation(ledger));
	}

	@Test
	void getDifference_negative() {
		Ledger ledger = new Ledger();
		ExpenseConcept carMaintenance = new ExpenseConcept("Car maintenance", 400);
		ledger.bookEntry(ExpenseFactory.createExpense(carMaintenance, 200));
		ledger.bookEntry(ExpenseFactory.createExpense(carMaintenance, 400));
		assertEquals(-200d, carMaintenance.calculateDeviation(ledger));
	}

	@Test
	void getActualSpending() {
		Ledger ledger = new Ledger();
		ExpenseConcept carMaintenance = new ExpenseConcept("Car maintenance");
		ledger.bookEntry(ExpenseFactory.createExpense(carMaintenance, 200));
		ledger.bookEntry(ExpenseFactory.createExpense(carMaintenance, 400));
		assertEquals(600d, carMaintenance.getActualSpending(ledger));
	}

	@Test
	void changeType() {
		ExpenseConcept carMaintenance = new ExpenseConcept("Car maintenance");
		VariableExpense variableExpense = new VariableExpense();
		carMaintenance.setType(variableExpense);
		IrregularExpense irregularExpense = new IrregularExpense();
		carMaintenance.changeType(irregularExpense);
		assertEquals(irregularExpense, carMaintenance.getType());
		assertEquals(0, variableExpense.getConcepts().size());
		assertEquals(1, irregularExpense.getConcepts().size());
	}
}
