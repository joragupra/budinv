package com.joragupra.budinv.domain;


public class ExpenseType extends AggregationBudgetedExpenseCategory {

	public ExpenseType(String name) {
		super(name);
	}

	public void registerConcept(ExpenseConcept concept) {
		super.addExpense(concept);
	}
	
	public void unregisterConcept(ExpenseConcept concept){
		super.removeExpense(concept);
	}
}
