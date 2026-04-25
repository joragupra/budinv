package com.joragupra.budinv.domain;

import java.time.LocalDate;

public class ExpenseFactory {

	public static IncurredExpense createExpense(ExpenseConcept concept, double amount) {
		IncurredExpense expense = new IncurredExpense(concept);
		expense.setAmount(amount);
		expense.setIncurredDate(LocalDate.now());
		return expense;
	}
}
