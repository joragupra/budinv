package com.joragupra.budinv.domain;

public interface Budgetable extends Expense {
	
	double getBudgetedAmount();
	
	double calculateDeviation(Ledger ledger);
}
