package com.joragupra.budinv.domain;

/**
 * Expense type corresponding to all expenses which we know
 * that are going to happen and when.
 */
public class RegularExpense extends ExpenseType {
	
	private static final String REGULAR_EXPENSE_NAME = "regular";
	
	public RegularExpense(){
		super(REGULAR_EXPENSE_NAME);
	}
}
