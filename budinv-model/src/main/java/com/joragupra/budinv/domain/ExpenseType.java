package com.joragupra.budinv.domain;

import java.util.Set;


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
	
	public Set<ExpenseConcept> getConcepts(){
		return (Set<ExpenseConcept>) super.getExpenses();
	}
}
