package com.joragupra.budinv.domain;

public class AggregationBudgetedExpenseCategory extends ExpenseCategory<Budgetable> {
	
	public AggregationBudgetedExpenseCategory(String name){
		super(name);
	}
	
	@Override
	public double getBudgetedAmount() {
		return calculateBudgetForAllBudgetedElements();
	}
	
	private double calculateBudgetForAllBudgetedElements() {
		double budget = 0.0;
		for (Budgetable concept : super.expenses) {
			budget += concept.getBudgetedAmount();
		}
		return budget;
	}

}
