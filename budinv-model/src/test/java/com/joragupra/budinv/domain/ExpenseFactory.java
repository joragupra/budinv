package com.joragupra.budinv.domain;

public class ExpenseFactory {
	
	public static Expense createExpense(ExpenseConcept concept, double amount){
		Expense expense = new Expense(concept);
		expense.setAmount(amount);
		return expense;
	}
}
