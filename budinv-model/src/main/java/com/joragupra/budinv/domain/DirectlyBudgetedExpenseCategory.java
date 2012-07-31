package com.joragupra.budinv.domain;

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
