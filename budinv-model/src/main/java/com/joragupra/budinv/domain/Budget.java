package com.joragupra.budinv.domain;

public class Budget {
	
	private double bugetedRevenue;
	
	private IrregularExpense budgetedIrregularExpense;
	
	private RegularExpense budgetedRegularExpense;
	
	private VariableExpense budgedVariableExpense;
	
	public Budget(){
		super();
	}
	
	public double getBudgetedRevenue(){
		return this.bugetedRevenue;
	}
	
	public void setBudgetedRevenue(double bugetedRevenue){
		this.bugetedRevenue = bugetedRevenue;
	}
	
	public double getBugetedRevenue() {
		return bugetedRevenue;
	}

	public void setBugetedRevenue(double bugetedRevenue) {
		this.bugetedRevenue = bugetedRevenue;
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

	public VariableExpense getBudgedVariableExpense() {
		return budgedVariableExpense;
	}

	public void setBudgedVariableExpense(VariableExpense budgedVariableExpense) {
		this.budgedVariableExpense = budgedVariableExpense;
	}
}
