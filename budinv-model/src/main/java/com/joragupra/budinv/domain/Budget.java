package com.joragupra.budinv.domain;

public class Budget {
	
	private double budgetedRevenue;
	
	private IrregularExpense budgetedIrregularExpense;
	
	private RegularExpense budgetedRegularExpense;
	
	private VariableExpense budgetedVariableExpense;
	
	public Budget(){
		super();
	}
	
	public double getBudgetedRevenue(){
		return this.budgetedRevenue;
	}
	
	public void setBudgetedRevenue(double budgetedRevenue){
		this.budgetedRevenue = budgetedRevenue;
	}

	public IrregularExpense getBudgetedIrregularExpense() {
		return budgetedIrregularExpense;
	}

	public void setBudgetedIrregularExpense(
			IrregularExpense budgetedIrregularExpense) {
		this.budgetedIrregularExpense = budgetedIrregularExpense;
	}

	public RegularExpense getBudgetedRegularExpense() {
		return budgetedRegularExpense;
	}

	public void setBudgetedRegularExpense(RegularExpense budgetedRegularExpense) {
		this.budgetedRegularExpense = budgetedRegularExpense;
	}

	public VariableExpense getBudgetedVariableExpense() {
		return budgetedVariableExpense;
	}

	public void setBudgetedVariableExpense(VariableExpense budgetedVariableExpense) {
		this.budgetedVariableExpense = budgetedVariableExpense;
	}
}
