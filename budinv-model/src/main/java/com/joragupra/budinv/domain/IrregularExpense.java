package com.joragupra.budinv.domain;

/**
 * Expense type corresponding to all expenses which we know
 * that are going to happen but not exactly when.
 */
public class IrregularExpense extends ExpenseType {
	
	private static final String IRREGULAR_EXPENSE_NAME = "irregular";
	
	public IrregularExpense(){
		super(IRREGULAR_EXPENSE_NAME);
	}
}
