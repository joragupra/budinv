package com.joragupra.budinv.domain;

public class IrregularExpense extends ExpenseType {
	
	private static final String IRREGULAR_EXPENSE_NAME = "irregular";
	
	public IrregularExpense(){
		super(IRREGULAR_EXPENSE_NAME);
	}
}
