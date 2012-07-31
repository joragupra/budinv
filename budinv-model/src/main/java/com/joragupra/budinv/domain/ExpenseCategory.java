package com.joragupra.budinv.domain;

import java.util.HashSet;
import java.util.Set;

public abstract class ExpenseCategory<T extends Expense> implements Budgetable, Expense {
	
	private String name;
	
	protected Set<T> expenses;

	public ExpenseCategory(String name){
		this.setName(name);
		this.expenses = new HashSet<T>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Set<? extends Expense> getExpenses() {
		return expenses;
	}

	public void setExpenses(Set<T> expenses) {
		this.expenses = expenses;
	}
	
	public void addExpense(T expense){
		this.expenses.add(expense);
	}
	
	public void removeExpense(Expense expense){
		this.expenses.remove(expense);
	}
	
	@Override
	public double getActualSpending() {
		return calculateSpendingForAllExpenses();
	}
	
	private double calculateSpendingForAllExpenses(){
		double total = 0.0;
		for(Expense expense : this.expenses){
			total += expense.getActualSpending();
		}
		return total;
	}
	
	@Override
	public double calculateDeviation() {
		return getBudgetedAmount() - getActualSpending();
	}
}
