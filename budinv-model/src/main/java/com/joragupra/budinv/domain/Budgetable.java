package com.joragupra.budinv.domain;

public interface Budgetable {
	
	double getBudgetedAmount();
	
	double getActualSpending();
	
	double getDifference();
}
