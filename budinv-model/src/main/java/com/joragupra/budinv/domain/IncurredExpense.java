package com.joragupra.budinv.domain;

/**
 * Special kind of bookkeeping entry corresponding to
 * money actually spent.
 */
public class IncurredExpense extends BookkeepingEntry implements Expense {
	
	private ExpenseConcept concept;
	
	public IncurredExpense(ExpenseConcept concept){
		this.concept = concept;
	}
	
	public ExpenseConcept getConcept() {
		return concept;
	}

	public void setConcept(ExpenseConcept concept) {
		this.concept = concept;
	}

	@Override
	public double getActualSpending(Ledger ledger) {
		return super.getAmount();
	}
}
