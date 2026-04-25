package com.joragupra.budinv.domain;

/**
 * A special kind of expense category whose budget corresponds to
 * the sum of all its expenses' budgets.
 */
public class AggregationBudgetedExpenseCategory extends ExpenseCategory<Budgetable> {
	
	public AggregationBudgetedExpenseCategory(String name){
		super(name);
	}
	
	@Override
	public double getBudgetedAmount() {
		return calculateBudgetForAllBudgetedElements();
	}
	
	private double calculateBudgetForAllBudgetedElements() {
		return super.expenses.stream().mapToDouble(Budgetable::getBudgetedAmount).sum();
	}

}
