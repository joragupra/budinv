package com.joragupra.budinv.domain;

import java.util.HashSet;
import java.util.Set;

public class PartialBudget implements Budgetable {
	
	private Set<Budgetable> elements;
	
	public PartialBudget(){
		this.elements = new HashSet<Budgetable>();
	}

	@Override
	public double getBudgetedAmount() {
		return calculateBudgetForAllBudgetedElements();
	}
	
	private double calculateBudgetForAllBudgetedElements(){
		double budget = 0.0;
		for(Budgetable concept : this.elements){
			budget += concept.getBudgetedAmount();
		}
		return budget;
	}

	@Override
	public double getActualSpending() {
		return calculateSpendingForAllBudgetedElements();
	}
	
	private double calculateSpendingForAllBudgetedElements(){
		double total = 0.0;
		for(Budgetable concept : this.elements){
			total += concept.getActualSpending();
		}
		return total;
	}

	@Override
	public double calculateDeviation() {
		return calculateBudgetForAllBudgetedElements() - calculateSpendingForAllBudgetedElements();
	}
}
