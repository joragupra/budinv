package com.joragupra.budinv.domain;

import java.util.HashSet;
import java.util.Set;

public class ExpenseType implements Budgetable {

	private String name;

	private Set<ExpenseConcept> registeredConcepts;

	public ExpenseType(String name) {
		this.name = name;
		this.registeredConcepts = new HashSet<ExpenseConcept>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<ExpenseConcept> getRegisteredConcepts() {
		return registeredConcepts;
	}

	public void setRegisteredConcepts(Set<ExpenseConcept> registeredConcepts) {
		this.registeredConcepts = registeredConcepts;
	}

	public void registerConcept(ExpenseConcept concept) {
		if (!this.registeredConcepts.contains(concept)) {
			this.registeredConcepts.add(concept);
		}
	}
	
	public void unregisterConcept(ExpenseConcept concept){
		this.registeredConcepts.remove(concept);
	}

	@Override
	public double getBudgetedAmount() {
		return calculateBudgetForAllConcepts();
	}
	
	private double calculateBudgetForAllConcepts(){
		double budget = 0.0;
		for(ExpenseConcept concept : this.registeredConcepts){
			budget += concept.getBudgetedAmount();
		}
		return budget;
	}

	@Override
	public double getActualSpending() {
		return calculateSpendingForAllConcepts();
	}
	
	private double calculateSpendingForAllConcepts(){
		double total = 0.0;
		for(ExpenseConcept concept : this.registeredConcepts){
			total += concept.getActualSpending();
		}
		return total;
	}

	@Override
	public double getDifference() {
		return calculateBudgetForAllConcepts() - calculateSpendingForAllConcepts();
	}
}
