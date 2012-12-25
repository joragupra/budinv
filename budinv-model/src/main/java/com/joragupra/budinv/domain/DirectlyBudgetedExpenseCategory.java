package com.joragupra.budinv.domain;

/**
 * Expense category which accepts a direct amount as a
 * budget (in spite of the others whose budget is calculated
 * as the sum of its items' budget.
 */
public class DirectlyBudgetedExpenseCategory extends ExpenseCategory {
	
	private double budgetedAmount;
	
	public DirectlyBudgetedExpenseCategory(String name){
		super(name);
	}
	
	@Override
	public double getBudgetedAmount() {
		return budgetedAmount;
	}

	public void setBudgetedAmount(double budgetedAmount) {
		this.budgetedAmount = budgetedAmount;
	}
}
