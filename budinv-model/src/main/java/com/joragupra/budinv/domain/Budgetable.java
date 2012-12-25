package com.joragupra.budinv.domain;

/**
 * Special kind of expenses. For them we can assign a budgeted
 * amount and calculate the deviation between the budgeted amount
 * and the actual spending.
 */
public interface Budgetable extends Expense {

	double getBudgetedAmount();
	
	double calculateDeviation(Ledger ledger);
}
