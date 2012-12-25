package com.joragupra.budinv.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * Group of expenses that can be budgeted.
 * @param <T>
 */
public abstract class ExpenseCategory<T extends Expense> implements Budgetable {

	private String expenseCategoryCode;

	private String name;

	protected Set<T> expenses;
	
	public ExpenseCategory(String name){
		//TODO - add valid implementation with automatic code generation
		this(System.currentTimeMillis()+"", name);
	}

	public ExpenseCategory(String code, String name) {
		this.expenseCategoryCode = code;
		this.name = name;
		this.expenses = new HashSet<T>();
	}

	public String getExpenseCategoryCode() {
		return expenseCategoryCode;
	}

	public void setExpenseCategoryCode(String expenseCategoryCode) {
		this.expenseCategoryCode = expenseCategoryCode;
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

	public void addExpense(T expense) {
		this.expenses.add(expense);
	}

	public void removeExpense(Expense expense) {
		this.expenses.remove(expense);
	}

	@Override
	public double getActualSpending(Ledger ledger) {
		return calculateSpendingForAllExpenses(ledger);
	}

	private double calculateSpendingForAllExpenses(Ledger ledger) {
		double total = 0.0;
		for (BookkeepingEntry entry : ledger.getEntries()) {
			if(entry instanceof IncurredExpense){
				Expense expense = (Expense) entry;
				total += expense.getActualSpending(ledger);
			}
		}
		return total;
	}

	@Override
	public double calculateDeviation(Ledger ledger) {
		return getBudgetedAmount() - getActualSpending(ledger);
	}
}
