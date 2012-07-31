package com.joragupra.budinv.domain;

public class ExpenseFactory {
	
	public static IncurredExpense createExpense(ExpenseConcept concept, double amount){
		IncurredExpense expense = new IncurredExpense(concept);
		expense.setAmount(amount);
		return expense;
	}
}
