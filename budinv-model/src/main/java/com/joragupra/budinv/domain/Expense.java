package com.joragupra.budinv.domain;

/**
 * Interface that marks items corresponding to expenses.
 * The principal property is we can calculate actual spending
 * for them.
 */
public interface Expense {
	
	double getActualSpending(Ledger ledger);
	
}
