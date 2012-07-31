package com.joragupra.budinv.domain;


public class ExpenseConcept extends DirectlyBudgetedExpenseCategory implements Budgetable {
	
	private String description;
	
	private ExpenseType type;
	
	public ExpenseConcept(String name){
		this(name, null, 0.0);
	}
	
	public ExpenseConcept(String name, double budgetedAmount){
		this(name, null, budgetedAmount);
	}
	
	public ExpenseConcept(String name, ExpenseType type, double budgetedAmount){
		super(name);
		this.type = type;
		super.setBudgetedAmount(budgetedAmount);
		if(type!=null){
			this.type.registerConcept(this);
		}
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
}
