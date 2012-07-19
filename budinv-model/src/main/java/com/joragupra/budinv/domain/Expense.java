package com.joragupra.budinv.domain;


public class Expense extends BookkeepingEntry {
	
	private ExpenseConcept concept;
	
	public Expense(ExpenseConcept concept){
		this.concept = concept;
		this.concept.bookExpense(this);
	}
	
	public ExpenseConcept getConcept() {
		return concept;
	}

	public void setConcept(ExpenseConcept concept) {
		this.concept = concept;
	}
}
