package com.joragupra.budinv.domain;

import java.util.Date;

public class ExpenseFactory {
	
	public static IncurredExpense createExpense(ExpenseConcept concept, double amount){
		IncurredExpense expense = new IncurredExpense(concept);
		expense.setAmount(amount);
		expense.setIncurredDate(new Date());
		return expense;
	}
}
