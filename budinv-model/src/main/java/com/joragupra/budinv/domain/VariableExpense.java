package com.joragupra.budinv.domain;

/**
 * Expense type corresponding to all expenses for which we
 * don't know their amount or if they are going to happen.
 */
public class VariableExpense extends ExpenseType {
	
	private static final String VARIABLE_EXPENSE_NAME = "variable";
	
	public VariableExpense(){
		super(VARIABLE_EXPENSE_NAME);
	}
}
