package com.joragupra.budinv.domain;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ExpenseConcept implements Budgetable {
	
	private String name;
	
	private String description;
	
	private ExpenseType type;
	
	private double budgetedAmount;
	
	private Set<Expense> expenses;
	
	public ExpenseConcept(String name){
		this(name, null, 0.0);
	}
	
	public ExpenseConcept(String name, double budgetedAmount){
		this(name, null, budgetedAmount);
	}
	
	public ExpenseConcept(String name, ExpenseType type, double budgetedAmount){
		this.name = name;
		this.type = type;
		this.expenses = new HashSet<Expense>();
		this.budgetedAmount = budgetedAmount;
		if(type!=null){
			this.type.registerConcept(this);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ExpenseType getType() {
		return type;
	}

	public void setType(ExpenseType type) {
		this.type = type;
	}
	
	public void changeType(ExpenseType type){
		if(this.type!=null){
			this.type.unregisterConcept(this);
			this.type = type;
			this.type.registerConcept(this);
		}
	}
	
	public Set<Expense> getExpenses() {
		return expenses;
	}

	public void setExpenses(Set<Expense> expenses) {
		this.expenses = expenses;
	}
	
	public void bookExpense(Expense expense){
		this.expenses.add(expense);
	}

	@Override
	public double getBudgetedAmount() {
		return budgetedAmount;
	}

	public void setBudgetedAmount(double budgetedAmount) {
		this.budgetedAmount = budgetedAmount;
	}

	@Override
	public double getActualSpending() {
		return calculateBookedExpensesAmount();
	}

	private double calculateBookedExpensesAmount() {
		double total = 0;
		Iterator<Expense> ex = this.expenses.iterator();
		while(ex.hasNext()){
			total += ex.next().getAmount();
		}
		return total;
	}

	@Override
	public double getDifference() {
		return getBudgetedAmount() - calculateBookedExpensesAmount();
	}
}
